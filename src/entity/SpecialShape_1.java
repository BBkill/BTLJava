package entity;

import gui.Board;

import java.awt.*;

public class SpecialShape_1 extends Shape{
    public SpecialShape_1(Board board) {

        super(board);

        int[][] ShapeSpecial = {
                { 1, 0, 1 },
                { 0, 1, 0 },
                { 1, 0, 1 }
        };
        this.setCoordinate(ShapeSpecial);
        this.setColor(Color.WHITE);
    }
}
