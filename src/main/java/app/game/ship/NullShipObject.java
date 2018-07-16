package app.game.ship;

import app.game.fire.Coordinates;

public class NullShipObject implements Ship {

    private static Ship instance = new NullShipObject();

    private NullShipObject() {
    }

    public static Ship instance() {
        return instance;
    }

    @Override
    public int length() {
        return 0;
    }

    @Override
    public int width() {
        return 0;
    }

    @Override
    public String toStringAt(Coordinates c) {
        return toString();
    }

    @Override
    public boolean isAlive() {
        return false;
    }

    @Override
    public void goTo(Ship[][] field, Coordinates coordinates) {
        int row = coordinates.row();
        int column = coordinates.column();
        field[row][column] = instance();
    }

    @Override
    public String toString() {
        return ".";
    }
}
