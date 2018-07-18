package app.game.battlefield;

import app.game.conf.BattlefieldConf;
import app.game.fire.Coordinates;
import app.game.ship.*;
import app.game.util.DoubleArrays;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

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
    public void battlefield_size_is_16x16() {
        assertEquals(16, battlefield.length());
        assertEquals(16, battlefield.width());
    }

    @Test
    public void battlefield_holds_references_to_ships() {

        Ship sWing = new SWing();
        Ship xWing = new XWing();
        Ship angle = new Angle();
        Ship bWing = new BWing();
        Ship aWing = new AWing();

        battlefield.with(angle).at(Coordinates.of(0, 0));
        battlefield.with(aWing).at(Coordinates.of(0, 7));
        battlefield.with(bWing).at(Coordinates.of(7, 0));
        battlefield.with(sWing).at(Coordinates.of(5, 5));
        battlefield.with(xWing).at(Coordinates.of(10, 10));

        assertThat(battlefield, contains().referenceTo(angle).at(Coordinates.of(0, 0)));
        assertThat(battlefield, contains().referenceTo(aWing).at(Coordinates.of(0, 8)));
        assertThat(battlefield, contains().referenceTo(bWing).at(Coordinates.of(7, 0)));
        assertThat(battlefield, contains().referenceTo(sWing).at(Coordinates.of(6, 6)));
        assertThat(battlefield, contains().referenceTo(xWing).at(Coordinates.of(10, 10)));
    }

    @Test
    public void length() {
        assertEquals(16, battlefield.length());
    }

    @Test
    public void width() {
        assertEquals(16, battlefield.width());
    }

    @Ignore
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

}