package app.game.api.controller;

import javax.ws.rs.BadRequestException;

public class ProtocolApiException extends BadRequestException {

    ProtocolApiException(Exception e) {
        super(e);
    }

    public ProtocolApiException(String s) {
        super(s);
    }
}
