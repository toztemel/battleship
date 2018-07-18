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
        return HexToCoordinatesConverter.toProtocolString(Coordinates.of(row, column));
    }

    @Test
    public void of() {
    }

    @Test
    public void row() {
    }

    @Test
    public void column() {
    }

    @Test
    public void incrementBy() {
    }

    @Test
    public void incrementBy1() {
    }

    @Test
    public void decrementBy() {
    }

    @Test
    public void decrementBy1() {
    }

    @Test
    public void new_coordinates_can_be_constructed_from_Hex_ProtocolString() {
        Coordinates c = HexToCoordinatesConverter.fromProtocolString("0x0");
        assertEquals(0, c.row());
        assertEquals(0, c.column());

        c = HexToCoordinatesConverter.fromProtocolString("Ax0");
        assertEquals(10, c.row());
        assertEquals(0, c.column());

        c = HexToCoordinatesConverter.fromProtocolString("AxA");
        assertEquals(10, c.row());
        assertEquals(10, c.column());

        c = HexToCoordinatesConverter.fromProtocolString("FxF");
        assertEquals(15, c.row());
        assertEquals(15, c.column());
    }

    @Test(expected = InvalidCoordinatesException.class)
    public void throws_Exception_when_parsing_wrong_format() {
        HexToCoordinatesConverter.fromProtocolString("0xG");
    }

    @Test(expected = InvalidCoordinatesException.class)
    public void throws_Exception_when_parsing_invalid_coordinates() {
        HexToCoordinatesConverter.fromProtocolString("-1x0");
    }

    @Test(expected = InvalidCoordinatesException.class)
    public void throws_Exception_when_parsing_invalid_coordinates_format() {
        HexToCoordinatesConverter.fromProtocolString("00");
    }

    @Test
    public void toProtocolString() {
    }
}