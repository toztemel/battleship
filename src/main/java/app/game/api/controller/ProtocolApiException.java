package app.game.api.controller;

import javax.ws.rs.BadRequestException;

public class ProtocolApiException extends BadRequestException {

    private String message;

    ProtocolApiException(Exception e) {
        super(e);
        message = e.getMessage();
    }

    public ProtocolApiException(String s) {
        super(s);

        message = s;
    }

    public String getUserMessage() {
        return message;
    }
}
