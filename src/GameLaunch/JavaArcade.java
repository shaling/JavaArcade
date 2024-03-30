package GameLaunch;

// JavaArcade.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import AirplaneWar.GameMain;
import Snake.SnakeGameMain;
import javax.swing.border.Border;
import ChessGame.ChessPackage.*;
import MiniCasinoPackage.*;
import pong.PongGame;

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
        Border raisedBevel = BorderFactory.createRaisedBevelBorder();
        Border loweredBevel = BorderFactory.createLoweredBevelBorder();
        Border compound = BorderFactory.createCompoundBorder(raisedBevel, loweredBevel);
        Color semiTransparent = new Color(3, 161, 248, 158);


        JButton snakeGameButton = new JButton("Snake Game");
        // change the font
        snakeGameButton.setFont(new Font("Arial", Font.PLAIN, 25));
        snakeGameButton.setBounds(348, 120, 200, 50);
        snakeGameButton.setBorder(compound);
        snakeGameButton.setBackground(semiTransparent);
        snakeGameButton.setForeground(Color.WHITE);
        snakeGameButton.setOpaque(true);

        snakeGameButton.addActionListener(new ActionListener() {
            @Override
           public void actionPerformed(ActionEvent e) {
                SnakeGameMain.main(new String[0]);
            }
        });


        JButton AirplaneGameButton = new JButton("AirplaneWar");
        // change the font
        AirplaneGameButton.setFont(new Font("Arial", Font.PLAIN, 25));
        AirplaneGameButton.setBounds(148, 210, 200, 50);
        AirplaneGameButton.setBorder(compound);
        AirplaneGameButton.setBackground(semiTransparent);
        AirplaneGameButton.setForeground(Color.WHITE);
        AirplaneGameButton.setOpaque(true);
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
        g3GameButton.setBounds(548, 210, 200, 50);
        g3GameButton.setBorder(compound);
        g3GameButton.setBackground(semiTransparent);
        g3GameButton.setForeground(Color.WHITE);
        g3GameButton.setOpaque(true);

        g3GameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MiniCasino.main(new String[]{});
            }
        });

        JButton g4GameButton = new JButton("PONG Game");
        // change the font
        g4GameButton.setFont(new Font("Arial", Font.PLAIN, 25));
        g4GameButton.setBounds(148, 330, 200, 50);
        g4GameButton.setBorder(compound);
        g4GameButton.setBackground(semiTransparent);
        g4GameButton.setForeground(Color.WHITE);
        g4GameButton.setOpaque(true);

        g4GameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PongGame.main(new String[]{});

            }
        });

        JButton g5GameButton = new JButton("Chess Engine");
        //change the font
        g5GameButton.setFont(new Font("Arial", Font.PLAIN, 25));
        g5GameButton.setBounds(548, 330, 200, 50);
        g5GameButton.setBorder(compound);
        g5GameButton.setBackground(semiTransparent);
        g5GameButton.setForeground(Color.WHITE);
        g5GameButton.setOpaque(true);

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