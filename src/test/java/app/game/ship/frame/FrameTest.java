package app.game.ship.frame;

import app.game.ship.*;
import app.game.util.Printer;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class FrameTest {

    @Test
    public void getFrame() {
        Ship s = new Ship() {
            @Override
            public int length() {
                return 0;
            }

            @Override
            public int width() {
                return 0;
            }

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
        System.out.println("Shape of " + bFrame.getClass().getSimpleName() + ":");
        Printer.print2DArray(bFrame.frame);
    }

    @Test
    public void ships_and_frames_are_coupled() {
        assertTrue(new Angle().getFrame() instanceof AngleFrame);
        assertTrue(new AWing().getFrame() instanceof AFrame);
        assertTrue(new BWing().getFrame() instanceof BFrame);
        assertTrue(new SWing().getFrame() instanceof SFrame);
        assertTrue(new XWing().getFrame() instanceof XFrame);
    }

}