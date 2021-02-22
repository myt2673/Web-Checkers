package com.webcheckers.application;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerLobbyTest {

    private static final Message NAME_TAKEN_MSG = Message.info("Error! User name already in use!");


    @Test
    void testContainsPlayer(){
        PlayerLobby playerLobby = new PlayerLobby();
        Player player1 = new Player("Player1");
        assertFalse(playerLobby.contains(player1));
    }

    @Test
    void testAddPlayer(){
        PlayerLobby playerLobby = new PlayerLobby();
        Player player1 = new Player("Player1");
        playerLobby.addPlayer(player1);
        assertTrue(playerLobby.contains(player1));
    }

    @Test
    void testPlayerCount(){
        PlayerLobby playerLobby = new PlayerLobby();
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");
        Player player3 = new Player("Player3");
        playerLobby.addPlayer(player1);
        playerLobby.addPlayer(player2);
        playerLobby.addPlayer(player3);
        assertEquals(3,playerLobby.playerCount());
    }

    @Test
    void testClear(){
        PlayerLobby playerLobby = new PlayerLobby();
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");
        Player player3 = new Player("Player3");
        playerLobby.addPlayer(player1);
        playerLobby.addPlayer(player2);
        playerLobby.addPlayer(player3);
        playerLobby.clearLobby();
        assertEquals(0, playerLobby.playerCount());
    }

    @Test
    void testPlayerNames(){
        ArrayList<String> names = new ArrayList<>();
        names.add("Player1");
        names.add("Player2");
        names.add("Player3");
        PlayerLobby playerLobby = new PlayerLobby();
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");
        Player player3 = new Player("Player3");
        playerLobby.addPlayer(player1);
        playerLobby.addPlayer(player2);
        playerLobby.addPlayer(player3);
        ArrayList <String> names2 = playerLobby.getPlayerNames();
        assertEquals(names,names2);
    }

    @Test
    void testValid(){
        PlayerLobby playerLobby = new PlayerLobby();
        assertTrue(playerLobby.isValid("TEST"));
    }

    @Test
    void testInvalid(){
        PlayerLobby playerLobby = new PlayerLobby();
        assertFalse(playerLobby.isValid("   *D "));
    }

    @Test
    void testKick(){
        PlayerLobby playerLobby = new PlayerLobby();
        Player player1 = new Player("Player1");
        playerLobby.addPlayer(player1);
        playerLobby.kickPlayer(player1);
        assertFalse(playerLobby.contains(player1));
    }

    @Test
    void testGetPlayer(){
        PlayerLobby playerLobby = new PlayerLobby();
        Player player1 = new Player("Player1");
        playerLobby.addPlayer(player1);
        assertEquals(player1, playerLobby.getPlayer(player1));
    }

    @Test
    void testGetPlayerString(){
        PlayerLobby playerLobby = new PlayerLobby();
        Player player1 = new Player("Player1");
        playerLobby.addPlayer(player1);
        assertEquals(player1, playerLobby.getPlayer("Player1"));
    }

    @Test
    void testGetEmptyPlayers(){
        PlayerLobby playerLobby = new PlayerLobby();
        assertEquals("", playerLobby.getPlayers());
    }
    @Test
    void testGetFilledPlayerLobby(){
        PlayerLobby playerLobby = new PlayerLobby();
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");
        Player player3 = new Player("Player3");
        playerLobby.addPlayer(player1);
        playerLobby.addPlayer(player2);
        playerLobby.addPlayer(player3);
        assertNotEquals("", playerLobby.getPlayers());
    }

}
