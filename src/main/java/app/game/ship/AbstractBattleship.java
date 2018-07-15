package app.game.ship;

import app.game.fire.Coordinates;
import app.game.fire.Shot;
import app.game.ship.frame.Frame;
import app.game.ship.frame.FrameFactory;

abstract class AbstractBattleship implements Battleship {

    private Frame frame;
    private Coordinates battlefieldCoordinates;

    AbstractBattleship() {
        frame = FrameFactory.create(this);
    }

    public Frame getFrame() {
        return frame;
    }

    @Override
    public Shot.Damage hitBy(Shot shot) {
        return frame.hitBy(byOffset(shot));
    }

    private Shot byOffset(Shot shot) {
        return new Shot(byOffset(Coordinates.of(shot.row(), shot.col())));
    }

    public Battleship rotate() {
        frame.rotate();
        return this;
    }

    public int length() {
        return frame.length();
    }

    public int width() {
        return frame.width();
    }

    public String toStringAt(Coordinates coordinates) {
        return frame.toStringAt(byOffset(coordinates));
    }

    private Coordinates byOffset(Coordinates coordinates) {
        return coordinates.decrementBy(battlefieldCoordinates);
    }

    @Override
    public void insertedAt(Coordinates coordinates) {
        battlefieldCoordinates = coordinates;
    }

    @Override
    public boolean isAlive() {
        return frame.isAlive();
    }
}
