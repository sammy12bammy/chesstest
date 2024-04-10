public class gameLogic {
    public static boolean debugMode = false;
    public static boolean debugMode1 = false;
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
                return pawnMoveLogic(grid, srow, scol, erow, ecol);
                
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
    /**
     * Returns true if the 2 spots that the player selected is a valid move for a pawn
     * 
     * @param grid : 2d array of the game
     * @param srow : starting y cord
     * @param scol : starting x cord
     * @param erow : end y cord
     * @param ecol : end x cord
     * @return
     */

    public static boolean pawnMoveLogic(Piece[][] grid, int srow, int scol, int erow, int ecol){
        String color = grid[srow][scol].getColor();
        //can only decrease in row for white
        if(color.equals("white")){
            if(srow == 6){
                if(scol != ecol){
                    return false;
                } 
                if(srow - 2 == erow || srow - 1 == erow){
                    return true;
                } 
            } else {
                if(scol != ecol){
                    return false;
                } 
                if(srow - 1 == erow){
                    return true;
                } 
            }          
        } else {
            if(srow == 1){
                if(scol != ecol){
                    return false;
                } 
                if(srow + 2 == erow || srow + 1 == erow){
                    return true;
                } 
            } else {
                if(scol != ecol){
                    return false;
                } 
                if(srow + 1 == erow){
                    return true;
                } 
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
 * ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
 * 
 */
    /**
     * New method for checking for checks. Takes a matrixs of pieces and returns true
     * if the king is in check
     * 
     * @param gameArr
     * @return
     */

     //problem here is that for every single time that colorInCheck is called,
     //the game array does not update and it checks the orignal spots that
     //the game was initalzied with but when prinitng the array using a
     //method from the game class, the game is updated
     //when the method is called, the piece that just moved is called and then
     //this method is called. The color of the piece that moved is passed through
     //which means that that piece color needs to be checked/ If this method is called
     //after a white move, the we need to check if the color in check is black. 
    public static boolean colorInCheck(gameBoard game, String color){
        String checkingColor;
        if(color.equals("white")){
            checkingColor = "black";
        } else{
            checkingColor = "white";
        }
        Piece[][] pieceArray = game.getGameBoardArray();
        //game.printGame();

        for(int row = 0; row < 8; row++){
            for(int col = 0; col < 8; col++){
                Piece piece = pieceArray[row][col];            
                if(piece != null && piece.getColor() != null && piece.getColor() != "na" && piece.getColor().equals(checkingColor) && !piece.getType().equals("king")){
                    //System.out.println("Grabbing a: " + piece.getColor() + " " + piece.getType() + " at row: " + row + " col: " + col);
                    if(isPieceCheckingKing(pieceArray, piece, row, col)){
                        return true;
                    }
                }
            }
        }

        return false;
    }
    
    /*
     * VAR COLOR is the current piece that is being checked color, not the king color
     */
    public static boolean isPieceCheckingKing(Piece[][] gameArr, Piece piece, int row, int col){
        String type = piece.getType();
        String color = piece.getColor();

        switch (type){
            case "pawn":
                return checkPawnCheck(gameArr, color, row, col);
            case "knight":
                return checkKnightCheck(gameArr, color, row, col);
            case "bishop":
                return checkBishopCheck(gameArr, color, row, col);
            case "rook":
                return checkRookCheck(gameArr, color, row, col);
            case "queen":
                return checkQueenCheck(gameArr, color, row, col);
            default:
                throw new IllegalArgumentException(type + " Piece isnt part of the game");
        }

    }
    
    
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
         
        if(pieceType.equals("rook")){
            return checkRookCheck(game, curPieceColor, row, col);
        }
        if(pieceType.equals("queen")){
            return checkQueenCheck(game, curPieceColor, row, col);
        }
        
        return false;
    }

    public static boolean checkPawnCheck(Piece[][] gameArr, String color, int row, int col){
        int cRow;
        int cCol;
        if(color.equals("white")){
            cRow = row -1;
            cCol = col + 1;
            if(cRow >= 0 && cCol < 8 && gameArr[cRow][cCol].getType().equals("king") && gameArr[cRow][cCol].getColor().equals("black")){
                return true;
            } 
            cCol = col - 1;
            if(cRow >= 0 && cCol >= 0 && gameArr[cRow][cCol].getType().equals("king") && gameArr[cRow][cCol].getColor().equals("black")){
                return true;
            }
            return false;
        } else {
            cRow = row + 1;
            cCol = col + 1;
            if(cRow < 8 && cCol < 8 && gameArr[cRow][cCol].getType().equals("king") && gameArr[cRow][cCol].getColor().equals("white")){
                return true;
            } 
            cCol = col - 1;
            if(cRow < 8 && cCol >= 0 && gameArr[cRow][cCol].getType().equals("king") && gameArr[cRow][cCol].getColor().equals("white")){
                return true;
            } 
            return false;
        }
    }

    public static boolean checkKnightCheck(Piece[][] game, String color, int row, int col){
        if(color.equals("white")){
            @SuppressWarnings("unused")
            int rowC = row -2;
            @SuppressWarnings("unused")
            int colC = col+ 1;
            //System.out.println("Checked Row: " + rowC + " Col: " + colC + ". Piece at spot: " + game[row-2][col+1].getType());
            if(knightBoundsCheck(row-2, col+1) && game[row-2][col+1].getType().equals("king") && game[row-2][col+1].getColor().equals("black")){
                return true;
            }
            rowC = row -2;
            colC = col -1;
            //System.out.println("Checked Row: " + rowC + " Col: " + colC + ". Piece at spot: " + game[rowC][colC].getType());
            if(knightBoundsCheck(row-2, col-1) && game[row-2][col-1].getType().equals("king")&& game[row-2][col-1].getColor().equals("black")){
                return true;
            }
            rowC = row +2;
            colC = col +1;
            //System.out.println("Checked Row: " + rowC + " Col: " + colC + ". Piece at spot: " + game[rowC][colC].getType());
            if(knightBoundsCheck(row+2, col+1) && game[row+2][col+1].getType().equals("king")&& game[row+2][col+1].getColor().equals("black")){
                return true;
            }
            rowC = row +2;
            colC = col -1;
            //System.out.println("Checked Row: " + rowC + " Col: " + colC + ". Piece at spot: " + game[rowC][colC].getType());
            if(knightBoundsCheck(row+2, col-1) && game[row+2][col-1].getType().equals("king")&& game[row+2][col-1].getColor().equals("black")){
                return true;
            }
            rowC = row +1;
            colC = col +2;
            //System.out.println("Checked Row: " + rowC + " Col: " + colC + ". Piece at spot: " + game[rowC][colC].getType());
            if(knightBoundsCheck(row+1, col+2) && game[row+1][col+2].getType().equals("king")&& game[row+1][col+2].getColor().equals("black")){
                return true;
            }
            rowC = row -1;
            colC = col +2;
            //System.out.println("Checked Row: " + rowC + " Col: " + colC + ". Piece at spot: " + game[rowC][colC].getType());
            if(knightBoundsCheck(row-1, col+2) && game[row-1][col+2].getType().equals("king")&& game[row-1][col+2].getColor().equals("black")){
                return true;
            }
            rowC = row +1;
            colC = col -2;
            //System.out.println("Checked Row: " + rowC + " Col: " + colC + ". Piece at spot: " + game[rowC][colC].getType());
            if(knightBoundsCheck(row+1, col-2) && game[row+1][col-2].getType().equals("king")&& game[row+1][col-2].getColor().equals("black")){
                return true;
            }
            rowC = row -1;
            colC = col -2;
            //System.out.println("Checked Row: " + rowC + " Col: " + colC + ". Piece at spot: " + game[rowC][colC].getType());
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
        String lookForColor;
        if(color.equals("white")){
            lookForColor = "white";
        } else {
            lookForColor = "black";
        }  
        if(debugMode1){ 
            System.out.println("----------------------------");
            System.out.println(color + "bishop check looking for : " + lookForColor);
            System.out.println("Row: " + row + " Col: " + col);
            System.out.println("----------------------------");
        }
        int rowCount = row -1;
        int colCount = col + 1;    
        //up right
        while(rowCount >= 0 && rowCount < 8 && colCount < 8 ){
            //if its a king of the other color
            if(debugMode){
                System.out.println("Checking row: " + rowCount + " col: " + colCount);
                System.out.println("Piece at place: " + game[rowCount][colCount].getType());
                System.out.println("Checking up right");
            }
            
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
            if(debugMode){
                System.out.println("Checking row: " + rowCount + " col: " + colCount);
                System.out.println("Piece at place: " + game[rowCount][colCount].getType());
                System.out.println("Checking up left");
            }
            
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
            if(debugMode){
                System.out.println("Checking bottom left");
                System.out.println("Checking row: " + rowCount + " col: " + colCount);
                System.out.println("Piece at place: " + game[rowCount][colCount].getType());
            }
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
            if(debugMode){
                System.out.println("Checking bottom right");
                System.out.println("Checking row: " + rowCount + " col: " + colCount);
                System.out.println("Piece at place: " + game[rowCount][colCount].getType());
            }
            
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
        while(rowCount < 8 && colCount < 8){
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
        while(rowCount < 8 && colCount >= 0){
            //System.out.println("Checking row: " + rowCount + " col: " + colCount + " .Piece at place: " + game[rowCount][colCount].getType());
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
            //System.out.println("Checking row: " + rowCount + " col: " + colCount + " .Piece at place: " + game[rowCount][colCount].getType());
            //if its a king of the other color
            if(game[rowCount][colCount].getType().equals("king") && !game[rowCount][colCount].getColor().equals(color)){
                return true;
            }
            //if next square is not a blank piece
            if(!game[rowCount][colCount].getType().equals("--")){
                //out of index
                rowCount = -10;
            }
            rowCount--;
        }
        //down
        rowCount = row + 1;
        colCount = col;   
        while(rowCount < 8){
            //System.out.println("Checking row: " + rowCount + " col: " + colCount + " .Piece at place: " + game[rowCount][colCount].getType());
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
    

}
