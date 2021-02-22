package com.webcheckers.model;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.*;

public class RowTest {

    @Test
    void testValid(){
        int index = 5;
        ArrayList<Space> spaces = new ArrayList<>();
        for(int i = 0; i < 8; i ++) {
            spaces.add(new Space(i ,null, Space.Color.DARK));
        }
        Row row = new Row(index, spaces);

        int actualIndex = row.getIndex();

        assertEquals(index, actualIndex);

        assertNotNull(row.iterator());
    }

}