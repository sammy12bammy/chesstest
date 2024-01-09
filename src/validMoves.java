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

        if(curPieceColor.equals(color)){
            return false;
        }

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

    public static boolean checkPawnCheck(Piece[][] game, String color, int row, int col){
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

    public static boolean checkKnightCheck(Piece[][] game, String color, int row, int col){
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

    public static boolean checkBishopCheck(Piece[][] game, String color, int row, int col){
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

    public static boolean checkRookCheck(Piece[][] game, String color, int row, int col){
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

    public static boolean checkQueenCheck(Piece[][] game, String color, int row, int col){
        if(checkBishopCheck(game, color, row, col)){
            return true;
        }
        if(checkRookCheck(game, color, row, col)){
            return true;
        }
        return false;
    }

}