package com.webcheckers.model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PositionTest {

    private final Position pos = new Position(1,4);

    @Test
    public void test_getRow(){
        assertEquals(1, pos.getRow());
    }

    @Test
    public void test_getCol(){
        assertEquals(4, pos.getCell());
    }

    @Test
    public void test_compare() {
        Position test = new Position(1,4);
        assertEquals(pos.compare(test), 0);

        test = new Position(7,7);
        assertEquals(pos.compare(test), 1);

        test = new Position(1,1);
        assertEquals(pos.compare(test), -1);
    }
}