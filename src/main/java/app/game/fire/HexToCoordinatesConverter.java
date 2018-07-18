package app.game.fire;

public final class HexToCoordinatesConverter {

    private static final int HEX = 16;

    private HexToCoordinatesConverter() {
    }

    public static Coordinates fromProtocolString(String hexString) throws InvalidCoordinatesException {
        int xIndex = hexString.indexOf("x");
        if (xIndex < 0) {
            throw new InvalidCoordinatesException();
        }

        int row;
        int column;

        try {
            row = Integer.parseInt(hexString.substring(0, xIndex), HEX);
            column = Integer.parseInt(hexString.substring(xIndex + 1), HEX);
        } catch (NumberFormatException e) {
            throw new InvalidCoordinatesException(e);
        }

        if (row < 0 || column < 0) {
            throw new InvalidCoordinatesException();
        }

        return Coordinates.of(row, column).withHexString(hexString);
    }

    static String toProtocolString(Coordinates c) {
        return Integer.toHexString(c.row()).toUpperCase()
                + "x"
                + Integer.toHexString(c.column()).toUpperCase();
    }
}
