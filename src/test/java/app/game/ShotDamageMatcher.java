package app.game;

import app.game.api.dto.firing.FiringResponse;
import app.game.fire.Coordinates;
import app.game.fire.Shot;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class ShotDamageMatcher extends TypeSafeMatcher<FiringResponse> {

    private static ShotDamageMatcher instance = new ShotDamageMatcher();

    private Coordinates coordinates;
    private Shot.Damage expected;

    static ShotDamageMatcher contains() {
        return instance;
    }

    static ShotDamageMatcher at(Coordinates c) {
        instance.coordinates = c;
        return instance;
    }

    @Override
    public boolean matchesSafely(FiringResponse response) {
        return expected.toString()
                .toLowerCase()
                .equals(
                        response.getShots()
                                .get(coordinates.toHexString())
                );
    }

    @Override
    public void describeMismatchSafely(FiringResponse r, Description description) {
        description.appendText(" it is ")
                .appendValue(r.getShots().get(coordinates.toHexString()));
    }

    ShotDamageMatcher is(Shot.Damage hit) {
        expected = hit;
        return this;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("Response of firing request must have result of ")
                .appendValue(expected.toString().toLowerCase())
                .appendText(" aiming row:").appendValue(coordinates.row())
                .appendText(" by column:").appendValue(coordinates.column());
    }
}
