import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;

import java.net.*;

import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;




public class chessRunnable{
    public static Piece[][] gameBoardArray;
    /**
     * Screen Width and Height
     * 
     * @Precondition : WIDTH has to equal Height (Has to be a square)
     */
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 850;

    final static String THERMINAL_TEXT_RESET = "\u001B[0m";
    final static String THERMINAL_TEXT_RED = "\u001B[31m";
    final static String THERMINAL_TEXT_GREEN = "\u001B[32m";
    final static String THERMINAL_TEXT_YELLOW = "\u001B[33m";
    final static int SECTION_DIVIDER_INT = SCREEN_WIDTH / 8;
    //starting and end cords for mouseLister
    private static int startX = -1;
    private static int startY = -1;
    private static int endX = -1;
    private static int endY = -1;
    public static void main(String[] args) throws Exception {
        //Sets up game in thermal and initalizes a gameBoard        
        clearThermialScreen();;
        gameBoard game = new gameBoard();
        gameBoardArray = game.getGameBoardArray();
        //game.printGame();
        System.out.println(THERMINAL_TEXT_GREEN + "Gameboard successfully initialized" + THERMINAL_TEXT_RESET);

        /*
         * Importing a images that is hosted by Imgur using java.net and java.io
         * The image file consist of 12 total peices and those peices are split up and given an index
         * This is stored in a array called imgs. If this array is called ex: imgs[0], the subimage
         * is called. This is useful when assigning pieces and makes it so I dont have to import 12 
         * different images
         * 
         * To see the index of pieces, see info.txt
         */
        Image imgs[] = getImgArray();
        

        /**
         * Visual game generation done through swing. Creation of chessboard using custom colors
         */
        JFrame window = new JFrame();
        window.setSize(SCREEN_WIDTH,SCREEN_HEIGHT);
        //window.setLocationRelativeTo(null);
        window.setResizable(false);
        JPanel panel = new JPanel(){
            @Override
            public void paint(Graphics g){
                super.paintComponent(g);
                boolean whiteSquare = true;
                for(int x = 0; x < 8; x++){
                    for(int y = 0; y <8; y++){
                        if(whiteSquare){
                            g.setColor(new Color(235, 235, 208));                                                          
                        } else {
                            g.setColor(new Color(119, 148, 85));
                        }
                        g.fillRect(x*((SCREEN_WIDTH-15) / 8),y*((SCREEN_HEIGHT - 40) / 8), SCREEN_WIDTH / 8, SCREEN_HEIGHT / 8);
                        //g.fillRect(x*(SCREEN_WIDTH / 8),y*(SCREEN_HEIGHT / 8), SCREEN_WIDTH / 8, SCREEN_HEIGHT / 8)
                        whiteSquare = !whiteSquare;
                    }
                    whiteSquare = !whiteSquare;                 
                }

                /* 
                * This displays the game on the JFrame
                * Iterate through the game array in the gameBoard.java class
                * IF there is a piece there, get the type at sets the index of that piece
                * An index of 12 or greater represents no pieces being there or "--" this gets skipped over
                */
                
                for(Piece[] row : game.getGameBoardArray()){
                    for(Piece piece : row){
                        int index = getImgIndex(piece);
                        
                        if(index < 12){
                            g.drawImage(imgs[index], piece.getX(), piece.getY(), this);
                        }       
                    }
                }          
            }
        };
        window.add(panel);
        window.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //if its the first click
                if(startX == -1){
                    //store cords
                    /*
                     * The purpose of dividing by final section divider is to turn x cords into a row that
                     * can be used in game methods such as isValidMove, etc/ StartX is the col and startY is
                     * the row
                     */
                    startX = e.getX() / SECTION_DIVIDER_INT;
                    startY = e.getY() / SECTION_DIVIDER_INT;
                    System.out.println("First Click");
                    System.out.println(startX + " , " + startY);
                } else {
                    //if second click
                    endX = e.getX() / SECTION_DIVIDER_INT;
                    endY = e.getY() / SECTION_DIVIDER_INT;
                    System.out.println("Second Click");
                    System.out.println(endX + " , " + endY);

                    Piece[][] gameArr = game.getGameBoardArray();
                     /* 
                            
                            write the stuff about moving the king and checking if the move prevents check here


                    */
                    if(gameLogic.castleDetection(gameArr, startX, startY, endX, endY)){
                        visualCastleChanges(gameArr, game, window); 
                    }
                    /*
                     * Checks if the starting piece at startX and startY of gameArr (game class array) is a 
                     * valid color. This prevents errors when selecting blank squares
                     */
                    else if(isColorAndTurnCorrectWhite(gameArr, game)){                     
                        makeChangesWhite(gameArr, game, window);
                        startX = -1;
                        startY = -1;
                        playerTurn(game);
                    } else if(isColorAndTurnCorrectBlack(gameArr, game)){
                        makeChangesBlack(gameArr, game, window);
                        startX = -1;
                        startY = -1;
                        playerTurn(game);
                    } else if(gameArr[startY][startX].getColor() == null){
                        /*
                         * Game detection for a not picking a valid starting square
                         * eg. picking a square with no pieces on it
                         */
                        System.out.println(THERMINAL_TEXT_YELLOW + "select valid square" + THERMINAL_TEXT_RESET);
                    } else {
                        System.out.println(THERMINAL_TEXT_RED + "Wrong color" + THERMINAL_TEXT_RESET);
                    }     
                    // *old* resets mouse click here
                }
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                
            }
             
