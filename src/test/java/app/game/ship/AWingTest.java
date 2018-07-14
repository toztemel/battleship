package app.game.ship;

import app.game.fire.Shot;
import app.game.fire.Shot.Damage;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AWingTest {

    private AWing ship;

    @Before
    public void setup() {
        ship = new AWing();
    }

    @Test
    public void miss_AWing_ship_when_frame_is_intact() {
        assertEquals(Damage.MISS, ship.hitBy(new Shot(0, 0)));
        assertEquals(Damage.MISS, ship.hitBy(new Shot(0, 2)));
        assertEquals(Damage.MISS, ship.hitBy(new Shot(1, 1)));
        assertEquals(Damage.MISS, ship.hitBy(new Shot(3, 1)));
    }

    @Test
    public void miss_AWing_ship_when_frame_is_out_of_bounds() {
        assertEquals(Damage.MISS, ship.hitBy(new Shot(0, 3)));
        assertEquals(Damage.MISS, ship.hitBy(new Shot(0, 10)));
    }

    @Test
    public void hit_AWing_ship_when_frame_is_damaged() {
        assertEquals(Damage.HIT, ship.hitBy(new Shot(0, 1)));
        assertEquals(Damage.HIT, ship.hitBy(new Shot(1, 0)));
        assertEquals(Damage.HIT, ship.hitBy(new Shot(1, 2)));
        assertEquals(Damage.HIT, ship.hitBy(new Shot(2, 0)));
        assertEquals(Damage.HIT, ship.hitBy(new Shot(2, 1)));
        assertEquals(Damage.HIT, ship.hitBy(new Shot(2, 2)));
        assertEquals(Damage.HIT, ship.hitBy(new Shot(3, 0)));
    }

    @Ignore
    @Test
    public void shootingSamePointDoesntDamage() {

    }

    @Test
    public void destroyingTheShip() {
        ship.hitBy(new Shot(0, 1));
        ship.hitBy(new Shot(1, 0));
        ship.hitBy(new Shot(1, 2));
        ship.hitBy(new Shot(2, 0));
        ship.hitBy(new Shot(2, 1));
        ship.hitBy(new Shot(2, 2));
        ship.hitBy(new Shot(3, 0));

        assertEquals(Damage.DESTROYED, ship.hitBy(new Shot(3, 2)));
    }

}