package ChessGame.ChessPackage;

import java.awt.Color;
import java.awt.Graphics2D;

public class ChessBoard {

    final int max_col = 8;
    final int max_row = 8;
    public static final int square_size = 100;
    public static final int half_square_size = square_size/2;

    public void draw(Graphics2D g2){
        int c = 0;
        for( int row = 0; row < max_row; row++){
            for(int col = 0; col< max_col; col++){
                if(c == 0 ){
                    g2.setColor(new Color(210,165,125));
                    c = 1;
                } else{
                    g2.setColor(new Color(175,115,70));
                    c = 0;
                }
                g2.fillRect(col*square_size, row*square_size, square_size,square_size); // x, y, width, height
            }
            if(c == 0){
                c = 1;
            }else{
                c = 0;
            }
        }
    }
}
