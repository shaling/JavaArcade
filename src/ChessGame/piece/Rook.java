package ChessGame.piece;

import ChessGame.ChessPackage.GamePanel;
import ChessGame.ChessPackage.Type;

public class Rook extends Piece{

    public Rook(int color, int col, int row){
        super(color, col, row);
        
        type = Type.ROOK;

        if(color == GamePanel.white){
            image = getImage("w-rook");
        }else{
            image = getImage("b-rook");
        }
    }

    @Override
    public boolean canMove(int targetCol, int targetRow){
        if(isWithinBoard(targetCol, targetRow) && !isSameSquare(targetCol, targetRow)){
            // the rook can move as long as its col or row is the same
            if(targetCol == preCol || targetRow ==  preRow){
                if(isValidSquare(targetCol, targetRow) && !pieceIsOnStraightLine(targetCol, targetRow)){
                    return true;
                }
            }
        }
        return false;
    }
    
}
