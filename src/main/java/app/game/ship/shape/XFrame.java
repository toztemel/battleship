package app.game.ship.shape;

import app.game.ship.Ship;

public class XFrame extends Frame {

    XFrame(Ship ship) {
        frame = new Ship[5][3];

        frame[0][0] = ship;
        frame[0][2] = ship;
        frame[1][0] = ship;
        frame[1][2] = ship;
        frame[2][1] = ship;
        frame[3][0] = ship;
        frame[3][2] = ship;
        frame[4][0] = ship;
        frame[4][2] = ship;
    }

}
