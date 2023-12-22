import java.util.ArrayList;

public class gameBoard {

    private Piece[][] game = new Piece[8][8];
    private int turn;
    private boolean madeMove;
    private boolean kingBeingMove = false;
    private boolean endGame = false;
    private ArrayList<Piece> capturedPieces = new ArrayList<Piece>();

    public gameBoard(){
        makePieces(game, "white");
        makePieces(game, "black");
        
        for(int i = 0; i < game.length; i++){
            for(int j = 0; j < game.length; j++){
                Piece cur = game[i][j];
                if(cur == null){
                    game[i][j] = new Piece("--", "na", i, j);
                }
            }
        }

            turn = 0;
    }

        public boolean getEndGame(){
            return endGame;
        }

        public int getTurn(){
            return turn;
        }

        public boolean getMoveBol(){
            return madeMove;
        }
        //for debug
        public void changePiece(Piece endPiece, int row, int col){
            game[row][col] = endPiece;
        }

        public void printCapturedPiece(){
            if(capturedPieces.size() == 0){
                System.out.println("No captured pieces yet");
                return;
            }
            
            for(int i = 0; i < capturedPieces.size(); i++){
                System.out.println(capturedPieces.get(i) + " ");
            }
        }

    private static void makePieces(Piece[][]arr, String color){
        try{
            if(color.equals("white")){
                arr[7][0] = new Piece("rook", "white", 7, 0);
                arr[7][1] = new Piece("knight", "white", 7, 1);
                arr[7][2] = new Piece("bishop", "white", 7, 2);
                arr[7][3] = new Piece("king", "white", 7, 3);
                arr[7][4] = new Piece("queen", "white", 7, 4);
                arr[7][5] = new Piece("bishop", "white", 7, 5);
                arr[7][6] = new Piece("knight", "white", 7, 6);
                arr[7][7] = new Piece("rook", "white", 7, 7);

                arr[6][0] = new Piece("pawn", "white", 6, 0);
                arr[6][1] = new Piece("pawn", "white", 6, 0);
                arr[6][2] = new Piece("pawn", "white", 6, 0);
                arr[6][3] = new Piece("pawn", "white", 6, 0);
                arr[6][4] = new Piece("pawn", "white", 6, 0);
                arr[6][5] = new Piece("pawn", "white", 6, 0);
                arr[6][6] = new Piece("pawn", "white", 6, 0);
                arr[6][7] = new Piece("pawn", "white", 6, 0);
            } 
            if(color.equals("black")){
                arr[0][0] = new Piece("rook", "black", 0, 0);
                arr[0][1] = new Piece("knight", "black", 0, 1);
                arr[0][2] = new Piece("bishop", "black", 0, 2);
                arr[0][3] = new Piece("king", "black", 0, 3);
                arr[0][4] = new Piece("queen", "black", 0, 4);
                arr[0][5] = new Piece("bishop", "black", 0, 5);
                arr[0][6] = new Piece("knight", "black",0 , 6);
                arr[0][7] = new Piece("rook", "black", 0, 7);

                arr[1][0] = new Piece("pawn", "black", 1, 0);
                arr[1][1] = new Piece("pawn", "black", 1, 1);
                arr[1][2] = new Piece("pawn", "black", 1, 2);
                arr[1][3] = new Piece("pawn", "black", 1, 3);
                arr[1][4] = new Piece("pawn", "black", 1, 4);
                arr[1][5] = new Piece("pawn", "black", 1, 5);
                arr[1][6] = new Piece("pawn", "black", 1, 6);
                arr[1][7] = new Piece("pawn", "black", 1, 7);
            }
        } catch(Exception e){
                System.out.println("Error with piece generation");
        }
    }
    
