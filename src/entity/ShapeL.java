package entity;

import gui.Board;

import java.awt.*;

public class ShapeL extends Shape {
    private int[][] shapeL = {
            { 1, 1, 1 },
            { 1, 0, 0 }
    };

    public ShapeL(Board board) {
        super(board);
        this.setCoordinate(shapeL);
        this.setColor(Color.GREEN);
    }
}
