package app.game.ship;

import app.game.fire.Coordinates;

import java.util.Arrays;

public class NullShipObject implements Ship {

    private static Ship instance = new NullShipObject();

    private NullShipObject() {
    }

    public static Ship instance() {
        return instance;
    }

    public static void fillArea(Ship[][] ships) {
        Arrays.stream(ships).forEach(row -> {
            Arrays.fill(row, instance);
        });
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
