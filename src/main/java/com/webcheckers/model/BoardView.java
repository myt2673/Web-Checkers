package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class BoardView implements Iterable{

    public static final int BOARD_DIMM = 8;
    private final int RED_SIDE = 3;
    private final int WHITE_SIDE = 4;

    private Space[][] board;

    public BoardView(){
        SetUpGame();
        setUpPieces();
    }
    public BoardView(boolean setUpPieces){
        if(!setUpPieces){
            SetUpGame();
        } else {
            SetUpGame();
            setUpPieces();
        }
    }
    private void SetUpGame(){
        this.board = new Space[BOARD_DIMM][BOARD_DIMM];

        for( int r = 0; r < BOARD_DIMM; r++ ) {
            for( int c = 0; c < BOARD_DIMM; c++ ) {
                if(r % 2 == 0) {
                    if(c % 2 == 0) {
                        board[r][c] = new Space(c, null, Space.Color.LIGHT);
                    } else {
                        board[r][c] = new Space(c, null, Space.Color.DARK);
                    }

                } else {
                    if(c % 2 == 1) {
                        board[r][c] = new Space(c, null, Space.Color.LIGHT);
                    } else {
                        board[r][c] = new Space(c, null, Space.Color.DARK);
                    }

                }
            }
        }

    }

    /**
     * Sets up the pieces for both players
     */
    private void setUpPieces(){
        for( int r = 0; r < BOARD_DIMM; r++ ){
            for( int c = 0; c < BOARD_DIMM; c++ ){
                if( r < RED_SIDE) {
                    if( board[r][c].isValid() ) {
                        board[r][c].setPiece(new Piece(Piece.Type.SINGLE, Piece.Color.RED));
                    }
                }
                else if( r > WHITE_SIDE) {
                    if( board[r][c].isValid() ){
                        board[r][c].setPiece(new Piece(Piece.Type.SINGLE, Piece.Color.WHITE));
                    }
                }
            }
        }
    }

    /**
     * Sets up the board for a simple jump to win the game for demo
     */
    public void setUpEndGame() {
        this.board[4][5].setPiece(new Piece(Piece.Type.SINGLE, Piece.Color.RED));
        this.board[5][4].setPiece(new Piece(Piece.Type.SINGLE, Piece.Color.WHITE));
    }

    /**
     * Sets up the board for a multiple jump for demo
     */
    public void setUpMultiJump() {
        this.board[0][1].setPiece(new Piece(Piece.Type.SINGLE, Piece.Color.RED));
        this.board[1][2].setPiece(new Piece(Piece.Type.SINGLE, Piece.Color.WHITE));
        this.board[3][4].setPiece(new Piece(Piece.Type.SINGLE, Piece.Color.WHITE));
        this.board[5][6].setPiece(new Piece(Piece.Type.SINGLE, Piece.Color.WHITE));
    }

    /**
     * Sets up the board to king a piece for demo
     */
    public void setUpKingPiece() {
        this.board[6][1].setPiece(new Piece(Piece.Type.SINGLE, Piece.Color.RED));
        this.board[7][4].setPiece(new Piece(Piece.Type.SINGLE, Piece.Color.WHITE));
    }

    /**
     * Get the entire board
     * @return the entire board
     */
    public Space[][] getBoard() {
        return board;
    }

    public Space[] getRow(int rowNum){
        return board[rowNum];
    }

    public boolean equals(BoardView other) {
        for( int row = 0; row < BOARD_DIMM; row++ ) {
            for( int col = 0; col < BOARD_DIMM; col ++ ) {
                Space currentBoardSpace = this.board[row][col];
                Space otherSpace = other.getBoard()[row][col];
                if( !currentBoardSpace.equals(otherSpace) ) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Goes through all the pieces and checks to see if there is any pieces for a player left
     * @param color the color of pieces to be looked for
     * @return how many pieces are left for a player
     */
    private int playerPiecesLeft( Piece.Color color ) {
        int numOfPieces = 0;

        for( int row = 0; row < BOARD_DIMM; row++ ) {
            for( int col = 0; col < BOARD_DIMM; col++ ) {
                Piece piece = this.board[row][col].getPiece();
                if( piece != null && piece.getColor() == color ) {
                    numOfPieces++;
                }
            }
        }

        return numOfPieces;

    }

    public boolean finishedGame() {
        return playerPiecesLeft( Piece.Color.RED ) == 0 || playerPiecesLeft( Piece.Color.WHITE ) == 0;
    }

    public Player.PlayerColor winnerColor() {
        if( finishedGame() ) {
            if( playerPiecesLeft( Piece.Color.RED ) == 0 ) {
                return Player.PlayerColor.WHITE;
            } else {
                return Player.PlayerColor.RED;
            }
        } else {
            return Player.PlayerColor.NONE;
        }
    }

    public void copyBoard( BoardView source ) {
        Space[][] sourceBoard = source.getBoard();
        for( int row = 0; row < BOARD_DIMM; row++ ) {
            for( int col = 0; col < BOARD_DIMM; col++ ) {
                this.board[row][col] = sourceBoard[row][col].copySpace();
            }
        }
    }
    public static BoardView flipBoard(BoardView source){
        BoardView flipped = new BoardView(false);
        for(int r = 0; r < BOARD_DIMM; r++){
            for(int c  = 0; c < BOARD_DIMM; c++){
                if(source.board[r][c].getPiece() != null){
                    flipped.board[BOARD_DIMM - 1 -r][BOARD_DIMM - 1 -c].setPiece(source.board[r][c].getPiece());
                }
            }
        }
        return flipped;
    }

    public void kingPieces() {
        int row = 7;
        Piece piece;
        for( int col = 0; col < BOARD_DIMM; col++ ) {
            piece = this.board[row][col].getPiece();

            if( piece != null && piece.getColor() == Piece.Color.RED ) {
                this.board[row][col].setPiece( new Piece( Piece.Type.KING, Piece.Color.RED ) );
            }
        }

        row = 0;
        for( int col = 0; col < BOARD_DIMM; col++ ) {
            piece = this.board[row][col].getPiece();

            if( piece != null && piece.getColor() == Piece.Color.WHITE ) {
                this.board[row][col].setPiece( new Piece( Piece.Type.KING, Piece.Color.WHITE ) );
            }
        }

    }
    @Override
    public Iterator<Row> iterator(){
        Collection<Row> arrayList = new ArrayList<>();
        for ( int row = 0 ; row < BOARD_DIMM ; row++ ) {
            Space[] rowList = board[row];
            ArrayList<Space> list = new ArrayList<>(Arrays.asList(rowList));
            arrayList.add(new Row(row, list));
        }
        return arrayList.iterator();
    }
}
