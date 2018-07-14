package app.game.ship.frame;

import app.game.ship.Ship;

class SFrame extends Frame {

    SFrame(Ship ship) {
        super(ship, new Ship[3][3]);
        fill(0, 1);
        fill(0, 2);
        fill(1, 1);
        fill(2, 0);
        fill(2, 1);
    }
}
