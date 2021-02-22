package com.webcheckers.model;

public class Piece {

    public enum Type { SINGLE, KING }
    public enum Color { RED, WHITE }

    private Type type;
    private Color color;

    /**
     * Constructor for piece
     * @param pieceType the type of this piece
     * @param pieceColor the color of this piece
     */
    public Piece( Type pieceType ,Color pieceColor) {
        this.type = pieceType;
        this.color = pieceColor;
    }

    /**
     * The type of this piece
     * Enumeration values must exactly match case and spelling
     * an enumeration of { SINGLE or KING }
     * @return the type of piece
     */
    public Type getType() {
        return this.type;
    }

    /**
     * The color of this piece
     * Enumeration values must exactly match case and spelling
     * an enumeration of { RED or WHITE }
     * @return the color of the piece
     */
    public Color getColor() {
        return this.color;
    }


    @Override
    public boolean equals(Object obj){
        if (this == obj)return true;
        if (!(obj instanceof Piece))return false;
        Piece that = (Piece)obj;
        return that.color.equals(this.color) && that.type.equals(this.type);
    }

    @Override
    public int hashCode(){
        return (int)Math.pow(this.color.hashCode() , this.type.hashCode());
    }



}
