package entity;

import gui.Board;

import java.awt.*;

public class ShapeI extends Shape {
    public ShapeI(Board board) {
        super(board);
        int[][] shapeI = {
                { 1, 1, 1, 1 }
        };
        this.setCoordinate(shapeI);
        this.setColor(Color.yellow);
    }
}
