package entity;

import gui.Board;

import java.awt.*;

public class ShapeL extends Shape {
    public ShapeL(Board board) {
        super(board);
        int[][] shapeL = {
                { 1, 1, 1 },
                { 1, 0, 0 }
        };
        this.setCoordinate(shapeL);
        this.setColor(Color.GREEN);
    }
}
