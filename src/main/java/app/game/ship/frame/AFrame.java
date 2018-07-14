package app.game.ship.frame;

import app.game.ship.Ship;

class AFrame extends Frame {

    AFrame(Ship ship) {
        super(ship, new Ship[4][3]);
        fill(0, 1);
        fill(1, 0);
        fill(1, 2);
        fill(2, 0);
        fill(2, 1);
        fill(2, 2);
        fill(3, 0);
        fill(3, 2);
    }
}
