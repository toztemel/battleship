package app.game.battlefield;

import app.game.conf.BattlefieldConf;
import app.game.fire.Coordinates;
import app.game.fire.Shot;
import app.game.ship.*;
import app.game.util.DoubleArrays;

import java.util.*;
import java.util.stream.Stream;

public class Battlefield {

    private Ship[][] field;
    private Ship aShip;
    private BattlefieldConf conf;
    private boolean randomly;

    Battlefield() {
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

    public Battlefield with(Ship s) {
        aShip = s;
        return this;
    }

    public String[] asString() {
        return DoubleArrays.asString(field);
    }

    public Battlefield at(Coordinates coordinates) {
        aShip.goTo(field, coordinates);
        return this;
    }

    public Shot.Damage fireAt(Shot shot) {
        Ship ship = field[shot.row()][shot.col()];
        if (ship == NullShipObject.instance()) {
            return Shot.Damage.MISS;
        } else {
            return ((Battleship) ship).hitBy(shot);
        }
    }

    public boolean allShipsKilled() {
        return Stream.of(field)
                .flatMap(Arrays::stream)
                .noneMatch(Ship::isAlive);
    }

    Battlefield setConf(BattlefieldConf conf) {
        this.conf = conf;
        return this;
    }

    public Battlefield randomly() {
        randomly = true;
        return this;
    }

    Battlefield build() {
        field = new Ship[conf.size()][conf.size()];
        DoubleArrays.fillEmpty(field);
        if (randomly) {
            generateRandomShips();
        }
        return this;
    }

    private void generateRandomShips() {
        List<Battleship> ships = new ArrayList<Battleship>(){
            {
                add(new Angle().rotate());
                add(new AWing().rotate());
                add(new BWing().rotate());
                add(new SWing().rotate());
                add(new XWing().rotate());

            }
        };
        Collections.shuffle(ships);
        locateShip(ships.toArray(new Battleship[0]));

    }

    private void locateShip(Battleship... ships) {
        for(Battleship ship : ships) {
            int maxRow = field.length - ship.length();
            int maxColumn = field[0].length - ship.width();
            Random random = new Random();
            int row = random.nextInt(maxRow + 1);
            int column = random.nextInt(maxColumn + 1);

            while(!isAvailable(field, ship, row, column)) {
                row = random.nextInt(maxRow + 1);
                column = random.nextInt(maxColumn + 1);
            }
            ship.goTo(field, Coordinates.of(row, column));

        }
    }

    private boolean isAvailable(Ship[][] field, Battleship ship, int targetRow, int targetColumn) {

        int shipLength = ship.length();
        int shipWidth = ship.width();

        for (int i = -1; i <= shipLength; i++) {
            for (int j = -1; j <= shipWidth; j++) {
                int row = targetRow + i;
                int column = targetColumn + j;

                if (!withinBoundaries(row, field.length)
                        || !(withinBoundaries(column, field[0].length))) {
                    continue;
                }

                Ship cell = field[targetRow + i][targetColumn + j];
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
