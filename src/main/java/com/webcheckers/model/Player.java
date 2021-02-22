package com.webcheckers.model;

public class Player {

    public enum PlayerColor{
        RED,
        WHITE,
        NONE
    }

    private String name;
    private PlayerColor playerColor;
    private boolean playing;

    /**
     * Constructor of player
     * @param name the name of the player
     */
    public Player(String name){
        this.name = name;
        playerColor = PlayerColor.NONE;
        this.playing = false;
    }

    /**
     * Gets the name of the player
     * @return the name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Distinguishes which player is joining a game by their color
     * @param playerColor the color's player to join the game
     */
    public void joinGame(PlayerColor playerColor){
        if( playerColor != PlayerColor.NONE ) {
            this.playerColor = playerColor;
            this.playing = true;
        }
    }

    /**
     * Allows the player to leave the game
     */
    public void leaveGame(){
        if ( this.isPlaying() ) {
            this.playerColor = PlayerColor.NONE;
            this.playing = false;
        }
    }

    /**
     * Gets the player's color
     * @return the player's color
     */
    public PlayerColor getPlayerColor() {
        return playerColor;
    }

    /**
     * Determine if the player is playing
     * @return true if is playing, false otherwise
     */
    public boolean isPlaying() {
        return this.playing;
    }

    /**
     * Returns a player's piece color
     * @return the player's piece color
     */
    public Piece.Color playerColorToPieceColor() {
        if( this.playerColor == PlayerColor.RED ) {
            return Piece.Color.RED;
        } else if( this.playerColor == PlayerColor.WHITE ) {
            return Piece.Color.WHITE;
        } else {
            return null;
        }
    }

    /**
     * Player equal's method
     * @param obj the ideal player object
     * @return true if they are the same, false otherwise
     */
    public boolean equals(Object obj) {
        if(obj == this) return true;
        if(!(obj instanceof Player)) return false;

        final Player that = (Player) obj;

        return this.name.equals(that.name);
    }

    /**
     * Create a unique hashcode
     * @return the unique hashcode
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString(){
        return "Player Name: " + getName();
    }
}
