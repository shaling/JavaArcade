package AirplaneWar;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Player extends AbstractFlyingObject {
    public static final int BULLET_SPEED = 3;
    
    public Player(){
        this.image = new ImageIcon("lib/player.png").getImage();
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
        this.x = Background.bgWidth/2 - width/2;
        this.y = Background.bgHeight - height*2;
    }
    
    public void move(int x, int y){
        this.x = x - width / 2;
        this.y = y - height/2;
        x = Math.max(x, 0);
        x = Math.min(x, Background.bgWidth - width);
        y = Math.max(y, 0);
        y = Math.min(y, Background.bgHeight - height);
        
        // if(this.x >= Background.bgWidth - width){
        //     this.x = Background.bgWidth - width;
        // }else if(this.x<=0){
        //     this.x = 0;
        // }
        
        // if(this.y>=Background.bgHeight-height){
        //     this.y = Background.bgHeight-height;
        // }else if(this.y<=0){
        //     this.y = 0;
        // }
    }
    
    public Bullet createBullet(){
        Bullet bullet = new Bullet(x+width/2-2, this.y-2,"lib/bullet_player.png",-1,BULLET_SPEED);
        return bullet;
    }
    @Override
    public void move(){
        // do nothing
    }

    public boolean outOfBounds(){
        return false;
    }

}
