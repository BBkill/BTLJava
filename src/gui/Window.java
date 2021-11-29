package gui;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    public static final int WIDTH = 460;
    public static final int HEIGHT = 638;



    private static JFrame window;
    private static WaitWindow waitWindow;
    private Board board;

    public Window() {

    }

    public void run()
    {
        window = new JFrame("Tetris");
        window.setIconImage(ImageLoader.loadImage("src\\gui\\img\\icon.png"));
        window.setSize(WIDTH, HEIGHT);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        board = new Board();
        waitWindow = new WaitWindow(this);
        window.addKeyListener(waitWindow);
        window.add(waitWindow);
        window.setBackground(Color.GRAY);
        window.setVisible(true);
    }

    public void startTetris() {
        window.remove(waitWindow);
        window.removeKeyListener(waitWindow);
        window.addMouseListener(board);
        window.addMouseMotionListener(board);
        window.addKeyListener(board);
        window.add(board);
        board.startGame();
        window.revalidate();
    }
}
