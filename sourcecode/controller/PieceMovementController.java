package controller;

import model.Board;
import model.GameState;
import model.Tetromino;

public class PieceMovementController {
    private final GameState state;
    private final Runnable lockCurrentPiece;

    public PieceMovementController(GameState state, Runnable lockCurrentPiece) {
        this.state = state;
        this.lockCurrentPiece = lockCurrentPiece;
    }

    public void moveLeft() {
        if (canMove() && state.getBoard().isValidBlock(state.getCurrentPiece(), -1, 0)) {
            state.getCurrentPiece().move(-1, 0);
        }
    }

    public void moveRight() {
        if (canMove() && state.getBoard().isValidBlock(state.getCurrentPiece(), 1, 0)) {
            state.getCurrentPiece().move(1, 0);
        }
    }

    public void moveDown() {
        if (!canMove()) {
            return;
        }

        if (state.getBoard().isValidBlock(state.getCurrentPiece(), 0, 1)) {
            state.getCurrentPiece().move(0, 1);
        } else {
            lockCurrentPiece.run();
        }
    }

    public void hardDrop() {
        if (!canMove()) {
            return;
        }

        Tetromino currentPiece = state.getCurrentPiece();
        Board board = state.getBoard();
        while (board.isValidBlock(currentPiece, 0, 1)) {
            currentPiece.move(0, 1);
        }
        lockCurrentPiece.run();
    }

    public void rotateCurrentPiece() {
        if (!canMove()) {
            return;
        }

        Tetromino currentPiece = state.getCurrentPiece();
        Board board = state.getBoard();

        currentPiece.rotate();

        if (board.isValidBlock(currentPiece, 0, 0)) {
            return;
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
    }

    private boolean canMove() {
        return !state.isGameOver() && !state.isPaused() && state.getCurrentPiece() != null;
    }
}
