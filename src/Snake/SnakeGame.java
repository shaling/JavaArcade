package Snake;

import javax.swing.*;

import GameLaunch.JavaArcade;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class SnakeGame extends JPanel implements ActionListener {
    private final int SIZE = 300;
    private final int DOT_SIZE = 10;
    private final int ALL_DOTS = 900;
    private int appleX;
    private int appleY;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int dots;
    private Timer timer;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;
    private JFrame frame;


    public SnakeGame() {
        initGame();
    }

    public void initGame() {
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 50 - i * 10;
            y[i] = 50;
        }
        locateApple();
        timer = new Timer(250, this);
        timer.start();
    }

    public void locateApple() {
        int r = (int) (Math.random() * SIZE / DOT_SIZE);
        appleX = ((r * DOT_SIZE));

        r = (int) (Math.random() * SIZE / DOT_SIZE);
        appleY = ((r * DOT_SIZE));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkCollision();
            move();
        }
        repaint();
    }

    public void checkApple() {
        if ((x[0] == appleX) && (y[0] == appleY)) {
            dots++;
            locateApple();
        }
    }

    public void checkCollision() {
        for (int i = dots; i > 0; i--) {
            if ((i > 4) && (x[0] == x[i]) && (y[0] == y[i])) {
                inGame = false;
            }
        }
        if (y[0] >= SIZE) {
            inGame = false;
        }
        if (y[0] < 0) {
            inGame = false;
        }
        if (x[0] >= SIZE) {
            inGame = false;
        }
        if (x[0] < 0) {
            inGame = false;
        }
        if (!inGame) {
            timer.stop();
            gameOver();
        }
    }

    public void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[(i - 1)];
            y[i] = y[(i - 1)];
        }
        if (left) {
            x[0] -= DOT_SIZE;
        }
        if (right) {
            x[0] += DOT_SIZE;
        }
        if (up) {
            y[0] -= DOT_SIZE;
        }
        if (down) {
            y[0] += DOT_SIZE;
        }
    }

    public void start() {
        frame = new JFrame();
        frame.setTitle("Snake");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 800);
        frame.add(this);
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();

                if ((key == KeyEvent.VK_LEFT) && (!right)) {
                    left = true;
                    up = false;
                    down = false;
                }

                if ((key == KeyEvent.VK_RIGHT) && (!left)) {
                    right = true;
                    up = false;
                    down = false;
                }

                if ((key == KeyEvent.VK_UP) && (!down)) {
                    up = true;
                    right = false;
                    left = false;
                }

                if ((key == KeyEvent.VK_DOWN) && (!up)) {
                    down = true;
                    right = false;
                    left = false;
                }
            }
        });
        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame) {
            g.setColor(Color.RED);
            g.fillRect(appleX, appleY, DOT_SIZE, DOT_SIZE);

            for (int i = 0; i < dots; i++) {
                g.setColor(Color.BLUE);
                g.fillRect(x[i], y[i], DOT_SIZE, DOT_SIZE);
            }
        } else {
            String msg = "Game Over";
            Font small = new Font("Helvetica", Font.BOLD, 14);
            FontMetrics metr = getFontMetrics(small);

            g.setColor(Color.BLACK);
            g.setFont(small);
            g.drawString(msg, (SIZE - metr.stringWidth(msg)) / 2, SIZE / 2);
        }
    }

    public void gameOver() {
        // Stop the timer
        timer.stop();

        // Show a dialog to let the player choose to play again or return to menu
        int option = JOptionPane.showOptionDialog(this,
                "Game Over! What do you want to do next?",
                "Game Over",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new Object[] { "Play Again", "Return to Menu" },
                null);

        if (option == JOptionPane.YES_OPTION) {
            // If the player chooses "Play Again", restart the game
            frame.dispose();
            new SnakeGame().start();
        } else {
            // If the player chooses "Return to Menu", close this window and show the main
            // menu
            frame.dispose();
           new JavaArcade();
        }
    }






    public static void main(String[] args) {
        new SnakeGame().start();
    }
}
