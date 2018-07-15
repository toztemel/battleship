package app.game.battlefield;

import app.game.fire.Coordinates;
import app.game.ship.Ship;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class BattlefieldShipReferenceMatcher extends TypeSafeMatcher<Battlefield> {

    private static BattlefieldShipReferenceMatcher instance = new BattlefieldShipReferenceMatcher();
    private Ship ship;
    private Coordinates coordinates;

    static BattlefieldShipReferenceMatcher contains() {
        return instance;
    }

    BattlefieldShipReferenceMatcher referenceTo(Ship s) {
        ship = s;
        return this;
    }

    BattlefieldShipReferenceMatcher at(Coordinates c) {
        coordinates = c;
        return this;
    }

    @Override
    public boolean matchesSafely(Battlefield battlefield) {
        return assertThatShipSpansTheArea(battlefield);
    }

    private boolean assertThatShipSpansTheArea(Battlefield battlefield) {
        for (int i = 0; i < ship.length(); i++) {
            for (int j = 0; j < ship.width(); j++) {
                if (!ship.equals(battlefield.shipAt(byOffset(i, j)))) {
                    return false;
                }
            }
        }
        return true;
    }

    private Coordinates byOffset(int i, int j) {
        return coordinates.incrementBy(i, j);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("Battlefield must keep reference to ")
                .appendValue(ship.getClass().getSimpleName())
                .appendText(" at row:").appendValue(coordinates.row())
                .appendValue(" by column:").appendValue(coordinates.column());
    }

}
