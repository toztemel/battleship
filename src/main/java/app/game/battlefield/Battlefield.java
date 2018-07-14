package app.game.battlefield;

import app.game.common.Coordinates;
import app.game.ship.Ship;
import app.game.util.Utility;

public class Battlefield {

    private static final int SIZE = 16;

    private Ship[][] field;

    Battlefield() {
        field = new Ship[SIZE][SIZE];
        Utility.fillEmpty(field);
    }

    int getLength() {
        return field.length;
    }

    int getWidth() {
        return field[0].length;
    }

    void insert(Ship ship, Coordinates coordinates) {
        int row = coordinates.row();
        int column = coordinates.column();
        for (int i = 0; i < ship.length(); i++) {
            for (int j = 0; j < ship.width(); j++) {
                field[row + i][column + j] = ship;
                ship.insertedAt(coordinates);
            }
        }
        Utility.print2DArray(field);
    }

    Ship getCell(Coordinates c) {
        return field[c.row()][c.column()];
    }
}
