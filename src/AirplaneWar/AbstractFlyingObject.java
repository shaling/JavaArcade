package AirplaneWar;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

public abstract class AbstractFlyingObject {
    protected int x;// x coordinate
    protected int y;// y coordinate
    protected int width;//the width of the image
    protected int height;//the height of the image
    protected Image image;//the image of the object
    private boolean isDead;

    public  void paint(Graphics g){
        g.drawImage(image, x, y, null);

    }
    
    public boolean isHit(AbstractFlyingObject fly){
        if (fly.y>=0) {
            // Area a = new Area(new Rectangle2D.Double(x, y, width, height));
            // a.intersect(new Area(new Rectangle2D.Double(fly.x, fly.y, fly.width, fly.height)));
            // return !a.isEmpty();
            Rectangle rect1 = new Rectangle(x, y, width, height);
            Rectangle rect2 = new Rectangle(fly.x, fly.y, fly.width, fly.height);
            return rect1.intersects(rect2);
        }else{
            return false;
        }

    }
    public boolean isDead(){
        return isDead;
    }
    public void setDead(){
        isDead = true;
    }
    public abstract void move();
    // public abstract boolean outOfBounds();
}
