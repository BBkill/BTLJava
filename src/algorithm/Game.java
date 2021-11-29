package algorithm;

public interface Game {
    public void checkOverGame();

    public void replayGame();

    public void pauseGame();

    public void continueGame();

    public void startGame();

    public void stopGame();

    public void increaseSpeed();

    public void decreaseSpeed();

    public int getLevel();
}
