package entity;

import gui.Board;

import java.awt.*;

public class ShapeS extends Shape {

    public ShapeS(Board board) {
        super(board);
        int[][] shapeS = {
                {0, 1, 1},
                {1, 1, 0}
        };
        this.setCoordinate(shapeS);
        this.setColor(Color.CYAN);
    }
}
