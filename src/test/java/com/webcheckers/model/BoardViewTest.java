package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardViewTest {

    private BoardView CuT;
    private BoardView CuTNoPieces;

    @BeforeEach
    void setUp() {
        CuTNoPieces = new BoardView(false);
        CuT = new BoardView( true);
    }

    @Test
    void getBoard() {
        assertNotNull(CuT.getBoard());
    }

    @Test
    void getRow() {
        for(int i = 0; i < 8; i++){
            assertNotNull(CuT.getRow(i));
        }

    }

    @Test
    void equals() {
        assertFalse( CuT.equals(CuTNoPieces) );
        assertTrue( CuT.equals(CuT) );
    }


    @Test
    void redPlayerFinishedGame(){
        Space[][] testSpaces = CuTNoPieces.getBoard();
        testSpaces[0][1] = new Space(1
                , new Piece(Piece.Type.SINGLE, Piece.Color.WHITE)
                , Space.Color.DARK);

        assertTrue( CuTNoPieces.finishedGame() );
    }
    @Test
    void testFlippedBoard(){
        BoardView flippedSpaces = BoardView.flipBoard(CuTNoPieces);
        assertNotEquals(flippedSpaces,CuTNoPieces);
    }

    @Test
    void whitePlayerFinishedGame(){
        Space[][] testSpaces = CuTNoPieces.getBoard();
        testSpaces[0][3] = new Space(1, new Piece(Piece.Type.SINGLE, Piece.Color.RED), Space.Color.DARK);

        assertTrue( CuTNoPieces.finishedGame() );

    }

    @Test
    void redWinner() {
        Space[][] testSpaces = CuTNoPieces.getBoard();
        testSpaces[0][3] = new Space(1, new Piece(Piece.Type.SINGLE, Piece.Color.RED), Space.Color.DARK);

        assertTrue( CuTNoPieces.finishedGame() );

        assertEquals(CuTNoPieces.winnerColor(), Player.PlayerColor.RED);

    }

    @Test
    void whiteWinner() {
        Space[][] testSpaces = CuTNoPieces.getBoard();
        testSpaces[0][1] = new Space(1, new Piece(Piece.Type.SINGLE, Piece.Color.WHITE), Space.Color.DARK);

        assertTrue( CuTNoPieces.finishedGame() );

        assertEquals(CuTNoPieces.winnerColor(), Player.PlayerColor.WHITE);

    }

    @Test
    void noWinner() {
        Space[][] testSpaces = CuT.getBoard();
        assertFalse( CuT.finishedGame() );
        assertEquals( Player.PlayerColor.NONE, CuT.winnerColor() );

    }

    @Test
    void kingPieces() {
        Space[][] testSpaces = CuTNoPieces.getBoard();
        Piece pieceTest = new Piece(Piece.Type.SINGLE, Piece.Color.WHITE);
        testSpaces[0][1] = new Space(1,pieceTest, Space.Color.DARK);

        assertEquals(testSpaces[0][1].getPiece().getType(), Piece.Type.SINGLE);

        CuTNoPieces.kingPieces();

        assertEquals(testSpaces[0][1].getPiece().getType(), Piece.Type.KING);
    }

    @Test
    void testSetUpEndGame() {
        CuT.setUpEndGame();
    }

    @Test
    void testSetUpMultiJump() {
        CuT.setUpMultiJump();
    }

    @Test
    void testSetUpKingPiece() {
        CuT.setUpKingPiece();
    }

    @Test
    public void testIteratorNotNull(){
        final boolean isRed = false;
        final BoardView testBoard = new BoardView();
        CuT = new BoardView(true);

        assertNotNull(CuT.iterator(), "BoardView Iterator created unsuccessfully.");
    }

}