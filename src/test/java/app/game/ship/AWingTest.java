package app.game.ship;

import app.game.fire.Shot;
import app.game.fire.Shot.Damage;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AWingTest {

    private AWing ship;

    @Before
    public void setup() {
        ship = new AWing();
    }

    @Test
    public void miss_ship_when_frame_is_intact() {
        assertEquals(Damage.MISS, ship.hitBy(Shot.at(0, 0)));
        assertEquals(Damage.MISS, ship.hitBy(Shot.at(0, 2)));
        assertEquals(Damage.MISS, ship.hitBy(Shot.at(1, 1)));
        assertEquals(Damage.MISS, ship.hitBy(Shot.at(3, 1)));
    }


    @Test
    public void hit_ship_until_destroyed() {
        assertEquals(Damage.HIT, ship.hitBy(Shot.at(0, 1)));
        assertEquals(Damage.HIT, ship.hitBy(Shot.at(1, 0)));
        assertEquals(Damage.HIT, ship.hitBy(Shot.at(1, 2)));
        assertEquals(Damage.HIT, ship.hitBy(Shot.at(2, 0)));
        assertEquals(Damage.HIT, ship.hitBy(Shot.at(2, 1)));
        assertEquals(Damage.HIT, ship.hitBy(Shot.at(2, 2)));
        assertEquals(Damage.HIT, ship.hitBy(Shot.at(3, 0)));
        assertEquals(Damage.KILL, ship.hitBy(Shot.at(3, 2)));
    }

}