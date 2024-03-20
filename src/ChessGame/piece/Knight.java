package piece;

import ChessPackage.GamePanel;
import ChessPackage.Type;

public class Knight extends Piece {

    public Knight(int colour, int col, int row) {
        super(colour, col, row);

        type = Type.KNIGHT;

       if(color == GamePanel.white){
            image = getImage("w-horse");
        }else{
            image = getImage("b-horse");
        }
    }

    @Override
    public boolean canMove(int targetCol, int targetRow){
        if(isWithinBoard(targetCol, targetRow)){
            // the knight can move only if its ratio of col and row is 1:2 or 2:1
            if(Math.abs(targetCol - preCol) * Math.abs(targetRow - preRow) == 2){
                if(isValidSquare(targetCol, targetRow)){
                    return true;
                }
            }
        }
        return false;
    }
    

}
