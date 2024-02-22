import java.util.ArrayList;

public class validMoves {
    /**
     * Checks if the move is a valid move by taking in coordiates and passing them through smaller functions
     * PostCondition: returns true or false, based on if the move can be executed
     * 
     * @param piece
     * @param erow
     * @param ecol
     * @return
     */
    public static boolean returnValMove(Piece[][] grid, int srow, int scol, int erow, int ecol){
        Piece sPiece = grid[srow][scol];
        Piece ePiece = grid[erow][ecol];
        if(sPiece == null){
            return false;
        } 
           
        if(ePiece != null && sPiece.getColor().equals(ePiece.getColor())){
                return false;
        }

            //pawn movement      
            if(sPiece.getType().equals("pawn")){
                return pawnMoveLogic(srow, scol, erow, ecol);
                
            }
            //knight
            if(sPiece.getType().equals("knight")){
                return knightMoveLogic(srow, scol, erow, ecol);
            }
            //bishop
            
            if(sPiece.getType().equals("bishop")){                                        
                if(Math.abs(srow - erow) == Math.abs(scol - ecol) && checkDiag(grid, srow,scol,erow,ecol)){
                    return true;
                }           
            }
            //rook
            if(sPiece.getType().equals("rook")){
                if(srow == erow || scol == ecol){
                    if(checkRow(grid, srow,scol,erow,ecol)){
                        return true;
                    }
                }           
            }
            //queen
            if(sPiece.getType().equals("queen")){
                if(Math.abs(srow - erow) == Math.abs(scol - ecol) && checkDiag(grid, srow,scol,erow,ecol)){
                    return true;
                } else if(srow == erow || scol == ecol){
                    if(checkRow(grid, srow, scol, erow, ecol)){
                        return true;
                    }
                }
            }
            
            //king
            if(sPiece.getType().equals("king")){
                return kingMoveLogic(grid, srow, scol, erow, ecol);
            }

            return false;
    }

