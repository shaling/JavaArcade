package ChessPackage;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.RenderingHints;

import piece.Bishop;
import piece.King;
import piece.Knight;
import piece.Pawn;
import piece.Piece;
import piece.Queen;
import piece.Rook;


public class GamePanel extends JPanel implements Runnable{

    public static GamePanel Instance;

    public static final int width = 1100;
    public static final int height = 800;
    final int fps = 60;
    Thread gameThread;
    ChessBoard board = new ChessBoard();
    Mouse mouse = new Mouse();
    JButton exitButton = new JButton("Exit Game");
    JButton restartButton = new JButton("Restart");

    // Pieces 
    public static ArrayList<Piece> pieces = new ArrayList<>(); //back up list
    public static ArrayList<Piece> simPieces = new ArrayList<>();
    ArrayList<Piece> promoPieces = new ArrayList<>();
    Piece activeP, checkingP;
    public static Piece castlingP;

    // Color
    public static final int white = 0;
    public static final int black = 1;
    int currentColor = white;

    // Booleans
    boolean canMove;
    boolean validSquare;
    boolean promotion;
    boolean gameOver;
    boolean stalemate;

    public GamePanel(){
        if(Instance != null){
            ExitChess.CloseChess();
            pieces = new ArrayList<>();
            simPieces = new ArrayList<>();
        }

        setPreferredSize(new Dimension(width,height));
        setBackground(Color.BLACK);

        addMouseMotionListener(mouse);
        addMouseListener(mouse);

        setPieces(white, 6, 7);
        setPieces(black, 1, 0);
        copyPieces(pieces, simPieces);

        Instance = this;
    }

