package com.webcheckers.model;

import java.util.*;

public class Game {
    private final Player redPlayer;
    private final Player whitePlayer;
    private Player activePlayer;
    private Player resignedPlayer;
    private final String gameId;
    private BoardView checkerBoard;
    private Stack<BoardView> previousMoves;
    private boolean validTurn;
    private final ArrayList<BoardView> previousTurns;
    private Piece.Color activeColor;
    private boolean gameFinished;
    public boolean isOver;

    private final HashMap<Player, BoardView> spectators;

    /**
     * Creates a new game instance
     * @param redPlayer the red player
     * @param whitePlayer the white player
     */
    public Game(Player redPlayer, Player whitePlayer, String gameId){
        this.redPlayer = redPlayer;
        this.activePlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        this.checkerBoard = setUpPreferences();
        this.previousMoves = new Stack<>();
        this.gameId = gameId;
        previousMoves.push(checkerBoard);
        this.resignedPlayer = null;
        this.validTurn = false;
        this.previousTurns = new ArrayList<>();
        previousTurns.add(checkerBoard);
        this.spectators = new HashMap<>();
        this.activeColor = Piece.Color.RED;
        this.gameFinished = false;
        this.isOver = false;
    }

    public Piece.Color getActiveColor() {
        return activeColor;
    }

    /**
     * Sets up certain preferences if anyone has a key in the their name
     * @return the initial turn
     */
    public BoardView setUpPreferences() {
        BoardView setUp = new BoardView(false);
        String keyName = getKeyNames();

        switch (keyName) {
            case "END GAME":
                setUp.setUpEndGame();
                break;
            case "MULTI JUMP":
                setUp.setUpMultiJump();
                break;
            case "KING PIECE":
                setUp.setUpKingPiece();
                break;
            default:
                setUp = new BoardView();
                break;
        }
        return setUp;
    }

    //    Accessors


    public Player getRedPlayer() {
        return this.redPlayer;
    }


    public Player getWhitePlayer() {
        return this.whitePlayer;
    }


    public BoardView getCheckerBoard(){
        return this.checkerBoard;
    }