    public void printGame(){
        for(Piece[] row: game)
            {
                for(Piece thing: row)
                {
                    System.out.print(thing + "  ");
                }
                System.out.println();
            }
    }
     
    
    protected void makeMove(String start, String end){

            int startRow = Integer.parseInt(start.substring(0,1));
            int startCol = Integer.parseInt(start.substring(1,2));
            int endRow = Integer.parseInt(end.substring(0,1));
            int endCol = Integer.parseInt(end.substring(1,2));

        String pieceColor = game[startRow][startCol].getColor();
        madeMove = false;

        //check if valid then move
        if(isValidMove(game[startRow][startCol], endRow, endCol)){
            if(turn % 2 == 0 && pieceColor.equals("white")){      
                if(checkPieceKG("white", startRow, startCol) && !kingBeingMove){
                    makeKingMove("white", startRow, startCol, endRow, endCol);
                } else{
                    moveMakingExecution(startRow, startCol, endRow, endCol);
                }         
            }
            if(turn % 2 == 1 && pieceColor.equals("black")){      
                moveMakingExecution(startRow, startCol, endRow, endCol);
            }
        } else {
            if(kingBeingMove){
                checkMate(pieceColor);
            } else{
                System.out.println("Not valid move");
            }
        }
        
    }

    private void moveMakingExecution(int startRow, int startCol, int endRow, int endCol){
        capturePiece(startRow, startCol, endRow, endCol);
        game[endRow][endCol] = game[startRow][startCol];
        game[startRow][startCol] = new Piece("--", "na", startRow, startCol);
        turn++;
    }

    private void capturePiece(int srow, int scol, int erow, int ecol){      
        Piece curPiece = game[srow][erow];
        String curPieceColor = curPiece.getColor();
        Piece endPiece = game[erow][ecol];
        String endPieceColor = endPiece.getColor();
        
        //the function will not run if the end spot is blank or if the end spot has the same color 
        if(curPiece.getType().equals("--")){
            return;
        }
        if(curPieceColor.equals(endPieceColor)){ //CHANGE WHEN DOING CASTLING
            return;
        }

        capturedPieces.add(endPiece);

        if(curPiece.getType().equals("pawn")){
            if(curPieceColor.equals("white")){
                //caputure peices up only
                if(srow - 1 == erow && scol + 1 == ecol){
                    game[erow][ecol] = new Piece("--", "na", erow, ecol);
                }
            }
            if(curPieceColor.equals("black")){
                if(srow + 1 == erow && scol + 1 == ecol){
                    game[erow][ecol] = new Piece("--", "na", erow, ecol);
                }
            }
            return;
        }

        game[erow][ecol] = new Piece("--", "na", erow, ecol);
    }

    private boolean isValidMove(Piece piece, int erow, int ecol){
            String curPieceColor = piece.getColor();
            String endSpotColor = game[erow][ecol].getColor();
            int srow = piece.getRow();
            int scol = piece.getCol();
            //change this when castling
            if(curPieceColor.equals(endSpotColor)){
                return false;
            }

            //pawn movement      
            if(piece.getType().equals("pawn")){
                return pawnMoveLogic(srow, scol, erow, ecol);
            }
            //knight
            if(piece.getType().equals("knight")){
                return knightMoveLogic(srow, scol, erow, ecol);
            }
            //bishop
            if(piece.getType().equals("bishop")){                                        
                if(Math.abs(srow - erow) == Math.abs(scol - ecol) && checkDiag(srow,scol,erow,ecol)){
                    return true;
                }           
            }
            //rook
            if(piece.getType().equals("rook")){
                if(srow == erow || scol == ecol){
                    if(checkRow(srow,scol,erow,ecol)){
                        return true;
                    }
                }           
            }
            //queen
            if(piece.getType().equals("queen")){
                if(Math.abs(srow - erow) == Math.abs(scol - ecol) && checkDiag(srow,scol,erow,ecol)){
                    return true;
                } else if(srow == erow || scol == ecol){
                    if(checkRow(srow, scol, erow, ecol)){
                        return true;
                    }
                }
            }
            //king
            if(piece.getType().equals("king")){
                return kingMoveLogic(srow, scol, erow, ecol);
            }

            return false;
    }

