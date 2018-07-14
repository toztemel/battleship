package app.game.ship.frame;

import app.game.ship.Ship;

class BFrame extends Frame {

    BFrame(Ship ship) {
        super(ship, new Ship[5][3]);
        fill(0, 0);
        fill(0, 1);
        fill(0, 2);
        fill(1, 0);
        fill(1, 2);
        fill(2, 0);
        fill(2, 1);
        fill(3, 0);
        fill(3, 2);
        fill(4, 0);
        fill(4, 1);
        fill(4, 2);
    }
}
