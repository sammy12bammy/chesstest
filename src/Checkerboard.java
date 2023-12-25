import javax.swing.*;
import java.awt.*;

public class Checkerboard extends JFrame {
    private static final int SCREEN_WIDTH = 600;
    private static final int SCREEN_HEIGHT = 600;

    public Checkerboard() {
        setTitle("Chess by Sam Balent");
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel checkerboardPanel = new JPanel(new GridLayout(8, 8));
        checkerboardPanel.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JPanel square = new JPanel();
                if ((row + col) % 2 == 0) {
                    square.setBackground(Color.WHITE);
                } else {
                    square.setBackground(Color.BLACK);
                }
                checkerboardPanel.add(square);
            }
        }

        add(checkerboardPanel);
    }

    public void showBoard(){
        setVisible(true);
    }
}
