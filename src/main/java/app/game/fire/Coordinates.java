package app.game.fire;

public final class Coordinates {

    private final int row;
    private final int column;

    private Coordinates(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public static Coordinates of(int row, int column) {
        return new Coordinates(row, column);
    }

    public int row() {
        return row;
    }

    public int column() {
        return column;
    }

    public Coordinates incrementBy(Coordinates offset) {
        return new Coordinates(row + offset.row, column + offset.column);
    }

    public Coordinates incrementBy(int i, int j) {
        return new Coordinates(row + i, column + j);
    }

    public Coordinates decrementBy(int i, int j) {
        return new Coordinates(row - i, column - j);
    }

    public Coordinates decrementBy(Coordinates offset) {
        if (offset == null) {
            return this;
        }
        return new Coordinates(row - offset.row, column - offset.column);
    }

    public String toProtocolString() {
        return Integer.toHexString(row).toUpperCase()
                + "x"
                + Integer.toHexString(column).toUpperCase();
    }
}
