package app.game.ship;

import app.game.fire.Shot;
import app.game.ship.frame.Frame;
import app.game.ship.frame.FrameFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BattleshipTest {

    @Test
    public void not_damaged_until_destroyed() {
        Frame frame = FrameFactory.create(new AWing());

        assertEquals(Shot.Damage.HIT, frame.hitBy(new Shot(0, 1)));
        assertEquals(Shot.Damage.HIT, frame.hitBy(new Shot(1, 0)));
        assertEquals(Shot.Damage.HIT, frame.hitBy(new Shot(1, 2)));
        assertEquals(Shot.Damage.HIT, frame.hitBy(new Shot(2, 0)));
        assertEquals(Shot.Damage.HIT, frame.hitBy(new Shot(2, 1)));
        assertEquals(Shot.Damage.HIT, frame.hitBy(new Shot(2, 2)));
        assertEquals(Shot.Damage.HIT, frame.hitBy(new Shot(3, 0)));
        assertEquals(Shot.Damage.DESTROYED, frame.hitBy(new Shot(3, 2)));
    }

    @Test
    public void no_further_damage_for_the_same_shot() {
        Frame frame = FrameFactory.create(new AWing());

        assertEquals(Shot.Damage.HIT, frame.hitBy(new Shot(0, 1)));
        assertEquals(Shot.Damage.HIT, frame.hitBy(new Shot(1, 0)));
        assertEquals(Shot.Damage.HIT, frame.hitBy(new Shot(1, 2)));
        assertEquals(Shot.Damage.HIT, frame.hitBy(new Shot(2, 0)));
        assertEquals(Shot.Damage.HIT, frame.hitBy(new Shot(2, 1)));
        assertEquals(Shot.Damage.HIT, frame.hitBy(new Shot(2, 2)));
        assertEquals(Shot.Damage.HIT, frame.hitBy(new Shot(3, 0)));
        assertEquals(Shot.Damage.HIT, frame.hitBy(new Shot(3, 0)));
        assertEquals(Shot.Damage.HIT, frame.hitBy(new Shot(3, 0)));
        assertEquals(Shot.Damage.HIT, frame.hitBy(new Shot(3, 0)));
        assertEquals(Shot.Damage.DESTROYED, frame.hitBy(new Shot(3, 2)));
    }
}