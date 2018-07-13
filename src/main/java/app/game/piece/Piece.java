package app.game.piece;

public abstract class Piece {

    private final CellOccupation cellOccupation;

    protected Piece(CellOccupation cellOccupation) {
        this.cellOccupation = cellOccupation;
    }

    public CellOccupation getCellOccupation() {
        return cellOccupation;
    }

}
