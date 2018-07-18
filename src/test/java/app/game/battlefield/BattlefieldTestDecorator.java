package app.game.battlefield;

import app.game.fire.Coordinates;
import app.game.ship.Ship;

public class BattlefieldTestDecorator {

    private static Battlefield battlefield;

    public static BattlefieldTestDecorator decorate(Battlefield battlefield) {
        BattlefieldTestDecorator.battlefield = battlefield;
        return new BattlefieldTestDecorator();
    }

    public BattlefieldTestDecorator with(Ship ship, Coordinates coordinates) {
        battlefield.with(ship, coordinates);
        return this;
    }
}