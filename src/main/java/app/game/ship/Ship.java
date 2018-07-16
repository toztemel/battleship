package app.game.ship;

import app.game.fire.Coordinates;

public interface Ship {

    String toStringAt(Coordinates coordinates);

    boolean isAlive();

    void goTo(Ship[][] field, Coordinates coordinates);
}
