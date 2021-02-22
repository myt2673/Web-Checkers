package com.webcheckers.model;

import com.webcheckers.appl.GameCenter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private Player redPlayer;
    private Player whitePlayer;
    private Player endGamePlayer;
    private Player multiJumpPlayer;
    private Player kingPiecePlayer;
    private Game CuT;
    private Game CuTEmpty;
    private Game CuTMulti;
    private Game CuTKing;

    @BeforeEach
    void setUp(){
        this.redPlayer = new Player("red");
        this.whitePlayer = new Player("white");
        this.endGamePlayer = new Player("END GAME");
        this.multiJumpPlayer = new Player("MULTI JUMP");
        this.kingPiecePlayer = new Player("KING PIECE");

        GameCenter.ViewMode viewMode = GameCenter.ViewMode.PLAYER;
        CuT = new Game(redPlayer,whitePlayer,"redVswhite");
        CuTEmpty = new Game(endGamePlayer, whitePlayer, "endGamePlayerVswhitePlayer");
        CuTMulti = new Game(multiJumpPlayer, whitePlayer, "multiJumpPlayerVswhitePlayer");
        CuTKing = new Game(kingPiecePlayer, whitePlayer, "kingPiecePlayerVswhitePlayer");

    }

    @Test
    void getRedPlayer() {
        assertSame( redPlayer, CuT.getRedPlayer() );
    }

    @Test
    void getWhitePlayer() {
        assertSame( whitePlayer, CuT.getWhitePlayer() );
    }

    @Test
    void getCheckerBoard() {
        assertNotNull( CuT.getCheckerBoard() );
    }

    @Test
    void getActivePlayer() {
        assertSame( redPlayer, CuT.getActivePlayer() );
    }

    @Test
    void getPlayerColor() {
        assertEquals(Player.PlayerColor.NONE, CuT.getPlayerColor(redPlayer));
    }

    @Test
    void initializeGame() {
        assertEquals( Player.PlayerColor.NONE, CuT.getRedPlayer().getPlayerColor());
        assertEquals( Player.PlayerColor.NONE, CuT.getWhitePlayer().getPlayerColor());
        CuT.initializeGame();
        assertNotNull(CuT.getRedPlayer().getPlayerColor());
        assertNotNull(CuT.getWhitePlayer().getPlayerColor());
    }

    @Test
    void testInvalidMakeMove(){
        Position src = new Position(2,1);
        Position dst = new Position(4,3);

        Move move = new Move(src, dst);
        assertFalse(CuT.makeMove(move));

        Position src2 = new Position(2,1);
        Position dst2 = new Position(4,0);

        Move move2 = new Move(src2, dst2);
        assertFalse(CuT.makeMove(move2));

    }

    @Test
    void testMakeSimpleMove() {
        Position src = new Position(2,1);
        Position dst = new Position(3,0);

        Move move = new Move(src, dst);
        assertTrue( CuT.makeMove(move) );
    }

    @Test
    void testMakeJumpMove() {
        Position src = new Position(2,1);
        Position dst = new Position(4,3);

        Move move = new Move(src, dst);
        assertFalse(CuT.makeMove(move)); // Can jump, but no between piece


        Player endGameName = new Player("END GAME");
        Player testPlayer = new Player("Extra");

        Game endGame = new Game( endGameName, testPlayer, "0");
        Position src2 = new Position(4,5);
        Position dst2 = new Position(6, 3);

        Move move2 = new Move( src2, dst2 );
        assertTrue(endGame.makeMove(move2));

    }

    @Test
    void validateTurn() {
        assertTrue( CuT.validateTurn() );
    }

    @Test
    void completedGame() {
        Player p1 = new Player("p1");
        Player p2 = new Player("p2");
        BoardView testBoard = new BoardView(false);

        Player winner = CuT.completedGame();
        assertNull(winner);

    }

    @Test
    void playerResigned(){
        CuT.initializeGame();
        CuT.playerResigned(redPlayer);
        assertTrue(CuT.isResigned());
    }

    @Test
    void isResigned(){
        assertFalse(CuT.isResigned());
        CuT.initializeGame();
        CuT.playerResigned(redPlayer);
        assertTrue(CuT.isResigned());
    }


    @Test
    void testGetSpectatorBoard() {
        Player spectator = new Player("Spec");
        assertNull(CuT.getSpectatorBoard(spectator));

        CuT.updateSpectator(spectator);

        assertNotNull(CuT.getSpectatorBoard(spectator));
        assertSame(CuT.getCheckerBoard(), CuT.getSpectatorBoard(spectator));

    }

    @Test
    void testGetGameResigned() {
        CuT.playerResigned(redPlayer);
        assertEquals(redPlayer.getName() + " has resigned.", CuT.getGameResult(redPlayer));
    }

    @Test
    void testGetGameRedWins() {
        Position src = new Position(4,5);
        Position dst = new Position(6, 3);
        Move move = new Move( src, dst );
        CuTEmpty.makeMove(move);
        CuTEmpty.submitTurn();

        assertEquals("You have captured all of " + whitePlayer.getName() + "'s pieces. Congratulations, you win!", CuTEmpty.getGameResult(endGamePlayer));

    }

    @Test
    void testGetGameResultSpectator() {
        Position src = new Position(4,5);
        Position dst = new Position(6, 3);
        Move move = new Move( src, dst );
        CuTEmpty.makeMove(move);
        CuTEmpty.submitTurn();

        assertEquals(endGamePlayer.getName() + " has captured all of " + whitePlayer.getName() + "'s pieces " + "and has won the game! Thank you for watching!", CuTEmpty.getGameResult(redPlayer));
    }

    @Test
    void testGameGameWhiteLose() {
        Position src = new Position(4,5);
        Position dst = new Position(6, 3);
        Move move = new Move( src, dst );
        CuTEmpty.makeMove(move);
        CuTEmpty.submitTurn();

        assertEquals( endGamePlayer.getName() + " has captured all of your pieces. You lose.", CuTEmpty.getGameResult(whitePlayer));
    }



    @Test
    void testSubmitTurn() {
        assertFalse( CuT.submitTurn() );

        Position src = new Position(2,1);
        Position dst = new Position(3,0);

        Move move = new Move(src, dst);
        assertTrue( CuT.makeMove(move) );

        assertTrue( CuT.submitTurn() );
    }

    @Test
    void testCompletedGame() {
        Position src = new Position(4,5);
        Position dst = new Position(6, 3);
        Move move = new Move( src, dst );
        CuTEmpty.makeMove(move);
        CuTEmpty.submitTurn();
        assertTrue(CuTEmpty.getCheckerBoard().finishedGame());
        assertEquals( endGamePlayer, CuTEmpty.completedGame() );
    }

    @Test
    void testGetNeighbors() {
        Piece redPiece = new Piece(Piece.Type.SINGLE, Piece.Color.RED);
        Piece whitePiece = new Piece(Piece.Type.SINGLE, Piece.Color.WHITE);
        Piece redKingPiece = new Piece(Piece.Type.KING, Piece.Color.RED);
        Piece whiteKingPiece = new Piece(Piece.Type.KING, Piece.Color.WHITE);
        Piece nullPiece = new Piece(null,null);

        assertNull(CuTEmpty.getNeighbors(nullPiece,0,0));

        CuTEmpty.getNeighbors(redPiece,0,1);

        CuTEmpty.getNeighbors(whitePiece,5,4);

        CuTEmpty.getNeighbors(redKingPiece, 2,1);

        CuTEmpty.getNeighbors(whiteKingPiece, 5,0);


    }


}