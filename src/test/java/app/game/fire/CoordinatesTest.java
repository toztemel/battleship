package app.game.fire;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CoordinatesTest {

    @Test
    public void coordinates_are_represented_by_capital_hex_string_in_protocol_api() {
        assertEquals("0x0", hexEncoding(0, 0));
        assertEquals("0x1", hexEncoding(0, 1));
        assertEquals("0xA", hexEncoding(0, 10));
        assertEquals("0xE", hexEncoding(0, 14));
        assertEquals("0xF", hexEncoding(0, 15));
        assertEquals("1x0", hexEncoding(1, 0));
        assertEquals("1xA", hexEncoding(1, 10));
        assertEquals("1xF", hexEncoding(1, 15));
        assertEquals("Fx0", hexEncoding(15, 0));
        assertEquals("FxA", hexEncoding(15, 10));
        assertEquals("FxF", hexEncoding(15, 15));
    }

    private String hexEncoding(int row, int column) {
        return Coordinates.of(row, column)
                .toProtocolString();
    }
}