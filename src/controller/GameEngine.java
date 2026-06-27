package controller;

import model.*;
import java.util.*;

public class GameEngine {
    private GameState state;
    private PieceQueue pieceQueue;
    private PieceMovementController movementController;
    private ScoreManager scoreManager;
    private GameLoopController gameLoopController;
    private final Random random;
    private Runnable viewUpdater;
    private SoundController soundManager;

    public GameEngine() {
        random = new Random();
        state = new GameState();
        scoreManager = new ScoreManager();
        pieceQueue = new PieceQueue(this::createNextPiece);
        movementController = new PieceMovementController(state, this::lockCurrentPieceAndContinue);
        gameLoopController = new GameLoopController(this::gameLoop);
        soundManager = new SoundController();
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
        state.reset();
        pieceQueue.reset();
        spawnNewPiece();
        gameLoopController.start();
        notifyView();
        soundManager.playBGM("/sounds/bgm.wav");
    }

    private void gameLoop() {
        if (state.isGameOver() || state.isPaused())
            return;

        if (state.getCurrentPiece() == null) {
            spawnNewPiece();
        } else {
            movementController.moveDown();
        }
        notifyView();
    }

    private void spawnNewPiece() {
        state.setCurrentPiece(pieceQueue.getNextPiece());
        if (!state.getBoard().isValidBlock(state.getCurrentPiece(), 0, 0)) {
            state.setGameOver(true);
            gameLoopController.stop();
            soundManager.stopBGM();
            notifyView();
        }
    }

    private Tetromino createNextPiece() {
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

    public Queue<Tetromino> getUpcomingPieces() {
        return pieceQueue.getUpcomingPieces();
    }

    private void lockCurrentPieceAndContinue() {
        state.getBoard().lockPiece(state.getCurrentPiece());
        int linesCleared = state.getBoard().clearLines();
        state.addScore(scoreManager.calculateScore(linesCleared));

        if (linesCleared > 0) {
            soundManager.playSFX("/sounds/clear.wav");
        }

        spawnNewPiece();
    }

    public void moveLeft() {
        movementController.moveLeft();
        notifyView();
    }

    public void moveRight() {
        movementController.moveRight();
        notifyView();
    }

    public void softDrop() {
        movementController.moveDown();
        notifyView();
    }

    public void hardDrop() {
        movementController.hardDrop();
        notifyView();
    }

    public void rotateCurrentPiece() {
        movementController.rotateCurrentPiece();
        notifyView();
    }

    public void togglePause() {
        if (state.isGameOver())
            return;
        state.togglePaused();
    }

    public void start() {
        gameLoopController.start();
    }

    public void stop() {
        gameLoopController.stop();
    }

    public Board getBoard() {
        return state.getBoard();
    }

    public Tetromino getCurrentPiece() {
        return state.getCurrentPiece();
    }

    public int getScore() {
        return state.getScore();
    }

    public boolean isGameOver() {
        return state.isGameOver();
    }

    public boolean isPaused() {
        return state.isPaused();
    }

    public void setDifficultyDelay(int ms, int multiplier) {
        this.gameLoopController.setDelay(ms);
        this.scoreManager.setMultiplier(multiplier);
    }

    public SoundController getSoundManager() {
        return soundManager;
    }
}
