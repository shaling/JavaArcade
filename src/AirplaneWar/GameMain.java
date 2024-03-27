package AirplaneWar;

import javax.swing.*;
import javax.sound.sampled.*;
import java.io.File;

import GameLaunch.JavaArcade;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class GameMain extends JPanel {

    private Player player = new Player();
    private Background bg = new Background();
    public ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    public ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    public ArrayList<Bullet> enemyBullets = new ArrayList<Bullet>();
    public ArrayList<Bomb> bombs = new ArrayList<Bomb>();
    private int score = 0;
    private int state;// 0:start 1:running 2:game over
    private int enemyCount1 = 0;
    private int enemyCount2 = 0;
    private int bullet_player = 0;
    private int bullet_enemy = 0;
    private Thread gmThread;
    private JFrame gmwindow;
    private Clip clip;

    private void playSound(String filepath){
        try {
            File soudFile = new File(filepath);
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(soudFile);
            clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            System.out.println("Error with playing sound.");
            e.printStackTrace();

        }
    }


    public void moveAll() {
        bg.move();
        for (Bullet bullet : bullets) {
            bullet.move();
        }
        for (Enemy enemy : enemies) {
            enemy.move();
        }
        for (Bullet bullet : enemyBullets) {
            bullet.move();
        }
    }

    public void createBullet() {
        bullet_player++;
        if (bullet_player % 20 == 0) {
            Bullet bullet = player.createBullet();
            bullets.add(bullet);
            bullet_player = 0;
        }
        bullet_enemy++;
        for (Enemy enemy : enemies) {
            if (enemy.getEnemyType() == 1) {
                if (bullet_enemy % 30 == 0) {
                    Bullet bullet = enemy.createBullet();
                    enemyBullets.add(bullet);
                }
            }
        }
        }

    public void createEnemy1() {
        enemyCount1++;
        if (enemyCount1 % 50 == 0) {
            Enemy enemy1 = new Enemy("lib/enemy1.png");
            Enemy enemy2 = new Enemy("lib/enemy2.png");
            enemies.add(enemy1);
            enemies.add(enemy2);
            enemyCount1 = 0;
        }
    }

    public void createEnemy2() {
        enemyCount2++;
        if (enemyCount2 % 500 == 0) {
            Enemy enemy3 = new Enemy("lib/boss.png");
            enemies.add(enemy3);
            enemyCount2 = 0;
        }
    }

    public void removeBullet() {
        for (int i = 0; i < bullets.size(); i++) {
            if (bullets.get(i).outOfBounds() || bullets.get(i).isDead()) {
                bullets.remove(i);
            }
        }
        // Remove the enemy bullets
        for (int i = 0; i < enemyBullets.size(); i++) {
            if (enemyBullets.get(i).outOfBounds() || enemyBullets.get(i).isDead()) {
                enemyBullets.remove(i);
            }
        }
    }

    public void removeEnemy() {
        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).outOfBounds() || enemies.get(i).isDead()) {
                enemies.remove(i);
            }
        }
    }

    public void removeBomb() {
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);
            if (bomb.getDuration() <= 0) {
                bombs.remove(i);
                i--; // Adjust the index because we just removed an element
            } else {
                bomb.setDuration(bomb.getDuration() - 1); // Decrease the duration of the bomb
            }
        }
    }

    public void checkHit() {
        for (int i = 0; i < bullets.size(); i++) {
            for (int j = 0; j < enemies.size(); j++) {
                if (bullets.get(i).isHit(enemies.get(j))) {
                    bullets.get(i).setDead();
                    enemies.get(j).setDead();
                    score += 5;
                    bombs.add(new Bomb(enemies.get(j).x, enemies.get(j).y));
                }
            }
        }
        for (int i = 0; i < enemyBullets.size(); i++) {
            if (player.isHit(enemyBullets.get(i))) {
                enemyBullets.get(i).setDead();
                player.setDead();
            }
        }
        for (Enemy enemy : enemies) {
            if (player.isHit(enemy)) {
                enemy.setDead();
                player.setDead();
            }
        }
        for(int i = 0; i <bullets.size();i++){
            for(int j = 0; j < enemyBullets.size();j++){
                if(bullets.get(i).isHit(enemyBullets.get(j))){
                    bullets.get(i).setDead();
                    enemyBullets.get(j).setDead();
                }
            }
        }

    }

    public void checkGameOver() {
        if (player.isDead()) {
            state = 2;
            endGame();
        }

    }

    public void restartGame() {
        gmThread.interrupt();
        player = new Player();
        enemies = new ArrayList<Enemy>();
        bullets = new ArrayList<Bullet>();
        bombs = new ArrayList<Bomb>();
        enemyBullets = new ArrayList<Bullet>();
        score = 0;
        state = 0;
    }

    public void paintScore(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new java.awt.Font("Arial", 1, 20));
        g.drawString("Score:" + score, 10, 20);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        bg.drawBackground(g);
        player.paint(g);
        paintScore(g);
        for (Enemy enemy : enemies) {
            enemy.paint(g);
        }
        for (Bullet bullet : bullets) {
            bullet.paint(g);
        }
        for (Bullet bullet : enemyBullets) {
            bullet.paint(g);
        }
        for (Bomb bomb : bombs) {
            bomb.paint(g);
        }
