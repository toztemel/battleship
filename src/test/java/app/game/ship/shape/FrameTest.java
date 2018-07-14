package app.game.ship.shape;

import app.game.TestUtil;
import app.game.ship.AWing;
import app.game.ship.Ship;
import app.game.ship.fire.Shot;
import app.game.ship.fire.Shot.Damage;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FrameTest {

    @Test
    public void getFrame() {
        Ship s = new Ship() {
            @Override
            public String toString() {
                return "*";
            }
        };
        print(new AngleFrame(s));
        print(new AFrame(s));
        print(new BFrame(s));
        print(new SFrame(s));
        print(new XFrame(s));
    }

    private void print(Frame bFrame) {
        TestUtil.print2DArray(bFrame.getFrame());
    }

    @Test
    public void damage_until_destroyed() {
        Frame frame = FrameFactory.create(new AWing());

        assertEquals(Damage.HIT, frame.hitBy(new Shot(0, 1)));
        assertEquals(Damage.HIT, frame.hitBy(new Shot(1, 0)));
        assertEquals(Damage.HIT, frame.hitBy(new Shot(1, 2)));
        assertEquals(Damage.HIT, frame.hitBy(new Shot(2, 0)));
        assertEquals(Damage.HIT, frame.hitBy(new Shot(2, 1)));
        assertEquals(Damage.HIT, frame.hitBy(new Shot(2, 2)));
        assertEquals(Damage.HIT, frame.hitBy(new Shot(3, 0)));
        assertEquals(Damage.DESTROYED, frame.hitBy(new Shot(3, 2)));
    }

    @Test
    public void not_further_damage_for_the_same_shot() {
        Frame frame = FrameFactory.create(new AWing());

        assertEquals(Damage.HIT, frame.hitBy(new Shot(0, 1)));
        assertEquals(Damage.HIT, frame.hitBy(new Shot(1, 0)));
        assertEquals(Damage.HIT, frame.hitBy(new Shot(1, 2)));
        assertEquals(Damage.HIT, frame.hitBy(new Shot(2, 0)));
        assertEquals(Damage.HIT, frame.hitBy(new Shot(2, 1)));
        assertEquals(Damage.HIT, frame.hitBy(new Shot(2, 2)));
        assertEquals(Damage.HIT, frame.hitBy(new Shot(3, 0)));
        assertEquals(Damage.HIT, frame.hitBy(new Shot(3, 0)));
        assertEquals(Damage.HIT, frame.hitBy(new Shot(3, 0)));
        assertEquals(Damage.HIT, frame.hitBy(new Shot(3, 0)));
        assertEquals(Damage.DESTROYED, frame.hitBy(new Shot(3, 2)));
    }

}