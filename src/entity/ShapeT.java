package entity;

import gui.Board;

import java.awt.*;

public class ShapeT extends Shape {
    public ShapeT(Board board) {
        super(board);
        int[][] shapeT = {
                { 1, 1, 1 },
                { 0, 1, 0 }
        };
        this.setCoordinate(shapeT);
        this.setColor(Color.red);
    }
}