    public BoardView getInvertedCheckerBoard(){
        return this.checkerBoard.flipBoard(checkerBoard);
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    /**
     * Gets the game Id of this game
     * @return the game Id
     */
    public String getGameId(){
        return gameId;
    }

    /**
     * Checks if a player is in this game
     * @param player the player to be examined
     * @return true if the player is in this game
     */
    public boolean isInGame(Player player) {
        return ((this.whitePlayer.equals(player)) || (this.redPlayer.equals(player)));
    }
    public void playerResigned(Player resignedPlayer){
        this.resignedPlayer = resignedPlayer;
    }

    /**
     * Gets the current player's color
     * @param currentPlayer the current player
     * @return the color of the current player
     */
    public Player.PlayerColor getPlayerColor( Player currentPlayer ){
        return currentPlayer.getPlayerColor();
    }

    /**
     * Gets the previous turns from this game
     * @return an array list of the previous turns
     */
    public ArrayList<BoardView> getPreviousTurns() {
        return this.previousTurns;
    }

    /**
     * Starts a game
     */
    public void initializeGame() {
        this.redPlayer.joinGame( Player.PlayerColor.RED );
        this.whitePlayer.joinGame( Player.PlayerColor.WHITE );
    }

    public boolean makeMove( Move move ) {
        BoardView turn = new BoardView();
        //CHANGED: 'getRecentedTurn()' to 'this.checkerboard' so that multiple single moves
        //are not longer allowed
        turn.copyBoard( this.previousMoves.peek() );
        Piece selectPiece = turn.getBoard()[move.getStartRow()][move.getStartCell()].getPiece();
        Move.Move_Type moveType = move.typeOfMove(selectPiece);

        //if( moveType == Move.Move_Type.ERROR ) {
         //   return false;
        //}

        switch ( moveType ) {
            case SIMPLE:
                if( move.validSimpleMove( turn ) && ( this.previousMoves.size() == 1 ) ) {
                    movePiece( move, turn, selectPiece );
                    this.previousMoves.push(turn);
                    this.validTurn = validateTurn();
                    return true;
                }
                break;
            case JUMP:
                Position between = move.validSimpleJump( turn );
                if( between != null ) {
                    movePiece( move, turn, selectPiece );
                    turn.getBoard()[between.getRow()][between.getCell()].setPiece( null );
                    this.previousMoves.push(turn);

                    Piece endPiece = previousMoves.peek().getBoard()[move.getEndRow()][move.getEndCell()].getPiece();
                    this.validTurn = !(canJump(move.getEnd(), previousMoves.peek(), getNeighbors(endPiece, move.getEndRow(), move.getEndCell())));
                    return true;
                }
                break;
            default:
                return false;
        }
        return false;
    }

    public void movePiece( Move move, BoardView turn, Piece piece ) {
        turn.getBoard()[move.getEndRow()][move.getEndCell()].setPiece( new Piece( piece.getType(), piece.getColor() ) );
        turn.getBoard()[move.getStartRow()][move.getStartCell()].setPiece( null );
    }

    /**
     * Checks if the piece could have jumped
     * @param startPos the start position
     * @param turn the board turn instance
     * @param neighbors all the neighboring spaces
     * @return true if the piece can jump
     */
    public boolean canJump( Position startPos, BoardView turn, Position[] neighbors ) {
        for( Position position : neighbors ) {
            if( position.inBounds() ) {
                Move move = new Move( startPos, position );
                Position between = move.validSimpleJump( turn );
                if( between != null ) {
                    return true;
                }
            }
        }

        return false;
    }

    public Position[] getNeighbors(Piece piece, int row, int col){
        Piece.Type pieceType = piece.getType();
        Piece.Color color = piece.getColor();
        if (pieceType == Piece.Type.SINGLE ) {
            if (color == Piece.Color.RED) {
                return new Position[]{new Position(row + 2, col + 2),
                        new Position(row + 2, col - 2)};
            }
            if (color == Piece.Color.WHITE) {
                return new Position[]{new Position(row - 2, col + 2),
                        new Position(row - 2, col - 2)};
            }
        }else if( pieceType == Piece.Type.KING ) {
            return new Position[]{new Position(row + 2, col - 2),
                    new Position(row + 2, col + 2),
                    new Position(row - 2, col - 2),
                    new Position(row - 2, col + 2)};
        }
        return null;
    }
    public boolean validateTurn() {
        Space[][] board = this.checkerBoard.getBoard();
        for( int row = 0; row < 8; row++ ) {
            for( int col = 0; col < 8; col++ ) {
                Space selectedSpace = board[row][col];

                if( selectedSpace.validSpaceWithPiece( this.activePlayer ) ) {

                    Piece selectedPiece = selectedSpace.getPiece();
                    Position[] neighbors = getNeighbors(selectedPiece, row, col);
                    Position startPos = new Position( row, col );

                    if( canJump( startPos, checkerBoard, neighbors ) ){
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public boolean submitTurn() {
        if(this.validTurn) {
            this.checkerBoard = this.previousMoves.pop();
            this.checkerBoard.kingPieces();
            this.previousTurns.add(checkerBoard);
            this.previousMoves = new Stack<>();
            this.previousMoves.push(checkerBoard);
            this.validTurn = false;

            if (this.activeColor.equals(Piece.Color.RED)) {
                this.activeColor = Piece.Color.WHITE;
            } else {
                this.activeColor = Piece.Color.RED;
            }
            if (this.activePlayer.equals(whitePlayer)) {
                this.activePlayer = redPlayer;
            } else {
                this.activePlayer = whitePlayer;
            }

            return true;
        }else{
            return false;
        }
    }

    /**
     * Backups a single move
     * @return if the turn was successfully backed-up
     */
    public boolean backup(){
        BoardView previousMove = this.previousMoves.pop();
        this.validTurn = validateTurn();
        return previousMove != null;
    }

    private String getKeyNames() {
        String redName = this.redPlayer.getName();
        String whiteName = this.whitePlayer.getName();

        if( redOrWhitePlayerNameEquals( redName, whiteName, "END GAME" ) ) {
            return "END GAME";
        } else if( redOrWhitePlayerNameEquals( redName, whiteName, "MULTI JUMP" ) ) {
            return "MULTI JUMP";
        } else if( redOrWhitePlayerNameEquals( redName, whiteName, "KING PIECE" ) ) {
            return "KING PIECE";
        } else {
            return "NONE";
        }
    }
    public boolean isResigned(){
        return this.resignedPlayer != null;
    }

    private boolean redOrWhitePlayerNameEquals(String redName, String whiteName, String key) {
        return redName.equals(key) || whiteName.equals(key);
    }
    public Player completedGame() {
        if( this.checkerBoard.finishedGame() ) {
            Player.PlayerColor winnerColor = this.checkerBoard.winnerColor();
            gameFinished = true;
            if( winnerColor == Player.PlayerColor.RED ) {
                return this.redPlayer;
            } else {
                return this.whitePlayer;
            }
        }

        return null;
    }

    public boolean isGameFinished() {
        return gameFinished;
    }

    public boolean isSpectatorUpdated(Player spectator){
        BoardView spectatorBoard = this.spectators.get(spectator);
        return spectatorBoard.equals(this.checkerBoard);
    }
    public boolean updateSpectator(Player spectator){
        this.spectators.put(spectator, this.checkerBoard);
        return true;
    }
    public BoardView getSpectatorBoard(Player spectator){
        return this.spectators.get(spectator);
    }
    public String getGameResult(Player currentUser){
        String gameResult;

        if(this.isResigned()){
            gameResult = this.resignedPlayer.getName() + " has resigned.";
        }else{
            Player winner = this.completedGame();
            Player loser;

            if(winner.equals(this.getRedPlayer())){
                loser = this.getWhitePlayer();
            }else{
                loser = this.getRedPlayer();
            }

            if(winner.equals(currentUser)){
                gameResult = "You have captured all of " + loser.getName()
                        + "'s pieces. Congratulations, you win!";
            }else if(loser.equals(currentUser)){
                gameResult = winner.getName() + " has captured all of your pieces. You lose.";
            }else{
                gameResult = winner.getName() + " has captured all of " + loser.getName() + "'s pieces "
                        + "and has won the game! Thank you for watching!";
            }
        }

        return gameResult;
    }
    public boolean removeSpectator(Player spectator){
        this.spectators.remove(spectator);
        return true;
    }

    public int getSpectatorNum() {
        return this.spectators.size();
    }
}