package app.game.ship.shape;

import app.game.ship.Ship;

public class SFrame extends Frame {

    SFrame(Ship ship) {
        frame = new Ship[3][3];

        frame[0][1] = ship;
        frame[0][2] = ship;
        frame[1][1] = ship;
        frame[2][0] = ship;
        frame[2][1] = ship;
    }

}
