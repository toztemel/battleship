package app.game.battlefield;

import app.game.ship.*;
import org.junit.Before;
import org.junit.Test;

import static app.game.battlefield.BattlefieldShipReferenceMatcher.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class BattlefieldTest {

    private Battlefield battlefield;

    @Before
    public void setup() {
        battlefield = new Battlefield();
    }

    @Test
    public void battlefield_size_is_16x16() {
        assertEquals(16, battlefield.getLength());
        assertEquals(16, battlefield.getWidth());
    }

    @Test
    public void battlefield_holds_references_to_ships() {

        Ship sWing = new SWing();
        Ship xWing = new XWing();
        Ship angle = new Angle();
        Ship bWing = new BWing();
        Ship aWing = new AWing();

        battlefield.insert(angle, 0, 0);
        battlefield.insert(aWing, 0, 7);
        battlefield.insert(bWing, 7, 0);
        battlefield.insert(sWing, 5, 5);
        battlefield.insert(xWing, 10, 10);

        assertThat(battlefield, contains().referenceTo(angle).at(0).by(0));
        assertThat(battlefield, contains().referenceTo(aWing).at(0).by(7));
        assertThat(battlefield, contains().referenceTo(bWing).at(7).by(0));
        assertThat(battlefield, contains().referenceTo(sWing).at(5).by(5));
        assertThat(battlefield, contains().referenceTo(xWing).at(10).by(10));
    }

}