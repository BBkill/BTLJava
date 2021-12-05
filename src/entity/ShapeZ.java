package entity;

import gui.Board;

import java.awt.*;

public class ShapeZ extends Shape {
    public ShapeZ(Board board) {
        super(board);
        int[][] shapeZ = {
                { 1, 1, 0 },
                { 0, 1, 1 }
        };
        this.setCoordinate(shapeZ);
        this.setColor(Color.pink);
    }
}
