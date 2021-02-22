package com.webcheckers.appl;
import com.webcheckers.model.*;
import java.util.*;

public class GameCenter {
    public enum ViewMode{  //maybe for the enhancements later?
        PLAYER,
        SPECTATOR, REPLAY
    }
    private HashMap<Player, Game> currentGames;
    private HashMap<Player,Player> playersInGame;
    private int gamesCompleted;


    private int gamesPlayed;
    private Player redPlayer;
    private Player whitePlayer;
    public GameCenter(){
        this.currentGames = new HashMap<>();
        this.playersInGame = new HashMap<>();
        this.gamesPlayed = 0;
        this.gamesCompleted = 0;
    }
    public String newGame(Player redPlayer, Player whitePlayer){
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        gamesPlayed++;
        String gameId = createGameId();
        Game newGame = new Game(redPlayer, whitePlayer, gameId);
        newGame.initializeGame();

        currentGames.put(redPlayer, newGame);
        currentGames.put(whitePlayer, newGame);
        return gameId;
    }

    public HashMap<String, Object> endGame(Player currentUser){
        HashMap<String, Object> modeOptions = new HashMap<>(2);
        Game endedGame = currentGames.get(currentUser);
        String gameResult = endedGame.getGameResult(currentUser);

        modeOptions.put("isGameOver", true);
        modeOptions.put("gameOverMessage", gameResult);

        currentUser.leaveGame();
        if(endedGame.getSpectatorNum() == 0)
                currentGames.remove(currentUser);
        endedGame.isOver = true;

        return modeOptions;
    }

    public boolean isInAnyGame(Player player) {
        for( Game game : currentGames.values() ) {
            if( game.isInGame(player)) {
                return true;
            }
        }
        return false;
    }
    public boolean hasGame(Player player1, Player player2) {
        for(Game game: currentGames.values()){
            if(player1.equals(game.getRedPlayer()) && player2.equals(game.getWhitePlayer()))
                return true;
            else if(player2.equals(game.getRedPlayer()) && player1.equals(game.getWhitePlayer()))
                return true;
        }

        return false;
    }
    public boolean isMyTurn(Player currentPlayer){
        Game game = currentGames.get(currentPlayer);
        return game.getActivePlayer().equals(currentPlayer);
    }
    public boolean submitTurn(Player player){
        Game game = currentGames.get(player);
        return game.submitTurn();
    }
    public void addInGamePlayers(Player p1, Player p2){
        playersInGame.put(p1, p2);
        playersInGame.put(p2, p1);
    }
    public boolean backup(Player player){
        Game game = currentGames.get(player);
        return game.backup();
    }
    public boolean isSpectatorUpdated(Game specGame, Player spectator){
        return specGame.isSpectatorUpdated(spectator);
    }
    public Game getGame(Player player){
        return currentGames.get(player);
    }
    public boolean requestMove(Player player, Move move){
        Game game = currentGames.get(player);
        return game.makeMove(move);
    }
    public void removeGame(Player player) {
        currentGames.remove(player);
        playersInGame.remove(player);
    }
    public boolean isPlayerInGame(Player player){
        return playersInGame.containsKey(player);
    }

    public Player getOpponent(Player player){
        return playersInGame.get(player);
    }

    private String createGameId(){
        return "Game " + gamesPlayed;
    }

    public Player getRedPlayer() {
        return redPlayer;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }
    public boolean updateSpectator(Game gameToSpec, Player spectator){
        gameToSpec.updateSpectator(spectator);
        return true;
    }
    public boolean removeSpectator(Game specGame, Player spectator){

        specGame.removeSpectator(spectator);

        if(specGame.getSpectatorNum() == 0 && specGame.isResigned() || (specGame.completedGame() != null))
            currentGames.remove(spectator,specGame);

        return specGame.removeSpectator(spectator);
    }

    public boolean isCurrentPlayerNotAbleToJoinGame(Player currentPlayer){
        return playersInGame.get(currentPlayer) == null;
    }

    public Game getGame(String gameId) {
        return currentGames.get(gameId);
    }
}
