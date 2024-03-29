package ChessGame.ChessPackage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class StartChess {


    public StartChess(){
        ImageIcon image = new ImageIcon("./res/piece/icon.jpg");

        JFrame window = new JFrame("Chess");
        window.setIconImage(image.getImage());
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setResizable(false);

        //Adds GamePanel to the window 
        GamePanel gp = new GamePanel();
        window.add(gp);
        window.pack();

        window.setLocationRelativeTo(null); 
        window.setVisible(true);

        gp.launchGame();
    }
}
