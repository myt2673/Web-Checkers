package com.webcheckers.application;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class GameCenterTest {

    private GameCenter CuT;

    private String GAME_ID_TEST = "Game 0";

    @BeforeEach
    void setup() {
        this.CuT = new GameCenter();
    }

    @Test
    void testGetGame() {
        Player player1 = new Player("redPlayer");
        Player player2 = new Player("whitePlayer");
        GameCenter.ViewMode viewMode = GameCenter.ViewMode.PLAYER;

        CuT.newGame(player1, player2);

        assertSame(player1, CuT.getGame(player1).getRedPlayer());
        assertSame(player2, CuT.getGame(player2).getWhitePlayer());
    }

    @Test
    void testRedPlayerHasGame() {
        Player player1 = new Player("redPlayer");
        Player player2 = new Player("whitePlayer");
        GameCenter.ViewMode viewMode = GameCenter.ViewMode.PLAYER;

        CuT.newGame(player1, player2);

        assertTrue( CuT.hasGame(player1, player2) );

    }

    @Test
    void testWhitePlayerHasGame(){
        Player player1 = new Player("redPlayer");
        Player player2 = new Player("whitePlayer");
        GameCenter.ViewMode viewMode = GameCenter.ViewMode.PLAYER;

        CuT.newGame(player1, player2);

        assertTrue( CuT.hasGame(player1, player2) );
    }

    @Test
    void testDoesNotHasGame(){
        Player player1 = new Player("redPlayer");
        Player player2 = new Player("whitePlayer");
        assertFalse( CuT.hasGame(player1, player2) );
    }

    @Test
    void testRequestMove() {
        Player player1 = new Player("redPlayer");
        Player player2 = new Player("whitePlayer");

        String cutGame = CuT.newGame(player1, player2);

        Move move = new Move( new Position(2,1), new Position(3,2) );

        assertTrue(CuT.requestMove(player1, move));
    }

    @Test
    void testSubmitTurn() {
        Player player1 = new Player("redPlayer");
        Player player2 = new Player("whitePlayer");

        String cutGame = CuT.newGame(player1, player2);

        assertFalse(CuT.submitTurn(player1));
    }

    @Test
    void testBackupMove() {
        Player player1 = new Player("redPlayer");
        Player player2 = new Player("whitePlayer");

        String cutGame = CuT.newGame(player1, player2);

        assertTrue(CuT.backup(player1));
    }

    @Test
    void testIsMyTurnRED() {
        Player player1 = new Player("redPlayer");
        Player player2 = new Player("whitePlayer");

        CuT.newGame(player1, player2);

        assertSame(player1, CuT.getGame(player1).getRedPlayer());
        assertSame(player2, CuT.getGame(player2).getWhitePlayer());

        assertTrue( CuT.isMyTurn(player1));
        assertFalse( CuT.isMyTurn(player2));
    }

    @Test
    void testIsMyTurnWHITE() {
        Player player1 = new Player("redPlayer");
        Player player2 = new Player("whitePlayer");

        String game = CuT.newGame(player1, player2);

        assertSame(player1, CuT.getGame(player1).getRedPlayer());
        assertSame(player2, CuT.getGame(player2).getWhitePlayer());

        CuT.getGame(player1).submitTurn();

        assertTrue( CuT.isMyTurn( player1) );
        assertFalse( CuT.isMyTurn(player2) );
    }

    @Test
    void testIsInAnyGame() {

        Player p1 = new Player("p1");
        Player p2 = new Player("p2");
        Player p3 = new Player("p3");
        Player p4 = new Player("p4");
        Player p5 = new Player("p5");
        Player p6 = new Player("p6");

        String game0 = CuT.newGame(p1, p2);
        String game1 = CuT.newGame(p3, p4);

        assertTrue( CuT.isInAnyGame(p1) );
        assertTrue( CuT.isInAnyGame(p2) );
        assertTrue( CuT.isInAnyGame(p4) );
        assertFalse( CuT.isInAnyGame(p6) );

    }

    @Test
    void testIsMyTurn() {

        Player p1 = new Player("p1");
        Player p2 = new Player("p2");

        String cutGame = CuT.newGame(p1, p2);

        assertTrue( CuT.isMyTurn(p1) );
        assertFalse( CuT.isMyTurn(p2) );

    }

    @Test
    void testRemoveGame() {
        Player player1 = new Player("redPlayer");
        Player player2 = new Player("whitePlayer");

        CuT.newGame(player1, player2);

        assertSame(player1, CuT.getGame(player1).getRedPlayer());
        assertSame(player2, CuT.getGame(player2).getWhitePlayer());

        CuT.removeGame(player1);

        assertNull(CuT.getGame(GAME_ID_TEST));
    }

    @Test
    void isSpectatorUpdated() {
        Player player1 = new Player("redPlayer");
        Player player2 = new Player("whitePlayer");
        Player spectator = new Player("Spec");
        Player spectator2 = new Player("Spec2");
        String game0 = CuT.newGame(player1, player2);
        CuT.updateSpectator(CuT.getGame(player1), spectator);
        assertTrue(CuT.isSpectatorUpdated(CuT.getGame(player1), spectator));
    }

    @Test
    void testRemoveSpectator() {
        Player player1 = new Player("redPlayer");
        Player player2 = new Player("whitePlayer");
        Player spectator = new Player("Spec");
        String game0 = CuT.newGame(player1, player2);

        CuT.updateSpectator(CuT.getGame(player1), spectator);

        assertTrue(CuT.isSpectatorUpdated(CuT.getGame(player1), spectator));
        assertTrue(CuT.removeSpectator(CuT.getGame(player1), spectator));
    }

    @Test
    void testUpdateSpectator() {
        Player player1 = new Player("redPlayer");
        Player player2 = new Player("whitePlayer");
        Player spectator = new Player("Spec");
        String game0 = CuT.newGame(player1, player2);

        assertTrue(CuT.updateSpectator(CuT.getGame(player1), spectator));
    }

    @Test
    void testHasGame() {
        Player p1 = new Player("p1");
        Player p2 = new Player("p2");
        Player p3 = new Player("p3");
        Player p4 = new Player("p4");
        Player p5 = new Player("p5");
        Player p6 = new Player("p6");

        CuT.newGame(p1,p2);
        CuT.newGame(p3,p4);

        assertTrue(CuT.hasGame(p1,p2));
        assertTrue(CuT.hasGame(p3,p4));
        assertFalse(CuT.hasGame(p5,p6));
        assertFalse(CuT.hasGame(p1, p5));
    }
    @Test
    void testEndGame(){
        Player player1 = new Player("redPlayer");
        Player player2 = new Player("whitePlayer");

        CuT.newGame(player1,player2);

        assertNotNull(CuT.getGame(player1));
    }
}