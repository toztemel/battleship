package app.game.ship;

import app.game.common.Coordinates;
import app.game.fire.Shot;
import app.game.ship.frame.Frame;
import app.game.ship.frame.FrameFactory;

abstract class Battleship implements Ship {

    private Frame frame;
    private Coordinates battlefieldCoordinates;

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

    public int length() {
        return frame.length();
    }

    public int width() {
        return frame.width();
    }

    public String toStringAt(Coordinates coordinates) {
        return frame.toStringAt(coordinates.decrementBy(battlefieldCoordinates));
    }

    @Override
    public void insertedAt(Coordinates coordinates) {
        battlefieldCoordinates = coordinates;
    }
}
