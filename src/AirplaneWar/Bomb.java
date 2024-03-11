package AirplaneWar;

import java.awt.Graphics;
import javax.swing.ImageIcon;

public class Bomb extends AbstractFlyingObject {
    private int duration;// the duration of the bomb

    public Bomb(int x, int y) {
        this.image = new ImageIcon("lib/bomb.png").getImage();// import the image of the bomb
        this.x = x;
        this.y = y;
        this.width = image.getWidth(null);//  get the width of the image
        this.height = image.getHeight(null);// get the height of the image
        this.duration = 5;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
    @Override
    public void move() {
        // do nothing
    }



}
