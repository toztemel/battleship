package app.game.ship;

import app.game.fire.Shot;
import app.game.fire.Shot.Damage;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

public class SWingTest {

    private Battleship ship;

    @Before
    public void setup() {
        ship = new SWing();
    }

    @Test
    public void miss_ship_when_frame_is_intact() {
        assertEquals(Damage.MISS, ship.hitBy(new Shot(0, 0)));
        assertEquals(Damage.MISS, ship.hitBy(new Shot(1, 0)));
        assertEquals(Damage.MISS, ship.hitBy(new Shot(1, 2)));
        assertEquals(Damage.MISS, ship.hitBy(new Shot(2, 2)));
    }

    @Test
    public void hit_ship_until_destroyed() {
        assertEquals(Damage.HIT, ship.hitBy(new Shot(0, 1)));
        assertEquals(Damage.HIT, ship.hitBy(new Shot(0, 2)));
        assertEquals(Damage.HIT, ship.hitBy(new Shot(1, 1)));
        assertEquals(Damage.HIT, ship.hitBy(new Shot(2, 0)));
        assertEquals(Damage.DESTROYED, ship.hitBy(new Shot(2, 1)));
    }

    @Ignore
    @Test
    public void ships_can_be_rotated(){
        Damage damage1 = ship.hitBy(new Shot(0, 0));
        Damage damage2 = ship.hitBy(new Shot(0, 0));
        assertEquals(damage1, damage2);

        ship.rotate();
        Damage damage3 = ship.hitBy(new Shot(0, 0));

        ship.rotate();
        Damage damage4 = ship.hitBy(new Shot(0, 0));

        if (damage1 == damage3) {
            assertNotEquals(damage1, damage4);
        } else {
            assertNotEquals(damage1, damage3);
        }
    }
}