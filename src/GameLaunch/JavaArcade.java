package GameLaunch;

// JavaArcade.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import AirplaneWar.GameMain;
import Snake.SnakeGameMain;
import ChessPackage.*;
public class JavaArcade extends JFrame {
    private static JFrame mainMenuWindow;
    public JavaArcade() {
        if (mainMenuWindow != null) {
            mainMenuWindow.dispose();
        }
        mainMenuWindow = this;
        setTitle("Java Arcade");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(896, 512);
        setResizable(false);

        setLocationRelativeTo(null);

        JPanel panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon bg = new ImageIcon("lib/arcade.png");
                g.drawImage(bg.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        panel.setLayout(null);
        setContentPane(panel);

        JButton snakeGameButton = new JButton("Snake Game");
        // change the font
        snakeGameButton.setFont(new Font("Arial", Font.PLAIN, 25));
        snakeGameButton.setBounds(348, 150, 200, 50);

        snakeGameButton.addActionListener(new ActionListener() {
            @Override
           public void actionPerformed(ActionEvent e) {
                SnakeGameMain.main(new String[0]);
            }
        });


        JButton AirplaneGameButton = new JButton("Airplane War Game");
        // change the font
        AirplaneGameButton.setFont(new Font("Arial", Font.PLAIN, 25));
        AirplaneGameButton.setBounds(348, 210, 200, 50);

        AirplaneGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameMain gm = new GameMain();
                gm.start();
            }
        });


        JButton g3GameButton = new JButton("Miniature Casino");
        // change the font
        g3GameButton.setFont(new Font("Arial", Font.PLAIN, 25));
        g3GameButton.setBounds(348, 270, 200, 50);

        g3GameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Start Miniature Casino");
            }
        });

        JButton g4GameButton = new JButton("PONG Game");
        // change the font
        g4GameButton.setFont(new Font("Arial", Font.PLAIN, 25));
        g4GameButton.setBounds(348, 330, 200, 50);

        g4GameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Start PONG Game");
            }
        });

        JButton g5GameButton = new JButton("Chess Engine");
        //change the font
        g5GameButton.setFont(new Font("Arial", Font.PLAIN, 25));
        g5GameButton.setBounds(348, 390, 200, 50);

        g5GameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StartChess();
            } 
        });


        add(snakeGameButton);
        add(AirplaneGameButton);
        add(g3GameButton);
        add(g4GameButton);
        add(g5GameButton);
        setVisible(true);
    }

    public static void main(String[] args) {
        new JavaArcade();
    }
}