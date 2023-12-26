import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;

import java.net.*;
import java.io.*;

import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;


public class chessmain{
    public static void main(String[] args) throws Exception {
        //contasts
        final int SCREEN_WIDTH = 800;
        final int SCREEN_HEIGHT = 800;
        //Sets up game in thermal and initalizes a gameBoard        
        gameBoard game = new gameBoard();
        game.printGame();
        System.out.println("Gameboard successfully initialized");

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
                    imgs[img_index] = allPieces.getSubimage(x, y, SUBIMAGE_SIZE, SUBIMAGE_SIZE).getScaledInstance(100,100, BufferedImage.SCALE_SMOOTH);
                    img_index++;
                }
            }
            System.out.println("Images split up successully");
        } catch (Exception MalformedURLException){
            System.out.println("Image of game pieces could not print due to a URL exception");
        }
         

        //Swing stuff
        JFrame window = new JFrame();
        window.setSize(SCREEN_WIDTH,SCREEN_HEIGHT + 24);
        window.setLocationRelativeTo(null);
        JPanel panel = new JPanel(){
            @Override
            public void paint(Graphics g){
                boolean whiteSquare = true;
                for(int x = 0; x < 8; x++){
                    for(int y = 0; y <8; y++){
                        if(whiteSquare){
                            g.setColor(new Color(235, 235, 208));
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

                //Ask Mr. Swiryn about how I can turn this into a method
                
                Piece[][] gameBoardArray = game.getGameBoardArray();
                for(Piece[] row : gameBoardArray){
                    for(Piece piece : row){
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
                        
                        if(index < 12){
                            g.drawImage(imgs[index], piece.getCol() * 100, piece.getRow() * 100, this);
                        }
                        
                        
                    }
                }
                //------------------------------------------------------------------------------------------------------------------------------------------
                
                
            }
        };
        window.add(panel);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setVisible(true);
                     
    }

    public static void clearThermialScreen() {  
        final String CLEAR_SCREEN_TEXT = "\033[H\033[2J";
        
        System.out.print(CLEAR_SCREEN_TEXT);  
        System.out.flush();  
    }
}