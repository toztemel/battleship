package app.game.battlefield;

import app.game.ship.Ship;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import static org.junit.Assert.assertEquals;

public class BattlefieldShipReferenceMatcher extends TypeSafeMatcher<Battlefield> {

    private static BattlefieldShipReferenceMatcher instance = new BattlefieldShipReferenceMatcher();
    private Ship ship;
    private int row;
    private int column;

    static BattlefieldShipReferenceMatcher contains() {
        return instance;
    }

    BattlefieldShipReferenceMatcher referenceTo(Ship s) {
        ship = s;
        return this;
    }

    BattlefieldShipReferenceMatcher at(int row) {
        this.row = row;
        return this;
    }

    BattlefieldShipReferenceMatcher by(int column) {
        this.column = column;
        return this;
    }

    @Override
    public boolean matchesSafely(Battlefield battlefield) {
        for (int i = 0; i < ship.length(); i++) {
            for (int j = 0; j < ship.width(); j++) {
                assertEquals(ship, battlefield.getCell(row + i, column + j));
            }
        }
        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("Battlefield must keep reference to ")
                .appendValue(ship.getClass().getSimpleName())
                .appendText(" at row:").appendValue(row)
                .appendValue(" by column:").appendValue(column);
    }

}
