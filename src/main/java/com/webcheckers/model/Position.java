package com.webcheckers.model;
import static com.webcheckers.model.BoardView.BOARD_DIMM;

public class Position {

    private int row;  // { 0 to 7 }
    private int cell;  // { 0 to 7 }

    /**
     * Constructor for position
     * @param row the row number
     * @param cell the cell number
     */
    public Position( int row, int cell ) {
        this.row = row;
        this.cell = cell;
    }

    /**
     * The row index of this position
     * @return an int from 0 to 7
     */
    public int getRow() {
        return this.row;
    }


    /**
     * The cell (column) index of this position
     * @return an int from 0 to 7
     */
    public int getCell() {
        return this.cell;
    }

    /**
     * Compares another position with this one
     * @param other position to be compared with
     *      0: if both positions are the same
     *      1: if the other position is larger
     *      -1: if this position is larger
     */
    public int compare( Position other ) {

        if ( this.equals( other ) ) {
            return 0;
        } else {
            if( this.row < other.getRow() ) {
                return 1;
            } else {
                return -1;
            }
        }

    }

    /**
     * Returns a new Position object that is in between this position and other position
     * @return a new Position object
     */
    public Position between( Position other ) {
        int difRow = ( other.getRow() - this.row ) / 2;
        int difCell = ( other.getCell() - this.cell ) / 2;


        return new Position( this.row + difRow, this.cell + difCell );
    }

    /**
     * Checks if this position is in bounds
     * @return
     */
    public boolean inBounds() {
        return this.row <= 7 && this.row >= 0 && this.cell <= 7 && this.cell >= 0;
    }

    public boolean equals( Object pos ) {
        if(pos == this) return true;
        if(!(pos instanceof Position)) return false;

        final Position that = (Position) pos;

        return this.row == that.getRow() && this.cell == that.getCell();
    }
}