    private boolean pawnMoveLogic(int srow, int scol, int erow, int ecol){
        if(srow == 1 || srow == 6){
            if(scol == ecol){
            //checks if rows are good
                if(srow - erow == 1 || erow - srow == 1 || srow - erow == 2 || erow - srow == 2){
                    return true;
                } 
            } 
        } else{           
            if(scol == ecol){
            //checks if rows are good
                if(srow - erow == 1 || erow - srow == 1){
                    return true;
                } 
            } 
        }
        //has to be a piece of other color
        String curPieceColor = game[srow][scol].getColor();
        if(!game[erow][ecol].getType().equals("--")){
            if(curPieceColor.equals("white")){
                //caputure peices up only
                if(srow - 1 == erow && scol + 1 == ecol){
                    return true;
                }
            }
            if(curPieceColor.equals("black")){
                if(srow + 1 == erow && scol + 1 == ecol){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean knightMoveLogic(int srow, int scol, int erow, int ecol){
        if(srow + 2 == erow && scol - 1 == ecol){
            return true;
        } else if(srow + 2 == erow && scol + 1 == ecol){
            return true;
        } else if(scol + 2 == ecol && srow + 1 == erow){
            return true;
        } else if(scol + 2 == ecol && srow - 1 == erow){
            return true;
        } else if(srow - 2 == erow && scol - 1 == ecol){
            return true;
        } else if(srow - 2 == erow && scol + 1 == ecol){
            return true;
        } else if(scol - 2 == ecol && srow + 1 == erow){
            return true;
        } else if(scol - 2 == ecol && srow - 1 == erow){
            return true;
        } 
        return false;
    }

    private boolean kingMoveLogic(int srow, int scol, int erow, int ecol){
        if(!game[erow][ecol].getType().equals("--")){
            return false;
        }

        if(Math.abs(srow - erow) == 1){
            return true;
        }

        if(Math.abs(scol - ecol) == 1){
            return true;
        }

        return false;
    }

    private boolean checkDiag(int srow, int scol, int erow, int ecol){          
            int counter = srow - erow - 1;
            final int UP_RIGHT = 0;
            final int DOWN_RIGHT = 1;
            final int DOWN_LEFT = 2;
            final int UP_LEFT = 3;
            int direction;

            if(srow > erow && scol < ecol){
                direction = UP_RIGHT;
            } else if(srow < erow && scol < ecol){
                direction = DOWN_RIGHT;
            } else if (srow < erow && scol > ecol){
                direction = DOWN_LEFT;
            } else {
                direction = UP_LEFT;
            }

            if(direction == UP_RIGHT){
                for(int i = 1; i <= counter; i++){
                    if(!game[srow+i][scol+i].getType().equals("--")){
                        return false;
                    }
                }
            }
            if(direction == DOWN_RIGHT){
                for(int i = 1; i <= counter; i++){
                    if(!game[srow-i][scol+i].getType().equals("--")){
                        return false;
                    }
                }
            }
            if(direction == DOWN_LEFT){
                for(int i = 1; i <= counter; i++){
                    if(!game[srow-i][scol-i].getType().equals("--")){
                        return false;
                    }
                }
            }
            if(direction == UP_LEFT){
                for(int i = 1; i <= counter; i++){
                    if(!game[srow+i][scol-i].getType().equals("--")){
                        return false;
                    }
                }
            }

            return true;
    }

    private boolean checkRow(int srow, int scol, int erow, int ecol){
            @SuppressWarnings("unused")

            int counter;
            final int UP = 0;
            final int RIGHT = 1;
            final int DOWN = 2;
            final int LEFT = 3;
            int direction;
                
            if(srow > erow){
                counter = srow-erow;
                direction = UP;
            } else if(srow < erow){
                counter = erow-srow;
                direction = DOWN;
            } else if(scol > ecol){
                counter = scol - ecol;
                direction = LEFT;
            } else {
                counter = ecol -scol;
                direction = RIGHT;
            }

            if(direction == UP){
                for(int i = 1; i < srow - erow; i++){
                    if(!game[srow - i][scol].getType().equals("--")){
                        return false;
                    }
                }
            }
            if(direction == DOWN){
                for(int i = 1; i < erow - srow; i++){
                    if(!game[srow + i][scol].getType().equals("--")){
                        return false;
                    }
                }
            }
            if(direction == RIGHT){
                for(int i = 1; i < scol - ecol; i++){
                    if(!game[srow][scol-1].getType().equals("--")){
                        return false;
                    }
                }
            }
            if(direction == LEFT){
                for(int i = 1; i < ecol - scol; i++){
                    if(!game[srow][scol+1].getType().equals("--")){
                        return false;
                    }
                }
            }
            
            return true;

    }
    // had to include the row and col to reuse code. type 9 to not affect parameters

    //checkPieceKG is a method for kingIsChecked. This checks the piece picked out from the orignal function.
    private boolean checkPieceKG(String kingColor, int row, int col){
        String curPieceColor = game[row][col].getColor();
        String pieceType = game[row][col].getType();

        if(curPieceColor.equals(kingColor)){
            return false;
        }

        if(pieceType.equals("pawn")){
            return checkPawnCheck(curPieceColor, row, col);
        }
        if(pieceType.equals("knight")){
            return checkKnightCheck(curPieceColor, row, col);
        }
        if(pieceType.equals("bishop")){
            return checkBishopCheck(curPieceColor, row, col);
        }
        if(pieceType.equals("rook")){
            return checkRookCheck(curPieceColor, row, col);
        }
        if(pieceType.equals("queen")){
            return checkQueenCheck(curPieceColor, row, col);
        }

        return false;
    }

    private boolean checkPawnCheck(String color, int row, int col){
        if(color.equals("white")){
            if(game[row-1][col+1].getType().equals("king") && game[row-1][col+1].getColor().equals("black")){
                return true;
            } else if(game[row-1][col-1].getType().equals("king") && game[row-1][col-1].getColor().equals("black")){
                return true;
            }
        } else {
            if(game[row+1][col+1].getType().equals("king") && game[row+1][col+1].getColor().equals("white")){
                return true;
            } else if(game[row+1][col-1].getType().equals("king") && game[row+1][col-1].getColor().equals("white")){
                return true;
            }
        }

        return false;
    }

    private boolean checkKnightCheck(String color, int row, int col){
        if(color.equals("white")){
            if(game[row-2][col+1].getType().equals("king") && game[row-2][col+1].getColor().equals("black")){
                return true;
            }
            if(game[row-2][col-1].getType().equals("king")&& game[row-2][col-1].getColor().equals("black")){
                return true;
            }
            if(game[row+2][col+1].getType().equals("king")&& game[row+2][col+1].getColor().equals("black")){
                return true;
            }
            if(game[row+2][col-1].getType().equals("king")&& game[row+2][col-1].getColor().equals("black")){
                return true;
            }
            if(game[row+1][col+2].getType().equals("king")&& game[row+1][col+2].getColor().equals("black")){
                return true;
            }
            if(game[row-1][col+2].getType().equals("king")&& game[row-1][col+2].getColor().equals("black")){
                return true;
            }
            if(game[row+1][col-2].getType().equals("king")&& game[row+1][col-2].getColor().equals("black")){
                return true;
            }
            if(game[row-1][col-2].getType().equals("king")&& game[row-1][col-2].getColor().equals("black")){
                return true;
            }
        } else {
            if(game[row-2][col+1].getType().equals("king") && game[row-2][col+1].getColor().equals("white")){
                return true;
            }
            if(game[row-2][col-1].getType().equals("king")&& game[row-2][col-1].getColor().equals("white")){
                return true;
            }
            if(game[row+2][col+1].getType().equals("king")&& game[row+2][col+1].getColor().equals("white")){
                return true;
            }
            if(game[row+2][col-1].getType().equals("king")&& game[row+2][col-1].getColor().equals("white")){
                return true;
            }
            if(game[row+1][col+2].getType().equals("king")&& game[row+1][col+2].getColor().equals("white")){
                return true;
            }
            if(game[row-1][col+2].getType().equals("king")&& game[row-1][col+2].getColor().equals("white")){
                return true;
            }
            if(game[row+1][col-2].getType().equals("king")&& game[row+1][col-2].getColor().equals("white")){
                return true;
            }
            if(game[row-1][col-2].getType().equals("king")&& game[row-1][col-2].getColor().equals("white")){
                return true;
            }
        }   
        return false;        
    }

    private boolean checkBishopCheck(String color, int row, int col){
        int rowCount;
        int colCount;
        
        if(color.equals("white")){
            //goes until it reaches a piece of same color
            //up right
            //row -  col + 
            rowCount = row - 1;
            colCount = col + 1;
            while(rowCount < 9 && colCount < 9 && game[rowCount][colCount].getType().equals("--") || rowCount < 9 && colCount < 9 && game[rowCount][colCount].getType().equals("--") || game[rowCount][colCount].getType().equals("king") && game[rowCount][colCount].getColor().equals("black")){
                if(game[rowCount][colCount].getType().equals("king") && game[rowCount][colCount].getColor().equals("black")){
                    return true;
                }
                rowCount--;
                colCount++;
            }
            //bottom right
            //row + col +1
            rowCount = row + 1;
            colCount = col + 1;
            while(rowCount < 9 && colCount < 9 && game[rowCount][colCount].getType().equals("--") || game[rowCount][colCount].getType().equals("king") && game[rowCount][colCount].getColor().equals("black")){
                if(game[rowCount][colCount].getType().equals("king") && game[rowCount][colCount].getColor().equals("black")){
                    return true;
                }
                rowCount++;
                colCount++;
            }
            //bottom left
            //row + col -
            rowCount = row + 1;
            colCount = col - 1;
            while(rowCount < 9 && colCount < 9 && game[rowCount][colCount].getType().equals("--") || game[rowCount][colCount].getType().equals("king") && game[rowCount][colCount].getColor().equals("black")){
                if(game[rowCount][colCount].getType().equals("king") && game[rowCount][colCount].getColor().equals("black")){
                    return true;
                }
                rowCount++;
                colCount--;
            }
            //Top Left
            //row - col -
            rowCount = row - 1;
            colCount = col - 1;
            while(rowCount < 9 && colCount < 9 && game[rowCount][colCount].getType().equals("--") || game[rowCount][colCount].getType().equals("king") && game[rowCount][colCount].getColor().equals("black")){
                if(game[rowCount][colCount].getType().equals("king") && game[rowCount][colCount].getColor().equals("black")){
                    return true;
                }
                rowCount--;
                colCount--;
            }

        } else {
            //up right
            //row -  col + 
            rowCount = row - 1;
            colCount = col + 1;
            while(rowCount < 9 && colCount < 9 && game[rowCount][colCount].getType().equals("--") || game[rowCount][colCount].getType().equals("king") && game[rowCount][colCount].getColor().equals("white")){
                if(game[rowCount][colCount].getType().equals("king") && game[rowCount][colCount].getColor().equals("white")){
                    return true;
                }
                rowCount--;
                colCount++;
            }
            //bottom right
            //row + col +1
            rowCount = row + 1;
            colCount = col + 1;
            while(rowCount < 9 && colCount < 9 && game[rowCount][colCount].getType().equals("--") || game[rowCount][colCount].getType().equals("king") && game[rowCount][colCount].getColor().equals("white")){
                if(game[rowCount][colCount].getType().equals("king") && game[rowCount][colCount].getColor().equals("white")){
                    return true;
                }
                rowCount++;
                colCount++;
            }
            //bottom left
            //row + col -
            rowCount = row + 1;
            colCount = col - 1;
            while(rowCount < 9 && colCount < 9 && game[rowCount][colCount].getType().equals("--") || game[rowCount][colCount].getType().equals("king") && game[rowCount][colCount].getColor().equals("white")){
                if(game[rowCount][colCount].getType().equals("king") && game[rowCount][colCount].getColor().equals("white")){
                    return true;
                }
                rowCount++;
                colCount--;
            }
            //Top Left
            //row - col -
            rowCount = row - 1;
            colCount = col - 1;
            while(rowCount < 9 && colCount < 9 && game[rowCount][colCount].getType().equals("--") || game[rowCount][colCount].getType().equals("king") && game[rowCount][colCount].getColor().equals("white")){
                if(game[rowCount][colCount].getType().equals("king") && game[rowCount][colCount].getColor().equals("white")){
                    return true;
                }
                rowCount--;
                colCount--;
            }
        }

        return false;
    }

    private boolean checkRookCheck(String color, int row, int col){
        int rowCount;
        int colCount;
        
        if(color.equals("white")){
            //up
            rowCount = row - 1;
            colCount = col;
            while(rowCount < 9 && colCount < 9 && game[rowCount][colCount].getType().equals("--") || game[rowCount][colCount].getType().equals("king") && game[rowCount][colCount].getColor().equals("black")){
                if(game[rowCount][colCount].getType().equals("king") && game[rowCount][colCount].getColor().equals("black")){
                    return true;
                }
                rowCount--;
            }
            //down
            rowCount = row + 1;
            colCount = col;
            while(rowCount < 9 && colCount < 9 && game[rowCount][colCount].getType().equals("--") || game[rowCount][colCount].getType().equals("king") && game[rowCount][colCount].getColor().equals("black")){
                if(game[rowCount][colCount].getType().equals("king") && game[rowCount][colCount].getColor().equals("black")){
                    return true;
                }
                rowCount++;
            }
            //right
            rowCount = row;
            colCount = col + 1;
            while(rowCount < 9 && colCount < 9 && game[rowCount][colCount].getType().equals("--") || game[rowCount][colCount].getType().equals("king") && game[rowCount][colCount].getColor().equals("black")){
                if(game[rowCount][colCount].getType().equals("king") && game[rowCount][colCount].getColor().equals("black")){
                    return true;
                }
                colCount++;
            }
            //left
            rowCount = row;
            colCount = col - 1;
            while(rowCount < 9 && colCount < 9 && game[rowCount][colCount].getType().equals("--") || game[rowCount][colCount].getType().equals("king") && game[rowCount][colCount].getColor().equals("black")){
                if(game[rowCount][colCount].getType().equals("king") && game[rowCount][colCount].getColor().equals("black")){
                    return true;
                }
                colCount--;
            }
        } else {
            //up
            rowCount = row - 1;
            colCount = col;
            while(rowCount < 9 && colCount < 9 && game[rowCount][colCount].getType().equals("--") || game[rowCount][colCount].getType().equals("king") && game[rowCount][colCount].getColor().equals("white")){
                if(game[rowCount][colCount].getType().equals("king") && game[rowCount][colCount].getColor().equals("white")){
                    return true;
                }
                rowCount--;
            }
            //down
            rowCount = row + 1;
            colCount = col;
            while(rowCount < 9 && colCount < 9 && game[rowCount][colCount].getType().equals("--") || game[rowCount][colCount].getType().equals("king") && game[rowCount][colCount].getColor().equals("white")){
                if(game[rowCount][colCount].getType().equals("king") && game[rowCount][colCount].getColor().equals("white")){
                    return true;
                }
                rowCount++;
            }
            //right
            rowCount = row;
            colCount = col + 1;
            while(rowCount < 9 && colCount < 9 && game[rowCount][colCount].getType().equals("--") || game[rowCount][colCount].getType().equals("king") && game[rowCount][colCount].getColor().equals("white")){
                if(game[rowCount][colCount].getType().equals("king") && game[rowCount][colCount].getColor().equals("white")){
                    return true;
                }
                colCount++;
            }
            //left
            rowCount = row;
            colCount = col - 1;
            while(rowCount < 9 && colCount < 9 && game[rowCount][colCount].getType().equals("--") || game[rowCount][colCount].getType().equals("king") && game[rowCount][colCount].getColor().equals("white")){
                if(game[rowCount][colCount].getType().equals("king") && game[rowCount][colCount].getColor().equals("white")){
                    return true;
                }
                colCount--;
            }
        }
        
        return false;
    }

    private boolean checkQueenCheck(String color, int row, int col){
        if(checkBishopCheck(color, row, col)){
            return true;
        }
        if(checkRookCheck(color, row, col)){
            return true;
        }
        return false;
    }

    private void makeKingMove(String color, int srow, int scol, int erow, int ecol){
        kingBeingMove = true;
        //find king position
        //check if the srow match
        //run makeMove if matches
        int kingRow;
        int kingCol;
        if(color.equals("white")){
            for(int i = 0; i < game.length; i++){
                for(int j = 0; i < game[j].length; j++){
                    if(game[i][j].getType().equals("king") && game[i][j].getColor().equals("white")){
                        kingRow = i;
                        kingCol = j;
                        break;
                    }
                }
            }
            kingRow = 9;
            kingCol = 9;
        } else {
            for(int i = 0; i < game.length; i++){
                for(int j = 0; i < game[j].length; j++){
                    if(game[i][j].getType().equals("king") && game[i][j].getColor().equals("white")){
                        kingRow = i;
                        kingCol = j;
                        break;
                    }
                }
            }
            kingRow = 9;
            kingCol = 9;
        }
        
        
        if(srow == kingRow && scol == kingCol){
            String start = game[srow][scol].getType();
            String end = game[erow][ecol].getType();
            makeMove(start, end);

        }

        kingBeingMove = false;
            

    }

    public void checkMate(String color){
        if(color.equals("white")){
            System.out.println("Black has won");
            endGame = true;
        }
    }
    

}