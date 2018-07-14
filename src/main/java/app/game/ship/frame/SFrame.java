package app.game.ship.frame;

import app.game.fire.Coordinates;
import app.game.ship.Ship;

class SFrame extends Frame {

    SFrame(Ship ship) {
        super(ship, new Ship[3][3]);
        fill(Coordinates.of(0, 1));
        fill(Coordinates.of(0, 2));
        fill(Coordinates.of(1, 1));
        fill(Coordinates.of(2, 0));
        fill(Coordinates.of(2, 1));
    }
}
