package entity;

import algorithm.Modified;
import algorithm.Movement;
import algorithm.Rotation;
import gui.Board;

import java.awt.*;

import static gui.Board.BLOCK_SIZE;
import static gui.Board.BOARD_HEIGHT;

public class Shape implements Movement, Rotation, Modified {
    //coordinate of entity at the beginning
    private int x = 4, y = 0;

    private int normal = 300;
    private int fast = 100;
    private int delayTimeFoMovement = normal;
    private long beginTime;

    private int dentalX = 0;

    private boolean collision = false;

    private int[][] coordinate;

    private Color color;

    private Board board;
    public Shape(Board board){
        this.board = board;
    }

    public void reset()
    {
        this.x=4;
        this.y=0;
        collision = false;
        delayTimeFoMovement = normal;

    }
    protected void setCoordinate(int[][] coordinate) {
        this.coordinate = coordinate;
    }

    protected void setColor(Color color) {
        this.color = color;
    }


    public void update()
    {
        if(collision)
        {
            for (int row  = 0; row < coordinate.length;row++)
            {
                for(int column = 0; column <coordinate[0].length;column++)
                {
                    if (coordinate[row][column] != 0)
                    {
                        board.getBoard()[y + row][x + column] = color;
                    }
                }
            }
            checkLine();
            board.setCurrentShape();

            return;
        }
        boolean moveX = true;
        //check the horizon
        if(!(x + dentalX +coordinate[0].length>10) && !(x+dentalX<0))
        {
            for(int row = 0; row < coordinate.length; row++)
            {
                for (int column = 0;column<coordinate[row].length;column++)
                {
                    if(coordinate[row][column]!=0)
                    {
                        if(board.getBoard()[y+row][x+dentalX+column]!=null)
                        {
                            moveX = false;
                        }
                    }
                }
            }
            if(moveX)
            {
                x = x + dentalX;
            }


        }
        dentalX = 0;
        //move
        if(System.currentTimeMillis()-beginTime>delayTimeFoMovement)
        {
            if(!(y+1+coordinate.length>BOARD_HEIGHT))
            {
                for(int row = 0; row < coordinate.length; row++)
                {
                    for (int column = 0;column<coordinate[row].length;column++)
                    {
                        if (coordinate[row][column]!=0)
                        {
                            if(board.getBoard()[y+1+row][x+dentalX+column]!=null)
                            {
                                collision = true;
                            }
                        }
                    }
                }
                if(!collision)
                {
                    y++;
                }

            }else {
                collision = true;
            }

            beginTime = System.currentTimeMillis();
        }
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }

    public int[][] getCoordinate() {
        return coordinate;
    }





    @Override
    public void render(Graphics g)
    {
        //draw shape
        for (int row = 0; row <coordinate.length;row++)
        {
            for (int column = 0 ; column<coordinate[0].length;column++)
            {
                if(coordinate[row][column] != 0)
                {
                    g.setColor(color);
                    g.fillRect(column*BLOCK_SIZE+x*BLOCK_SIZE, row*BLOCK_SIZE+y*BLOCK_SIZE,BLOCK_SIZE,BLOCK_SIZE);
                }

            }
        }
    }


    // remove the line if it fulled
    @Override
    public void checkLine() {
        int bottomLine = board.getBoard().length-1;
        for(int topLine = board.getBoard().length-1; topLine >0 ; topLine--)
        {
            int count = 0;
            for(int column = 0;column < board.getBoard()[0].length;column++)
            {
                if(board.getBoard()[topLine][column]!=null)
                {
                    count++;
                }
                board.getBoard()[bottomLine][column]=board.getBoard()[topLine][column];
            }
            if(count < board.getBoard()[0].length)
            {
                bottomLine--;
            }
        }
    }

    @Override
    public void speedUp() {
        delayTimeFoMovement = fast;
    }

    @Override
    public void speedDown() {
        delayTimeFoMovement = normal;
    }

    @Override
    public void moveRight() {
        dentalX = 1;
    }

    @Override
    public void moveLeft() {
        dentalX = -1;
    }

    @Override
    public void rotate() {
        int[][] rotateShape = transposeShape(coordinate);
        reverseShape(rotateShape);
        //check coordinate if to close to the boor
        if((x+rotateShape[0].length > Board.BOARD_WIDTH)||(y + rotateShape.length>20))
        {
            return;
        }

        //check for collision with other shapes

        for(int row = 0; row < rotateShape.length; row++)
        {
            for(int column = 0; column <rotateShape[row].length; column++)
            {
                if(rotateShape[row][column] != 0)
                {
                    if(board.getBoard()[y + row][x+column] != null)
                    {
                        return;
                    }
                }
            }
        }
        coordinate = rotateShape;
    }

    @Override
    public int[][] transposeShape(int[][] matrix) {
        int[][] temp = new int[matrix[0].length][matrix.length];
        for(int row = 0; row <matrix.length;row++)
        {
            for (int column = 0; column < matrix[0].length;column++)
            {
                temp[column][row] = matrix[row][column];
            }
        }
        return temp;
    }

    @Override
    public void reverseShape(int[][] matrix) {
        int middle = matrix.length / 2;
        for(int row = 0; row <middle;row ++)
        {
            int[] tmp = matrix[row];
            matrix[row] = matrix[matrix.length-row -1 ];
            matrix[matrix.length - row -1] = tmp;
        }
    }
}
