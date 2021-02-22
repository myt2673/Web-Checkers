package com.webcheckers.appl;

import com.webcheckers.model.Player;
import com.webcheckers.util.Message;

import java.util.ArrayList;

public class PlayerLobby {
    private static ArrayList<Player> players;

    private static final Message NAME_TAKEN_MSG = Message.info("Error! User name already in use!");


    public PlayerLobby(){
        players = new ArrayList<>();
    }

    public String getListOfPlayers(Player loggedInPlayer){
        String listPlayers = "";

        for (Player currentPlayer : players){
            if (currentPlayer.equals(loggedInPlayer)){
                continue;
            }
            listPlayers += "<li> <a class='player' href='/game?opponentName=" +
                    currentPlayer.getName() + "'>" + currentPlayer.getName() + " </a></li>";
        }
        return listPlayers;
    }




    public ArrayList<String> getPlayerNames(){
        ArrayList<String> names = new ArrayList<>();
        for (Player player : players){
            names.add(player.getName());
        }
        return names;
    }

    /***
     * a method to add a new player to this PlayerLobby's player attribute.
     * @param player the player to be added into this PlayerLobby
     */
    public void addPlayer(Player player){
        players.add(player);
    }

    public Player getPlayer(Player player){
        int i = players.indexOf(player);
        if(i == -1){
            return null;
        }
        return players.get(i);
    }
    /***
     * a method to remove all players from the PlayerLobby
     */
    public void clearLobby(){
        players.clear();
    }

    public Player getPlayer(String playerName){
        for (Player player : players){
            if (player.getName().equals(playerName)){
                return player;
            }
        }
        return null;
    }

    /***
     * returns an integer representing the amount of players in this PlayerLobby
     * @return an int, based on the size of the players attribute for the number of players in the PlayerLobby
     */
    public int playerCount(){
        return players.size();
    }

    /***
     * returns a string listing out all of the players in the PlayerLobby
     * @return a string printing each player's name on a line.
     */
    public String getPlayers(){
        String names = "";
        for(int i = 0; i < playerCount(); i++){
            names += players.get(i) + "\n";
        }
        return names;
    }

    /***
     * removes a player from the PlayerLobby
     * @param player the player to be removed from the player attribute in this PlayerLobby object.
     */
    public void kickPlayer(Player player){
        players.remove(player);
    }

    /***
     * a method the returns a boolean based on if this PlayerLobby contains the inputted player.
     * @param player the player in question if it is in this PlayerLobby or not.
     * @return true if the players attribute of PlayerCLobby contains player, false otherwise.
     */
    public boolean contains(Player player){
        return players.contains(player);
    }

    public boolean isValid(String playerName) {
        return playerName != null && playerName.matches("^[a-zA-Z0-9]*$");
    }
}
