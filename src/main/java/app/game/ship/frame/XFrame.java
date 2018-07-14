package app.game.ship.frame;

import app.game.ship.Ship;

class XFrame extends Frame {

    XFrame(Ship ship) {
        super(ship, new Ship[5][3]);
        fill(0, 0);
        fill(0, 2);
        fill(1, 0);
        fill(1, 2);
        fill(2, 1);
        fill(3, 0);
        fill(3, 2);
        fill(4, 0);
        fill(4, 2);
    }
}
