package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class WaitWindow extends JPanel implements KeyListener {

    private static final long serialVersionUID = 1L;
    private BufferedImage instruction;
    private Window window;
    private BufferedImage[] playButton = new BufferedImage[4];

    private Timer timer;

    public WaitWindow(Window window)
    {
        instruction = ImageLoader.loadImage("D:\\TetrisBTL\\src\\gui\\img\\arrow.png");
        timer = new Timer(1000 / 60, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        timer.start();
        this.window=window;

    }

    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(Color.black);
        g.fillRect(0,0,WaitWindow.WIDTH,WaitWindow.HEIGHT);
        g.drawImage(instruction,WaitWindow.WIDTH/2-instruction.getWidth()/2,30-instruction.getHeight()/2+150,null);

        g.setColor(Color.red);

        g.drawString("Press space to play", 150, WaitWindow.HEIGHT/2+100);
    }


    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getKeyChar()==KeyEvent.VK_SPACE)
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
