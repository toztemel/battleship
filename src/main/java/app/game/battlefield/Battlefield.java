package app.game.battlefield;

import app.game.fire.Coordinates;
import app.game.fire.Shot;
import app.game.ship.Battleship;
import app.game.ship.Emptiness;
import app.game.ship.Ship;
import app.game.util.DoubleArrays;

import java.util.Arrays;
import java.util.stream.Stream;

import static app.game.battlefield.Constants.BATTLEFIELD_SIZE;

public class Battlefield implements ShipHolder, Inserter {

    private Ship[][] field;
    private Ship aShip;

    Battlefield() {
        initializeField();
    }

    public static Inserter getInstance() {
        return new Battlefield();
    }

    private void initializeField() {
        field = new Ship[BATTLEFIELD_SIZE][BATTLEFIELD_SIZE];
        DoubleArrays.fillEmpty(field);
    }

    int length() {
        return field.length;
    }

    int width() {
        return field[0].length;
    }

    Ship shipAt(Coordinates c) {
        return field[c.row()][c.column()];
    }

    @Override
    public ShipHolder with(Ship s) {
        aShip = s;
        return this;
    }

    @Override
    public Battlefield build() {
        return this;
    }

    public Battlefield print() {
        DoubleArrays.print2DArray(field);
        return this;
    }

    @Override
    public Battlefield at(Coordinates coordinates) {
        int row = coordinates.row();
        int column = coordinates.column();
        for (int i = 0; i < aShip.length(); i++) {
            for (int j = 0; j < aShip.width(); j++) {
                field[row + i][column + j] = aShip;
                aShip.insertedAt(coordinates);
            }
        }
        return this;
    }

    public void fireAt(Shot shot) {
        Ship ship = field[shot.row()][shot.col()];
        if (ship == Emptiness.instance()) {
            shot.missed();
        } else {
            ((Battleship) ship).hitBy(shot);
        }
    }

    public boolean allShipsKilled() {
        return Stream.of(field)
                .flatMap(Arrays::stream)
                .anyMatch(Ship::isAlive);
    }
}
