package app.game.ship;

import app.game.ship.fire.Shot;
import app.game.ship.shape.Frame;
import app.game.ship.shape.FrameFactory;

abstract class Battleship implements Ship {

    private Frame frame;

    Battleship() {
        frame = FrameFactory.create(this);
    }

    public Frame getFrame() {
        return frame;
    }

    public Shot.Damage hitBy(Shot shot) {
        return frame.hitBy(shot);
    }

}
