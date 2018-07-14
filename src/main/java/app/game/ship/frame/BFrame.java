package app.game.ship.frame;

import app.game.fire.Coordinates;
import app.game.ship.Ship;

class BFrame extends Frame {

    BFrame(Ship ship) {
        super(ship, new Ship[5][3]);
        fill(Coordinates.of(0, 0));
        fill(Coordinates.of(0, 1));
        fill(Coordinates.of(0, 2));
        fill(Coordinates.of(1, 0));
        fill(Coordinates.of(1, 2));
        fill(Coordinates.of(2, 0));
        fill(Coordinates.of(2, 1));
        fill(Coordinates.of(3, 0));
        fill(Coordinates.of(3, 2));
        fill(Coordinates.of(4, 0));
        fill(Coordinates.of(4, 1));
        fill(Coordinates.of(4, 2));
    }
}
