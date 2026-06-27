package model;

public class GameState {
    private final Board board;
    private Tetromino currentPiece;
    private int score;
    private boolean gameOver;
    private boolean paused;

    public GameState() {
        this.board = new Board();
        reset();
    }

    public void reset() {
        board.clear();
        currentPiece = null;
        score = 0;
        gameOver = false;
        paused = false;
    }

    public Board getBoard() {
        return board;
    }

    public Tetromino getCurrentPiece() {
        return currentPiece;
    }

    public void setCurrentPiece(Tetromino currentPiece) {
        this.currentPiece = currentPiece;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int points) {
        score += points;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean isPaused() {
        return paused;
    }

    public void togglePaused() {
        paused = !paused;
    }
}
