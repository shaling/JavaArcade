package AirplaneWar;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Image;
public class Background {
    private Image bg1;
    private Image bg2;
    private int y1;
    private int y2;
    private int speed;
    protected static int bgHeight=800;
    protected static int bgWidth=600;

    public Background(){
        // import the background image
        bg1 = new ImageIcon("lib/background.jpg").getImage();
        bg2 = new ImageIcon("lib/background.jpg").getImage();
        y1 = 0;
        y2 = -bgHeight;
        speed = 1;
    }
    public void drawBackground(Graphics g){
        // draw the background image
        g.drawImage(bg1, 0, y1,bgWidth,bgHeight,null);
        g.drawImage(bg2, 0, y2,bgWidth,bgHeight,null);
    }
    public void move(){
        y1 += speed;
        y2 += speed;
        if (y1>=bgHeight) {
            y1 = -bgHeight;
        }
        if (y2>=bgHeight) {
            y2 = -bgHeight;
        }
    }
    public  int getBgHeight(){
        return bgHeight;
    }
    public int getBgWidth(){
        return bgWidth;
    }

}