//        if (state == 2) {
//            g.setColor(Color.red);
//            g.setFont(new java.awt.Font("Arial", 1, 40));
//            g.drawString("Game Over!", 200, 400);
//            g.drawString("Score:" + score, 200, 450);
//        }
        if(state == 0){
            g.setColor(Color.red);
            g.setFont(new java.awt.Font("Arial", 1, 40));
            g.drawString("Click to Start", 200, 400);
        }

    }

    public void initUi(JFrame window) {
        this.gmwindow = window;
        playSound("lib/bgm_airplane.wav");
        MouseAdapter ma = new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                if (state == 1) {
                    player.move(e.getX(), e.getY());
                }
            }

            public void mouseClicked(MouseEvent e) {
                if (state == 0) {
                    state = 1;
                } else if (state == 2) {
                    restartGame();
                }
            }
        };
        this.addMouseListener(ma);
        this.addMouseMotionListener(ma);
        gmThread = new Thread() {
            public void run() {
                while (true) {
                    if (state == 1) {
                        moveAll();
                        createBullet();
                        createEnemy1();
                        createEnemy2();
                        removeBullet();
                        removeEnemy();
                        removeBomb();
                        checkHit();
                        checkGameOver();
                    }
                    repaint();
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }
        };
        gmThread.start();

    }
    public void start() {
        JFrame jf = new JFrame("Airplane War");
        jf.setSize(bg.getBgWidth(), bg.getBgHeight());
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setResizable(false);
        jf.add(this);
        jf.setVisible(true);
        initUi(jf);
        // gmwindow = jf;
        
    }


    public void endGame() {
        // Show a dialog when the game ends
        int option = JOptionPane.showOptionDialog(null, 
                                                  "Game Over!  Your score is "+score+ "\nWhat do you want to do next?",
                                                  "Game Over", 
                                                  JOptionPane.YES_NO_OPTION, 
                                                  JOptionPane.QUESTION_MESSAGE, 
                                                  null, 
                                                  new Object[]{"Play Again", "Return to Menu"}, 
                                                  null);

        if (option == JOptionPane.YES_OPTION) {
            gmThread.interrupt();
            clip.stop();
            clip.close();
//            gmwindow.dispose();
            SwingUtilities.invokeLater(() -> {
                gmwindow.dispose();
                new GameMain().start();
            });
            // If the player chooses "Play Again", restart the game
//            start();
        } else {
            gmThread.interrupt();
            // If the player chooses "Return to Menu", show the main menu
            clip.stop();
            clip.close();
            SwingUtilities.invokeLater(() -> {
                gmwindow.dispose();
                new JavaArcade();
            });
        }
    }


    public static void main(String[] args) {
        GameMain gm = new GameMain();
        gm.start();

    }
}