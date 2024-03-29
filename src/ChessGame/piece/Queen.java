package ChessGame.piece;

import ChessGame.ChessPackage.GamePanel;
import ChessGame.ChessPackage.Type;

public class Queen extends Piece{

    public Queen(int colour, int col, int row) {
        super(colour, col, row);
        
        type = Type.QUEEN;

        if(color == GamePanel.white){
            image = getImage("w-queen");
        }else{
            image = getImage("b-queen");
        }
    }

    @Override
    public boolean canMove(int targetCol, int targetRow){
        if(isWithinBoard(targetCol, targetRow) && !isSameSquare(targetCol, targetRow)){
            // Queen can move any number of squares up, down, and diagonal

            //for Vertical & Horizontal
            if(targetCol == preCol || targetRow ==  preRow){
                if(isValidSquare(targetCol, targetRow) && !pieceIsOnStraightLine(targetCol, targetRow)){
                    return true;
                }
            }

            //for Diagonal
            if(Math.abs(targetCol - preCol) == Math.abs(targetRow - preRow)){
                if(isValidSquare(targetCol, targetRow) && !pieceIsOnDiagonalLine(targetCol, targetRow)){
                    return true;
                }
            }
        }
        return false;
    }
}
