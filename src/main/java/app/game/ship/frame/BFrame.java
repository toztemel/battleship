package app.game.ship.frame;

import app.game.ship.Ship;

class BFrame extends Frame {

    BFrame(Ship ship) {
        frame = new Ship[5][3];

        frame[0][0] = ship;
        frame[0][1] = ship;
        frame[0][2] = ship;
        frame[1][0] = ship;
        frame[1][2] = ship;
        frame[2][0] = ship;
        frame[2][1] = ship;
        frame[3][0] = ship;
        frame[3][2] = ship;
        frame[4][0] = ship;
        frame[4][1] = ship;
        frame[4][2] = ship;
    }

}
