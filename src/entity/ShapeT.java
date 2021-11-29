package entity;

import gui.Board;

import java.awt.*;

public class ShapeT extends Shape {
    private int[][] shapeT = {
            { 1, 1, 1 },
            { 0, 1, 0 }
    };

    public ShapeT(Board board) {
        super(board);
        this.setCoordinate(shapeT);
        this.setColor(Color.red);
    }
}
