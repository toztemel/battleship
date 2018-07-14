package app.game.ship;

import app.game.fire.Shot;
import app.game.ship.frame.Frame;
import app.game.ship.frame.FrameFactory;

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

    public void rotate() {
        frame.rotate();
    }
}
