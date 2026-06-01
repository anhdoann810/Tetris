package controller;

import model.*;
import javax.swing.Timer;
import java.util.Random;

public class GameEngine {
    private Board board;
    private Tetromino currentPiece;
    private int score;
    private boolean isGameOver;
    private boolean isPaused;
    private Timer timer;
    private Random random;
    private Runnable viewUpdater;

    public GameEngine() {
        board = new Board();
        score = 0;
        isGameOver = false;
        isPaused = false;
        random = new Random();
        timer = new Timer(500, e -> gameLoop());
    }

    public void setViewUpdater(Runnable updater) {
        this.viewUpdater = updater;
    }

    private void notifyView() {
        if (viewUpdater != null) {
            viewUpdater.run();
        }
    }

    public void resetGame() {
        board.clear();
        score = 0;
        isGameOver = false;
        isPaused = false;
        currentPiece = null;
        timer.start();
        notifyView();
    }

    private void gameLoop() {
        if (isGameOver || isPaused) return;

        if (currentPiece == null) {
            spawnNewPiece();
        } else {
            movePieceDown();
        }
        notifyView();
    }

    private void spawnNewPiece() {
        int shapeType = random.nextInt(7);
        if (shapeType == 0) {
            currentPiece = new StraightShape(Board.COLS / 2, -1);
        } else if (shapeType == 1) {
            currentPiece = new SquareShape(Board.COLS / 2, -1);
        } else if (shapeType == 2) {
            currentPiece = new LShape(Board.COLS / 2, -1);
        } else if (shapeType == 3) {
            currentPiece = new JShape(Board.COLS / 2, -1);
        } else if (shapeType == 4) {
            currentPiece = new TShape(Board.COLS / 2, -1);
        } else if (shapeType == 5) {
            currentPiece = new SShape(Board.COLS / 2, -1);
        } else if (shapeType == 6) {
            currentPiece = new ZShape(Board.COLS / 2, -1);
        }

        if (!board.isValidBlock(currentPiece, 0, 0)) {
            isGameOver = true;
            timer.stop();
            // Call notifyView one last time before stopping completely
            notifyView();
        }
    }

    private void movePieceDown() {
        if (board.isValidBlock(currentPiece, 0, 1)) {
        currentPiece.move(0, 1);
        } else {
        lockCurrentPieceAndContinue();
        }
    }

    private void lockCurrentPieceAndContinue() {
        board.lockPiece(currentPiece);
        int linesCleared = board.clearLines();
        score += calculateScore(linesCleared);

        spawnNewPiece();
    }

    private int calculateScore(int linesCleared) {
        switch (linesCleared) {
            case 1: return 100;
            case 2: return 300;
            case 3: return 500;
            case 4: return 800;
            default: return 0;
        }
    }

    public void moveLeft() {
        if (!isGameOver && !isPaused && currentPiece != null && board.isValidBlock(currentPiece, -1, 0)) {
            currentPiece.move(-1, 0);
            notifyView();
        }
    }

    public void moveRight() {
        if (!isGameOver && !isPaused && currentPiece != null && board.isValidBlock(currentPiece, 1, 0)) {
            currentPiece.move(1, 0);
            notifyView();
        }
    }

    public void softDrop() {
        if (!isGameOver && !isPaused && currentPiece != null) {
            movePieceDown();
            notifyView();
        }
    }

    public void hardDrop() {
        if (isGameOver || isPaused || currentPiece == null) return;

        while (board.isValidBlock(currentPiece, 0, 1)) {
            currentPiece.move(0, 1);
        }
        lockCurrentPieceAndContinue();
        notifyView();
    }

    public void rotateCurrentPiece() {
        if (isGameOver || isPaused || currentPiece == null) return;

        currentPiece.rotate();

        if (board.isValidBlock(currentPiece, 0, 0)) {
        } else if (board.isValidBlock(currentPiece, -1, 0)) {
            currentPiece.move(-1, 0);
        } else if (board.isValidBlock(currentPiece, 1, 0)) {
            currentPiece.move(1, 0);
        } else if (board.isValidBlock(currentPiece, -2, 0)) {
            currentPiece.move(-2, 0);
        } else if (board.isValidBlock(currentPiece, 2, 0)) {
            currentPiece.move(2, 0);
        } else if (board.isValidBlock(currentPiece, 0, -1)) {
            currentPiece.move(0, -1);
        } else {
            currentPiece.rotateBack();
        }
        notifyView();
    }

    public void togglePause() {
        if (isGameOver) return;
        isPaused = !isPaused;
    }

    public void start() {
        if (!timer.isRunning()) {
            timer.start();
        }
    }

    public void stop() {
        timer.stop();
    }

    public Board getBoard() {
        return board;
    }

    public Tetromino getCurrentPiece() {
        return currentPiece;
    }

    public int getScore() {
        return score;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public boolean isPaused() {
        return isPaused;
    }
}
