package ChessGame.piece;

import ChessGame.ChessPackage.GamePanel;
import ChessGame.ChessPackage.Type;

public class Bishop extends Piece {

    public Bishop(int colour, int col, int row) {
        super(colour, col, row);

        type = Type.BISHOP;

        if(color == GamePanel.white){
            image = getImage("w-bishop");
        }else{
            image = getImage("b-bishop");
        }
    }

    @Override
    public boolean canMove(int targetCol, int targetRow){
        if(isWithinBoard(targetCol, targetRow) && !isSameSquare(targetCol, targetRow)){
            // Bishop is moving diagonally, the ratio is always 1:1
            if(Math.abs(targetCol - preCol) == Math.abs(targetRow - preRow)){
                if(isValidSquare(targetCol, targetRow) && !pieceIsOnDiagonalLine(targetCol, targetRow)){
                    return true;
                }
            }
        }
        return false;
    }

}
