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


public class chessmain{
    public static Piece[][] gameBoardArray;
    public static final int SCREEN_WIDTH = 800;
    final static int SCREEN_HEIGHT = 800;
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
        game.printGame();
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
        Image imgs[] = new Image[12];
        try{
            BufferedImage allPieces = ImageIO.read(new URL("https://i.imgur.com/qr1ZYFe.png"));   

            final int IMAGE_ALL_PIECES_WIDTH = 1200;
            final int IMAGE_ALL_PIECES_HEIGHT = 400;
            final int SUBIMAGE_SIZE = 200;

            int img_index = 0;

            for(int y = 0; y < IMAGE_ALL_PIECES_HEIGHT; y += SUBIMAGE_SIZE){
                for(int x = 0; x < IMAGE_ALL_PIECES_WIDTH; x += SUBIMAGE_SIZE){
                    imgs[img_index] = allPieces.getSubimage(x, y, SUBIMAGE_SIZE, SUBIMAGE_SIZE).getScaledInstance(SCREEN_WIDTH / 8,SCREEN_HEIGHT / 8, BufferedImage.SCALE_SMOOTH);
                    img_index++;
                }
            }
            System.out.println(THERMINAL_TEXT_GREEN + "Images split up successully" + THERMINAL_TEXT_RESET);
        } catch (Exception MalformedURLException){
            System.out.println(THERMINAL_TEXT_RED + "Image of game pieces could not print due to a URL exception" + THERMINAL_TEXT_RESET);
        }
         

        //Swing stuff
        JFrame window = new JFrame();
        window.setSize(SCREEN_WIDTH,SCREEN_HEIGHT + 24);
        window.setLocationRelativeTo(null);
        JPanel panel = new JPanel(){
            @Override
            public void paint(Graphics g){
                super.paintComponent(g);
                boolean whiteSquare = true;
                for(int x = 0; x < 8; x++){
                    for(int y = 0; y <8; y++){
                        if(whiteSquare){
                            try{
                                g.setColor(new Color(235, 235, 208));
                                //System.out.println(THERMINAL_TEXT_GREEN + "Square " + x + y + " drawn successully" + THERMINAL_TEXT_RESET);
                                
                            } catch(Exception e){
                                System.out.println(THERMINAL_TEXT_RED + "Could not load checkerboard due to color" + THERMINAL_TEXT_RESET);
                            }
                            
                            
                        } else {
                            g.setColor(new Color(119, 148, 85));
                        }
                        g.fillRect(x*(SCREEN_WIDTH / 8),y*(SCREEN_HEIGHT / 8), SCREEN_WIDTH / 8, SCREEN_HEIGHT / 8);
                        whiteSquare = !whiteSquare;
                    }
                    whiteSquare = !whiteSquare;                 
                }

                //------------------------------------------------------------------------------------------------------------------------------------------
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
                //------------------------------------------------------------------------------------------------------------------------------------------
                
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
                     * Checks if the starting piece at startX and startY of gameArr (game class array) is a 
                     * valid color. This prevents errors when selecting blank squares
                     */
                    if(gameArr[startY][startX].getColor() != null && gameArr[startY][startX].getColor().equals("white") && game.getTurn() % 2 == 0){                     
                        if(validMoves.returnValMove(gameArr, startY, startX, endY, endX)){                    
                            gameArr[startY][startX].setX(endX * SECTION_DIVIDER_INT);
                            gameArr[startY][startX].setY(endY * SECTION_DIVIDER_INT);
                            window.repaint();
                            //changes to game class
                            game.makeMove(startY, startX, endY, endX);
                            game.printGame();                        
                            if(validMoves.pieceThatMovedIsCheckingKing(gameArr, "white", endY, endX)){
                                System.out.println("King is checked");
                            }
                        } else {
                            System.out.println(THERMINAL_TEXT_RED + "validMove false" + THERMINAL_TEXT_RESET);
                        }                  
                    } else if(gameArr[startY][startX].getColor() != null && gameArr[startY][startX].getColor().equals("black") && game.getTurn() % 2 == 1){
                        if(validMoves.returnValMove(gameArr, startY, startX, endY, endX)){
                            gameArr[startY][startX].setX(endX * SECTION_DIVIDER_INT);
                            gameArr[startY][startX].setY(endY * SECTION_DIVIDER_INT);
                            window.repaint();
                            game.makeMove(startY, startX, endY, endX);
                            game.printGame();
                            if(validMoves.pieceThatMovedIsCheckingKing(gameArr, "white", endY, endX)){
                                System.out.println("King is checked");
                            }
                        } else {
                            System.out.println(THERMINAL_TEXT_RED + "validMove false" + THERMINAL_TEXT_RESET);
                        }
                    } else if(gameArr[startY][startX].getColor() == null){
                        System.out.println(THERMINAL_TEXT_YELLOW + "select valid square" + THERMINAL_TEXT_RESET);
                    } else {
                        System.out.println(THERMINAL_TEXT_RED + "Not a valid move" + THERMINAL_TEXT_RESET);
                    }
                    
                    //resets mouse click
                    startX = -1;
                    startY = -1;               
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

    public static Piece getPieceAt(int x, int y, Piece[][] arr){
        //Thank God for java rounding and casting
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

    public static void clearThermialScreen() {  
        final String CLEAR_SCREEN_TEXT = "\033[H\033[2J";
        
        System.out.print(CLEAR_SCREEN_TEXT);  
        System.out.flush();  
    }
}