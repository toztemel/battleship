package app.game.piece;

public class CellOccupation {

    private final int length;
    private final int width;

    CellOccupation(int length, int width) {
        this.length = length;
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }
}
