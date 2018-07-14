package app.game.ship;

import app.game.fire.Shot;
import app.game.fire.Shot.Damage;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class XWingTest {

    private Battleship ship;

    @Before
    public void setup() {
        ship = new XWing();
    }

    @Test
    public void miss_ship_when_frame_is_intact() {
        assertEquals(Damage.MISS, ship.hitBy(new Shot(0, 1)));
        assertEquals(Damage.MISS, ship.hitBy(new Shot(1, 1)));
        assertEquals(Damage.MISS, ship.hitBy(new Shot(2, 0)));
        assertEquals(Damage.MISS, ship.hitBy(new Shot(2, 2)));
        assertEquals(Damage.MISS, ship.hitBy(new Shot(3, 1)));
        assertEquals(Damage.MISS, ship.hitBy(new Shot(4, 1)));
    }

    @Test
    public void hit_ship_until_destroyed() {
        assertEquals(Damage.HIT, ship.hitBy(new Shot(0, 0)));
        assertEquals(Damage.HIT, ship.hitBy(new Shot(0, 2)));
        assertEquals(Damage.HIT, ship.hitBy(new Shot(1, 0)));
        assertEquals(Damage.HIT, ship.hitBy(new Shot(1, 2)));
        assertEquals(Damage.HIT, ship.hitBy(new Shot(2, 1)));
        assertEquals(Damage.HIT, ship.hitBy(new Shot(3, 0)));
        assertEquals(Damage.HIT, ship.hitBy(new Shot(3, 2)));
        assertEquals(Damage.HIT, ship.hitBy(new Shot(4, 0)));
        assertEquals(Damage.DESTROYED, ship.hitBy(new Shot(4, 2)));
    }

}