package app.game.fire;

public final class Coordinates {

    private final int row;
    private final int column;
    private String hexRepresentation;

    private Coordinates(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public static Coordinates of(int row, int column) {
        return new Coordinates(row, column);
    }

    public static Coordinates fromProtocolString(String coordinateStr) {
        return HexToCoordinatesConverter.fromProtocolString(coordinateStr);
    }

    public int row() {
        return row;
    }

    public int column() {
        return column;
    }

    public Coordinates decrementBy(Coordinates offset) {
        if (offset == null) {
            return this;
        }
        return new Coordinates(row - offset.row, column - offset.column);
    }

    Coordinates withHexString(String s) {
        hexRepresentation = s;
        return this;
    }

    public String toHexString() {
        if (null == hexRepresentation) {
            hexRepresentation = HexToCoordinatesConverter.toProtocolString(this);
        }
        return hexRepresentation;
    }
}
