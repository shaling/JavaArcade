package ChessPackage;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import GameLaunch.JavaArcade;

public class ExitChess {
    public static void CloseChess(){

        // If the player chooses "Return to Menu", show the main menu
        JFrame frame = (JFrame)SwingUtilities.getRoot(GamePanel.Instance);
        if(frame != null){
            frame.dispose();
            new JavaArcade();
        }
        else{
            System.out.println("Win is null");
        }

        if(frame == null){
            System.out.println("frame is null");
        }



    }
}
