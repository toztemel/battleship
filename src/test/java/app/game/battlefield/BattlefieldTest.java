package app.game.battlefield;

import app.game.api.dto.firing.FiringResults;
import app.game.conf.BattlefieldConf;
import app.game.fire.Coordinates;
import app.game.fire.Shot;
import app.game.ship.*;
import app.game.util.DoubleArrays;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static app.game.battlefield.BattlefieldShipReferenceMatcher.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class BattlefieldTest {

    private Battlefield battlefield;

    @Before
    public void setup() {
        battlefield = BattlefieldFactory.getInstance()
                .configure(new BattlefieldConf())
                .createTestInstance();
    }

    @Test
    public void battlefield_holds_references_to_ships() {

        Ship sWing = new SWing();
        Ship xWing = new XWing();
        Ship angle = new Angle();
        Ship bWing = new BWing();
        Ship aWing = new AWing();

        battlefield.with(angle, Coordinates.of(0, 0));
        battlefield.with(aWing, Coordinates.of(0, 7));
        battlefield.with(bWing, Coordinates.of(7, 0));
        battlefield.with(sWing, Coordinates.of(5, 5));
        battlefield.with(xWing, Coordinates.of(10, 10));

        assertThat(battlefield, contains().referenceTo(angle).at(Coordinates.of(0, 0)));
        assertThat(battlefield, contains().referenceTo(aWing).at(Coordinates.of(0, 8)));
        assertThat(battlefield, contains().referenceTo(bWing).at(Coordinates.of(7, 0)));
        assertThat(battlefield, contains().referenceTo(sWing).at(Coordinates.of(6, 6)));
        assertThat(battlefield, contains().referenceTo(xWing).at(Coordinates.of(10, 10)));
    }

    @Test
    public void battlefield_randomly_generates_ships() {
        int i = 0;
        while (i++ < 20) {
            battlefield = BattlefieldFactory.getInstance()
                    .configure(new BattlefieldConf())
                    .createInstance();

            DoubleArrays.print2DArray(battlefield.asString());
            System.out.println();
        }
    }

    @Test
    public void battlefield_delegates_shooting_to_ships(){
        Ship sWing = new SWing();

        battlefield.with(sWing, Coordinates.of(5, 5));

        List<Shot> shots = new ArrayList<Shot>(){
            {
                add(new Shot(Coordinates.of(5,5)));
                add(new Shot(Coordinates.of(6,6)));
            }
        };
        FiringResults firingResults = battlefield.shotBy(shots);

        assertEquals(Shot.Damage.MISS, firingResults.get("5x5"));
        assertEquals(Shot.Damage.HIT, firingResults.get("6x6"));
    }

}