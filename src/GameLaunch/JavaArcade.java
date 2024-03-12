package GameLaunch;

// JavaArcade.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import AirplaneWar.GameMain;
import Snake.SnakeGameMain;

public class JavaArcade extends JFrame {
    private static JFrame mainMenuWindow;
    public JavaArcade() {
        if (mainMenuWindow != null) {
            mainMenuWindow.dispose();
        }
        mainMenuWindow = this;
        setTitle("Java Arcade");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1));

            JButton snakeGameButton = new JButton("Snake Game");
        // change the font
        snakeGameButton.setFont(new Font("Arial", Font.PLAIN, 30));

        snakeGameButton.addActionListener(new ActionListener() {
            @Override
           public void actionPerformed(ActionEvent e) {
                SnakeGameMain.main(new String[0]);
            }
        });


        JButton AirplaneGameButton = new JButton("Airplane War Game");
        // change the font
        AirplaneGameButton.setFont(new Font("Arial", Font.PLAIN, 30));

        AirplaneGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameMain gm = new GameMain();
                gm.start();
            }
        });


        JButton g3GameButton = new JButton("Miniature Casino");
        // change the font
        g3GameButton.setFont(new Font("Arial", Font.PLAIN, 30));

        g3GameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Start Miniature Casino");
            }
        });

        JButton g4GameButton = new JButton("PONG Game");
        // change the font
        g4GameButton.setFont(new Font("Arial", Font.PLAIN, 30));

        g4GameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Start PONG Game");
            }
        });

        JButton g5GameButton = new JButton("2D Chess");
        //change the font
        g5GameButton.setFont(new Font("Arial", Font.PLAIN, 30));

        g5GameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Start 2D Chess");
            } 
        });


        add(snakeGameButton);
        add(AirplaneGameButton);
        add(g3GameButton);
        add(g4GameButton);
        setVisible(true);
    }

    public static void main(String[] args) {
        new JavaArcade();
    }
}