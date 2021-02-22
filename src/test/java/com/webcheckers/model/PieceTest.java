package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PieceTest {

    private Piece CuT;

    @BeforeEach
    void setUp() {
        CuT = new Piece(Piece.Type.SINGLE, Piece.Color.RED);
    }

    @Test
    void testGetType() {
        assertEquals(Piece.Type.SINGLE, CuT.getType());
    }

    @Test
    void testGetColor() {
        assertEquals(Piece.Color.RED, CuT.getColor());
    }

    @Test
    void testEquals() {
        assertTrue( CuT.equals(CuT) );
        Piece piece = new Piece(Piece.Type.SINGLE, Piece.Color.RED);
        assertTrue( CuT.equals(piece) );
    }

    @Test
    void testHashCode() {
        int hashCode = (int)Math.pow(Piece.Color.RED.hashCode() , Piece.Type.SINGLE.hashCode());
        assertEquals(hashCode, CuT.hashCode());
    }

}