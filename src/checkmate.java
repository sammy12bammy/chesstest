import java.util.ArrayList;
import java.util.HashMap;

public class checkmate {
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

        //find way out of mate

        if(canMoveOutOfMate(gameArr, game, row, col)){
            return false;
        } else if (otherPieceCanMove(game)){
            return false;
        } else {
            return true;
        }
    }
    //one of your pieces can move to get your king out of mate
    public static boolean otherPieceCanMove(gameBoard game){
        //color that is in check
        String color;
        if(game.getTurn() % 2 == 0){
            color = "white";
        } else {
            color = "black";
        }

        Piece[][] arr = game.getGameBoardArray();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = arr[row][col];
                if (piece != null && piece.getColor() != null && piece.getColor().equals(color)) {
                    gameBoard copyGettingChecked = copyGameBoard(game);
                    if(copyGettingChecked.pieceMovedAndNotinCheck(row, col, color)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static gameBoard copyGameBoard(gameBoard board) {
        gameBoard returnBoard = new gameBoard();
        Piece[][] oldArr = board.getGameBoardArray();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece curPiece = oldArr[i][j];
                returnBoard.copySpot(i, j, curPiece);
            }
        }

        return returnBoard;
    }

    public static HashMap<Integer, Integer> getMovesForPiece(Piece piece, Piece[][] gameArr){
        HashMap<Integer, Integer> returnMap;
        String type = piece.getType();
        int row = piece.getRow();
        int col = piece.getCol();

        switch (type){
            case "pawn":
                returnMap = getMapForPawn(gameArr, row, col, piece.getColor());
            case "knight":
                returnMap = getMapForKnight(gameArr, row, col, piece.getColor());
            case "bishop":
                returnMap = getMapForBishop(gameArr, row, col, piece.getColor());
            case "rook":
                returnMap = getMapForRook(gameArr, row, col, piece.getColor());
            case "queen":
                returnMap = getMapForQueen(gameArr, row, col, piece.getColor());
        }

        return returnMap;
    }

    public static HashMap<Integer, Integer> getMapForPawn(Piece[][] gameArr, int row, int col, String color){
        HashMap<Integer, Integer> returnMap = new HashMap<Integer, Integer>();
        String otherColor;
        if(color.equals("white")){
            otherColor = "black";
        } else {
            otherColor = "white";
        }

        if(color.equals("white")){
            //blocking
            if(row == 6){
                if(gameArr[row - 1][col].getType().equals("--")){
                    returnMap.put(row - 1, col);
                }
                if(gameArr[row - 2][col].getType().equals("--")){
                    returnMap.put(row - 2, col);
                }
            } else {
                if(gameArr[row - 1][col].getType().equals("--")){
                    returnMap.put(row - 1, col);
                }
            }
            //capture
            if(gameArr[row-1][col+1].getColor().equals(otherColor)){
                returnMap.put(row-1, col+1);
            }
            if(gameArr[row-1][col-1].getColor().equals(otherColor)){
                returnMap.put(row-1, col-1);
            }
        } else {
            if(row == 1){
                //blocking
                if(gameArr[row + 1][col].getType().equals("--")){
                    returnMap.put(row - 1, col);
                }
                if(gameArr[row + 2][col].getType().equals("--")){
                    returnMap.put(row - 2, col);
                }
            } else {
                if(gameArr[row + 1][col].getType().equals("--")){
                    returnMap.put(row - 1, col);
                }
            }
            //capture
            if(gameArr[row+1][col+1].getColor().equals(otherColor)){
                returnMap.put(row+1, col+1);
            }
            if(gameArr[row+1][col-1].getColor().equals(otherColor)){
                returnMap.put(row+1, col-1);
            }
        }
        return returnMap;
    }  

    public static HashMap<Integer, Integer> getMapForKnight(Piece[][] gameArr, int row, int col, String color){
        HashMap<Integer, Integer> returnMap = new HashMap<Integer, Integer>();
        String otherColor;
        if(color.equals("white")){
            otherColor = "black";
        } else {
            otherColor = "white";
        }

        if(gameArr[row-2][col-1].getType().equals("--") || gameArr[row-2][col-1].getColor().equals(otherColor)){
            returnMap.put(row-2, col-1);
        }
        if(gameArr[row-2][col+1].getType().equals("--") || gameArr[row-2][col+1].getColor().equals(otherColor)){
            returnMap.put(row-2, col+1);
        }
        if(gameArr[row-1][col+2].getType().equals("--") || gameArr[row-1][col+2].getColor().equals(otherColor)){
            returnMap.put(row-1, col+2);
        }
        if(gameArr[row+1][col+2].getType().equals("--") || gameArr[row+1][col+2].getColor().equals(otherColor)){
            returnMap.put(row+1, col+2);
        }
        if(gameArr[row+2][col+1].getType().equals("--") || gameArr[row+2][col+1].getColor().equals(otherColor)){
            returnMap.put(row+2, col+1);
        }
        if(gameArr[row+2][col-1].getType().equals("--") || gameArr[row+2][col-1].getColor().equals(otherColor)){
            returnMap.put(row+2, col-1);
        }
        if(gameArr[row+1][col-2].getType().equals("--") || gameArr[row+1][col-2].getColor().equals(otherColor)){
            returnMap.put(row+1, col-2);
        }
        if(gameArr[row-1][col-2].getType().equals("--") || gameArr[row-1][col-2].getColor().equals(otherColor)){
            returnMap.put(row-1, col-2);
        }


        return returnMap;
    }

    public static HashMap<Integer, Integer> getMapForBishop(Piece[][] gameArr, int row, int col, String color){
        HashMap<Integer, Integer> returnMap = new HashMap<Integer, Integer>();
        String otherColor;
        if(color.equals("white")){
            otherColor = "black";
        } else {
            otherColor = "white";
        }
          //up right
          int rowCount = row -1;
          int colCount = col + 1;      
          while(rowCount >= 0 && colCount < 8){
              if(gameArr[rowCount][colCount].getType().equals("--")){
                  returnMap.put(rowCount, colCount);
              } else if(gameArr[rowCount][colCount].getColor().equals(otherColor)){
                  returnMap.put(rowCount, colCount);
                  rowCount = -10;
              } else {
                  rowCount = -10;
              }
              rowCount--;
              colCount++;
          }
          //up left
          rowCount = row -1;
          colCount = col - 1;      
          while(rowCount >= 0 && colCount >= 0){
              if(gameArr[rowCount][colCount].getType().equals("--")){
                  returnMap.put(rowCount, colCount);
              } else if(gameArr[rowCount][colCount].getColor().equals(otherColor)){
                  returnMap.put(rowCount, colCount);
                  rowCount = -10;
              } else {
                  rowCount = -10;
              }
              rowCount--;
              colCount--;
          }
          //down right
          rowCount = row + 1;
          colCount = col + 1;      
          while(rowCount < 8 && colCount < 8){
              if(gameArr[rowCount][colCount].getType().equals("--")){
                  returnMap.put(rowCount, colCount);
              } else if(gameArr[rowCount][colCount].getColor().equals(otherColor)){
                  returnMap.put(rowCount, colCount);
                  rowCount = 10;
              } else {
                  rowCount = 10;
              }
              rowCount++;
              colCount++;
          }
          //down left
          rowCount = row + 1;
          colCount = col - 1;      
          while(rowCount < 8 && colCount >= 0){
              if(gameArr[rowCount][colCount].getType().equals("--")){
                  returnMap.put(rowCount, colCount);
              } else if(gameArr[rowCount][colCount].getColor().equals(otherColor)){
                  returnMap.put(rowCount, colCount);
                  rowCount = 10;
              } else {
                  rowCount = 10;
              }
              rowCount++;
              colCount--;
          }


        return returnMap;
    }

    public static HashMap<Integer, Integer> getMapForRook(Piece[][] gameArr, int row, int col, String color){
        HashMap<Integer, Integer> returnMap = new HashMap<Integer, Integer>();
        String otherColor;
        if(color.equals("white")){
            otherColor = "black";
        } else {
            otherColor = "white";
        }     
        int rowCount;
        int colCount;
        //right
        rowCount = row;
        colCount = col + 1;
        while(colCount < 8){
            if(gameArr[rowCount][colCount].getType().equals("--")){
                returnMap.put(rowCount, colCount);
            } else if(gameArr[rowCount][colCount].getColor().equals(otherColor)){
                returnMap.put(rowCount, colCount);
                colCount = 10;
            } else {
                colCount = 10;
            }
            colCount++;
        }
        //left
        rowCount = row;
        colCount = col - 1;
        while(colCount >= 0 ){
            if(gameArr[rowCount][colCount].getType().equals("--")){
                returnMap.put(rowCount, colCount);
            } else if(gameArr[rowCount][colCount].getColor().equals(otherColor)){
                returnMap.put(rowCount, colCount);
                colCount = -10;
            } else {
                colCount = -10;
            }
            colCount--;
        }
        //up
        rowCount = row - 1;
        colCount = col;
        while(rowCount >= 0 ){
            if(gameArr[rowCount][colCount].getType().equals("--")){
                returnMap.put(rowCount, colCount);
            } else if(gameArr[rowCount][colCount].getColor().equals(otherColor)){
                returnMap.put(rowCount, colCount);
                rowCount = -10;
            } else {
                rowCount = -10;
            }
            rowCount--;
        }
        rowCount = row + 1;
        colCount = col;
        while(rowCount < 8){
            if(gameArr[rowCount][colCount].getType().equals("--")){
                returnMap.put(rowCount, colCount);
            } else if(gameArr[rowCount][colCount].getColor().equals(otherColor)){
                returnMap.put(rowCount, colCount);
                rowCount = 10;
            } else {
                rowCount = 10;
            }
            rowCount++;
        }

        return returnMap;
    }
    public static HashMap<Integer, Integer> getMapForQueen(Piece[][] gameArr, int row, int col, String color){
        HashMap<Integer, Integer> map1 = new HashMap<Integer, Integer>();
        HashMap<Integer, Integer> map2 = getMapForBishop(gameArr, row, col, color);
        HashMap<Integer, Integer> map3 = getMapForRook(gameArr, row, col, color);

        map1.putAll(map2);
        map1.putAll(map3);

        return map1;
    }

    /**
     * This method detects if the piece (king) can be moved to avoid checkmate. This includes the
     * capture of pieces
     * 
     * 
     * @param gameArr
     * @param game
     * @param row
     * @param col
     * @return
     */
    public static boolean canMoveOutOfMate(Piece[][] gameArr, gameBoard game, int row, int col){
        Piece king = gameArr[row][col];
        boolean[][] spotsCovered;

        if(king.getColor().equals("white")){
            spotsCovered = game.getWhiteCoverage();
        } else {
            spotsCovered = game.getBlackCoverage();
        }
        //check all around the king to see if there are only spots that arent covered and dont have pieces
        //crow and ccol represent what spot we are checking, checks out of bounds first, then open spot, then piece coverage
        //if the piece is able to move out of checkmate, returns true for if statement
        int cRow;
        int cCol;

        //up
        cRow = row - 1;
        cCol = col;
        if(cRow >= 0 && gameArr[cRow][cCol].getType().equals("--") && spotsCovered[cRow][cCol] == false){
            return true;
        }
        //down
        cRow = row + 1;
        cCol = col;
        if(cRow < 8 && gameArr[cRow][cCol].getType().equals("--") && spotsCovered[cRow][cCol] == false){
            return true;
        }
        //left
        cRow = row;
        cCol = col - 1;
        if(cCol >= 0 && gameArr[cRow][cCol].getType().equals("--") && spotsCovered[cRow][cCol] == false){
            return true;
        }
        //right
        cRow = row;
        cCol = col + 1;
        if(cCol < 8 && gameArr[cRow][cCol].getType().equals("--") && spotsCovered[cRow][cCol] == false){
            return true;
        }
        //upright
        cRow = row - 1;
        cCol = col + 1;
        if(cRow >= 0 && cCol < 8 && gameArr[cRow][cCol].getType().equals("--") && spotsCovered[cRow][cCol] == false){
            return true;
        }
        //upleft
        cRow = row - 1;
        cCol = col - 1;
        if(cRow >= 0 && cCol >= 0 && gameArr[cRow][cCol].getType().equals("--") && spotsCovered[cRow][cCol] == false){
            return true;
        }
        //downright
        cRow = row + 1;
        cCol = col + 1;
        if(cRow < 8 && cCol < 8 && gameArr[cRow][cCol].getType().equals("--") && spotsCovered[cRow][cCol] == false){
            return true;
        }
        //downleft
        cRow = row + 1;
        cCol = col - 1;
        if(cRow < 8 && cCol >= 0 && gameArr[cRow][cCol].getType().equals("--") && spotsCovered[cRow][cCol] == false){
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
     * UPDATE: this method now picks up spots that the pieces cover that are not the same color, e.g. a black knight 
     * covers a white pawn so the king cannot move there
     * 
     * @param piece : piece that wants to be checked
     * @param gameArr : matrix of the game
     * @return ArrayList of empty spots piece could travel too
     */

    public static ArrayList<Piece> getAreaForPiece(Piece piece, Piece[][] gameArr){
        ArrayList<Piece> coveredSpots;
        //the variable notPieceColor represents the color of pieces that the current piece can capture
        //a white knight can capture any black piece
        String notPieceColor;
        if(piece.getColor().equals("white")){
            notPieceColor = "black";
        } else {
            notPieceColor = "white";
        }
        String type = piece.getType();

        switch (type) {
            case "pawn":
                coveredSpots = getPawnCoverage(piece, gameArr);
                break;
            case "knight":
                coveredSpots = getKnightCoverage(piece, gameArr, notPieceColor);
                break;
            case "bishop":
                coveredSpots = getBishopCoverage(piece, gameArr, notPieceColor);
                break;
            case "rook":
                coveredSpots = getRookCoverage(piece, gameArr, notPieceColor);
                break;
            case "queen":
                coveredSpots = getQueenCoverage(piece, gameArr, notPieceColor);
                break;
            case "king":
                coveredSpots = getKingCoverage(piece, gameArr, notPieceColor);
                break;
            default:
                throw new IllegalArgumentException("Invalid Piece input at line 727");
        }
        return coveredSpots;
    }

    public static ArrayList<Piece> getPawnCoverage(Piece piece, Piece[][] gameArr){
        ArrayList<Piece> spots = new ArrayList<Piece>();
        int cRow;
        int cCol;
        if(piece.getColor().equals("white")){
            //right
            cRow = piece.getRow() - 1;
            cCol = piece.getCol() + 1;
            if(cRow >= 0 && cCol < 8 && (gameArr[cRow][cCol].getType().equals("--") || gameArr[cRow][cCol].getColor().equals("black"))){
                spots.add(gameArr[cRow][cCol]);
            }
            //left
            cRow = piece.getRow() - 1;
            cCol = piece.getCol() - 1;
            if(cRow >= 0 && cCol < 8 && (gameArr[cRow][cCol].getType().equals("--") || gameArr[cRow][cCol].getColor().equals("black"))){
                spots.add(gameArr[cRow][cCol]);
            }
        } else {
            Piece right = gameArr[piece.getRow()+1][piece.getCol() + 1];
            Piece left = gameArr[piece.getRow()+1][piece.getCol() - 1];

            if(right.getCol() < 8 && right.getRow() < 8 && (right.getType().equals("--")|| right.getColor().equals("white"))){
                spots.add(right);
            }
            if(left.getRow() < 8 && left.getCol() >= 0 && (left.getType().equals("--") || left.getColor().equals("white"))){
                spots.add(left);
            }
        }

        return spots;
    }
    public static ArrayList<Piece> getKnightCoverage(Piece piece, Piece[][] gameArr, String notPieceColor){
        int cRow;
        int cCol;
        ArrayList<Piece> spots = new ArrayList<Piece>();

        cRow = piece.getRow() - 2;
        cCol = piece.getCol() + 1;
        if(cRow >= 0 && cCol < 8 && (gameArr[cRow][cCol].getType().equals("--") || gameArr[cRow][cCol].getColor().equals(notPieceColor))){
            spots.add(gameArr[piece.getRow()-2][piece.getCol()+1]);
        }
        cRow = piece.getRow() - 2;
        cCol = piece.getCol() - 1;
        if(cRow >= 0 && cCol >= 0 && (gameArr[cRow][cCol].getType().equals("--") || gameArr[cRow][cCol].getColor().equals(notPieceColor))){
            spots.add(gameArr[piece.getRow()-2][piece.getCol()-1]);    
        }
        cRow = piece.getRow() - 1;
        cCol = piece.getCol() + 2;
        if(cRow >= 0 && cCol < 8 && (gameArr[cRow][cCol].getType().equals("--") || gameArr[cRow][cCol].getColor().equals(notPieceColor))){
                spots.add(gameArr[piece.getRow()-1][piece.getCol()+2]);
        }
        cRow = piece.getRow() + 1;
        cCol = piece.getCol() + 2;
        if(cRow < 8 && cCol < 8 && (gameArr[cRow][cCol].getType().equals("--") || gameArr[cRow][cCol].getColor().equals(notPieceColor))){
            spots.add(gameArr[piece.getRow()+1][piece.getCol()+2]);
        }
        cRow = piece.getRow() + 2;
        cCol = piece.getCol() + 1;
        if(cRow < 8 && cCol < 8 && (gameArr[cRow][cCol].getType().equals("--") || gameArr[cRow][cCol].getColor().equals(notPieceColor))){
            spots.add(gameArr[piece.getRow()+2][piece.getCol()+1]);
        }
        cRow = piece.getRow() + 2;
        cCol = piece.getCol() - 1;
        if(cRow < 8 && cCol >= 0 && (gameArr[cRow][cCol].getType().equals("--") || gameArr[cRow][cCol].getColor().equals(notPieceColor))){
            spots.add(gameArr[piece.getRow()+2][piece.getCol()-1]);
        }
        cRow = piece.getRow() + 1;
        cCol = piece.getCol() - 2;
        if(cRow < 8 && cCol >= 0 && (gameArr[cRow][cCol].getType().equals("--") || gameArr[cRow][cCol].getColor().equals(notPieceColor))){
            spots.add(gameArr[piece.getRow()+1][piece.getCol()-2]);
        }
        cRow = piece.getRow() - 1;
        cCol = piece.getCol() - 2;
        if(cRow >= 0 && cCol >= 0 && (gameArr[cRow][cCol].getType().equals("--") || gameArr[cRow][cCol].getColor().equals(notPieceColor))){
            spots.add(gameArr[piece.getRow()-1][piece.getCol()-2]);
        }
        return spots;  
    }

    public static ArrayList<Piece> getBishopCoverage(Piece piece, Piece[][] gameArr, String notPieceColor){
        ArrayList<Piece> spots = new ArrayList<Piece>();
        //up right
        int rowCount = piece.getRow() -1;
        int colCount = piece.getCol() + 1;      
        while(rowCount >= 0 && colCount < 8){
            //if cur piece is blank
            if(gameArr[rowCount][colCount].getType().equals("--")){
                spots.add(gameArr[rowCount][colCount]);
            } else if(gameArr[rowCount][colCount].getColor().equals(notPieceColor)){
                spots.add(gameArr[rowCount][colCount]);
                rowCount = 10;
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
            } else if(gameArr[rowCount][colCount].getColor().equals(notPieceColor)){
                spots.add(gameArr[rowCount][colCount]);
                rowCount = 10;
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
            } else if(gameArr[rowCount][colCount].getColor().equals(notPieceColor)){
                spots.add(gameArr[rowCount][colCount]);
                rowCount = 10;
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
            } else if(gameArr[rowCount][colCount].getColor().equals(notPieceColor)){
                spots.add(gameArr[rowCount][colCount]);
                rowCount = 10;
            } else {
                //if its not
                rowCount = 10;
            }
            rowCount--;
            colCount--;
        }
        return spots;
    }
    public static ArrayList<Piece> getRookCoverage(Piece piece, Piece[][] gameArr, String notPieceColor){
        ArrayList<Piece> spots = new ArrayList<Piece>();
        //up 
        int rowCount = piece.getRow() -1;
        int colCount = piece.getCol();      
        while(rowCount >= 0){
            //if cur piece is blank
            if(gameArr[rowCount][colCount].getType().equals("--")){
                spots.add(gameArr[rowCount][colCount]);
            } else if(gameArr[rowCount][colCount].getColor().equals(notPieceColor)){
                spots.add(gameArr[rowCount][colCount]);
                rowCount = 10;
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
            } else if(gameArr[rowCount][colCount].getColor().equals(notPieceColor)){
                spots.add(gameArr[rowCount][colCount]);
                rowCount = 10;
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
            } else if(gameArr[rowCount][colCount].getColor().equals(notPieceColor)){
                spots.add(gameArr[rowCount][colCount]);
                rowCount = 10;
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
            } else if(gameArr[rowCount][colCount].getColor().equals(notPieceColor)){
                spots.add(gameArr[rowCount][colCount]);
                rowCount = 10;
            } else {
                //if its not
                colCount = 10;
            }
            colCount++;
        }
        return spots;
    }
    public static ArrayList<Piece> getQueenCoverage(Piece piece, Piece[][] gameArr, String notPieceColor){

        ArrayList<Piece> diagSpots = getBishopCoverage(piece, gameArr, notPieceColor);
        ArrayList<Piece> upAndDownSpots = getRookCoverage(piece, gameArr, notPieceColor);

        //combining and merging 2 arrays without duplicates
        diagSpots.removeAll(upAndDownSpots);
        diagSpots.addAll(upAndDownSpots);

        return diagSpots;
    }
    public static ArrayList<Piece> getKingCoverage(Piece piece, Piece[][] gameArr, String notPieceColor){
        ArrayList<Piece> spots = new ArrayList<Piece>();

        int checkRow;
        int checkCol;

        //up
        checkRow = piece.getRow() - 1;
        checkCol = piece.getCol();
        if(checkRow >= 0 && (gameArr[checkRow][checkCol].getType().equals("--") || gameArr[checkRow][checkCol].getColor().equals(notPieceColor))){
            spots.add(gameArr[checkRow][checkCol]);
        }
        //down
        checkRow = piece.getRow() + 1;
        checkCol = piece.getCol();
        if(checkRow < 8 && (gameArr[checkRow][checkCol].getType().equals("--") || gameArr[checkRow][checkCol].getColor().equals(notPieceColor))){
            spots.add(gameArr[checkRow][checkCol]);
        }
        //left
        checkRow = piece.getRow();
        checkCol = piece.getCol() - 1;
        if(checkCol >= 0 && (gameArr[checkRow][checkCol].getType().equals("--") || gameArr[checkRow][checkCol].getColor().equals(notPieceColor))){
            spots.add(gameArr[checkRow][checkCol]);
        }

        //right
        checkRow = piece.getRow();
        checkCol = piece.getCol() + 1;
        if(checkCol < 8 && (gameArr[checkRow][checkCol].getType().equals("--") || gameArr[checkRow][checkCol].getColor().equals(notPieceColor))){
            spots.add(gameArr[checkRow][checkCol]);
        }

        //upright
        checkRow = piece.getRow() - 1;
        checkCol = piece.getCol() + 1;
        if(checkRow >= 0 && checkCol < 8 && (gameArr[checkRow][checkCol].getType().equals("--") || gameArr[checkRow][checkCol].getColor().equals(notPieceColor))){
            spots.add(gameArr[checkRow][checkCol]);
        }

        //upleft
        checkRow = piece.getRow() - 1;
        checkCol = piece.getCol() - 1;
        if(checkRow >= 0 && checkCol >= 0 && (gameArr[checkRow][checkCol].getType().equals("--") || gameArr[checkRow][checkCol].getColor().equals(notPieceColor))){
            spots.add(gameArr[checkRow][checkCol]);
        }

        //downright
        checkRow = piece.getRow() + 1;
        checkCol = piece.getCol() + 1;
        if(checkRow < 8 && checkCol < 8 && (gameArr[checkRow][checkCol].getType().equals("--") || gameArr[checkRow][checkCol].getColor().equals(notPieceColor))){
            spots.add(gameArr[checkRow][checkCol]);
        }

        //downleft
        checkRow = piece.getRow() + 1;
        checkCol = piece.getCol() - 1;
        if(checkRow < 8 && checkCol >= 0 && (gameArr[checkRow][checkCol].getType().equals("--") || gameArr[checkRow][checkCol].getColor().equals(notPieceColor))){
            spots.add(gameArr[checkRow][checkCol]);
        }
        return spots;
    }
}
