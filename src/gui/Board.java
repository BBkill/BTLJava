package gui;

import algorithm.Game;
import entity.*;
import entity.Shape;

import javax.swing.*;
import java.awt.*;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Board extends JPanel implements KeyListener, Game, MouseMotionListener, MouseListener {

    public static int STATE_GAME_PLAY = 0;

    public static int STATE_GAME_PAUSE = 1;

    public static int STATE_GAME_OVER = 2;

    private int state = STATE_GAME_PLAY;

    private Timer gameLoop;

    private static int FPS = 75;

    private static int DELAY_TIME_GAME = FPS / 1000;

    public static final int BOARD_WIDTH = 10,BOARD_HEIGHT = 20;

    public static final int BLOCK_SIZE = 30;

    private Color[][] board = new Color[BOARD_HEIGHT][BOARD_WIDTH];

    private BufferedImage backGround, gameOver, newGameButton, continueButton, replayButtom;


    private ShapeT shapeT = new ShapeT(this);
    private ShapeI shapeI = new ShapeI(this);
    private ShapeO shapeO = new ShapeO(this);
    private ShapeL shapeL = new ShapeL(this);
    private ShapeZ shapeZ = new ShapeZ(this);
    private ShapeS shapeS = new ShapeS(this);
    private ShapeJ shapeJ = new ShapeJ(this);


    private Shape[] shapes =  {shapeI, shapeJ, shapeL, shapeS, shapeZ, shapeO, shapeT};
    
    private Shape currentShape = shapes[2];

    private Shape nextShape = shapes[2];

    private int score = 0;

    public Board()
    {
        gameLoop = new Timer(DELAY_TIME_GAME, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
                repaint();
            }
        });

        backGround = ImageLoader.loadImage("src\\gui\\img\\backGround.png");
        gameOver = ImageLoader.loadImage("src\\gui\\img\\gameOver.png");
        newGameButton = ImageLoader.loadImage("src\\gui\\img\\newGame.png");
        continueButton = ImageLoader.loadImage("src\\gui\\img\\continue.png");
        replayButtom = ImageLoader.loadImage("src\\gui\\img\\replay.png");
        gameLoop.start();
    }


    private void update()
    {
        if(state == STATE_GAME_PLAY)
        {
            currentShape.update();
        }

    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(0,0, getWidth(), getHeight());
        g.drawImage(backGround,0,0,null);
        //draw the shape when it moves
        currentShape.render(g);


        //draw the shape when it stops moving
        for (int row = 0; row < board.length; row++)
        {
            for (int column = 0 ; column < board[row].length; column++)
            {
                if(board[row][column] != null)
                {
                    g.setColor(board[row][column]);
                    g.fillRect(column*BLOCK_SIZE, row*BLOCK_SIZE,BLOCK_SIZE,BLOCK_SIZE);
                }

            }
        }


        //draw the board (the line)
        g.setColor(Color.black);
        for (int row = 0 ; row < BOARD_HEIGHT; row++)
        {
            g.drawLine(0, BLOCK_SIZE * row, BLOCK_SIZE * BOARD_WIDTH,BLOCK_SIZE * row);
        }

        for (int column = 0; column < BOARD_WIDTH + 1; column++)
        {
            g.drawLine(column * BLOCK_SIZE, 0, column * BLOCK_SIZE, BLOCK_SIZE * BOARD_HEIGHT);
        }



        if(state == STATE_GAME_OVER )
        {
            g.drawImage(gameOver.getScaledInstance(300,180,BufferedImage.SCALE_DEFAULT), 0,200,null);
            g.drawImage(replayButtom.getScaledInstance(50,50,1),125,350,null);
        }
    }


    public void setCurrentShape()
    {

        currentShape = nextShape;
        setNextShape();
        currentShape.reset();
        checkOverGame();

    }
    public void setNextShape()
    {
        Random random = new Random();
        nextShape = shapes[random.nextInt(7)];
    }

    public Color[][] getBoard()
    {
        return board;
    }



    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_DOWN)
        {
            currentShape.speedUp();
        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            currentShape.moveRight();
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            currentShape.moveLeft();
        }
        else if(e.getKeyCode() == KeyEvent.VK_UP) {
            currentShape.rotate();
        }else if(e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            if(state == STATE_GAME_PLAY)
            {
                pauseGame();
            }
            else if(state == STATE_GAME_PAUSE)
            {
                continueGame();
            }
        }

        if(state == STATE_GAME_OVER)
        {
            if(e.getKeyCode() == KeyEvent.VK_SPACE) {
                replayGame();
            }
        }


    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_DOWN)
        {
            currentShape.speedDown();
        }
    }

    @Override
    public void checkOverGame() {
        int[][] coordinate = currentShape.getCoordinate();
        for(int row = 0; row < coordinate.length;row++)
        {
            for (int column = 0;column<coordinate[row].length;column++)
            {
                if(coordinate[row][column]!=0)
                {
                    if(board[row + currentShape.getY()][column + currentShape.getX()]!=null)
                    {
                        state = STATE_GAME_OVER;
                    }
                }
            }
        }


    }

    @Override
    public void replayGame() {
        //clear the board
        for(int row = 0; row < board.length;row++)
        {
            for(int column = 0; column < board[row].length;column++)
            {
                board[row][column] = null;
            }
        }
        state = STATE_GAME_PLAY;
        setCurrentShape();

    }

    @Override
    public void pauseGame() {
        state = STATE_GAME_PAUSE;
    }

    @Override
    public void continueGame() {
        state = STATE_GAME_PLAY;
    }

    @Override
    public void startGame() {
        stopGame();
        setNextShape();
        setCurrentShape();
        state = STATE_GAME_PLAY;
        gameLoop.start();
    }

    @Override
    public void stopGame() {
        score = 0;
        for(int row = 0; row < board.length;row++)
        {
            for(int column = 0; column < board[row].length;column++)
            {
                board[row][column] = null;
            }
        }
        gameLoop.stop();
        state = STATE_GAME_PAUSE;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