    public void launchGame(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    // colour would be 0 for white team and 1 for black team, back row is for other pieces after pawn
    // White team pawn row is 6 and back row is 7
    // Black team pawn row is 1 and back row is 0
    public void setPieces(int colour, int pawn, int back){  
        for(int i = 0; i < 8; i++){
            pieces.add(new Pawn(colour, i,pawn));  
        }
        pieces.add(new Knight(colour, 1,back));
        pieces.add(new Knight(colour, 6,back));

        pieces.add(new Rook(colour, 0,back));
        pieces.add(new Rook(colour, 7,back));

        pieces.add(new Bishop(colour,2,back));
        pieces.add(new Bishop(colour, 5,back));

        pieces.add(new Queen(colour, 3,back));
        pieces.add(new King(colour, 4,back));

    }

    private void copyPieces (ArrayList<Piece> source, ArrayList<Piece> target){
        target.clear();
        for(int i = 0; i< source.size(); i++){
            target.add(source.get(i));
        }
    }

    @Override
    public void run() {

        // Game Loop is a sequence of processes that run continuosly as long as the game is running. 
        double drawInterval = 100000000/fps;
        double delta = 0;
        long lastTime = System.nanoTime(); // we use System.nanoTime() to meause the elapsed time and call update and repaint methods once everu 1/60 of a second
        long currentTime;

        while(gameThread != null){

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if(delta >= 1){
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update(){
        if(promotion){
            promoting();
        }else if(!gameOver && !stalemate){
            /// Mouse Button pressed ////
            if(mouse.pressed){
                if(activeP == null){
                //If the activeP is null, check if you can pick up a piece
                    for(Piece piece : simPieces){
                        // If the mouse is on ally piece, pick it up as the activeP
                        if(piece.color == currentColor && 
                            piece.col == mouse.x/ChessBoard.square_size &&
                            piece.row == mouse.y/ChessBoard.square_size){
                            activeP = piece;
                        }
                    }
                }else{
                // if a player is holding a piece, simulate the move "Thinking phase"
                    simulate();
                }
            }

            // Mouse Button released ////
            if(!mouse.pressed){
                if(activeP != null){
                    if(validSquare){
                        // Move confirmed ///
                        // Update the piece list in case a piece has been captured and removed
                        copyPieces(simPieces, pieces);
                        activeP.updatePosition();
                        if(castlingP != null){
                            castlingP.updatePosition();
                        }
                        if(isKingInCheck() && isCheckmate()){
                            gameOver = true;
                        }else if(isStalemate() && !isKingInCheck()){
                            stalemate = true;
                        }
                        else{ // The game is still going on
                            if(canPromote()){
                                promotion = true;
                            }else{
                                changePlayer();
                            }
                            // Simulation phase is over
                        }
                    }else{
                        // The move is not valid, so reset everything
                        copyPieces(pieces, simPieces); //restore the original list
                        activeP.resetPosition();
                        activeP = null;
                    }
                }
            }
        }
    }

    private void simulate(){
        canMove = false;
        validSquare = false;

        // Reset the piece list in every loop
        // For restoring the removed piece during the simulation
        copyPieces(pieces, simPieces);

        // Reseting the castling piece's position
        if(castlingP != null){
            castlingP.col = castlingP.preCol;
            castlingP.x = castlingP.getX(castlingP.col);
            castlingP = null;
        }

        // if a piece is being held, update its position
        activeP.x = mouse.x - ChessBoard.half_square_size;
        activeP.y = mouse.y - ChessBoard.half_square_size;
        activeP.col = activeP.getCol(activeP.x);
        activeP.row = activeP.getRow(activeP.y);

        // check if a piece is over a reachable square
        if(activeP.canMove(activeP.col, activeP.row)){
            canMove = true;

            // if hitting a piece, 
            if(activeP.hittingPiece != null){ // if returns an object (Piece), then it was hit and has to be removed from pieces list
                //remove it from the list
                simPieces.remove(activeP.hittingPiece.getIndex());
            }
            checkCastling();
            if(!isIllegal(activeP) && !opponentCanCaptureKing()){
                validSquare = true;
            }
        }
    }

    private boolean isIllegal(Piece king){
        if(king.type == Type.KING){
            for(Piece piece : simPieces){
                if(piece != king && piece.color != king.color 
                    && piece.canMove(king.col, king.row)){
                    return true;
                }
            }
        }
        return false;
    }
    private boolean opponentCanCaptureKing(){
        Piece king = getKing(false); // getting the current colored King

        for(Piece piece : simPieces){
            if(piece.color != king.color && piece.canMove(king.col, king.row)){ //checks if there is a piece that can move to the king's square
                return true;
            }
        }
        return false;
    }

    private boolean isKingInCheck() {
        Piece king = getKing(true);
        if(activeP.canMove(king.col, king.row)){ //checks if the active piece can move where the opponent king is
            checkingP = activeP; // sets that piece as a checking piece
            return true;
        }else{
            checkingP = null;
        }
        return false;
    }

    private Piece getKing(boolean opponent){
        Piece king = null;

        for(Piece piece : simPieces){
            if(opponent){
                if(piece.type == Type.KING && piece.color != currentColor){
                    king = piece; //returns opponents king
                }
            }else{
                if(piece.type == Type.KING && piece.color == currentColor){
                    king = piece; //returns your king
                }
            }
        }
        return king;
    }

    private boolean isCheckmate(){
        Piece king = getKing(true); // get opponent king

        if(kingCanMove(king)){  // passes opponent king, check if it can move in any of the directions
            return false; // if it can move, then it is not a checkmate
        }else{ //means it cannot move
            // still has a chance if there is any piece to block the attack
            // Checks the position of the checking piece and the king in check
            int colDiff = Math.abs(checkingP.col - king.col);
            int rowDiff = Math.abs(checkingP.row - king.row);

            if(colDiff == 0){
                // The checking piece is attacking vertically

                if(checkingP.row < king.row){
                    // The checking piece is above the king
                    for(int row = checkingP.row; row < king.row; row++){
                        for(Piece piece : simPieces){
                            if(piece != king && piece.color != currentColor 
                                && piece.canMove(checkingP.col, row)){ //this piece can block the attack
                                    return false;
                            }
                        }
                    }
                }else if(checkingP.row > king.row){
                    // The checking piece is below the king
                    for(int row = checkingP.row; row > king.row; row--){
                        for(Piece piece : simPieces){
                            if(piece != king && piece.color != currentColor 
                                && piece.canMove(checkingP.col, row)){ 
                                    return false;
                            }
                        }
                    }
                }
            }else if(rowDiff == 0){
                // The checking piece is attacking horizontally

                if(checkingP.col < king.col){
                    // The checking piece is to the left
                    for(int col = checkingP.col; col < king.col; col++){
                        for(Piece piece : simPieces){
                            if(piece != king && piece.color != currentColor 
                                && piece.canMove(col, checkingP.row)){ //this piece can block the attack
                                    return false;
                            }
                        }
                    }
                }else if(checkingP.col > king.col){
                    // The checking piece is to the right
                    for(int col = checkingP.col; col > king.col; col--){
                        for(Piece piece : simPieces){
                            if(piece != king && piece.color != currentColor 
                                && piece.canMove(col, checkingP.row)){ //this piece can block the attack
                                    return false;
                            }
                        }
                    }
                }
            }else if(colDiff == rowDiff){
                // The checking piece is attacking diagonally

                if(checkingP.row < king.row){
                    // The checking piece is above the king

                    if(checkingP.col < king.col){
                        // The checking piece is in the upper left
                        for(int col = checkingP.col, row = checkingP.row; col < king.col; col++, row++){
                            for(Piece piece : simPieces){
                                if(piece != king && piece.color != currentColor 
                                    && piece.canMove(col, row)){ //this piece can block the attack
                                    return false;
                                }
                            }
                        }
                    }
                    if(checkingP.col < king.col){
                            // The checking piece is in the upper right
                            for(int col = checkingP.col, row = checkingP.row; col > king.col; col--, row++ ){
                                for(Piece piece : simPieces){
                                    if(piece != king && piece.color != currentColor 
                                        && piece.canMove(col, row)){ //this piece can block the attack
                                        return false;
                                    }
                                }
                            }
                    }
                }
                if(checkingP.row > king.row){
                    // The checking piece is below the king

                    if(checkingP.col < king.col){
                        // The checking piece is in the lower left
                        for(int col = checkingP.col, row = checkingP.row; col < king.col; col++, row-- ){
                            for(Piece piece : simPieces){
                                if(piece != king && piece.color != currentColor 
                                    && piece.canMove(col, row)){ //this piece can block the attack
                                    return false;
                                }
                            }
                        }
                    }
                    if(checkingP.col > king.col){
                        // The checking piece is in the lower right
                        for(int col = checkingP.col, row = checkingP.row; col > king.col; col--, row-- ){
                            for(Piece piece : simPieces){
                                if(piece != king && piece.color != currentColor 
                                    && piece.canMove(col, row)){ //this piece can block the attack
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    private boolean kingCanMove(Piece king){

        // Simulate if there is any square where the king can move to
        if(isValidMove(king, -1, -1)){
            return true;
        }
        if(isValidMove(king, 0, -1)){
            return true;
        }
        if(isValidMove(king, 1, -1)){
            return true;
        }
        if(isValidMove(king, -1, 0)){
            return true;
        }
        if(isValidMove(king, 1, 0)){
            return true;
        }
        if(isValidMove(king, -1, 1)){
            return true;
        }
        if(isValidMove(king, 0, 1)){
            return true;
        }
        if(isValidMove(king, 1, 1)){
            return true;
        }
        return false;
    }

    private boolean isValidMove(Piece king, int colPlus, int rowPlus){
        boolean isValidMove = false;
        //Update the king's position for a second
        king.col += colPlus;
        king.row += rowPlus;

        if(king.canMove(king.col, king.row)){ // checks if the king can move to the square
            if(king.hittingPiece != null){      //if can, checks if its hitting any piece
                simPieces.remove(king.hittingPiece.getIndex()); //if it does, removes it from the list
            }
            if(!isIllegal(king)){
                isValidMove = true;
            }
        }
        // Reset the king's position, and restore the removed piece
        king.resetPosition();
        copyPieces(pieces, simPieces);

        return isValidMove;
    }

    private boolean isStalemate(){
        int count = 0;
        //Count the number of pieces
        for(Piece piece : simPieces){
            if(piece.color != currentColor){
                count++;
            }
        }
        // If only one piece (the king) is left
        if(count == 1){
            if(!kingCanMove(getKing(true))){ // there is no place to move for the king
                return true;
            }
        }
        return false;
    }

    private void promoting() {
        if(mouse.pressed){
            for(Piece piece : promoPieces){
                if((piece.col == mouse.x/ChessBoard.square_size) && (piece.row == mouse.y/ChessBoard.square_size)){

                    switch (piece.type) {
                        case ROOK:
                        simPieces.add(new Rook(currentColor, activeP.col, activeP.row)); 
                            break;

                        case KNIGHT:
                        simPieces.add(new Knight(currentColor, activeP.col, activeP.row)); 
                            break;

                        case BISHOP:
                        simPieces.add(new Bishop(currentColor, activeP.col, activeP.row)); 
                            break;

                        case QUEEN:
                        simPieces.add(new Queen(currentColor, activeP.col, activeP.row)); 
                            break;

                        default:
                            break;
                    }
                    simPieces.remove(activeP.getIndex());
                    copyPieces(simPieces, pieces); //update the back up list as well
                    activeP = null;
                    promotion = false;
                    changePlayer();
                }
            }
        }
    }

    private void checkCastling(){
        if(castlingP != null){
            if(castlingP.col == 0){ // rook on the left
                castlingP.col += 3; // moves 3 squares
            }else if(castlingP.col == 7){ // rook on the right
                castlingP.col -= 2; // moves 2 squares
            }
            castlingP.x = castlingP.getX(castlingP.col);
        }
    }

    private void changePlayer(){
        if(currentColor == white){
            currentColor = black;
            // Reset black's 2 stepped status
            for(Piece piece : pieces){
                if(piece.color == black){
                    piece.twoStepped = false;
                }
            }
        }else{
            currentColor = white;
            // Reset white's 2 stepped status
            for(Piece piece : pieces){
                if(piece.color == white){
                    piece.twoStepped = false;
                }
            }
        }
        activeP = null;
    }

    public boolean canPromote(){
        if(activeP.type == Type.PAWN){
            if(currentColor == white && activeP.row == 0 || currentColor == black && activeP.row == 7){
                promoPieces.clear();
                promoPieces.add(new Rook(currentColor, 9, 2));
                promoPieces.add(new Knight(currentColor, 9, 3));
                promoPieces.add(new Bishop(currentColor, 9, 4));
                promoPieces.add(new Queen(currentColor, 9, 5));
                return true;
            }
        }
        return false;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        //Board
        Graphics2D g2 = (Graphics2D) g;
        board.draw(g2);

        //Pieces
        for(Piece p: simPieces){
            p.draw(g2);
        }

        if(activeP != null){ 
            if(canMove){
                if(isIllegal(activeP) || opponentCanCaptureKing()){ //the square becomes grey if the king cannot move to that square 
                    g2.setColor(Color.gray);                        // or the king is in check, because then no other piece can be moved
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
                    g2.fillRect((activeP.col * ChessBoard.square_size), 
                                (activeP.row * ChessBoard.square_size), 
                                (ChessBoard.square_size), (ChessBoard.square_size));
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                }else{
                    //marks half transparent the square where the piece has been moved to, 
                    //and only if a piece allowed to move there
                    g2.setColor(Color.white);
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
                    g2.fillRect((activeP.col * ChessBoard.square_size), 
                                (activeP.row * ChessBoard.square_size), 
                                (ChessBoard.square_size), (ChessBoard.square_size));
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                }
                
            }
            //Draw the active piece in the end so it won't be hidden by the board or the colored square
            activeP.draw(g2);
        }

        // Status messages
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setFont(new Font("Book Antiqua", Font.PLAIN, 40));
        g2.setColor(Color.WHITE);

        if(promotion){
            g2.drawString("Promote to:",840,150);
            for(Piece piece : promoPieces){
                g2.drawImage(piece.image, piece.getX(piece.col), piece.getY(piece.row), 
                            ChessBoard.square_size, ChessBoard.square_size, null);
            }
        }else{
            if(currentColor == white){
                g2.drawString("White's turn", 840,550);
                if(checkingP != null && checkingP.color == black){
                    g2.setColor(Color.red);
                    g2.drawString("The King", 840,650);
                    g2.drawString("is in check!", 840,700);
                }
            }else{
                g2.drawString("Black's turn", 840,250);
                if(checkingP != null && checkingP.color == white){
                    g2.setColor(Color.red);
                    g2.drawString("The King", 840,100);
                    g2.drawString("is in check!", 840,150);
                }
            }
        }
        if(gameOver){
            WhenGameEnds(g2);
            String str = "";
            if(currentColor == white){
                str = "White Wins";
            }else{
                str = "Black Wins";
            }
            g2.setFont(new Font("Arial", Font.PLAIN, 90));
            g2.setColor(Color.green);
            g2.drawString(str,200,420);
        }
        if(stalemate){
            WhenGameEnds(g2);
            g2.setFont(new Font("Arial", Font.PLAIN, 90));
            g2.setColor(Color.GRAY);
            g2.drawString("Stalemate",200,420);
        }
    }

    boolean once = true;
    void WhenGameEnds(Graphics g2){
        if(once){
            once = false;
            //display exit
            exitButton.addActionListener( e -> ExitChess.CloseChess());
            exitButton.setFont(new Font("Arial", Font.PLAIN, 40));
            exitButton.setBounds(0, 500, 400, 100);
            exitButton.setVisible(true);
            this.add(exitButton);

            //display restart
            restartButton.addActionListener( e -> new StartChess());
            restartButton.setFont(new Font("Arial", Font.PLAIN, 40));
            restartButton.setBounds(400, 500, 400, 100);
            restartButton.setVisible(true);
            this.add(restartButton);
            paint(g2);

            gameThread = null;

            
            


        }
    }
}
