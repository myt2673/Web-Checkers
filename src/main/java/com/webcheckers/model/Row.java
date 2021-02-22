package com.webcheckers.model;

import java.util.*;

public class Row implements Iterable<Space>{
    private final List<Space> spaces;
    private final int index;

    // Row Constructor
    public Row(int index, ArrayList<Space> spaces ){
        this.spaces = spaces;
        this.index = index;
    }

    // Get Functions
    public int getIndex() {
        return index;
    }

    @Override
    public Iterator<Space> iterator() {
        return spaces.iterator();
    }
}