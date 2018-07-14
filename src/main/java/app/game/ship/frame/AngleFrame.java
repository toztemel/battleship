package app.game.ship.frame;

import app.game.common.Coordinates;
import app.game.ship.Ship;

class AngleFrame extends Frame {

    AngleFrame(Ship ship) {
        super(ship, new Ship[4][3]);
        fill(Coordinates.of(0, 0));
        fill(Coordinates.of(1, 0));
        fill(Coordinates.of(2, 0));
        fill(Coordinates.of(3, 0));
        fill(Coordinates.of(3, 1));
        fill(Coordinates.of(3, 2));
    }
}