    public static boolean pawnMoveLogic(int srow, int scol, int erow, int ecol){
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
            if(Math.abs(scol - ecol) == 1 && Math.abs(srow - erow) == 1){
                return true;
            } 
        }
        return false;
    }

    public static boolean knightMoveLogic(int srow, int scol, int erow, int ecol){
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

    public static boolean checkDiag(Piece[][] game, int srow, int scol, int erow, int ecol){          
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
                if(srow+i < 8 && scol+i < 8 && !game[srow+i][scol+i].getType().equals("--")){
                    return false;
                }             
            }
        }
        if(direction == DOWN_RIGHT){
            for(int i = 1; i <= counter; i++){
                if(srow-i >= 0 && scol+i < 8 && !game[srow-i][scol+i].getType().equals("--")){
                    return false;
                }
            }
        }
        if(direction == DOWN_LEFT){
            for(int i = 1; i <= counter; i++){
                if(srow-i >= 0 && scol-i >= 8 && !game[srow-i][scol-i].getType().equals("--")){
                    return false;
                }
            }
        }
        if(direction == UP_LEFT){
            for(int i = 1; i <= counter; i++){
                if(srow+i < 8 && scol-i >= 0 && !game[srow+i][scol-i].getType().equals("--")){
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean checkRow(Piece[][] game, int srow, int scol, int erow, int ecol){
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

    public static boolean kingMoveLogic(Piece[][] game, int srow, int scol, int erow, int ecol){
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
/*
 * Start of checking for checks
 */
    public static boolean pieceThatMovedIsCheckingKing(Piece[][] game, String color,int row, int col){
        String curPieceColor = game[row][col].getColor();
        String pieceType = game[row][col].getType();

        if(pieceType.equals("pawn")){
            return checkPawnCheck(game, curPieceColor, row, col);
        }
        if(pieceType.equals("knight")){
            return checkKnightCheck(game, curPieceColor, row, col);
        }
         
        if(pieceType.equals("bishop")){
            return checkBishopCheck(game, curPieceColor, row, col);
        }
        /* 
        if(pieceType.equals("rook")){
            return checkRookCheck(game, curPieceColor, row, col);
        }
        if(pieceType.equals("queen")){
            return checkQueenCheck(game, curPieceColor, row, col);
        }
        */
        

        return false;
    }

    public static boolean checkPawnCheck(Piece[][] game, String color, int row, int col){
        if(color.equals("white")){
            Piece whiteRight = game[row-1][col+1];
            Piece whiteLeft = game[row-1][col-1];
            if(boundPawnCheck(row, col) && whiteRight.getType().equals("king") && whiteRight.getColor().equals("black")){
                return true;
            } else if(boundPawnCheck(row, col) && whiteLeft.getType().equals("king") && whiteLeft.getColor().equals("black")){
                return true;
            } else {
                return false;
            }
        } else {
            Piece blackRight = game[row+1][col+1];
            Piece blackLeft = game[row+1][col-1];
            if(boundPawnCheck(row, col) && blackRight.getType().equals("king") && blackRight.getColor().equals("white")){
                return true;
            } else if(boundPawnCheck(row, col) && blackLeft.getType().equals("king") && blackLeft.getColor().equals("white")){
                return true;
            } else {
                return false;
            }
        }
    }

    public static boolean boundPawnCheck(int row, int col){
        if(row >= 8 || row < 0){
            return false;
        } else if(col >= 8 || col < 0){
            return false;
        } else {
            return true;
        }
    }

    public static boolean checkKnightCheck(Piece[][] game, String color, int row, int col){
        System.out.println("Got to knight check");
        if(color.equals("white")){
            int rowC = row -2;
            int colC = col+ 1;
            System.out.println("Checked Row: " + rowC + " Col: " + colC + ". Piece at spot: " + game[row-2][col+1].getType());
            if(knightBoundsCheck(row-2, col+1) && game[row-2][col+1].getType().equals("king") && game[row-2][col+1].getColor().equals("black")){
                return true;
            }
            rowC = row -2;
            colC = col -1;
            System.out.println("Checked Row: " + rowC + " Col: " + colC + ". Piece at spot: " + game[rowC][colC].getType());
            if(knightBoundsCheck(row-2, col-1) && game[row-2][col-1].getType().equals("king")&& game[row-2][col-1].getColor().equals("black")){
                return true;
            }
            rowC = row +2;
            colC = col +1;
            System.out.println("Checked Row: " + rowC + " Col: " + colC + ". Piece at spot: " + game[rowC][colC].getType());
            if(knightBoundsCheck(row+2, col+1) && game[row+2][col+1].getType().equals("king")&& game[row+2][col+1].getColor().equals("black")){
                return true;
            }
            rowC = row +2;
            colC = col -1;
            System.out.println("Checked Row: " + rowC + " Col: " + colC + ". Piece at spot: " + game[rowC][colC].getType());
            if(knightBoundsCheck(row+2, col-1) && game[row+2][col-1].getType().equals("king")&& game[row+2][col-1].getColor().equals("black")){
                return true;
            }
            rowC = row +1;
            colC = col +2;
            System.out.println("Checked Row: " + rowC + " Col: " + colC + ". Piece at spot: " + game[rowC][colC].getType());
            if(knightBoundsCheck(row+1, col+2) && game[row+1][col+2].getType().equals("king")&& game[row+1][col+2].getColor().equals("black")){
                return true;
            }
            rowC = row -1;
            colC = col +2;
            System.out.println("Checked Row: " + rowC + " Col: " + colC + ". Piece at spot: " + game[rowC][colC].getType());
            if(knightBoundsCheck(row-1, col+2) && game[row-1][col+2].getType().equals("king")&& game[row-1][col+2].getColor().equals("black")){
                return true;
            }
            rowC = row +1;
            colC = col -2;
            System.out.println("Checked Row: " + rowC + " Col: " + colC + ". Piece at spot: " + game[rowC][colC].getType());
            if(knightBoundsCheck(row+1, col-2) && game[row+1][col-2].getType().equals("king")&& game[row+1][col-2].getColor().equals("black")){
                return true;
            }
            rowC = row -1;
            colC = col -2;
            System.out.println("Checked Row: " + rowC + " Col: " + colC + ". Piece at spot: " + game[rowC][colC].getType());
            if(knightBoundsCheck(row-1, col-2) && game[row-1][col-2].getType().equals("king")&& game[row-1][col-2].getColor().equals("black")){
                return true;
            }
        } else {
            if(knightBoundsCheck(row-2, col+1) && game[row-2][col+1].getType().equals("king") && game[row-2][col+1].getColor().equals("white")){
                return true;
            }
            if(knightBoundsCheck(row-2, col-1) && game[row-2][col-1].getType().equals("king")&& game[row-2][col-1].getColor().equals("white")){
                return true;
            }
            if(knightBoundsCheck(row+2, col+1) && game[row+2][col+1].getType().equals("king")&& game[row+2][col+1].getColor().equals("white")){
                return true;
            }
            if(knightBoundsCheck(row+2, col-1) && game[row+2][col-1].getType().equals("king")&& game[row+2][col-1].getColor().equals("white")){
                return true;
            }
            if(knightBoundsCheck(row+1, col+2) && game[row+1][col+2].getType().equals("king")&& game[row+1][col+2].getColor().equals("white")){
                return true;
            }
            if(knightBoundsCheck(row-1, col+2) && game[row-1][col+2].getType().equals("king")&& game[row-1][col+2].getColor().equals("white")){
                return true;
            }
            if(knightBoundsCheck(row+1, col-2) && game[row+1][col-2].getType().equals("king")&& game[row+1][col-2].getColor().equals("white")){
                return true;
            }
            if(knightBoundsCheck(row-1, col-2) && game[row-1][col-2].getType().equals("king")&& game[row-1][col-2].getColor().equals("white")){
                return true;
            }
        }   
        return false;        
    }
    /*
     * This method checks if the cords that are being checked are out of bounds. Due to the 
     * and (&&) statements in the knight check method, the conditional statement wont continue
     * to be evaluated and therefore not throw a execption
     * 
     * @return : returns true if the spot checking is withen the gameboard
     */
    private static boolean knightBoundsCheck(int curCheckRow, int curCheckCol){
        if(curCheckRow >= 8 || curCheckRow < 0){
            return false;
        }
        if(curCheckCol >= 8 || curCheckCol < 0){
            return false;
        }

        return true;
    }
/**
 * @Param color: current piece color on row and col
 */
    public static boolean checkBishopCheck(Piece[][] game, String color, int row, int col){
        int rowCount = row -1;
        int colCount = col + 1;    
        //up right
        while(rowCount >= 0 && colCount < 8 ){
            //if its a king of the other color
            System.out.println("Checking row: " + rowCount + " col: " + colCount + " .Piece at place: " + game[rowCount][colCount].getType());
            if(game[rowCount][colCount].getType().equals("king") && !game[rowCount][colCount].getColor().equals(color)){
                return true;
            }
            //if next square is not a blank piece
            if(!game[rowCount][colCount].getType().equals("--")){
                //out of index
                rowCount = 10;
            }
            rowCount--;
            colCount++;
        }
        //up left
        rowCount = row -1;
        colCount = col -1;

        while(rowCount >= 0 && colCount >= 0 && rowCount < 8 && colCount < 8){
            //if its a king of the other color
            System.out.println("Checking row: " + rowCount + " col: " + colCount + " .Piece at place: " + game[rowCount][colCount].getType());
            if(game[rowCount][colCount].getType().equals("king") && !game[rowCount][colCount].getColor().equals(color)){
                return true;
            }
            //if next square is not a blank piece
            if(!game[rowCount][colCount].getType().equals("--")){
                //out of index
                rowCount = 10;
            }
            rowCount--;
            colCount--;
        }
        //bot left
        rowCount = row +1;
        colCount = col -1;

        while(rowCount < 8 && colCount >= 0){
            //if its a king of the other color
            System.out.println("Checking row: " + rowCount + " col: " + colCount + " .Piece at place: " + game[rowCount][colCount].getType());
            if(game[rowCount][colCount].getType().equals("king") && !game[rowCount][colCount].getColor().equals(color)){
                return true;
            }
            //if next square is not a blank piece
            if(!game[rowCount][colCount].getType().equals("--")){
                //out of index
                rowCount = 10;
            }
            rowCount++;
            colCount--;
        }
        //bot right
        rowCount = row +1;
        colCount = col +1;

        while(rowCount < 8 && colCount < 8 ){
            //if its a king of the other color
            System.out.println("Checking row: " + rowCount + " col: " + colCount + " .Piece at place: " + game[rowCount][colCount].getType());
            if(game[rowCount][colCount].getType().equals("king") && !game[rowCount][colCount].getColor().equals(color)){
                return true;
            }
            //if next square is not a blank piece
            if(!game[rowCount][colCount].getType().equals("--")){
                //out of index
                rowCount = 10;
            }
            rowCount++;
            colCount++;
        }



        
        return false;
            
    }
    /*
     * Checks the diagonal for bishop and queen checks, similar to the knight check method
     * Uopdate: checks rooks as well, dont feel like changing all the names
     * @return : true if in bounds
     */
    

    public static boolean checkRookCheck(Piece[][] game, String color, int row, int col){
        //right
        int rowCount = row;
        int colCount = col + 1;    
        while(rowCount >= 0 && colCount < 8){
            System.out.println("Checking row: " + rowCount + " col: " + colCount + " .Piece at place: " + game[rowCount][colCount].getType());
            //if its a king of the other color
            if(game[rowCount][colCount].getType().equals("king") && !game[rowCount][colCount].getColor().equals(color)){
                return true;
            }
            //if next square is not a blank piece
            if(!game[rowCount][colCount].getType().equals("--")){
                //out of index
                rowCount = 10;
            }
            colCount++;
        }
        //left
        rowCount = row;
        colCount = col - 1;    
        while(rowCount >= 0 && colCount >= 0){
            System.out.println("Checking row: " + rowCount + " col: " + colCount + " .Piece at place: " + game[rowCount][colCount].getType());
            //if its a king of the other color
            if(game[rowCount][colCount].getType().equals("king") && !game[rowCount][colCount].getColor().equals(color)){
                return true;
            }
            //if next square is not a blank piece
            if(!game[rowCount][colCount].getType().equals("--")){
                //out of index
                rowCount = 10;
            }
            colCount--;
        }
        //up
        rowCount = row - 1;
        colCount = col;   
        while(rowCount >= 0){
            System.out.println("Checking row: " + rowCount + " col: " + colCount + " .Piece at place: " + game[rowCount][colCount].getType());
            //if its a king of the other color
            if(game[rowCount][colCount].getType().equals("king") && !game[rowCount][colCount].getColor().equals(color)){
                return true;
            }
            //if next square is not a blank piece
            if(!game[rowCount][colCount].getType().equals("--")){
                //out of index
                rowCount = 10;
            }
            rowCount--;
        }
        //down
        rowCount = row + 1;
        colCount = col;   
        while(rowCount < 8){
            System.out.println("Checking row: " + rowCount + " col: " + colCount + " .Piece at place: " + game[rowCount][colCount].getType());
            //if its a king of the other color
            if(game[rowCount][colCount].getType().equals("king") && !game[rowCount][colCount].getColor().equals(color)){
                return true;
            }
            //if next square is not a blank piece
            if(!game[rowCount][colCount].getType().equals("--")){
                //out of index
                rowCount = 10;
            }
            rowCount++;
        }

        return false;
    }

    public static boolean checkQueenCheck(Piece[][] game, String color, int row, int col){       
        if(checkBishopCheck(game, color, row, col)){
            return true;
        }
        if(checkRookCheck(game, color, row, col)){
            return true;
        }
        return false;
    }

    public static boolean castleDetection(Piece[][] game, int scol, int srow, int ecol, int erow){
        Piece startP = game[srow][scol];
        Piece endP = game[erow][ecol];
        if(startP.getType().equals("--") || endP.getType().equals("--")){
            return false;
        }
        if(startP.canCastle() == false){
            return false;
        } 
        if(endP.canCastle() == false){
            return false;
        }
        //short castle
        if(ecol - scol == 3){
            //castle for white
            if(game[7][1].getType().equals("--") && game[7][2].getType().equals("--")){
                return true;
            //for black
            } else if(game[0][1].getType().equals("--") && game[0][2].getType().equals("--")){
                return true;
            } else {
                return false;
            }
        } else {
        //long caslte
            //white
            if(game[7][7].getType().equals("--") && game[7][6].getType().equals("--") && game[7][3].getType().equals("--")){
                return true;
                //black
            } else if(game[0][4].getType().equals("--") && game[0][5].getType().equals("--") && game[0][6].getType().equals("--")){
                return true;
            } else {
                return false;
            }
        }
        //return false;
    }
    /*
     * Beginning of mate detection
     */
    //check all the squares around the king and if they are empty return false
    public static boolean checkForMate(Piece[][] gameArr, gameBoard game){
        int row = 0;
        int col = 0;
        String color;
        if(game.getTurn() % 2 == 0){
            color = "white";
        } else {
            color = "black";
        }
        for(Piece[] arrRow : gameArr){
            for(Piece piece : arrRow){
                if(piece.getType().equals("king") && piece.getColor().equals(color)){
                    row = piece.getRow();
                    col = piece.getCol();
                }
            }
        }

        
        
        if(!gameArr[row][col].getType().equals("king")){
            return false;
        }

        //check 8 spots

        if(checkSmootherMate(gameArr, row, col)){
            return true;
        }

        return false
    }

    public static boolean checkSmootherMate(Piece[][] gameArr, int row, int col){
        //up 
        int cRow = row - 1;
        int cCol = col;
        if(cRow >= 0 && gameArr[cRow][cCol].getType().equals("--")){
            return true;
        }
        //down 
        cRow = row + 1;
        cCol = col;
        if(cRow < 8 && gameArr[cRow][cCol].getType().equals("--")){
            return true;
        }
        //right
        cRow = row;
        cCol = col + 1;
        if(cCol < 8 && gameArr[cRow][cCol].getType().equals("--")){
            return true;
        }
        //left
        cRow = row;
        cCol = col - 1;
        if(cCol >= 0 && gameArr[cRow][cCol].getType().equals("--")){
            return true;
        }
        //up right
        cRow = row - 1;
        cCol = col + 1;
        if(cCol < 8 && cRow >= 0 && gameArr[cRow][cCol].getType().equals("--")){
            return true;
        }
        //up left
        cRow = row - 1;
        cCol = col - 1;
        if(cCol >= 0 && cRow >= 0 && gameArr[cRow][cCol].getType().equals("--")){
            return true;
        }
        //down left
        cRow = row + 1;
        cCol = col - 1;
        if(cCol >= 0 && cRow < 8 && gameArr[cRow][cCol].getType().equals("--")){
            return true;
        }
        //down right
        cRow = row + 1;
        cCol = col + 1;
        if(cCol < 8 && cRow < 8 && gameArr[cRow][cCol].getType().equals("--")){
            return true;
        }
        return false;
    }
    /**
     * This method is part of detecting checkmate. A method in gameBoard calls this method when looping through
     * the game Array. Everytime a piece is detected that isnt a blank square, the piece get passed through the 
     * params along with the gameArr, which is a matrix of the game. The method below detects what type the piece 
     * is and calls appropriate methods based on the piece type. These methods return arrayList of spots that the
     * piece covers. Eg a pawn would return a arraylist of piece type that represent spots that it could travel to
     * and could capture. This gets added into a matrix called *color spaceArea. colorSpaceArea is referenced when 
     * determining is a piece has any valid spots to move to when checking for checkamte
     * 
     * @param piece : piece that wants to be checked
     * @param gameArr : matrix of the game
     * @return ArrayList of empty spots piece could travel too
     */

    public static ArrayList<Piece> getAreaForPiece(Piece piece, Piece[][] gameArr){
        ArrayList<Piece> coveredSpots;
        if(piece.getType().equals("pawn")){
            coveredSpots = getPawnCoverage(piece, gameArr);
        } else if (piece.getType().equals("knight")){
            coveredSpots = getKnightCoverage(piece, gameArr);
        } else if (piece.getType().equals("bishop")){
            coveredSpots = getBishopCoverage(piece, gameArr);
        } else if (piece.getType().equals("rook")){
            coveredSpots = getRookCoverage(piece, gameArr);
        } else if (piece.getType().equals("queen")){
            coveredSpots = getQueenCoverage(piece, gameArr);
        } else{
            coveredSpots = getKingCoverage(piece, gameArr);
        }

        return coveredSpots;
    }

    public static ArrayList<Piece> getPawnCoverage(Piece piece, Piece[][] gameArr){
        ArrayList<Piece> spots = new ArrayList<Piece>();
        if(piece.getColor().equals("white")){
            Piece right = gameArr[piece.getRow()-1][piece.getCol() + 1];
            Piece left = gameArr[piece.getRow()-1][piece.getCol() - 1];
            //FIX OUT OF BOUNDS
            if(right.getType().equals("--")){
                spots.add(right);
            }
            if(left.getType().equals("--")){
                spots.add(right);
            }
        } else {
            Piece right = gameArr[piece.getRow()+1][piece.getCol() + 1];
            Piece left = gameArr[piece.getRow()+1][piece.getCol() - 1];

            if(right.getType().equals("--")){
                spots.add(right);
            }
            if(left.getType().equals("--")){
                spots.add(right);
            }
        }

        return spots;
    }
    //FIX OUT OF BOUNDS
    public static ArrayList<Piece> getKnightCoverage(Piece piece, Piece[][] gameArr){
        ArrayList<Piece> spots = new ArrayList<Piece>();
        if(gameArr[piece.getRow()-2][piece.getCol()+1].getType().equals("--")){
            spots.add(gameArr[piece.getRow()-2][piece.getCol()+1]);
        }
        if(gameArr[piece.getRow()-2][piece.getCol()-1].getType().equals("--")){
            spots.add(gameArr[piece.getRow()-2][piece.getCol()-1]);    
        }
        if(gameArr[piece.getRow()-1][piece.getCol()+2].getType().equals("--")){
                spots.add(gameArr[piece.getRow()-1][piece.getCol()+2]);
        }
        if(gameArr[piece.getRow()+1][piece.getCol()+2].getType().equals("--")){
            spots.add(gameArr[piece.getRow()+1][piece.getCol()+2]);
        }
        if(gameArr[piece.getRow()-2][piece.getCol()+1].getType().equals("--")){
            spots.add(gameArr[piece.getRow()-2][piece.getCol()+1]);
        }
        if(gameArr[piece.getRow()-2][piece.getCol()-1].getType().equals("--")){
            spots.add(gameArr[piece.getRow()-2][piece.getCol()-1]);
        }
        if(gameArr[piece.getRow()+1][piece.getCol()-2].getType().equals("--")){
            spots.add(gameArr[piece.getRow()+1][piece.getCol()-2]);
        }
        if(gameArr[piece.getRow()-1][piece.getCol()-2].getType().equals("--")){
            spots.add(gameArr[piece.getRow()-1][piece.getCol()-2]);
        }
        return spots;  
    }

    public static ArrayList<Piece> getBishopCoverage(Piece piece, Piece[][] gameArr){
        ArrayList<Piece> spots = new ArrayList<Piece>();
        //up right
        int rowCount = piece.getRow() -1;
        int colCount = piece.getCol() + 1;      
        while(rowCount >= 0 && colCount < 8){
            //if cur piece is blank
            if(gameArr[rowCount][colCount].getType().equals("--")){
                spots.add(gameArr[rowCount][colCount]);
            } else {
                //if its not
                rowCount = 10;
            }
            rowCount--;
            colCount++;
        }
        //down right
        rowCount = piece.getRow() + 1;
        colCount = piece.getCol() + 1;      
        while(rowCount < 8 && colCount < 8){
            //if cur piece is blank
            if(gameArr[rowCount][colCount].getType().equals("--")){
                spots.add(gameArr[rowCount][colCount]);
            } else {
                //if its not
                rowCount = 10;
            }
            rowCount++;
            colCount++;
        }
        //down left
        rowCount = piece.getRow() + 1;
        colCount = piece.getCol() - 1;      
        while(rowCount < 8 && colCount >= 0){
            //if cur piece is blank
            if(gameArr[rowCount][colCount].getType().equals("--")){
                spots.add(gameArr[rowCount][colCount]);
            } else {
                //if its not
                rowCount = 10;
            }
            rowCount++;
            colCount--;
        }
        //up left
        rowCount = piece.getRow() - 1;
        colCount = piece.getCol() - 1;      
        while(rowCount >= 0 && colCount >= 0){
            //if cur piece is blank
            if(gameArr[rowCount][colCount].getType().equals("--")){
                spots.add(gameArr[rowCount][colCount]);
            } else {
                //if its not
                rowCount = 10;
            }
            rowCount--;
            colCount--;
        }
        return spots;
    }

    public static ArrayList<Piece> getRookCoverage(Piece piece, Piece[][] gameArr){
        ArrayList<Piece> spots = new ArrayList<Piece>();
        //up 
        int rowCount = piece.getRow() -1;
        int colCount = piece.getCol();      
        while(rowCount >= 0){
            //if cur piece is blank
            if(gameArr[rowCount][colCount].getType().equals("--")){
                spots.add(gameArr[rowCount][colCount]);
            } else {
                //if its not
                rowCount = -10;
            }
            rowCount--;
        }
        //down 
        rowCount = piece.getRow() + 1;
        colCount = piece.getCol();      
        while(rowCount < 8){
            //if cur piece is blank
            if(gameArr[rowCount][colCount].getType().equals("--")){
                spots.add(gameArr[rowCount][colCount]);
            } else {
                //if its not
                rowCount = 10;
            }
            rowCount++;
        }
        // left
        rowCount = piece.getRow();
        colCount = piece.getCol() - 1;      
        while(colCount >= 0){
            //if cur piece is blank
            if(gameArr[rowCount][colCount].getType().equals("--")){
                spots.add(gameArr[rowCount][colCount]);
            } else {
                //if its not
                colCount = -10;
            }
            colCount--;
        }
        //right
        rowCount = piece.getRow();
        colCount = piece.getCol() + 1;      
        while(colCount < 8){
            //if cur piece is blank
            if(gameArr[rowCount][colCount].getType().equals("--")){
                spots.add(gameArr[rowCount][colCount]);
            } else {
                //if its not
                colCount = 10;
            }
            colCount++;
        }
        return spots;
    }

    public static ArrayList<Piece> getQueenCoverage(Piece piece, Piece[][] gameArr){
        ArrayList<Piece> diagSpots = getBishopCoverage(piece, gameArr);
        ArrayList<Piece> upAndDownSpots = getRookCoverage(piece, gameArr);

        //combining and merging 2 arrays without duplicates
        diagSpots.removeAll(upAndDownSpots);
        diagSpots.addAll(upAndDownSpots);

        return diagSpots;
    }

    public static ArrayList<Piece> getKingCoverage(Piece piece, Piece[][] gameArr){
        ArrayList<Piece> spots = new ArrayList<Piece>();

        int checkRow;
        int checkCol;

        //up
        checkRow = piece.getRow() - 1;
        checkCol = piece.getCol();
        if(checkRow >= 0 && gameArr[checkRow][checkCol].getType().equals("--")){
            spots.add(gameArr[checkRow][checkCol]);
        }
        //down
        checkRow = piece.getRow() + 1;
        checkCol = piece.getCol();
        if(checkRow < 8 && gameArr[checkRow][checkCol].getType().equals("--")){
            spots.add(gameArr[checkRow][checkCol]);
        }
        //left
        checkRow = piece.getRow();
        checkCol = piece.getCol() - 1;
        if(checkCol >= 0 && gameArr[checkRow][checkCol].getType().equals("--")){
            spots.add(gameArr[checkRow][checkCol]);
        }

        //right
        checkRow = piece.getRow();
        checkCol = piece.getCol() + 1;
        if(checkCol < 8 && gameArr[checkRow][checkCol].getType().equals("--")){
            spots.add(gameArr[checkRow][checkCol]);
        }

        //upright
        checkRow = piece.getRow() - 1;
        checkCol = piece.getCol() + 1;
        if(checkRow >= 0 && checkCol < 8 && gameArr[checkRow][checkCol].getType().equals("--")){
            spots.add(gameArr[checkRow][checkCol]);
        }

        //upleft
        checkRow = piece.getRow() - 1;
        checkCol = piece.getCol() - 1;
        if(checkRow >= 0 && checkCol >= 0 && gameArr[checkRow][checkCol].getType().equals("--")){
            spots.add(gameArr[checkRow][checkCol]);
        }

        //downright
        checkRow = piece.getRow() + 1;
        checkCol = piece.getCol() + 1;
        if(checkRow < 8 && checkCol < 8 && gameArr[checkRow][checkCol].getType().equals("--")){
            spots.add(gameArr[checkRow][checkCol]);
        }

        //downleft
        checkRow = piece.getRow() + 1;
        checkCol = piece.getCol() - 1;
        if(checkRow < 8 && checkCol >= 0 && gameArr[checkRow][checkCol].getType().equals("--")){
            spots.add(gameArr[checkRow][checkCol]);
        }
        return spots;
    }

}
