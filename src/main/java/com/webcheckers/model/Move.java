package com.webcheckers.model;

public class Move {
    public enum Move_Type { SIMPLE, JUMP, ERROR }

    private enum MOVE_COLOR { RED, WHITE, EMPTY }

    private Position start;
    private Position end;

    /**
     * Move constructor
     * @param start declares the starting position
     * @param end declares the end position
     */
    public Move( Position start, Position end ) {
        this.start = start;
        this.end = end;
    }

    /**
     * Checks the piece and returns the type of move the piece was
     * @param piece the piece to be checked
     * @return the type of move the piece made
     */
    public Move_Type typeOfMove( Piece piece ) {
        int rowDifference = this.end.getRow() - this.start.getRow();
        int colDifference = this.end.getCell() - this.start.getCell();
        Move_Type moveType;
        MOVE_COLOR moveColor;

        boolean difference = Math.abs(rowDifference) == Math.abs(colDifference);


        System.out.println("Row: " + rowDifference);
        System.out.println("Col: "+ colDifference);
        System.out.println("Diff: " + difference);



        if(difference) {
            switch (rowDifference) {
                case 1:
                    moveType = Move_Type.SIMPLE;
                    moveColor = MOVE_COLOR.RED;
                    break;
                case 2:
                    moveType = Move_Type.JUMP;
                    moveColor = MOVE_COLOR.RED;
                    break;
                case -1:
                    moveType = Move_Type.SIMPLE;
                    moveColor = MOVE_COLOR.WHITE;
                    break;
                case -2:
                    moveType = Move_Type.JUMP;
                    moveColor = MOVE_COLOR.WHITE;
                    break;
                default:
                    System.out.println("Error Type!");
                    moveType = Move_Type.ERROR;
                    moveColor = MOVE_COLOR.EMPTY;
            }
        } else {
            System.out.println("Error Type!");
            return Move_Type.ERROR;
        }

        if( piece.getType() == Piece.Type.SINGLE ) { // if a single piece is moving the correct direction
            Piece.Color pieceColor = piece.getColor();
            if( pieceColor == Piece.Color.WHITE && moveColor != MOVE_COLOR.WHITE ) {
                return Move_Type.ERROR;
            }
            if( pieceColor == Piece.Color.RED && moveColor != MOVE_COLOR.RED ) {
                return Move_Type.ERROR;
            }
        }

        /*
         * assume either:
         *      Piece is single and moving correct direction
         *      Piece is king and can move any direction
         */

        return moveType;
    }

    /**
     * The starting position of the move
     * @return the position
     */
    public Position getStart() {
        return this.start;
    }

    /**
     * The ending position of the move
     * @return the position
     */
    public Position getEnd() {
        return this.end;
    }

    /**
     * Gets the row value from the start position
     * @return the row form the start position
     */
    public int getStartRow() {
        return this.start.getRow();
    }

    /**
     * Gets the cell value from the start position
     * @return the cell from the start position
     */
    public int getStartCell() {
        return this.start.getCell();
    }

    /**
     * Gets the row value from the end position
     * @return the row from the end position
     */
    public int getEndRow() {
        return this.end.getRow();
    }

    /**
     * Gets the cell value from the end position
     * @return the cell from the end position
     */
    public int getEndCell() {
        return this.end.getCell();
    }

    public boolean validSimpleMove( BoardView board ) {
        Space[][] checkerBoard = board.getBoard();

        return ( !checkerBoard[getStartRow()][getStartCell()].isValid()
                && checkerBoard[getEndRow()][getEndCell()].isValid() );
    }

    public Position validSimpleJump( BoardView board ) {
        Position between = this.start.between( this.end );
        Space[][] checkerBoard = board.getBoard();

        if ( !checkerBoard[getStartRow()][getStartCell()].isValid() && // checks if piece at start
                !checkerBoard[between.getRow()][between.getCell()].isValid() && // checks if piece in between
                checkerBoard[getEndRow()][getEndCell()].isValid() ) { // checks if no piece at end

            Piece betweenPiece = checkerBoard[between.getRow()][between.getCell()].getPiece();
            Piece startingPiece = checkerBoard[getStartRow()][getStartCell()].getPiece();

            if (betweenPiece.getColor() != startingPiece.getColor()) { // checks if pieces are different color
                return between;
            }
        }
        return null;
    }

}