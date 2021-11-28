package gui;

import javax.swing.*;
import java.awt.*;

public class Window {

    private static final int WIDTH = 445 , HEIGHT = 638;

    private JFrame window;
    private WaitWindow waitWindow;
    private Board board;
    public Window()
    {
        window = new JFrame("Tetris");
        window.setSize(WIDTH,HEIGHT);
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

    public void startTetris()
    {
        window.remove(waitWindow);
        window.add(board);
        window.addMouseListener(board);
        window.addMouseListener(board);
        window.addKeyListener(board);
        window.remove(waitWindow);
        board.startGame();
        window.revalidate();
    }
}
