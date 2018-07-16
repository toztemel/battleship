package app.game.ship;

import app.game.fire.Coordinates;

public interface Ship {

    int length();

    int width();

    String toStringAt(Coordinates coordinates);

    boolean isAlive();

    void goTo(Ship[][] field, Coordinates coordinates);
}
