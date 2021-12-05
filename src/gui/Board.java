package gui;

import algorithm.Game;
import entity.*;
import entity.Shape;

import javax.swing.*;
import java.awt.*;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Board extends JPanel implements KeyListener, Game, MouseMotionListener, MouseListener {

    public static int STATE_GAME_PLAY = 0;

    public static int STATE_GAME_PAUSE = 1;

    public static int STATE_GAME_OVER = 2;

    private int state = STATE_GAME_PLAY;

    private final Timer gameLoop;

    private static final int FPS = 75;

    private int speedGame = 500;

    private static final int DELAY_TIME_GAME = FPS / 1000;

    public static final int BOARD_WIDTH = 10, BOARD_HEIGHT = 20;

    public static final int BLOCK_SIZE = 30;

    private static final Color[][] board = new Color[BOARD_HEIGHT][BOARD_WIDTH];

    private static BufferedImage backGround, gameOver, newGameButton, continueButton, replayButton, pauseButton,
            quiteButton;

    private static Rectangle newGameRect, pauseRect, quitRect, replayRect;

    private int mouseX, mouseY;

    private boolean leftClick = false;

    private final ShapeT shapeT = new ShapeT(this);
    private final ShapeI shapeI = new ShapeI(this);
    private final ShapeO shapeO = new ShapeO(this);
    private final ShapeL shapeL = new ShapeL(this);
    private final ShapeZ shapeZ = new ShapeZ(this);
    private final ShapeS shapeS = new ShapeS(this);
    private final ShapeJ shapeJ = new ShapeJ(this);
    private final ShapeSpecial shapeSpecial = new ShapeSpecial(this);
    private final SpecialShape_1 specialShape_1 = new SpecialShape_1(this);
    private final SpecialShape_2 specialShape_2 = new SpecialShape_2(this);
    private final SpecialShape_3 specialShape_3 = new SpecialShape_3(this);

    private final ArrayList<Shape> specialShapes = new ArrayList<Shape>(){{
        add(specialShape_2);
        add(specialShape_3);
        add(specialShape_1);
        add(shapeSpecial);
        }
    };
    private final Shape[] shapes = { shapeI, shapeJ, shapeL, shapeS, shapeZ, shapeO, shapeT, shapeSpecial,shapeI, shapeJ, shapeL, shapeS, shapeZ, shapeO, shapeT, specialShape_1, specialShape_2,specialShape_3, shapeI, shapeJ, shapeL, shapeS, shapeZ, shapeO, shapeT};

    private Shape currentShape = shapes[1];

    private Shape nextShape = shapes[2];

    private int score = 0;

    private final Timer buttonLapse = new Timer(300, new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            buttonLapse.stop();
        }
    });

    public Board() {
        gameLoop = new Timer(DELAY_TIME_GAME, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
                repaint();
            }
        });

        mouseX = 0;
        mouseY = 0;

        backGround = ImageLoader.loadImage("src\\gui\\img\\backGround.png");
        gameOver = ImageLoader.loadImage("src\\gui\\img\\gameOver.png");
        newGameButton = ImageLoader.loadImage("src\\gui\\img\\newGame.png");
        continueButton = ImageLoader.loadImage("src\\gui\\img\\continue.png");
        replayButton = ImageLoader.loadImage("src\\gui\\img\\replay.png");
        pauseButton = ImageLoader.loadImage("src\\gui\\img\\pauseGame.png");
        quiteButton = ImageLoader.loadImage("src\\gui\\img\\quit.png");


        newGameRect = new Rectangle(Window.WIDTH - 138, Window.HEIGHT / 2 + 80, 100, 40);
        pauseRect = new Rectangle(Window.WIDTH - 138, Window.HEIGHT / 2 + 130, 100, 40);
        quitRect = new Rectangle(Window.WIDTH - 138, Window.HEIGHT / 2 + 180, 100, 40);
        replayRect = new Rectangle(135, 370, 50, 70);
        gameLoop.start();
    }

    private void update() {
        if (newGameRect.contains(mouseX, mouseY) && leftClick) {
            replayGame();
        }
        if (pauseRect.contains(mouseX, mouseY) && state == STATE_GAME_PLAY && leftClick && !buttonLapse.isRunning()) {
            buttonLapse.start();
            pauseGame();
        } else if (pauseRect.contains(mouseX, mouseY) && state == STATE_GAME_PAUSE && leftClick && !buttonLapse.isRunning()) {
            buttonLapse.start();
            continueGame();
        }
        if (quitRect.contains(mouseX, mouseY) && leftClick) {
            System.exit(0);
        }

        if (state == STATE_GAME_PLAY) {
            if(specialShapes.contains(currentShape) )
            {
                currentShape.updateSpecial();
            }
            else
            {
                currentShape.update();
            }
        }

        if (replayRect.contains(mouseX, mouseY) && state == STATE_GAME_OVER && leftClick)
        {
            replayGame();
        }
    }

    public int getSpeedGame() {
        return speedGame;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(backGround, 0, 0, null);


        // draw the shape when it stops moving
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length; column++) {
                if (board[row][column] != null) {
                    g.setColor(board[row][column]);
                    g.fillRect(column * BLOCK_SIZE, row * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                }
            }
        }

        // draw the next shape
        g.setColor(nextShape.getColor());
        for (int row = 0; row < nextShape.getCoordinate().length; row++) {
            for (int col = 0; col < nextShape.getCoordinate()[0].length; col++) {
                if (nextShape.getCoordinate()[row][col] != 0) {
                    g.fillRect(col * 30 + 320, row * 30 + 50, Board.BLOCK_SIZE, Board.BLOCK_SIZE);
                    g.setColor(Color.black);
                    g.drawLine(col * 30 + 320, row * 30 + 50, col * 30 + 320 + Board.BLOCK_SIZE, row * 30 + 50);
                    g.drawLine(col * 30 + 320, row * 30 + 50, col * 30 + 320, row * 30 + 50 + Board.BLOCK_SIZE);

                }
                g.setColor(nextShape.getColor());
            }
        }

        // draw the shape when it moves
        currentShape.fall(g);


        // draw score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Georgia", Font.BOLD, 20));
        g.drawString("SCORE", Window.WIDTH - 125, Window.HEIGHT / 2 - 130);
        g.drawString(score + "", Window.WIDTH - 125, Window.HEIGHT / 2 - 100);

        // draw the game's level
        g.setFont(new Font("Georgia", Font.BOLD, 20));
        g.drawString("Level: ", Window.WIDTH - 125, Window.HEIGHT / 2 - 70);
        g.drawString(getLevel() + "", Window.WIDTH -60, Window.HEIGHT / 2 - 70);




        // draw function button


        if (newGameRect.contains(mouseX, mouseY)) {
            g.drawImage(newGameButton.getScaledInstance(100 + 6, 30 + 6, 1), Window.WIDTH - 138, Window.HEIGHT / 2 + 50,
                    null);
        } else {
            g.drawImage(newGameButton.getScaledInstance(100, 30, 1), Window.WIDTH - 138, Window.HEIGHT / 2 + 50, null);
        }

        if (pauseRect.contains(mouseX, mouseY)) {
            g.drawImage(pauseButton.getScaledInstance(100 + 6, 30 + 6, 1), Window.WIDTH - 138, Window.HEIGHT / 2 + 100,
                    null);
        } else {
            g.drawImage(pauseButton.getScaledInstance(100, 30, 1), Window.WIDTH - 138, Window.HEIGHT / 2 + 100, null);
        }
        if (quitRect.contains(mouseX, mouseY)) {
            g.drawImage(quiteButton.getScaledInstance(100 + 6, 30 + 6, 1), Window.WIDTH - 138, Window.HEIGHT / 2 + 150,
                    null);
        } else {
            g.drawImage(quiteButton.getScaledInstance(100, 30, 1), Window.WIDTH - 138, Window.HEIGHT / 2 + 150, null);
        }

        // draw the board (the line)
        g.setColor(Color.black);
        for (int row = 0; row < BOARD_HEIGHT; row++) {
            g.drawLine(0, BLOCK_SIZE * row, BLOCK_SIZE * BOARD_WIDTH, BLOCK_SIZE * row);
        }
        for (int column = 0; column < BOARD_WIDTH + 1; column++) {
            g.drawLine(column * BLOCK_SIZE, 0, column * BLOCK_SIZE, BLOCK_SIZE * BOARD_HEIGHT);
        }

        // draw game over image
        if (state == STATE_GAME_OVER) {
            g.drawImage(gameOver.getScaledInstance(300, 180, BufferedImage.SCALE_DEFAULT), 0, 200, null);
            if (replayRect.contains(mouseX, mouseY)) {
                g.drawImage(replayButton.getScaledInstance(56, 56, 1), 120, 345, null);
            } else {
                g.drawImage(replayButton.getScaledInstance(50, 50, 1), 125, 350, null);
            }
        }

        // draw game pause
        if (state == STATE_GAME_PAUSE) {
            if (pauseRect.contains(mouseX, mouseY)) {
                g.drawImage(continueButton.getScaledInstance(100 + 6, 30 + 6, 1), Window.WIDTH - 138,
                        Window.HEIGHT / 2 + 100, null);
            } else {
                g.drawImage(continueButton.getScaledInstance(100, 30, 1), Window.WIDTH - 138, Window.HEIGHT / 2 + 100,
                        null);
            }
        }
    }

    public void setCurrentShape() {
        currentShape = nextShape;
        currentShape.reset();
        setNextShape();
        checkOverGame();
    }




    private void setNextShape() {
        Random random = new Random();
        nextShape = shapes[random.nextInt(shapes.length)];
    }

    public Color[][] getBoard() {
        return board;
    }

    public void addScore() {
        score++;
    }

    public void setSpeedGame(int speedGame) {
        this.speedGame = speedGame;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            currentShape.speedUp();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            currentShape.moveRight();
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            currentShape.moveLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            currentShape.rotate();
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (state == STATE_GAME_PLAY) {
                pauseGame();
            } else if (state == STATE_GAME_PAUSE) {
                continueGame();
            }
        }

        if (state == STATE_GAME_OVER) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                replayGame();
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            currentShape.speedDown();
        }
    }

    @Override
    public void checkOverGame() {
        int[][] coordinate = currentShape.getCoordinate();
        for (int row = 0; row < coordinate.length; row++) {
            for (int column = 0; column < coordinate[row].length; column++) {
                if (coordinate[row][column] != 0) {
                    if (board[row + currentShape.getY()][column + currentShape.getX()] != null) {
                        state = STATE_GAME_OVER;
                    }
                }
            }
        }

    }

    @Override
    public void replayGame() {
        // clear the board
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length; column++) {
                board[row][column] = null;
            }
        }
        state = STATE_GAME_PLAY;
        score = 0;

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
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length; column++) {
                board[row][column] = null;
            }
        }
        gameLoop.stop();
        state = STATE_GAME_PAUSE;
    }

    @Override
    public void increaseSpeed() {
        if (speedGame > 100)
        {
            speedGame = speedGame - 50;
        }
    }

    @Override
    public void decreaseSpeed() {
        if (speedGame <500)
        {
            speedGame = speedGame + 50;
        }

    }

    @Override
    public int getLevel() {
        if (speedGame == 500) return 1;
        if (speedGame == 450) return 2;
        if (speedGame == 400) return 3;
        if (speedGame == 350) return 4;
        if (speedGame == 300) return 5;
        if (speedGame == 250) return 6;
        if (speedGame == 200) return 7;
        if (speedGame == 150) return 8;
        return 9;
    }

    @Override
    public void upLevel() {
        if(score < 10 ) setSpeedGame(500);
        else
        if(score < 20 ) setSpeedGame(450);
        else
        if(score < 30 ) setSpeedGame(400);
        else
        if(score < 40 ) setSpeedGame(350);
        else
        if(score < 50 ) setSpeedGame(300);
        else
        if(score < 60 ) setSpeedGame(250);
        else
        if(score < 70 ) setSpeedGame(200);
        else
        if(score < 80 ) setSpeedGame(150);
        else
        if(score > 90 ) setSpeedGame(100);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftClick = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftClick = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
