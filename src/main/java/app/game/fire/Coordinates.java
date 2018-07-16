package app.game.fire;

public final class Coordinates {

    private static final int HEX = 16;

    private String hexRepresentation;
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

    public Coordinates withStringRepresentation(String s) {
        hexRepresentation = s;
        return this;
    }

    public String toHexString() {
        if(null == hexRepresentation) {
            hexRepresentation = CoordinatesFormatter.toProtocolString(this);
        }
        return hexRepresentation;
    }
}
