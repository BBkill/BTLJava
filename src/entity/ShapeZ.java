package entity;

import gui.Board;

import java.awt.*;

public class ShapeZ extends Shape{
    private int[][] shapeZ = {
            {1,1,0},
            {0,1,1}
    };
    public ShapeZ(Board board) {
        super(board);
        this.setCoordinate(shapeZ);
        this.setColor(Color.pink);
    }
}
