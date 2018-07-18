package app.game.battlefield;

import app.game.api.dto.firing.FiringResults;
import app.game.conf.BattlefieldConf;
import app.game.fire.Coordinates;
import app.game.fire.Shot;
import app.game.ship.Battleship;
import app.game.ship.NullShipObject;
import app.game.ship.Ship;
import app.game.util.DoubleArrays;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Battlefield {

    private Ship[][] field;
    private BattlefieldConf conf;

    Battlefield() {
    }

    public String[] asString() {
        return DoubleArrays.asString(field);
    }

    public boolean allShipsKilled() {
        return Stream.of(field)
                .flatMap(Arrays::stream)
                .noneMatch(Ship::isAlive);
    }

    public FiringResults fireAt(List<Shot> shots) {
        FiringResults firingResults = new FiringResults();
        for (Shot shot : shots) {
            Shot.Damage result = fireAt(shot);
            firingResults.put(shot.asHexString(), result);
        }
        return firingResults;
    }

    private Shot.Damage fireAt(Shot shot) {
        Ship ship = field[shot.row()][shot.col()];
        if (ship == NullShipObject.instance()) {
            return Shot.Damage.MISS;
        } else {
            return ((Battleship) ship).hitBy(shot);
        }
    }

    Battlefield build() {
        field = new Ship[conf.size()][conf.size()];
        NullShipObject.fillArea(field);
        return this;
    }

    Battlefield randomly() {
        new BattlefieldDecorater(field).decorateRandomly();
        return this;
    }

    Ship shipAt(Coordinates c) {
        return field[c.row()][c.column()];
    }

    Battlefield with(Ship ship, Coordinates coordinates) {
        ship.goTo(field, coordinates);
        return this;
    }

    Battlefield setConf(BattlefieldConf conf) {
        this.conf = conf;
        return this;
    }
}
