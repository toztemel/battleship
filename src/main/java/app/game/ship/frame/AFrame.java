package app.game.ship.frame;

import app.game.common.Coordinates;
import app.game.ship.Ship;

class AFrame extends Frame {

    AFrame(Ship ship) {
        super(ship, new Ship[4][3]);
        fill(Coordinates.of(0, 1));
        fill(Coordinates.of(1, 0));
        fill(Coordinates.of(1, 2));
        fill(Coordinates.of(2, 0));
        fill(Coordinates.of(2, 1));
        fill(Coordinates.of(2, 2));
        fill(Coordinates.of(3, 0));
        fill(Coordinates.of(3, 2));
    }
}
