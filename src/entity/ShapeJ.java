package entity;

import gui.Board;

import java.awt.*;

public class ShapeJ extends Shape {
    public ShapeJ(Board board) {
        super(board);
        int[][] shapeJ = {
                { 1, 1, 1 },
                { 0, 0, 1 }
        };
        this.setCoordinate(shapeJ);
        this.setColor(Color.magenta);
    }
}
