package app.game.ship;

import app.game.ship.shape.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BattleshipTest {

    @Test
    public void ships_and_frames_are_associated() {
        assertTrue(new Angle().getFrame() instanceof AngleFrame);
        assertTrue(new AWing().getFrame() instanceof AFrame);
        assertTrue(new BWing().getFrame() instanceof BFrame);
        assertTrue(new SWing().getFrame() instanceof SFrame);
        assertTrue(new XWing().getFrame() instanceof XFrame);
    }

}