//SnakeGame
package Snake;

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.font.TextLayout;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class SnakeGame extends JPanel implements ActionListener{

    private final int width;
    private final int height;
    private final int cellSize;
    private final Random random = new Random();
    private final List<GamePoint> snake = new ArrayList<>();
    private static final int FRAME_RATE = 20;
    private int highScore;
    private int speed;
    private float hue = 0.0f;
    private boolean gameStarted = false;
    private boolean gameOver = false;
    private boolean isAccelerating = false;
    private Timer timer;
    private Clip introMusicClip;
    private Clip backgroundMusicClip;
    private Clip acceleratingMusicClip;
    private ImageIcon backgroundImage;
    private ImageIcon foodImage;
    private ImageIcon gameBackgroundImage;
    private GamePoint food;
    private Direction direction = Direction.RIGHT;
    private Direction newDirection = Direction.RIGHT;

    public SnakeGame(final int width, final int height) {
        this.width = width;
        this.height = height;
        this.cellSize = width / (FRAME_RATE * 2);

        setPreferredSize(new Dimension(width, height));
        setBackground(new Color(127, 255,212));

        speed = 1000 / FRAME_RATE * 3;
        timer = new Timer(speed, this);
    }

    protected void paintComponent(final Graphics graphics) {
        super.paintComponent(graphics);

        if (!gameStarted) {
            drawStartScreen(graphics);
        } else {
            drawGameScreen(graphics);
            if (gameOver) {
                drawGameOverScreen(graphics);
            }
        }
    }

    private void printMessage(final Graphics graphics, final String message) {
        graphics.setColor(Color.decode("#f38630"));
        graphics.setFont(graphics.getFont().deriveFont(30F));
        int currentHeight = height / 3;
        final var graphics2D = (Graphics2D) graphics;
        final var frc = graphics2D.getFontRenderContext();
        for (final var line : message.split("\n")) {
            final var layout = new TextLayout(line, graphics.getFont(), frc);
            final var bounds = layout.getBounds();
            final var targetWidth = (float) (width - bounds.getWidth()) / 2;
            layout.draw(graphics2D, targetWidth, currentHeight);
            currentHeight += graphics.getFontMetrics().getHeight();
        }
    }

    private void drawStartScreen(Graphics graphics) {
        backgroundImage = new ImageIcon("lib/Background.png");
        graphics.drawImage(backgroundImage.getImage(), 0, 0, width, height, this);
        printMessage(graphics, "Press Enter & Begin Game");
    }

    private void drawGameScreen(Graphics graphics) {
        gameBackgroundImage = new ImageIcon("lib/Laurentian.png");
        foodImage = new ImageIcon("lib/GoldCoin.png");

        graphics.setColor(new Color(127, 255, 212));
        graphics.fillRect(0, 0, width, height);
        graphics.drawImage(gameBackgroundImage.getImage(), 0, 0, width, height, this);

        graphics.setColor(Color.cyan);
        graphics.drawImage(foodImage.getImage(), food.x, food.y, cellSize, cellSize, this);
        drawSnake(graphics);
        drawScore(graphics, snake.size() - 1);
    }

    private void drawGameOverScreen(Graphics graphics) {
        final int currentScore = snake.size() - 1;
        if (currentScore > highScore) {
            highScore = currentScore;
        }
        printMessage(graphics, "Your Score: " + currentScore
                + "\nHighest Score: " + highScore
                + "\nPress Enter & Play Again");
    }

    public static Clip playMusic(String filePath) {
        Clip clip = null;
        try {
            File musicFile = new File(filePath);
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicFile);
            clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        return clip;
    }

    private void handleKeyEvent(final int keyCode) {
        if (!gameStarted) {
            if (keyCode == KeyEvent.VK_ENTER) {
                gameStarted = true;
                if (introMusicClip != null) {
                    introMusicClip.stop();
                }
                if (backgroundMusicClip == null) {
                    backgroundMusicClip = playMusic("lib/GamingBackgroundMusic.wav");
                } else {
                    backgroundMusicClip.start();
                }
            }
        } else if (!gameOver) {
            switch (keyCode) {
                case KeyEvent.VK_UP:
                    if (direction != Direction.DOWN) {
                        newDirection = Direction.UP;
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != Direction.UP) {
                        newDirection = Direction.DOWN;
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != Direction.LEFT) {
                        newDirection = Direction.RIGHT;
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    if (direction != Direction.RIGHT) {
                        newDirection = Direction.LEFT;
                    }
                    break;
            }
            if (keyCode == KeyEvent.VK_SPACE && !isAccelerating) {
                isAccelerating = true;
                speed = (int)(1000 / FRAME_RATE * 0.5);
                timer.setDelay(speed);

                if (backgroundMusicClip != null) {
                    backgroundMusicClip.stop();
                }
                if (acceleratingMusicClip == null) {
                    acceleratingMusicClip = playMusic("lib/EngineSoundEffect.wav");
                } else {
                    acceleratingMusicClip.start();
                }
            }
        } else if (keyCode == KeyEvent.VK_ENTER) {
            gameStarted = true;
            gameOver = false;
            resetGameData();
        }
    }

    public void startGame() {
        introMusicClip = playMusic("lib/Funny.wav");

        resetGameData();
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        requestFocusInWindow();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(final KeyEvent e) {
                handleKeyEvent(e.getKeyCode());
            }

            @Override
            public void keyReleased(final KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    isAccelerating = false;
                    speed = 1000 / FRAME_RATE * 3;
                    timer.setDelay(speed);

                    if (acceleratingMusicClip != null) {
                        acceleratingMusicClip.stop();
                    }
                    if (!gameOver && backgroundMusicClip != null) {
                        backgroundMusicClip.start();
                        backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY); // Ensure it loops continuously
                    }
                }
            }
        });
        timer.start();
    }

    private void drawSnake(Graphics graphics) {
        for (int i = 0; i < snake.size(); i++) {
            GamePoint point = snake.get(i);
            Color snakeColor = Color.getHSBColor(hue, 1.0f, 1.0f);
            graphics.setColor(snakeColor);
            graphics.fillOval(point.x, point.y, cellSize, cellSize);

            if (i == 0) {
                graphics.setColor(Color.BLACK);
                int eyeSize = cellSize / 4;
                int eyeOffset = cellSize / 4;
                switch (direction) {
                    case UP:
                        graphics.fillOval(point.x + eyeOffset, point.y + eyeOffset, eyeSize, eyeSize);
                        graphics.fillOval(point.x + 2 * eyeOffset, point.y + eyeOffset, eyeSize, eyeSize);
                        break;
                    case DOWN:
                        graphics.fillOval(point.x + eyeOffset, point.y + 2 * eyeOffset, eyeSize, eyeSize);
                        graphics.fillOval(point.x + 2 * eyeOffset, point.y + 2 * eyeOffset, eyeSize, eyeSize);
                        break;
                    case LEFT:
                        graphics.fillOval(point.x + eyeOffset, point.y + eyeOffset, eyeSize, eyeSize);
                        graphics.fillOval(point.x + eyeOffset, point.y + 2 * eyeOffset, eyeSize, eyeSize);
                        break;
                    case RIGHT:
                        graphics.fillOval(point.x + 2 * eyeOffset, point.y + eyeOffset, eyeSize, eyeSize);
                        graphics.fillOval(point.x + 2 * eyeOffset, point.y + 2 * eyeOffset, eyeSize, eyeSize);
                        break;
                }
            }

            hue += 0.05;
            if (hue > 1.0f) {
                hue = 0.0f;
            }
        }
    }

    private void drawScore(Graphics graphics, int score) {
        graphics.setColor(Color.decode("#f38630"));
        graphics.setFont(graphics.getFont().deriveFont(20F));
        String scoreText = "Score: " + score + " (Tip: Use the Space bar to boost your speed)";
        graphics.drawString(scoreText, 10, 30);
    }

    private void playEatSound() {
        try {
            File soundFile = new File("lib/Yummy.wav");
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(soundFile));
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void generateFood() {
        do {
            food = new GamePoint(random.nextInt(width / cellSize) * cellSize,
                    random.nextInt(height / cellSize) * cellSize);
        } while (snake.contains(food));
    }

    private void playOverSound() {
        try {
            File soundFile = new File("lib/oops.wav");
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(soundFile));
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void resetGameData() {
        snake.clear();
        snake.add(new GamePoint(width / 2, height / 2));
        generateFood();
        speed = 1000 / FRAME_RATE * 3;
        timer.setDelay(speed);

        if (backgroundMusicClip != null) {
            backgroundMusicClip.start();
        }
    }

    private void move() {
        direction = newDirection;

        final GamePoint head = snake.getFirst();
        final GamePoint newHead = switch (direction) {
            case UP -> new GamePoint(head.x, head.y - cellSize);
            case DOWN -> new GamePoint(head.x, head.y + cellSize);
            case LEFT -> new GamePoint(head.x - cellSize, head.y);
            case RIGHT -> new GamePoint(head.x + cellSize, head.y);
        };
        snake.addFirst(newHead);

        if (newHead.equals(food)) {
            generateFood();
            playEatSound();
        } else if (isCollision()) {
            gameOver = true;
            playOverSound();
            snake.removeFirst();
        } else {
            snake.removeLast();
        }
    }

    private boolean isCollision() {
        final GamePoint head = snake.getFirst();
        final var invalidWidth = (head.x < 0) || (head.x >= width);
        final var invalidHeight = (head.y < 0) || (head.y >= height);
        if (invalidWidth || invalidHeight) {
            return true;
        }

        return snake.size() != new HashSet<>(snake).size();
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        if (gameStarted && !gameOver) {
            move();
            if (isAccelerating && !acceleratingMusicClip.isRunning()) {
                acceleratingMusicClip.setFramePosition(0);
                acceleratingMusicClip.start();
            }
        } else if (gameOver) {
            if (backgroundMusicClip != null) {
                backgroundMusicClip.stop();
            }
            if (acceleratingMusicClip != null) {
                acceleratingMusicClip.stop();
            }
        }
        repaint();
    }

    private record GamePoint(int x, int y) {
    }

    private enum Direction {
        UP, DOWN, RIGHT, LEFT
    }
}