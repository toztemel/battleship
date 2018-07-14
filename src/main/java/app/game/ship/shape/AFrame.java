package app.game.ship.shape;

import app.game.ship.Ship;

public class AFrame extends Frame {

    AFrame(Ship ship) {
        frame = new Ship[4][3];

        frame[0][1] = ship;
        frame[1][0] = ship;
        frame[1][2] = ship;
        frame[2][0] = ship;
        frame[2][1] = ship;
        frame[2][2] = ship;
        frame[3][0] = ship;
        frame[3][2] = ship;
    }

}
