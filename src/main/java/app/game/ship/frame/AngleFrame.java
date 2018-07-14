package app.game.ship.frame;

import app.game.ship.Ship;

class AngleFrame extends Frame {

    AngleFrame(Ship ship) {
        super(ship, new Ship[4][3]);
        fill(0, 0);
        fill(1, 0);
        fill(2, 0);
        fill(3, 0);
        fill(3, 1);
        fill(3, 2);
    }
}
