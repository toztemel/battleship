package app.game.ship.shape;

import app.game.ship.Ship;

public class AngleFrame extends Frame {

    AngleFrame(Ship ship) {
        frame = new Ship[4][3];

        frame[0][0] = ship;
        frame[1][0] = ship;
        frame[2][0] = ship;
        frame[3][0] = ship;
        frame[3][1] = ship;
        frame[3][2] = ship;
    }

}
