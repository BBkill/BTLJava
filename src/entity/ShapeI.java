package entity;

import gui.Board;

import java.awt.*;

public class ShapeI extends Shape {
    private int[][] shapeI = {
            { 1, 1, 1, 1 }
    };

    public ShapeI(Board board) {
        super(board);
        this.setCoordinate(shapeI);
        this.setColor(Color.yellow);
    }
}
