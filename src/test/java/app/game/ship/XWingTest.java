package app.game.ship;

import app.game.fire.Shot;
import app.game.fire.Shot.Damage;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class XWingTest {

    private AbstractBattleship ship;

    @Before
    public void setup() {
        ship = new XWing();
    }

    @Test
    public void miss_ship_when_frame_is_intact() {
        Shot shot = Shot.at(0, 1);
        ship.hitBy(shot);
        assertEquals(Damage.MISS, shot.result());

        shot = Shot.at(1, 1);
        ship.hitBy(shot);
        assertEquals(Damage.MISS, shot.result());

        shot = Shot.at(2, 0);
        ship.hitBy(shot);
        assertEquals(Damage.MISS, shot.result());

        shot = Shot.at(2, 2);
        ship.hitBy(shot);
        assertEquals(Damage.MISS, shot.result());

        shot = Shot.at(3, 1);
        ship.hitBy(shot);
        assertEquals(Damage.MISS, shot.result());

        shot = Shot.at(4, 1);
        ship.hitBy(shot);
        assertEquals(Damage.MISS, shot.result());
    }

    @Test
    public void hit_ship_until_destroyed() {
        Shot shot = Shot.at(0, 0);
        ship.hitBy(shot);
        assertEquals(Damage.HIT, shot.result());

        shot = Shot.at(0, 2);
        ship.hitBy(shot);
        assertEquals(Damage.HIT, shot.result());

        shot = Shot.at(1, 0);
        ship.hitBy(shot);
        assertEquals(Damage.HIT, shot.result());

        shot = Shot.at(1, 2);
        ship.hitBy(shot);
        assertEquals(Damage.HIT, shot.result());

        shot = Shot.at(2, 1);
        ship.hitBy(shot);
        assertEquals(Damage.HIT, shot.result());

        shot = Shot.at(3, 0);
        ship.hitBy(shot);
        assertEquals(Damage.HIT, shot.result());

        shot = Shot.at(3, 2);
        ship.hitBy(shot);
        assertEquals(Damage.HIT, shot.result());

        shot = Shot.at(4, 0);
        ship.hitBy(shot);
        assertEquals(Damage.HIT, shot.result());

        shot = Shot.at(4, 2);
        ship.hitBy(shot);
        assertEquals(Damage.KILL, shot.result());
    }

}