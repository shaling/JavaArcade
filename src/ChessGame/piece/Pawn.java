package ChessGame.piece;

import ChessGame.ChessPackage.GamePanel;
import ChessGame.ChessPackage.Type;

public class Pawn extends Piece {
    
    public Pawn(int color, int col, int row){
        super(color, col, row);

        type = Type.PAWN;

        if(color == GamePanel.white){
            image = getImage("w-pawn");
        }else{
            image = getImage("b-pawn");
        }
    }

    @Override
    public boolean canMove(int targetCol, int targetRow){
        if(isWithinBoard(targetCol, targetRow) && !isSameSquare(targetCol, targetRow)){

            // Define the move value based on its color
            int moveValue;
            if(color == GamePanel.white){
                moveValue = -1;
            }else{
                moveValue = 1; //if black
            }

            //check the hitting piece
            hittingPiece = getHittingPiece(targetCol, targetRow);

            // 1 square moovement
            if(targetCol == preCol && targetRow == preRow + moveValue && hittingPiece == null){
                return true;
            }

            // 2 square movement
            if(targetCol == preCol && targetRow == preRow + moveValue*2 && hittingPiece == null 
                && moved == false && !pieceIsOnStraightLine(targetCol, targetRow)){
                return true;
            }

            // Diagonal movement & Capture (if a piece is on a square diagonally in from of it)
            if(Math.abs(targetCol - preCol) == 1 && targetRow == preRow + moveValue 
                && hittingPiece != null && hittingPiece.color != color){
                return true;
            }

            // En Passant
            if(Math.abs(targetCol - preCol) == 1 && targetRow == preRow + moveValue){
                for(Piece piece : GamePanel.simPieces){
                    if(piece.col == targetCol && piece.row == preRow && piece.twoStepped){
                        hittingPiece = piece;
                        return true;
                    }
                }
            } 
        }
        return false;
    }
}
