package AirplaneWar;
import java.awt.Graphics;
import javax.swing.ImageIcon;
/*
 * This class is used to create the bullet
 */
public class Bullet extends AbstractFlyingObject{
    private int speed;// the speed of the bullet
    private int direction;// the direction of the bullet, -1:up 1:down
    public Bullet(int x, int y,String imagePath,int direction, int speed){
        this.image = new ImageIcon(imagePath).getImage();// import the image of the bullet
        this.width = image.getWidth(null);//  get the width of the image
        this.height = image.getHeight(null);// get the height of the image
        this.x = x;// set the x coordinate of the bullet
        this.y = y;// set the y coordinate of the bullet
        this.speed = speed;// set the speed of the bullet
        this.direction = direction;
    }
    @Override
    public void move(){
        y += speed*direction;
    }

    public boolean outOfBounds(){
        if (direction == 1) {
            return this.y >= Background.bgHeight;
            
        }else{
            return this.y <= -Background.bgHeight;
        }
    }

    
}
