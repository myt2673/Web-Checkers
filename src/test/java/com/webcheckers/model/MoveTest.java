package com.webcheckers.model;
import org.junit.jupiter.api.Test;

import javax.management.loading.ClassLoaderRepository;

import static org.junit.jupiter.api.Assertions.*;

class MoveTest {

    @Test
    void testMoveNullity() {
        final Position src = new Position(2, 1);
        final Position dst = new Position(3,2);

        final Move CuT = new Move( src, dst );

        assertNotNull( CuT, "Move is not null");
    }

    @Test
    void testValidSimpleMoveTrueRED_RIGHT() {
        final BoardView board = new BoardView();
        final Position startPos = new Position(2, 1);
        final Position endPos = new Position(3,2);

        final Move CuT = new Move( startPos, endPos );

        assertTrue(CuT.validSimpleMove(board), "Simple Move is true");
    }

    @Test
    void testValidSimpleMoveTooLargeJumpRED_RIGHT() {
        final BoardView board = new BoardView();
        final Position startPos = new Position(2, 1);
        final Position endPos = new Position(4,2);

        final Move CuT = new Move( startPos, endPos );

        assertFalse(CuT.validSimpleMove( board ), "Simple Move is false");
    }

    @Test
    void testValidSimpleMoveNotPieceRED_RIGHT() {
        final BoardView board = new BoardView();
        final Position startPos = new Position(2, 2);
        final Position endPos = new Position(2,3);

        final Move CuT = new Move( startPos, endPos );

        assertFalse(CuT.validSimpleMove( board ), "Simple Move is false");
    }

    @Test
    void testValidSimpleJumpTrueRED_RIGHT() {
        final BoardView board = new BoardView();
        final Position startPos = new Position(2, 1);
        final Position endPos = new Position(4,3);
        board.getBoard()[3][2].setPiece(new Piece(Piece.Type.SINGLE, Piece.Color.WHITE));

        final Move CuT = new Move( startPos, endPos );
        final Position between = new Position(3, 2);

        assertEquals(between, CuT.validSimpleJump( board ), "Simple Jump equals between");
    }


    @Test
    void testValidSimpleJumpTooFarRED_RIGHT() {
        final BoardView board = new BoardView();
        final Position startPos = new Position(2, 1);
        final Position endPos = new Position(5,4);
        board.getBoard()[3][2].setPiece(new Piece(Piece.Type.SINGLE, Piece.Color.WHITE));

        final Move CuT = new Move(startPos, endPos);

        assertNull(CuT.validSimpleJump(board), "Simple Jump too large");
    }


    @Test
    void testValidSimpleJumpSameColorRED_RIGHT() {
        final BoardView board = new BoardView();
        final Position startPos = new Position(2, 1);
        final Position endPos = new Position(4,3);
        board.getBoard()[3][2].setPiece(new Piece(Piece.Type.SINGLE, Piece.Color.RED));

        final Move CuT = new Move(startPos, endPos);

        assertNull(CuT.validSimpleJump(board), "Simple Jump equals between");
    }

    @Test
    void testValidSimpleMoveTrueRED_LEFT() {
        final BoardView board = new BoardView();
        final Position startPos = new Position(2, 5);
        final Position endPos = new Position(3,4);

        final Move CuT = new Move(startPos, endPos);

        assertTrue(CuT.validSimpleMove(board), "Simple Move is true");
    }

    @Test
    void testValidSimpleMoveTooLargeJumpRED_LEFT() {
        final BoardView board = new BoardView();
        final Position startPos = new Position(2, 5);
        final Position endPos = new Position(5,3);

        final Move CuT = new Move(startPos, endPos);

        assertFalse(CuT.validSimpleMove(board), "Simple Move is false");
    }

    @Test
    void testValidSimpleMoveNotPieceRED_LEFT() {
        final BoardView board = new BoardView();
        final Position startPos = new Position(2, 4);
        final Position endPos = new Position(3,3);

        final Move CuT = new Move(startPos, endPos);

        assertFalse(CuT.validSimpleMove(board), "Simple Move is false");
    }

    @Test
    void testValidSimpleJumpTrueRED_LEFT() {
        final BoardView board = new BoardView();
        final Position startPos = new Position(2, 5);
        final Position endPos = new Position(4,3);
        board.getBoard()[3][4].setPiece(new Piece(Piece.Type.SINGLE, Piece.Color.WHITE));

        final Move CuT = new Move(startPos, endPos);
        final Position between = new Position(3, 4);

        assertEquals(between, CuT.validSimpleJump(board), "Simple Jump equals between");
    }


