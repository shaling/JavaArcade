package AirplaneWar;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.ImageIcon;
/*
 * This class is used to create the enemy airplane
 */

public class Enemy extends AbstractFlyingObject{
    private int speed;// the speed of the enemy airplane
    private int enemyType;
    private static final int BULLET_SPEED = 5;

    public Enemy(String imagePath){
        this.image = new ImageIcon(imagePath).getImage();
        // this.image = new ImageIcon("lib/enemy1.png").getImage();// import the image of the enemy airplane
        this.width = image.getWidth(null);//  get the width of the image
        this.height = image.getHeight(null);// get the height of the image
        
        // set the position of the enemy airplanes and make them appear at random positions in the window
        Random random = new Random();
        this.x = random.nextInt(Background.bgWidth - width/2);
        this.y = -Background.bgHeight;
        if (imagePath.equals("lib/boss.png")) {
            enemyType = 1;
            this.speed = 3;
        }else{
            enemyType = 0;
            this.speed = random.nextInt(2, 4);// set the speed of the enemy airplane
        }

    }
    @Override
    public void move(){
        y += speed;
    }
    public boolean outOfBounds(){
        return this.y >= Background.bgHeight;
    }
    public Bullet createBullet(){
        Bullet bullet = new Bullet(x+width/2, this.y+height-5,"lib/bullet_enemy.png",1,BULLET_SPEED);
        return bullet;
    }
    public int getEnemyType(){
        return enemyType;
    }
    

}