            @Override
            public void mouseReleased(MouseEvent e) {
                
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                
            }

            @Override
            public void mouseExited(MouseEvent e) {
                
            }
        });
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setVisible(true);
                     
    }
    /**
     * Returns a array with the images split up
     * @return
     */
    public static Image[] getImgArray(){
        Image retArr[] = new Image[12];
        final int IMAGE_ALL_PIECES_WIDTH = 1200;
        final int IMAGE_ALL_PIECES_HEIGHT = 400;
        final int SUBIMAGE_SIZE = 200;
        int img_index = 0;
        try{
            /*
             * Try case for uplaoding the image using Imgur. Will work as long as their is a stable wifi
             * connection and Imgur is still hosting the image
             */
            BufferedImage allPieces = ImageIO.read(new File("chess.png")); 
            for(int y = 0; y < IMAGE_ALL_PIECES_HEIGHT; y += SUBIMAGE_SIZE){
                for(int x = 0; x < IMAGE_ALL_PIECES_WIDTH; x += SUBIMAGE_SIZE){
                    retArr[img_index] = allPieces.getSubimage(x, y, SUBIMAGE_SIZE, SUBIMAGE_SIZE).getScaledInstance(100 , 100, BufferedImage.SCALE_SMOOTH);
                    img_index++;
                }
            }
            System.out.println(THERMINAL_TEXT_GREEN + "Images split up successully" + THERMINAL_TEXT_RESET);
        } catch (Exception MalformedURLException){
            System.out.println(THERMINAL_TEXT_RED + "Image of game pieces could not print due to a URL exception. Trying file" + THERMINAL_TEXT_RESET);
            try{
                BufferedImage allPieces = ImageIO.read(new URL("https://i.imgur.com/qr1ZYFe.png"));
                for(int y = 0; y < IMAGE_ALL_PIECES_HEIGHT; y += SUBIMAGE_SIZE){
                    for(int x = 0; x < IMAGE_ALL_PIECES_WIDTH; x += SUBIMAGE_SIZE){
                        retArr[img_index] = allPieces.getSubimage(x, y, SUBIMAGE_SIZE, SUBIMAGE_SIZE).getScaledInstance(SCREEN_WIDTH / 8,SCREEN_HEIGHT / 8, BufferedImage.SCALE_SMOOTH);
                        img_index++;
                    }
                }   
                System.out.println(THERMINAL_TEXT_GREEN + "Images split up successully" + THERMINAL_TEXT_RESET);
            } catch(IOException ex){
                System.out.println(THERMINAL_TEXT_RED + "Image of game pieces could not print due to a FileIO exception" + THERMINAL_TEXT_RESET);
                
            }
        }
        return retArr;
    }
   
    public static void makeChangesWhite(Piece[][] gameArr, gameBoard game, JFrame window){
        if(gameLogic.returnValMove(game, gameArr, startY, startX, endY, endX)){              
            /**
            * makes changes to the visual swing JFrame. Changes the X and Y of the pieces to 
            the respective spots where they belong. A x and y instance variable for the piece 
            class had to implemented because changing the row and col would not properly display
            a moment in the JFrame 
            */                
            gameArr[startY][startX].setX(endX * SECTION_DIVIDER_INT - 5);
            gameArr[startY][startX].setY(endY * SECTION_DIVIDER_INT);
            window.repaint();
            /**
             * Makes changes to the gameBoard class. This keeps track of the piece and their positions.
             * This is a back end change and will not affect how the game is displayed visually
             */
            game.makeMove(startY, startX, endY, endX);
            if(gameLogic.colorInCheck(game, "black")){
                game.setKingCheckedBlack(true);
                System.out.println("King is checked");
                if(checkmate.checkForMate(gameArr, game)){
                    System.out.println("Checkmate");
                    //window.dispose();
                    //System.exit(0);
                    return;
                } else {
                    System.out.println("Not in checkmate");
                }
            } else {
                System.out.println("King is not checked");
            }
        } else {
            System.out.println(THERMINAL_TEXT_RED + "validMove false" + THERMINAL_TEXT_RESET);
        }
    }

    public static void makeChangesBlack(Piece[][] gameArr, gameBoard game, JFrame window){
        if(gameLogic.returnValMove(game, gameArr, startY, startX, endY, endX)){
            gameArr[startY][startX].setX(endX * SECTION_DIVIDER_INT);
            gameArr[startY][startX].setY(endY * SECTION_DIVIDER_INT);
            window.repaint();
            game.makeMove(startY, startX, endY, endX);
            //game.printGame();
            //KING CHECK
            /* 
            if(gameLogic.pieceThatMovedIsCheckingKing(gameArr, "white", endY, endX)){
                System.out.println("King is checked");
                game.setKingCheckedWhite(true);
            } else {
                System.out.println("King is not checked");
            }
            */
            if(gameLogic.colorInCheck(game, "white")){
                game.setKingCheckedWhite(true);
                System.out.println("King is checked");
                if(checkmate.checkForMate(gameArr, game)){
                    System.out.println("Checkmate");
                    //window.dispose();
                    //System.exit(0);
                    return;
                } else {
                    System.out.println("Not in checkmate");
                }
            } else {
                System.out.println("King is not checked");
            }
        } else {
            System.out.println(THERMINAL_TEXT_RED + "validMove false" + THERMINAL_TEXT_RESET);
        }
    }

    public static boolean isColorAndTurnCorrectWhite(Piece[][] gameArr, gameBoard game){
        return gameArr[startY][startX].getColor() != null && gameArr[startY][startX].getColor().equals("white") && game.getTurn() % 2 == 0;
    }

    public static boolean isColorAndTurnCorrectBlack(Piece[][] gameArr, gameBoard game){
        return gameArr[startY][startX].getColor() != null && gameArr[startY][startX].getColor().equals("black") && game.getTurn() % 2 == 1;
    }

    public static void visualCastleChanges(Piece[][] gameArr, gameBoard game, JFrame window){
        if(gameArr[startY][startX].getColor().equals("white")){
            game.castleWhite(startX, startY, endX, endY);
        } else {
            game.castleBlack(startX, startY, endX, endY);
        }
        window.repaint();
    }

    public static void playerTurn(gameBoard game){
        String playerGoing;
        if(game.getTurn() % 2 == 0){
            playerGoing = "white";
        } else {
            playerGoing = "black";
        }
        System.out.println("Player Turn: " + playerGoing);
    }

    /*
     * Returns the image index of a piece in the imgs array. See info.txt for exact index value for each piece
     */
    public static int getImgIndex(Piece piece){
        int index = 0;

        if(piece.getType().equals("king") && piece.getColor().equals("white")){
            index = 0;
        } else if(piece.getType().equals("queen") && piece.getColor().equals("white")){
            index = 1;
        } else if(piece.getType().equals("bishop") && piece.getColor().equals("white")){
            index = 2;
        } else if(piece.getType().equals("knight") && piece.getColor().equals("white")){
            index = 3;
        } else if(piece.getType().equals("rook") && piece.getColor().equals("white")){
            index = 4;
        } else if(piece.getType().equals("pawn") && piece.getColor().equals("white")){
            index = 5;
        } else if(piece.getType().equals("king") && piece.getColor().equals("black")){
            index = 6;
        } else if(piece.getType().equals("queen") && piece.getColor().equals("black")){
            index  = 7;
        } else if(piece.getType().equals("bishop") && piece.getColor().equals("black")){
            index = 8;
        } else if(piece.getType().equals("knight") && piece.getColor().equals("black")){
            index  = 9;
        } else if(piece.getType().equals("rook") && piece.getColor().equals("black")){
            index = 10;
        } else if(piece.getType().equals("pawn") && piece.getColor().equals("black")){
            index = 11;
        } else {
            index = 12;
        }
        return index;
    }
    /**
     * Returns the pieces at a x and y position that is clicked on
     * PreCondition : parameters
     * PostCondition : return piece
     * 
     * @param x
     * @param y
     * @param arr
     * @return piece or null
     */
    public static Piece getPieceAt(int x, int y, Piece[][] arr){
        int XPOS = x / SECTION_DIVIDER_INT;
        int YPOS = y / SECTION_DIVIDER_INT;
        
        for(Piece[] row : arr){
            for(Piece piece : row){
                if(piece.getRow() == YPOS && piece.getCol() == XPOS && !piece.getType().equals("--")){
                    return piece;
                }
            }
        }
        return null;
    }
    //clears consel screen
    public static void clearThermialScreen() {  
        final String CLEAR_SCREEN_TEXT = "\033[H\033[2J";
        
        System.out.print(CLEAR_SCREEN_TEXT);  
        System.out.flush();  
    }
}