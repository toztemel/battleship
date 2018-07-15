package app.game.battlefield;

import app.game.ship.Ship;

public interface Inserter {

    ShipHolder insert(Ship s);

    Battlefield build();
}
