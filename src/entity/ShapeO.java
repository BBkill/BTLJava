package entity;

import gui.Board;

import java.awt.*;

public class ShapeO extends Shape {
    public ShapeO(Board board) {
        super(board);
        int[][] shapeO = {
                { 1, 1 },
                { 1, 1 }
        };
        this.setCoordinate(shapeO);
        this.setColor(Color.orange);
    }
}
