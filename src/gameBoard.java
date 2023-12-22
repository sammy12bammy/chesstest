import java.util.ArrayList;

public class gameBoard {

    private String[][] game = new String[8][8];
    private int turn;
    private boolean madeMove;
    private boolean kingBeingMove = false;
    private boolean endGame = false;
    private ArrayList<String> capturedPieces = new ArrayList<String>();

    public gameBoard(){
        makePieces(game, "white");
        makePieces(game, "black");
        
        for(int i = 0; i < game.length; i++){
            for(int j = 0; j < game.length; j++){
                String cur = game[i][j];
                if(cur == null){
                    game[i][j] = "--";
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
        public void changePiece(String endPiece, int row, int col){
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

    private static void makePieces(String[][]arr, String color){
        try{
            if(color.equals("white")){
                arr[7][0] = "wR";
                arr[7][1] = "wN";
                arr[7][2] = "wB";
                arr[7][3] = "wK";
                arr[7][4] = "wQ";
                arr[7][5] = "wB";
                arr[7][6] = "wN";
                arr[7][7] = "wR";
                arr[6][0] = "wP";
                arr[6][1] = "wP";
                arr[6][2] = "wP";
                arr[6][3] = "wP";
                arr[6][4] = "wP";
                arr[6][5] = "wP";
                arr[6][6] = "wP";
                arr[6][7] = "wP";
            } 
            if(color.equals("black")){
                arr[0][0] = "bR";
                arr[0][1] = "bN";
                arr[0][2] = "bB";
                arr[0][3] = "bK";
                arr[0][4] = "bQ";
                arr[0][5] = "bB";
                arr[0][6] = "bN";
                arr[0][7] = "bR";
                arr[1][0] = "bP";
                arr[1][1] = "bP";
                arr[1][2] = "bP";
                arr[1][3] = "bP";
                arr[1][4] = "bP";
                arr[1][5] = "bP";
                arr[1][6] = "bP";
                arr[1][7] = "bP";
            }
        } catch(Exception e){
                System.out.println("Error with piece generation");
        }
    }
    
    public void printGame(){
        for(String[] row: game)
            {
                for(String thing: row)
                {
                    System.out.print(thing.substring(1,2) + "  ");
                }
                System.out.println();
            }
    }
    
    protected void makeMove(String start, String end){
            String piece = getPiece(start);

            int startRow = Integer.parseInt(start.substring(0,1));
            int startCol = Integer.parseInt(start.substring(1,2));
            int endRow = Integer.parseInt(end.substring(0,1));
            int endCol = Integer.parseInt(end.substring(1,2));

        String pieceColor = getPieceColor(startRow, startCol);
        madeMove = false;

        //check if valid then move


        if(isValidMove(piece, startRow, startCol, endRow, endCol)){
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
        game[startRow][startCol] = "--";
        turn++;
    }

    private void capturePiece(int srow, int scol, int erow, int ecol){      
        String curPiece = game[srow][erow];
        String curPieceColor = getPieceColor(srow, scol);
        String endPiece = game[erow][ecol];
        String endPieceColor = getPieceColor(erow, ecol);
        
        //the function will not run if the end spot is blank or if the end spot has the same color 
        if(curPiece.equals("--")){
            return;
        }
        if(curPieceColor.equals(endPieceColor)){ //CHANGE WHEN DOING CASTLING
            return;
        }

        capturedPieces.add(endPiece);

        if(curPiece.equals("Pawn")){
            if(curPieceColor.equals("white")){
                //caputure peices up only
                if(srow - 1 == erow && scol + 1 == ecol){
                    game[erow][ecol] = "--";
                }
            }
            if(curPieceColor.equals("black")){
                if(srow + 1 == erow && scol + 1 == ecol){
                    game[erow][ecol] = "--";
                }
            }
            return;
        }

        game[erow][ecol] = "--";
    }

        // returns null if no piece at the location
    private String getPieceColor(int row, int col){
        String piece = game[row][col];
        if(piece.substring(0,1).equals("w")){
                return "white";   
        } else if(piece.substring(0,1).equals("b")){
            return "black";
        } else {
            return null;
        }
    }

    private boolean isValidMove(String piece, int srow, int scol, int erow, int ecol){
            String curPieceColor = getPieceColor(srow,scol);
            String endSpotColor = getPieceColor(erow,ecol);
            //change this when castling
            if(curPieceColor.equals(endSpotColor)){
                return false;
            }

            //pawn movement      
            if(piece.equals("Pawn")){
                return pawnMoveLogic(srow, scol, erow, ecol);
            }
            //knight
            if(piece.equals("Knight")){
                return knightMoveLogic(srow, scol, erow, ecol);
            }
            //bishop
            if(piece.equals("Bishop")){                                        
                if(Math.abs(srow - erow) == Math.abs(scol - ecol) && checkDiag(srow,scol,erow,ecol)){
                    return true;
                }           
            }
            //rook
            if(piece.equals("Rook")){
                if(srow == erow || scol == ecol){
                    if(checkRow(srow,scol,erow,ecol)){
                        return true;
                    }
                }           
            }
            //queen
            if(piece.equals("Queen")){
                if(Math.abs(srow - erow) == Math.abs(scol - ecol) && checkDiag(srow,scol,erow,ecol)){
                    return true;
                } else if(srow == erow || scol == ecol){
                    if(checkRow(srow, scol, erow, ecol)){
                        return true;
                    }
                }
            }
            //king
            if(piece.equals("King")){
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
        String curPieceColor = getPieceColor(srow, scol);
        if(!game[erow][ecol].equals("--")){
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
        if(!game[erow][ecol].equals("--")){
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
                    if(!game[srow+i][scol+i].equals("--")){
                        return false;
                    }
                }
            }
            if(direction == DOWN_RIGHT){
                for(int i = 1; i <= counter; i++){
                    if(!game[srow-i][scol+i].equals("--")){
                        return false;
                    }
                }
            }
            if(direction == DOWN_LEFT){
                for(int i = 1; i <= counter; i++){
                    if(!game[srow-i][scol-i].equals("--")){
                        return false;
                    }
                }
            }
            if(direction == UP_LEFT){
                for(int i = 1; i <= counter; i++){
                    if(!game[srow+i][scol-i].equals("--")){
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
                    if(!game[srow - i][scol].equals("--")){
                        return false;
                    }
                }
            }
            if(direction == DOWN){
                for(int i = 1; i < erow - srow; i++){
                    if(!game[srow + i][scol].equals("--")){
                        return false;
                    }
                }
            }
            if(direction == RIGHT){
                for(int i = 1; i < scol - ecol; i++){
                    if(!game[srow][scol-1].equals("--")){
                        return false;
                    }
                }
            }
            if(direction == LEFT){
                for(int i = 1; i < ecol - scol; i++){
                    if(!game[srow][scol+1].equals("--")){
                        return false;
                    }
                }
            }
            
            return true;

    }
    // had to include the row and col to reuse code. type 9 to not affect parameters
    private String getPiece(String startCord){
            //makes variable names more clear and tell exactly what you are moving
        
            int firstNum = Integer.parseInt(startCord.substring(0,1));
            int secondNum = Integer.parseInt(startCord.substring(1,2));
            
            String piece = game[firstNum][secondNum];

            if(piece.substring(1,2).equals("P")){
                return "Pawn";
            }
            if(piece.substring(1,2).equals("R")){
                return "Rook";
            }
            if(piece.substring(1,2).equals("N")){
                return "Knight";
            }
            if(piece.substring(1,2).equals("B")){
                return "Bishop";
            }
            if(piece.substring(1,2).equals("Q")){
                return "Queen";
            }
            if(piece.substring(1,2).equals("K")){
                return "King";
            }

            return null;        
   }

    //checkPieceKG is a method for kingIsChecked. This checks the piece picked out from the orignal function.
    private boolean checkPieceKG(String kingColor, int row, int col){
        String curPieceColor = getPieceColor(row,col);
        String piece = getPiece(Integer.toString(row) + Integer.toString(col));

        if(curPieceColor.equals(kingColor)){
            return false;
        }

        if(piece.equals("Pawn")){
            return checkPawnCheck(curPieceColor, row, col);
        }
        if(piece.equals("Knight")){
            return checkKnightCheck(curPieceColor, row, col);
        }
        if(piece.equals("Bishop")){
            return checkBishopCheck(curPieceColor, row, col);
        }
        if(piece.equals("Rook")){
            return checkRookCheck(curPieceColor, row, col);
        }
        if(piece.equals("Queen")){
            return checkQueenCheck(curPieceColor, row, col);
        }

        return false;
    }

    private boolean checkPawnCheck(String color, int row, int col){
        if(color.equals("white")){
            if(game[row-1][col+1].equals("bK")){
                return true;
            } else if(game[row-1][col-1].equals("bK")){
                return true;
            }
        } else {
            if(game[row+1][col+1].equals("wK")){
                return true;
            } else if(game[row+1][col-1].equals("wK")){
                return true;
            }
        }

        return false;
    }

    private boolean checkKnightCheck(String color, int row, int col){
        if(color.equals("white")){
            if(game[row-2][col+1].equals("bK")){
                return true;
            }
            if(game[row-2][col-1].equals("bK")){
                return true;
            }
            if(game[row+2][col+1].equals("bK")){
                return true;
            }
            if(game[row+2][col-1].equals("bK")){
                return true;
            }
            if(game[row+1][col+2].equals("bK")){
                return true;
            }
            if(game[row-1][col+2].equals("bK")){
                return true;
            }
            if(game[row+1][col-2].equals("bK")){
                return true;
            }
            if(game[row-1][col-2].equals("bK")){
                return true;
            }
        } else {
            if(game[row-2][col+1].equals("wK")){
                return true;
            }
            if(game[row-2][col-1].equals("wK")){
                return true;
            }
            if(game[row+2][col+1].equals("wK")){
                return true;
            }
            if(game[row+2][col-1].equals("wK")){
                return true;
            }
            if(game[row+1][col+2].equals("wK")){
                return true;
            }
            if(game[row-1][col+2].equals("wK")){
                return true;
            }
            if(game[row+1][col-2].equals("wK")){
                return true;
            }
            if(game[row-1][col-2].equals("wK")){
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
            while(rowCount < 9 && colCount < 9 && game[rowCount][colCount].equals("--") || game[rowCount][colCount].equals("bK")){
                if(game[rowCount][colCount].equals("bK")){
                    return true;
                }
                rowCount--;
                colCount++;
            }
            //bottom right
            //row + col +1
            rowCount = row + 1;
            colCount = col + 1;
            while(rowCount < 9 && colCount < 9 && game[rowCount][colCount].equals("--") || game[rowCount][colCount].equals("bK")){
                if(game[rowCount][colCount].equals("bK")){
                    return true;
                }
                rowCount++;
                colCount++;
            }
            //bottom left
            //row + col -
            rowCount = row + 1;
            colCount = col - 1;
            while(rowCount < 9 && colCount < 9 && game[rowCount][colCount].equals("--") || game[rowCount][colCount].equals("bK")){
                if(game[rowCount][colCount].equals("bK")){
                    return true;
                }
                rowCount++;
                colCount--;
            }
            //Top Left
            //row - col -
            rowCount = row - 1;
            colCount = col - 1;
            while(rowCount < 9 && colCount < 9 && game[rowCount][colCount].equals("--") || game[rowCount][colCount].equals("bK")){
                if(game[rowCount][colCount].equals("bK")){
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
            while(rowCount < 9 && colCount < 9 && game[rowCount][colCount].equals("--") || game[rowCount][colCount].equals("wK")){
                if(game[rowCount][colCount].equals("wK")){
                    return true;
                }
                rowCount--;
                colCount++;
            }
            //bottom right
            //row + col +1
            rowCount = row + 1;
            colCount = col + 1;
            while(rowCount < 9 && colCount < 9 && game[rowCount][colCount].equals("--") || game[rowCount][colCount].equals("wK")){
                if(game[rowCount][colCount].equals("wK")){
                    return true;
                }
                rowCount++;
                colCount++;
            }
            //bottom left
            //row + col -
            rowCount = row + 1;
            colCount = col - 1;
            while(rowCount < 9 && colCount < 9 && game[rowCount][colCount].equals("--") || game[rowCount][colCount].equals("wK")){
                if(game[rowCount][colCount].equals("wK")){
                    return true;
                }
                rowCount++;
                colCount--;
            }
            //Top Left
            //row - col -
            rowCount = row - 1;
            colCount = col - 1;
            while(rowCount < 9 && colCount < 9 && game[rowCount][colCount].equals("--") || game[rowCount][colCount].equals("wK")){
                if(game[rowCount][colCount].equals("wK")){
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
            while(rowCount < 9 && colCount < 9 && game[rowCount][colCount].equals("--") || game[rowCount][colCount].equals("bK")){
                if(game[rowCount][colCount].equals("bK")){
                    return true;
                }
                rowCount--;
            }
            //down
            rowCount = row + 1;
            colCount = col;
            while(rowCount < 9 && colCount < 9 && game[rowCount][colCount].equals("--") || game[rowCount][colCount].equals("bK")){
                if(game[rowCount][colCount].equals("bK")){
                    return true;
                }
                rowCount++;
            }
            //right
            rowCount = row;
            colCount = col + 1;
            while(rowCount < 9 && colCount < 9 && game[rowCount][colCount].equals("--") || game[rowCount][colCount].equals("bK")){
                if(game[rowCount][colCount].equals("bK")){
                    return true;
                }
                colCount++;
            }
            //left
            rowCount = row;
            colCount = col - 1;
            while(rowCount < 9 && colCount < 9 && game[rowCount][colCount].equals("--") || game[rowCount][colCount].equals("bK")){
                if(game[rowCount][colCount].equals("bK")){
                    return true;
                }
                colCount--;
            }
        } else {
            //up
            rowCount = row - 1;
            colCount = col;
            while(rowCount < 9 && colCount < 9 && game[rowCount][colCount].equals("--") || game[rowCount][colCount].equals("wK")){
                if(game[rowCount][colCount].equals("wK")){
                    return true;
                }
                rowCount--;
            }
            //down
            rowCount = row + 1;
            colCount = col;
            while(rowCount < 9 && colCount < 9 && game[rowCount][colCount].equals("--") || game[rowCount][colCount].equals("wK")){
                if(game[rowCount][colCount].equals("wK")){
                    return true;
                }
                rowCount++;
            }
            //right
            rowCount = row;
            colCount = col + 1;
            while(rowCount < 9 && colCount < 9 && game[rowCount][colCount].equals("--") || game[rowCount][colCount].equals("wK")){
                if(game[rowCount][colCount].equals("wK")){
                    return true;
                }
                colCount++;
            }
            //left
            rowCount = row;
            colCount = col - 1;
            while(rowCount < 9 && colCount < 9 && game[rowCount][colCount].equals("--") || game[rowCount][colCount].equals("wK")){
                if(game[rowCount][colCount].equals("wK")){
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
                    if(game[i][j].equals("wK")){
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
                    if(game[i][j].equals("bK")){
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
            String start = getPiece(Integer.toString(srow) + Integer.toString(scol));
            String end = getPiece(Integer.toString(erow) + Integer.toString(ecol));
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