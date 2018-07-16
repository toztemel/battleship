package app.game.fire;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CoordinatesFormatterTest {

    @Test
    public void fromProtocolString() {
        String hexString = "0x0";
        Coordinates c = CoordinatesFormatter.fromProtocolString(hexString);

        assertEquals(0, c.row());
        assertEquals(0, c.column());
        assertEquals(hexString, c.toHexString());

        hexString = "0xA";
        c = CoordinatesFormatter.fromProtocolString(hexString);
        assertEquals(0, c.row());
        assertEquals(10, c.column());
        assertEquals(hexString, c.toHexString());

        hexString = "FxF";
        c = CoordinatesFormatter.fromProtocolString(hexString);
        assertEquals(15, c.row());
        assertEquals(15, c.column());
        assertEquals(hexString, c.toHexString());

    }

    @Test
    public void toProtocolString() {
        Coordinates c = Coordinates.of(0, 0);
        String hexString = CoordinatesFormatter.toProtocolString(c);
        assertEquals(hexString, "0x0");

        c = Coordinates.of(0, 10);
        hexString = CoordinatesFormatter.toProtocolString(c);
        assertEquals("0xA", hexString);

        c = Coordinates.of(15,15);
        hexString = CoordinatesFormatter.toProtocolString(c);
        assertEquals("FxF", hexString);
    }
}