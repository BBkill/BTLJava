package entity;

import gui.Board;

import java.awt.*;

public class ShapeSpecial extends Shape {
    public ShapeSpecial(Board board) {

        super(board);

        int[][] ShapeSpecial = {
                { 0, 1, 0 },
                { 1, 1, 1 },
                { 0, 1, 0 }
        };
        this.setCoordinate(ShapeSpecial);
        this.setColor(Color.WHITE);
    }
}
