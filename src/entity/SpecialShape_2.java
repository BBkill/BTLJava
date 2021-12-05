package entity;

import gui.Board;

import java.awt.*;

public class SpecialShape_2 extends Shape {
    public SpecialShape_2(Board board) {

        super(board);

        int[][] ShapeSpecial = {
                { 1, 0, 0 },
                { 1, 1, 0 },
                { 1, 1, 1 }
        };
        this.setCoordinate(ShapeSpecial);
        this.setColor(Color.WHITE);
    }
}
