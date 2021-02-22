package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


@Tag("Model-tier")
public class SpaceTest {

    private static final int DEFAULT_IDX = 0;
    private static final Piece DEFAULT_PIECE = null;
    private static final Space.Color DEFAULT_COLOR = Space.Color.DARK;
    private static final Piece DUMMY_PIECE = new Piece(Piece.Type.SINGLE, Piece.Color.RED);
    private Space CuT;

    @BeforeEach
    void setUp() {
        CuT = new Space(DEFAULT_IDX, DEFAULT_PIECE, DEFAULT_COLOR);
    }

    @Test
    public void testSpaceCreation(){
        final Space CuT = new Space(DEFAULT_IDX, DEFAULT_PIECE, DEFAULT_COLOR);
        assertNotNull(CuT, "Space is not null");
    }

    @Test
    public void testCellIdx(){
        final Space CuT = new Space(DEFAULT_IDX, DEFAULT_PIECE, DEFAULT_COLOR);
        assertEquals(DEFAULT_IDX, CuT.getCellIdx(), "Space CellIdx is 0");
    }

    @Test
    public void testGetPiece(){
        final Space CuT = new Space(DEFAULT_IDX, DUMMY_PIECE, DEFAULT_COLOR);
        assertEquals(new Piece(Piece.Type.SINGLE, Piece.Color.RED), CuT.getPiece(), "Space Piece is equal");
    }

    @Test
    public void testSpaceLightColor(){
        final Space CuT = new Space(DEFAULT_IDX, DEFAULT_PIECE, Space.Color.LIGHT);
        assertEquals(Space.Color.LIGHT, CuT.getColor(), "Space Light color works");
    }

    @Test
    public void testSpaceDarkColor(){
        final Space CuT = new Space (DEFAULT_IDX, DEFAULT_PIECE, DEFAULT_COLOR);
        assertEquals(Space.Color.DARK, CuT.getColor(), "Space Dark color works");
    }

    @Test
    void testEquals() {
        assertFalse(CuT.equals(new Piece(Piece.Type.SINGLE, Piece.Color.RED)));

        assertTrue( CuT.equals(CuT) );

    }

}