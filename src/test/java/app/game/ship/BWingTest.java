package app.game.ship;

import app.game.fire.Shot;
import app.game.fire.Shot.Damage;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BWingTest {

    private AbstractBattleship ship;

    @Before
    public void setup() {
        ship = new BWing();
    }

    @Test
    public void miss_ship_when_frame_is_intact() {
        Shot shot = Shot.at(1, 1);
        assertEquals(Damage.MISS,ship.hitBy(shot));

        shot = Shot.at(3, 1);
        assertEquals(Damage.MISS,ship.hitBy(shot));
    }

    @Test
    public void hit_ship_until_destroyed() {
        Shot shot = Shot.at(0, 0);
        assertEquals(Damage.HIT,ship.hitBy(shot));

        shot = Shot.at(0, 1);
        assertEquals(Damage.HIT,ship.hitBy(shot));

        shot = Shot.at(0, 2);
        assertEquals(Damage.HIT,ship.hitBy(shot));

        shot = Shot.at(1, 0);
        assertEquals(Damage.HIT,ship.hitBy(shot));

        shot = Shot.at(1, 2);
        assertEquals(Damage.HIT,ship.hitBy(shot));

        shot = Shot.at(2, 0);
        assertEquals(Damage.HIT,ship.hitBy(shot));

        shot = Shot.at(2, 1);
        assertEquals(Damage.HIT,ship.hitBy(shot));

        shot = Shot.at(3, 0);
        assertEquals(Damage.HIT, ship.hitBy(shot));

        shot = Shot.at(3, 2);
        assertEquals(Damage.HIT,ship.hitBy(shot));

        shot = Shot.at(4, 0);
        assertEquals(Damage.HIT,ship.hitBy(shot));

        shot = Shot.at(4, 1);
        assertEquals(Damage.HIT,ship.hitBy(shot));

        shot = Shot.at(4, 2);
        assertEquals(Damage.KILL,ship.hitBy(shot));
    }

}