package com.thaind.jcp.enhance_threadsafed_object.threadsafe;

import com.thaind.jcp.volatile_threadsafe.Immutable;

@Immutable
public class Point {
    public final int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
