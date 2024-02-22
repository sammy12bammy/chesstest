import java.util.ArrayList;

public class gameBoard {
    //instance variabls for game board class
    private Piece[][] game = new Piece[8][8];
    private boolean[][] whiteSpaceArea = new boolean[8][8];
    private boolean[][] blackSpaceArea = new boolean[8][8];
    private int turn;
    private boolean madeMove;
    private boolean kingCheckedWhite;
    private boolean kingCheckedBlack;
    private boolean endGame = false;
    private ArrayList<Piece> capturedPieces = new ArrayList<Piece>();
    
    /**
     * Initializes a gameboard clas. This class holds all neccasary methods for chess
     * PostCondition: Initzalizing pieces objects on 8x8 board and setting turn to 0 (Player white)
     * 
     * @param : no params and no overload
     */
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
        kingCheckedWhite = false;
        kingCheckedBlack = false;
    }
//Getter and setter methods Methods
        public void setKingCheckedWhite(boolean bol){
            kingCheckedWhite = bol;
        }

        public boolean getKingCheckedWhite(){
            return kingCheckedWhite;
        }

        public void setKingCheckedBlack(boolean bol){
            kingCheckedBlack = bol;
        }

        public boolean getKingCheckedBlack(){
            return kingCheckedBlack;
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

        public Piece[][] getGameBoardArray(){
            return game;
        }
//------------------------------------------------------------------------------------

    /** 
     * Prints all captured pieces in capturedPieces array when called
     * PreCondition: ArrayList capturedPieces must be initalized
     */      
    public void printCapturedPiece(){
            if(capturedPieces.size() == 0){
                System.out.println("No captured pieces yet");
                return;
            }
            
            for(int i = 0; i < capturedPieces.size(); i++){
                System.out.println(capturedPieces.get(i) + " ");
            }
        }
        /**
         * Initizalizes all pieces on games by creating new instances of Piece class and setting their params to match position, color, and type
         * Precondition: game[][] arr must initalized, color must be initalized
         * PostCondition: game[][] has pieces!!
         * 
         * @param arr
         * @param color
         */
    private static void makePieces(Piece[][]arr, String color){
        try{
            if(color.equals("white")){
                arr[7][0] = new Piece("rook", "white", 7, 0, true);
                arr[7][1] = new Piece("knight", "white", 7, 1);
                arr[7][2] = new Piece("bishop", "white", 7, 2);
                arr[7][3] = new Piece("king", "white", 7, 3, true);
                arr[7][4] = new Piece("queen", "white", 7, 4);
                arr[7][5] = new Piece("bishop", "white", 7, 5);
                arr[7][6] = new Piece("knight", "white", 7, 6);
                arr[7][7] = new Piece("rook", "white", 7, 7, true);

                arr[6][0] = new Piece("pawn", "white", 6, 0);
                arr[6][1] = new Piece("pawn", "white", 6, 1);
                arr[6][2] = new Piece("pawn", "white", 6, 2);
                arr[6][3] = new Piece("pawn", "white", 6, 3);
                arr[6][4] = new Piece("pawn", "white", 6, 4);
                arr[6][5] = new Piece("pawn", "white", 6, 5);
                arr[6][6] = new Piece("pawn", "white", 6, 6);
                arr[6][7] = new Piece("pawn", "white", 6, 7);
            } 
            if(color.equals("black")){
                arr[0][0] = new Piece("rook", "black", 0, 0, true);
                arr[0][1] = new Piece("knight", "black", 0, 1);
                arr[0][2] = new Piece("bishop", "black", 0, 2);
                arr[0][3] = new Piece("king", "black", 0, 3, true);
                arr[0][4] = new Piece("queen", "black", 0, 4);
                arr[0][5] = new Piece("bishop", "black", 0, 5);
                arr[0][6] = new Piece("knight", "black",0 , 6);
                arr[0][7] = new Piece("rook", "black", 0, 7, true);

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
    //prints out the game in the terminal
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
    //-------------------------------------------------------------------------------------------------------------
    /**
     * Makes move methods that checks if the move is valid and if the king is checked. Captures piece before swapping 
     * Precondition: must have game[][] and start and end must be passed through as parameters
     * PostCondition: swaps and captures piece
     * 
     * @param start
     * @param end
     */ 
    
    protected void makeMove(int srow, int scol, int erow, int ecol){
        capturePiece(srow, scol, erow, ecol);
        game[erow][ecol] = game[srow][scol];
        game[srow][scol] = new Piece("--", "na", srow, scol);
        if(turn % 2 == 0){
            updateWhiteArea();
        } else {
            updateBlackArea();
        }
        turn++;     
    }

    protected void castleWhite(int scol, int srow, int ecol, int erow){
        boolean shortCastle;
        if(ecol - scol == 3){
            shortCastle = false;
        } else {
            shortCastle = true;
        }
        if(shortCastle){
            game[7][1] = new Piece("rook", "white", 7, 1, false);
            game[7][2] = new Piece("king", "white", 7, 2, false);
            game[7][0] = new Piece("--", "na", 7, 0);
            game[7][3] = new Piece("--", "na", 7, 3);
        } else {
            game[7][4] = new Piece("rook", "white", 7, 4, false);
            game[7][5] = new Piece("king", "white", 7, 5, false);
            game[7][7] = new Piece("--", "na", 7, 7);
            game[7][6] = new Piece("--", "na", 7, 6);
            game[7][3] = new Piece("--", "na", 7, 3);
        }
        turn++;
    }

    protected void castleBlack(int scol, int srow, int ecol, int erow){
        boolean shortCastle;
        if(ecol - scol == 3){
            shortCastle = false;
        } else {
            shortCastle = true;
        }
        if(shortCastle){
            game[0][1] = new Piece("rook", "black", 0, 1, false);
            game[0][2] = new Piece("king", "black", 0, 2, false);
            game[0][0] = new Piece("--", "na", 0, 0);
            game[0][3] = new Piece("--", "na", 0, 3);
        } else {
            game[0][4] = new Piece("rook", "black", 0, 4, false);
            game[0][5] = new Piece("king", "black", 0, 5, false);
            game[0][7] = new Piece("--", "na", 0, 7);
            game[0][6] = new Piece("--", "na", 0, 6);
            game[0][3] = new Piece("--", "na", 0, 3);
        }
        turn++;
    }
    
    /**
     * Captures pieces and adds it to capturedPieces
     * PreCondition: must be called through makeMove()
     * PostCondition: pieces is removed and store in capturedPieces
     * 
     * @param srow
     * @param scol
     * @param erow
     * @param ecol
     */
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


    public void checkMate(String color){
        if(color.equals("white")){
            System.out.println("Black has won");
            endGame = true;
        }
    }
    /**
     * Calls a static method in validMoves that returns a arraylist of spots that the piece that moved is occuping 
     * Updates the 2d array for white moves
     * @param row
     * @param col
     */
    public void updateWhiteArea(int row, int col){
        for(Piece[] rowInArr : game){
            for(Piece piece : rowInArr){
                if(piece.getColor().equals("white")){
                    
                }
            }
        }
    }

    public void updateBlackArea(){

    }
    

}