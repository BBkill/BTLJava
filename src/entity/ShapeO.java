package entity;

import gui.Board;

import java.awt.*;

public class ShapeO extends Shape{
    private int[][] shapeO = {
            {1,1},
            {1,1}
    };
    public ShapeO(Board board) {
        super(board);
        this.setCoordinate(shapeO);
        this.setColor(Color.orange);
    }
}
