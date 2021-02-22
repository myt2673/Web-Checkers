package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-tier")
class PlayerTest {

    Player p1;

    @BeforeEach
    public void initializeTest(){
        this.p1 = new Player("Player 1");
    }

    @Test
    public void test_getName(){
        assertEquals("Player 1", p1.getName());
    }

    @Test
    public void test_toString(){
        assertEquals("Player Name: Player 1" , p1.toString());
    }

    @Test
    public void test_hashCode(){
        Player p2 = new Player("Player 2");
        assertNotEquals(p1.hashCode(),p2.hashCode());
    }

    @Test
    public void test_equals(){
        Player p2 = new Player("Player 2");
        assertNotEquals(p1,p2);
    }
    @Test
    public void test_isPlaying(){
        Player p2 = new Player("Player 2");
        assertFalse(p2.isPlaying());
    }
    @Test
    public void test_leaveGame(){
        Player p2 = new Player("Player 2");
        Game game = new Game(p1,p2,"0");

        p2.leaveGame();
        assertFalse(p2.isPlaying());
    }
}