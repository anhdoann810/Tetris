package controller;

import model.*;
import javax.swing.Timer;
import java.util.*;

public class GameEngine {
    private Board board;
    private Tetromino currentPiece;
    private int score;
    private boolean isGameOver;
    private boolean isPaused;
    private Timer timer;
    private Random random;
    private Runnable viewUpdater;
    private int scoreMultiplier = 1;


    //attribute for a queue of 3 next piece
    private static final int  NEXT_PIECE_COUNT = 3;
    private Queue<Tetromino> queuePiece = new LinkedList<>();


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
        // clear old queue and add new 3 pieces to queue
        queuePiece.clear();
        addPieceToQueue();
        getNewPiece();
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
        getNewPiece();
        if (!board.isValidBlock(currentPiece, 0, 0)) {
            isGameOver = true;
            timer.stop();
            // Call notifyView one last time before stopping completely
            notifyView();
        }
    }

    // new method to do only job: create new Piece
    private Tetromino createNextPiece()
    {
            int shapeType = random.nextInt(7);
            if (shapeType == 0) {
                return new StraightShape(Board.COLS / 2, -1);
            } else if (shapeType == 1) {
                return new SquareShape(Board.COLS / 2, -1);
            } else if (shapeType == 2) {
                return new LShape(Board.COLS / 2, -1);
            } else if (shapeType == 3) {
                return new JShape(Board.COLS / 2, -1);
            } else if (shapeType == 4) {
                return new TShape(Board.COLS / 2, -1);
            } else if (shapeType == 5) {
                return new SShape(Board.COLS / 2, -1);
            } else {
                return new ZShape(Board.COLS / 2, -1);
            }
    }
    // fill the queue when needed
    private void addPieceToQueue()
    {
        while(queuePiece.size() < NEXT_PIECE_COUNT)
        {
            queuePiece.add(createNextPiece());
        }
    }

    // get new piece for currentPiece
    private void getNewPiece()
    {
        currentPiece = queuePiece.remove();
        addPieceToQueue();
    }

    public Queue<Tetromino> getUpcomingPieces()
    {
        return queuePiece;
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
        int baseScore = 0;
        switch (linesCleared) {
            case 1:
                baseScore = 100;
                break;
            case 2:
                baseScore = 300;
                break;
            case 3:
                baseScore = 500;
                break;
            case 4:
                baseScore = 800;
                break;
            default:
                baseScore = 0;
                break;
        }
        return baseScore * scoreMultiplier;
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

    public void setDifficultyDelay(int ms, int multiplier) {
        this.timer.setDelay(ms);
        this.scoreMultiplier = multiplier;
    }
}
