package app.game.ship;

import app.game.fire.Coordinates;

public class Emptiness implements Ship {

    private static Ship instance = new Emptiness();

    private Emptiness() {
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
    public void insertedAt(Coordinates c) {

    }

    @Override
    public String toString() {
        return ".";
    }
}