    @Test
    void testValidSimpleJumpTooFarRED_LEFT() {
        final BoardView board = new BoardView();
        final Position startPos = new Position(2, 5);
        final Position endPos = new Position(5,2);
        board.getBoard()[3][4].setPiece(new Piece(Piece.Type.SINGLE, Piece.Color.WHITE));

        final Move CuT = new Move(startPos, endPos);

        assertNull(CuT.validSimpleJump(board), "Simple Jump too large");
    }


    @Test
    void testValidSimpleJumpSameColorRED_LEFT() {
        final BoardView board = new BoardView();
        final Position startPos = new Position(2, 5);
        final Position endPos = new Position(4,3);
        board.getBoard()[3][4].setPiece(new Piece(Piece.Type.SINGLE, Piece.Color.RED));

        final Move CuT = new Move(startPos, endPos);

        assertNull(CuT.validSimpleJump(board), "Simple Jump equals between");
    }

    @Test
    void testValidSimpleMoveTrueWHITE_RIGHT() {
        final BoardView board = new BoardView();
        final Position startPos = new Position(5, 2);
        final Position endPos = new Position(4,3);

        final Move CuT = new Move(startPos, endPos);

        assertTrue(CuT.validSimpleMove(board), "Simple Move is true");
    }

    @Test
    void testValidSimpleMoveTooLargeJumpWHITE_RIGHT() {
        final BoardView board = new BoardView();
        final Position startPos = new Position(5, 2);
        final Position endPos = new Position(2,4);

        final Move CuT = new Move(startPos, endPos);

        assertFalse(CuT.validSimpleMove(board), "Simple Move is false");
    }

    @Test
    void testValidSimpleMoveNotPieceWHITE_RIGHT() {
        final BoardView board = new BoardView();
        final Position startPos = new Position(5, 3);
        final Position endPos = new Position(4,4);

        final Move CuT = new Move(startPos, endPos);

        assertFalse(CuT.validSimpleMove(board), "Simple Move is false");
    }

    @Test
    void testValidSimpleJumpTrueWHITE_RIGHT() {
        final BoardView board = new BoardView();
        final Position startPos = new Position(5, 2);
        final Position endPos = new Position(3,4);
        board.getBoard()[4][3].setPiece(new Piece(Piece.Type.SINGLE, Piece.Color.RED));

        final Move CuT = new Move(startPos, endPos);
        final Position between = new Position(4, 3);

        assertEquals(between, CuT.validSimpleJump(board), "Simple Jump equals between");
    }


    @Test
    void testValidSimpleJumpTooFarWHITE_RIGHT() {
        final BoardView board = new BoardView();
        final Position startPos = new Position(5, 2);
        final Position endPos = new Position(2,5);
        board.getBoard()[4][3].setPiece(new Piece(Piece.Type.SINGLE, Piece.Color.RED));

        final Move CuT = new Move(startPos, endPos);

        assertNull(CuT.validSimpleJump(board), "Simple Jump too large");
    }


    @Test
    void testValidSimpleJumpSameColorWHITE_RIGHT() {
        final BoardView board = new BoardView();
        final Position startPos = new Position(5, 2);
        final Position endPos = new Position(3,4);
        board.getBoard()[4][3].setPiece(new Piece(Piece.Type.SINGLE, Piece.Color.WHITE));

        final Move CuT = new Move(startPos, endPos);

        assertNull(CuT.validSimpleJump(board), "Simple Jump equals between");
    }

    @Test
    void testValidSimpleMoveTrueWHITE_LEFT() {
        final BoardView board = new BoardView();
        final Position startPos = new Position(5, 6);
        final Position endPos = new Position(4,5);

        final Move CuT = new Move(startPos, endPos);

        assertTrue(CuT.validSimpleMove(board), "Simple Move is true");
    }

    @Test
    void testValidSimpleMoveTooLargeJumpWHITE_LEFT() {
        final BoardView board = new BoardView();
        final Position startPos = new Position(5, 6);
        final Position endPos = new Position(2,1);

        final Move CuT = new Move(startPos, endPos);

        assertFalse(CuT.validSimpleMove(board), "Simple Move is false");
    }

    @Test
    void testValidSimpleMoveNotPieceWHITE_LEFT() {
        final BoardView board = new BoardView();
        final Position startPos = new Position(5, 5);
        final Position endPos = new Position(4,4);

        final Move CuT = new Move(startPos, endPos);

        assertFalse(CuT.validSimpleMove(board), "Simple Move is false");
    }

