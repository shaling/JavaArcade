package piece;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import ChessPackage.ChessBoard;
import ChessPackage.GamePanel;
import ChessPackage.Type;

import java.io.*;

public class Piece {

    public Type type;
    public BufferedImage image;
    public int x, y;
    public int col, row, preCol, preRow;
    public int color;
    public Piece hittingPiece;
    public boolean moved, twoStepped;

    public Piece(int colour, int col, int row){

        this.color = colour;
        this.col = col;
        this.row = row;
        x = getX(col);
        y = getY(row);
        preCol = col;
        preRow = row; 

    }

    public BufferedImage getImage(String imagePath){

        BufferedImage image = null;
        try{
            File file = new File("./res/piece/" + imagePath + ".png");
            image = ImageIO.read(file);
        }catch(IOException e){
            e.printStackTrace();
        }
        return image;
    }

    public int getX(int col){
        return col * ChessBoard.square_size;
    }

    public int getY(int row){
        return row * ChessBoard.square_size;
    }

    public int getCol(int x){
        // to put icon in the middle of the square
        return( x + ChessBoard.half_square_size)/ChessBoard.square_size; 
    }

    public int getRow(int y){
        return( y + ChessBoard.half_square_size)/ChessBoard.square_size;
    }

    public int getIndex(){

        for(int index = 0; index < GamePanel.simPieces.size(); index++){ //searches for an index of a piece in a list
            if(GamePanel.simPieces.get(index) == this){ //checks if the element in the list with that index (an object of the piece class)                            
                return index;                           //is the same as the piece this method was called to, and returns an index of it, if its true
            }
        }
        return 0;
    }

    public void updatePosition(){
        // To check En Passant
        if(type == Type.PAWN){
            if(Math.abs(row - preRow) == 2){
                twoStepped = true;
            }
        }
        x = getX(col);
        y = getY(row);
        preCol = getCol(x);
        preRow = getRow(y);
        moved = true;
    }

    public void resetPosition(){
        col = preCol;
        row = preRow;
        x = getX(col);
        y = getY(row);
    }

    public boolean canMove(int targetCol, int targetRow){
        return false;
    }

    public boolean isWithinBoard(int targetCol, int targetRow){
        if(targetCol >= 0 && targetCol <=7 && 
            targetRow >= 0 && targetRow <= 7 ){
            return true;
        }
        return false;
    }

    public Piece getHittingPiece(int targetCol, int targetRow){
        for(Piece piece : GamePanel.simPieces){
            //checks if there is a piece in the square where we are planning to move our piece
            if(piece.col == targetCol && piece.row == targetRow && piece != this){ 
                return piece;
            }
        }
        return null;
    }

    public boolean isSameSquare(int targetCol, int targetRow){
        if(targetCol == preCol && targetRow ==  preRow){
            return true;
        }
        return false;
    }

    public boolean isValidSquare(int targetCol, int targetRow){
        hittingPiece = getHittingPiece(targetCol, targetRow);

        if(hittingPiece == null){ // the square is empty
            return true; 
        }
        else{ //the square is taken by another piece
            if(hittingPiece.color != this.color ){ // if the color if different it can be taken
                return true;
            } else{ //if the color if the same, piece cannot move here
                hittingPiece = null;
            }
        }
        return false;
    }

    public boolean pieceIsOnStraightLine(int targetCol, int targetRow){
        // When this piece is moving to the left, checks if there is a piece in that square
        for(int c = preCol-1; c > targetCol; c--){
            for(Piece piece : GamePanel.simPieces){
                if(piece.col == c && piece.row == targetRow){
                    hittingPiece = piece;
                    return true;
                }
            }
        }

        // When this piece is moving to the right, checks if there is a piece in that square
        for(int c = preCol+1; c < targetCol; c++){
            for(Piece piece : GamePanel.simPieces){
                if(piece.col == c && piece.row == targetRow){
                    hittingPiece = piece;
                    return true;
                }
            }
        }

        // When this piece is moving up, checks if there is a piece in that square
        for(int r = preRow-1; r > targetRow; r--){
            for(Piece piece : GamePanel.simPieces){
                if((piece.row == r) && (piece.col == targetCol)){
                    hittingPiece = piece;
                    return true;
                }
            }
        }

        // When this piece is moving down, checks if there is a piece in that square
        for(int r = preRow+1; r < targetRow; r++){
            for(Piece piece : GamePanel.simPieces){
                if(piece.row == r && piece.col == targetCol){
                    hittingPiece = piece;
                    return true;
                }
            }
        }

        return false; // if none of them returned true then there is no piece on the way 
    }

    public boolean pieceIsOnDiagonalLine(int targetCol, int targetRow){
        if(targetRow < preRow){
            // Up left
            for(int c = preCol-1; c > targetCol; c--){
                int diff = Math.abs(c - preCol);
                for(Piece piece : GamePanel.simPieces){
                    if(piece.col == c && piece.row == preRow - diff){
                        hittingPiece = piece;
                        return true;
                    }
                } 
            }
            // Up right
            for(int c = preCol+1; c < targetCol; c++){
                int diff = Math.abs(c - preCol);
                for(Piece piece : GamePanel.simPieces){
                    if(piece.col == c && piece.row == preRow - diff){
                        hittingPiece = piece;
                        return true;
                    }
                } 
            }
        }
        
        if(targetRow > preRow){
            // Down left
            for(int c = preCol-1; c > targetCol; c--){
                int diff = Math.abs(c - preCol);
                for(Piece piece : GamePanel.simPieces){
                    if(piece.col == c && piece.row == preRow + diff){
                        hittingPiece = piece;
                        return true;
                    }
                } 
            }
            // Down right
            for(int c = preCol+1; c < targetCol; c++){
                int diff = Math.abs(c - preCol);
                for(Piece piece : GamePanel.simPieces){
                    if(piece.col == c && piece.row == preRow + diff){
                        hittingPiece = piece;
                        return true;
                    }
                } 
            }
        }
        return false;// if none of them returned true then there is no piece on the way
    }

    public void draw(Graphics2D g2){
        g2.drawImage(image,x,y, ChessBoard.square_size, ChessBoard.square_size, null);
    }
}
