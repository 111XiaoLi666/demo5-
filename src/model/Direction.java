package model;

import java.io.Serializable;

public class Direction implements Serializable {
    public static final Direction UP = new Direction("上", -1, 0);
    public static final Direction DOWN = new Direction("下", 1, 0);
    public static final Direction LEFT = new Direction("左", 0, -1);
    public static final Direction RIGHT = new Direction("右", 0, 1);
    public static final Direction[] ALL_DIRECTIONS = {UP, DOWN, LEFT, RIGHT};

    public String name;
    public int h, w;

    public Direction(String name, int h, int w) {
        this.name = name;
        this.h = h;
        this.w = w;
    }

    public Direction reverse() {
        return new Direction("反" + name, -h, -w);
    }

}
