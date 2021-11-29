package entity;

import gui.Board;

import java.awt.*;

public class ShapeJ extends Shape {
    private int[][] shapeJ = {
            { 1, 1, 1 },
            { 0, 0, 1 }
    };

    public ShapeJ(Board board) {
        super(board);
        this.setCoordinate(shapeJ);
        this.setColor(Color.magenta);
    }
}
