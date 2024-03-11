package GameLaunch;

// JavaArcade.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import AirplaneWar.GameMain;
import Snake.SnakeGame;

public class JavaArcade extends JFrame {
    private static JFrame mainMenuWindow;
    public JavaArcade() {
        if (mainMenuWindow != null) {
            mainMenuWindow.dispose();
        }
        mainMenuWindow = this;
        setTitle("Java Arcade");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1));

        JButton snakeGameButton = new JButton("Start Snake Game");
        snakeGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SnakeGame().start();
            }
        });

        JButton AirplaneGameButton = new JButton("Start Airplane War Game");
        AirplaneGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameMain gm = new GameMain();
                gm.start();
            }
        });
        JButton g3GameButton = new JButton("Game3");
        g3GameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Game3");
            }
        });

        JButton g4GameButton = new JButton("Game4");
        g4GameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Game4");
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