package app.game.battlefield;

import app.game.ship.Ship;

public interface Inserter {

    ShipHolder with(Ship s);

    Battlefield build();
}
