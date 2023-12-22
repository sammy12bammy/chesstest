import java.util.Scanner;

public class chessmain {
    public static void main(String[] args) throws Exception {
        gameBoard game = new gameBoard();
        Scanner in = new Scanner(System.in);

        game.printGame();
 
        while(!game.getEndGame()){
            String start;
            String end;

            System.out.println("Enter start");
            start = in.nextLine();
            System.out.println("Enter end");
            end = in.nextLine();

            clearScreen();

            if(start.equals("stop")){
                in.close();
                break;
            }

            game.makeMove(start,end);
            game.printGame();
            game.printCapturedPiece();

        }  
            
    }

    public static void clearScreen() {  
        final String CLEAR_SCREEN_TEXT = "\033[H\033[2J";
        
        System.out.print(CLEAR_SCREEN_TEXT);  
        System.out.flush();  
    }
}