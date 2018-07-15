package app.game.ship;

import app.game.fire.Coordinates;

public interface Ship {

    int length();

    int width();

    String toStringAt(Coordinates coordinates);

    void insertedAt(Coordinates coordinates);

    boolean isAlive();
}
