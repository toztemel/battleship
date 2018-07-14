package app.game.ship;

import app.game.common.Coordinates;

public interface Ship {

    int length();

    int width();

    String toStringAt(Coordinates coordinates);

    void insertedAt(Coordinates coordinates);
}
