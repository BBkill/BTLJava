package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class WaitWindow extends JPanel implements KeyListener{

    private static final long serialVersionUID = 1L;
    private BufferedImage instruction,playButton, backGround;
    private Window window;

    private Timer timer;

    public WaitWindow(Window window)
    {
        instruction = ImageLoader.loadImage("src\\gui\\img\\guide.png");
        playButton = ImageLoader.loadImage("src\\gui\\img\\play.png");
        backGround = ImageLoader.loadImage("src\\gui\\img\\backGround.png");



        timer = new Timer(75 / 1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });

        this.window=window;
        timer.start();
    }

    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(Color.black);
        g.fillRect(0,0,WaitWindow.WIDTH,WaitWindow.HEIGHT);
        g.drawImage(backGround,0,0,null);
        g.drawImage(instruction,60,25,null);
        g.drawImage(playButton,0,170,null);
    }


    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getKeyChar()==KeyEvent.VK_ENTER)
        {
            window.startTetris();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


}
