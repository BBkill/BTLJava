package algorithm;

import java.awt.*;

public interface Movement {
    public void speedUp();

    public void speedDown();

    public void moveRight();

    public void moveLeft();

    public void checkLine();

    public void fall(Graphics g);
}
