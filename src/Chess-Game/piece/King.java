package piece;

import ChessPackage.GamePanel;
import ChessPackage.Type;

public class King extends Piece{

    public King(int colour, int col, int row) {
        super(colour, col, row);

        type = Type.KING;

        if(color == GamePanel.white){
            image = getImage("w-king");
        }else{
            image = getImage("b-king");
        }
    }

    @Override
    public boolean canMove(int targetCol, int targetRow){

        if(isWithinBoard(targetCol, targetRow)){
            //abs means absolute such as |-2| = 2 in case of an negative numbers, 
            //must equal 1 because King has range of only one square to move
            if(Math.abs(targetCol - preCol) + Math.abs(targetRow - preRow) == 1 ||
                Math.abs(targetCol - preCol) * Math.abs(targetRow - preRow) == 1 ){

                    if(isValidSquare(targetCol, targetRow)){ //if a king can reach the square and the square is valid, then the King can move
                        return true;
                    }
            }

            //----------------------Castling-------------------------------
            if(!moved){
                // right castling
                if(targetCol == preCol+2 && targetRow == preRow // checking if the target col is 2 more, and target row should be the same
                    && !pieceIsOnStraightLine(targetCol, targetRow)){ // cecking if there is no pieces in range of 2 squares to the right
                        for(Piece piece : GamePanel.simPieces){ // scanning the list for the pieces
                            if(piece.col == preCol+3 && piece.row == preRow && !piece.moved){ // if there is a piece and its 3 columns away, in the same row, and hasn't moved 
                                GamePanel.castlingP = piece;                             //then it means it's a rook since it's its starting position then we get this piece as castling piece
                                return true;
                            }
                    }
                }
                // left castling
                if(targetCol == preCol-2 && targetRow == preRow 
                    && !pieceIsOnStraightLine(targetCol, targetRow)){ // cecking if there is no pieces in range of 2 squares to the left
                        Piece p[] = new Piece[2]; // temporary array
                        for(Piece piece : GamePanel.simPieces){ 
                            if(piece.col == preCol-3 && piece.row == preRow ){ //position of the knight
                                p[0] = piece; // has to be empty
                            }
                            if(piece.col == preCol-4 && piece.row == preRow ){ 
                                p[1] = piece; // has to be rook and be in a starting position
                            }
                            if(p[0] == null &&  p[1] != null && p[1].moved == false){
                                GamePanel.castlingP = p[1];                             
                                return true;
                            }
                    }
                }
            }
        }
        return false;
    }
    
}