    @Test
    void testValidSimpleJumpTrueWHITE_LEFT() {
        final BoardView board = new BoardView();
        final Position startPos = new Position(5, 6);
        final Position endPos = new Position(3,4);
        board.getBoard()[4][5].setPiece(new Piece(Piece.Type.SINGLE, Piece.Color.RED));

        final Move CuT = new Move(startPos, endPos);
        final Position between = new Position(4, 5);

        assertEquals(between, CuT.validSimpleJump(board), "Simple Jump equals between");
    }


    @Test
    void testValidSimpleJumpTooFarWHITE_LEFT() {
        final BoardView board = new BoardView();
        final Position startPos = new Position(5, 6);
        final Position endPos = new Position(2,3);
        board.getBoard()[4][5].setPiece(new Piece(Piece.Type.SINGLE, Piece.Color.RED));

        final Move CuT = new Move(startPos, endPos);

        assertNull(CuT.validSimpleJump(board), "Simple Jump too large");
    }


    @Test
    void testValidSimpleJumpSameColorWHITE_LEFT() {
        final BoardView board = new BoardView();
        final Position startPos = new Position(5, 6);
        final Position endPos = new Position(3,4);
        board.getBoard()[4][3].setPiece(new Piece(Piece.Type.SINGLE, Piece.Color.WHITE));

        final Move CuT = new Move(startPos, endPos);

        assertNull(CuT.validSimpleJump(board), "Simple Jump equals between");
    }

    @Test
    void testGetStartNull() {
        final Space[][] board = new Space[8][8];
        final Position startPos = null;
        final Position endPos = new Position(3,4);

        final Move CuT = new Move( startPos, endPos );

        assertNull( CuT.getStart(), "Start is null");
    }

    @Test
    void testGetStartRED_LEFT() {
        final BoardView board = new BoardView();
        final Position startPos = new Position(2, 1);
        final Position endPos = new Position(3,2);

        final Move CuT = new Move(startPos, endPos);

        CuT.validSimpleMove(board);

        assertEquals(startPos, CuT.getStart(), "Start is saved correctly");
    }

    @Test
    void testGetStartRED_RIGHT() {
        final BoardView board = new BoardView();
        final Position startPos = new Position(2, 1);
        final Position endPos = new Position(3,2);

        final Move CuT = new Move(startPos, endPos);

        CuT.validSimpleMove(board);

        assertEquals( startPos, CuT.getStart(), "Start is saved correctly");
    }

    @Test
    void testGetStartWHITE_LEFT() {
        final BoardView board = new BoardView();
        final Position startPos = new Position(5, 6);
        final Position endPos = new Position(4,5);

        final Move CuT = new Move(startPos, endPos);

        CuT.validSimpleMove(board);

        assertEquals(startPos, CuT.getStart(), "Start is saved correctly");
    }

    @Test
    void testGetStartWHITE_RIGHT() {
        final BoardView board = new BoardView();
        final Position startPos = new Position(5, 2);
        final Position endPos = new Position(4,3);

        final Move CuT = new Move(startPos, endPos);

        CuT.validSimpleMove(board);

        assertEquals( startPos, CuT.getStart(), "Start is saved correctly");
    }

    @Test
    void testGetEndNull() {
        final Space[][] board = new Space[8][8];
        //final Move CuT = new Move( board );

        //assertNull(CuT.getStart(), "End is null");
    }

    @Test
    void testGetEndRED_LEFT() {
        final BoardView board = new BoardView();
        final Position startPos = new Position(2, 1);
        final Position endPos = new Position(3,2);

        final Move CuT = new Move(startPos, endPos);

        CuT.validSimpleMove(board);

        assertEquals(endPos, CuT.getEnd(), "Start is saved correctly");
    }

    @Test
    void testGetEndRED_RIGHT() {
        final BoardView board = new BoardView();
        final Position startPos = new Position(2, 1);
        final Position endPos = new Position(3,2);

        final Move CuT = new Move(startPos, endPos);

        CuT.validSimpleMove(board);

        assertEquals( endPos, CuT.getEnd(), "Start is saved correctly");
    }

    @Test
    void testGetEndWHITE_LEFT() {
        final BoardView board = new BoardView();
        final Position startPos = new Position(5, 6);
        final Position endPos = new Position(4,5);

        final Move CuT = new Move(startPos, endPos);

        CuT.validSimpleMove(board);

        assertEquals(endPos, CuT.getEnd(), "Start is saved correctly");
    }

    @Test
    void testGetEndWHITE_RIGHT() {
        final BoardView board = new BoardView();
        final Position startPos = new Position(5, 2);
        final Position endPos = new Position(4,3);

        final Move CuT = new Move(startPos, endPos);

        CuT.validSimpleMove(board);

        assertEquals( endPos, CuT.getEnd(), "Start is saved correctly");
    }
}