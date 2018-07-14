package app.game.ship.shape;

import app.game.ship.*;

public class FrameFactory {

    public static Frame create(Ship ship) {
        if (ship instanceof Angle) {
            return new AngleFrame(ship);
        } else if (ship instanceof AWing) {
            return new AFrame(ship);
        } else if (ship instanceof BWing) {
            return new BFrame(ship);
        } else if (ship instanceof SWing) {
            return new SFrame(ship);
        } else {
            return new XFrame(ship);
        }
    }
}
