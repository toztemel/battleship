package app.game.ship;

import app.game.common.Coordinates;

public class DamagedShip implements Ship {

    private static Ship instance = new DamagedShip();

    private DamagedShip() {
    }

    public static Ship getInstance() {
        return instance;
    }

    @Override
    public int length() {
        return 1;
    }

    @Override
    public int width() {
        return 1;
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
        return "X";
    }
}
