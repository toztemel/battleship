package app.game.fire;

class InvalidCoordinatesException extends IllegalArgumentException {

    InvalidCoordinatesException(NumberFormatException e) {
        super(e);
    }

    InvalidCoordinatesException() {
    }
}
