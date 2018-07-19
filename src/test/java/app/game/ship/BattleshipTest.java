package app.game.ship;

import app.game.fire.Coordinates;
import app.game.fire.Shot;
import app.game.ship.frame.Frame;
import app.game.ship.frame.FrameFactory;
import app.game.util.DoubleArrays;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BattleshipTest {

    @Test
    public void not_damaged_until_destroyed() {
        Frame frame = FrameFactory.create(new AWing());

        Shot shot = Shot.at(0, 1);
        assertEquals(Shot.Damage.HIT, frame.hitBy(shot));

        shot = Shot.at(1, 0);
        assertEquals(Shot.Damage.HIT, frame.hitBy(shot));

        shot = Shot.at(1, 2);
        assertEquals(Shot.Damage.HIT, frame.hitBy(shot));

        shot = Shot.at(2, 0);
        assertEquals(Shot.Damage.HIT, frame.hitBy(shot));

        shot = Shot.at(2, 1);
        assertEquals(Shot.Damage.HIT, frame.hitBy(shot));

        shot = Shot.at(2, 2);
        assertEquals(Shot.Damage.HIT, frame.hitBy(shot));

        shot = Shot.at(3, 0);
        assertEquals(Shot.Damage.HIT, frame.hitBy(shot));

        shot = Shot.at(3, 2);
        assertEquals(Shot.Damage.KILL, frame.hitBy(shot));
    }

    @Test
    public void no_further_damage_for_the_same_shot() {
        Frame frame = FrameFactory.create(new AWing());

        Shot shot = Shot.at(0, 1);
        assertEquals(Shot.Damage.HIT, frame.hitBy(shot));

        shot = Shot.at(1, 0);
        assertEquals(Shot.Damage.HIT, frame.hitBy(shot));

        shot = Shot.at(1, 2);
        assertEquals(Shot.Damage.HIT, frame.hitBy(shot));

        shot = Shot.at(2, 0);
        assertEquals(Shot.Damage.HIT, frame.hitBy(shot));

        shot = Shot.at(2, 1);
        assertEquals(Shot.Damage.HIT, frame.hitBy(shot));

        shot = Shot.at(2, 2);
        assertEquals(Shot.Damage.HIT, frame.hitBy(shot));

        shot = Shot.at(3, 0);
        assertEquals(Shot.Damage.HIT, frame.hitBy(shot));

        shot = Shot.at(3, 0);
        assertEquals(Shot.Damage.HIT, frame.hitBy(shot));

        shot = Shot.at(3, 0);
        assertEquals(Shot.Damage.HIT, frame.hitBy(shot));

        shot = Shot.at(3, 0);
        assertEquals(Shot.Damage.HIT, frame.hitBy(shot));

        shot = Shot.at(3, 2);
        assertEquals(Shot.Damage.KILL, frame.hitBy(shot));

    }

    @Test
    public void rotate_a_ship() {
        Battleship ship = new XWing();
        ship.rotate();
    }

    @Test
    public void ship_locates_itself_when_the_coordinates_are_provided() {
        Battleship ship = new XWing();
        Ship[][] field = new Ship[16][16];
        ship.goTo(field, Coordinates.of(10, 10));

        assertEquals(ship, field[10][10]);
        assertEquals(ship, field[10][12]);
        assertEquals(ship, field[14][10]);
        assertEquals(ship, field[14][12]);
    }
}