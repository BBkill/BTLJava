package entity;

import gui.Board;

import java.awt.*;

public class ShapeS extends Shape {
    private int[][] shapeS = {
            { 0, 1, 1 },
            { 1, 1, 0 }
    };

    public ShapeS(Board board) {
        super(board);
        this.setCoordinate(shapeS);
        this.setColor(Color.CYAN);
    }
}
