package app.game.battlefield;

import app.game.fire.Coordinates;
import app.game.ship.*;

import java.util.*;

class BattlefieldDecorater {

    private Ship[][] field;

    BattlefieldDecorater(Ship[][] field) {
        this.field = field;
    }

    void decorateRandomly() {
        List<Battleship> ships = new ArrayList<Battleship>() {
            {
                add(new Angle().rotate());
                add(new AWing().rotate());
                add(new BWing().rotate());
                add(new SWing().rotate());
                add(new XWing().rotate());
            }
        };
        Collections.shuffle(ships); // the order of insertion effects randomness
        insert(ships.toArray(new Battleship[0]));
    }

    private void insert(Battleship... ships) {
        Arrays.stream(ships).forEach(ship -> {
            int rowOffset = field.length - ship.length();
            int columnOffset = field[0].length - ship.width();

            int row;
            int column;
            Random random = new Random();
            do {
                row = random.nextInt(rowOffset + 1);
                column = random.nextInt(columnOffset + 1);
            } while (!isAvailable(field, ship, row, column));

            ship.goTo(field, Coordinates.of(row, column));
        });
    }

    private boolean isAvailable(Ship[][] field, Battleship ship, int row0, int column0) {

        int shipLength = ship.length();
        int shipWidth = ship.width();

        for (int i = -1; i <= shipLength; i++) {
            for (int j = -1; j <= shipWidth; j++) {
                int row = row0 + i;
                int column = column0 + j;

                if (!withinBoundaries(row, field.length)
                        || !(withinBoundaries(column, field[0].length))) {
                    continue;
                }

                Ship cell = field[row0 + i][column0 + j];
                if (!(cell instanceof NullShipObject)) {
                    return false;
                }
            }
        }

        return true;

    }

    private boolean withinBoundaries(int index, int limit) {
        return index >= 0 && index < limit;
    }
}
