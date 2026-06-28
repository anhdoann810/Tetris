package controller;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Supplier;
import model.Tetromino;

public class PieceQueue {
    private static final int NEXT_PIECE_COUNT = 3;

    private final Queue<Tetromino> queuePiece;
    private final Supplier<Tetromino> pieceSupplier;

    public PieceQueue(Supplier<Tetromino> pieceSupplier) {
        this.pieceSupplier = pieceSupplier;
        this.queuePiece = new LinkedList<>();
    }

    public void reset() {
        queuePiece.clear();
        refill();
    }

    public Tetromino getNextPiece() {
        Tetromino nextPiece = queuePiece.remove();
        refill();
        return nextPiece;
    }

    public Queue<Tetromino> getUpcomingPieces() {
        return queuePiece;
    }

    private void refill() {
        while (queuePiece.size() < NEXT_PIECE_COUNT) {
            queuePiece.add(pieceSupplier.get());
        }
    }
}
