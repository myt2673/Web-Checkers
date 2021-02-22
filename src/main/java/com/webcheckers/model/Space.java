package com.webcheckers.model;

public class Space {


    public enum Color { LIGHT, DARK }

    private int cellIdx;
    private Piece piece;
    private Color color;

    /**
     * General construtor for a space
     * @param cellIdx the cell number for the row
     * @param piece the piece on the space
     * @param color the color of the space
     */
    public Space( int cellIdx, Piece piece, Color color ) {
        this.cellIdx = cellIdx;
        this.piece = piece;
        this.color = color;
    }

    /**
     * The index of this space (a cell within a row) within the board
     * int from 0 to 7
     * @return cell index
     */
    public int getCellIdx() {
        return this.cellIdx;
    }

    /**
     * The method will return true if the space is a valid location to place a piece
     * That is, it is a dark square and has no other piece on it
     * @return if valid
     */
    public boolean isValid() {
        return ( this.color == Color.DARK && this.piece == null );
    }

    /**
     * The piece that resides on this space, if any
     * @return the piece
     */
    public Piece getPiece() {
        return this.piece;
    }

    /**
     * The color of this space
     * @return the color
     */
    public Color getColor(){
        return this.color;
    }

    /**
     * Sets a piece on a space.
     * @param piece the piece to be placed
     */
    public void setPiece( Piece piece ) {
        this.piece = piece;
    }

    /**
     * Copies over a space
     * @return the new space copied
     */
    public Space copySpace() {
        Piece tempPiece;
        if( this.piece != null ) {
            tempPiece = new Piece(this.piece.getType(),this.piece.getColor());
        } else {
            tempPiece = null;
        }
        return new Space( this.cellIdx, tempPiece, this.color );
    }

    public boolean validSpaceWithPiece( Player player ) {
        return ( getColor() == Color.DARK ) &&
                ( getPiece() != null ) &&
                ( player.playerColorToPieceColor() == getPiece().getColor() );

    }

    @Override
    public boolean equals( Object other ) {
        if (this == other)return true;
        if (!(other instanceof Space))return false;
        Space that = (Space)other;
        if( this.piece == null && that.piece == null ) {
            return true;
        }
        if( that.piece == null || this.piece == null ) {
            return false;
        }
        return this.color == that.color && this.piece.equals(that.piece);
    }
}
