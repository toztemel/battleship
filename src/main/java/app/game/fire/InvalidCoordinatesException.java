package app.game.fire;

public class InvalidCoordinatesException extends IllegalArgumentException {

    public InvalidCoordinatesException(NumberFormatException e) {
        super(e);
    }

    public InvalidCoordinatesException() {
    }
}
