package app.game.api.controller;

import javax.ws.rs.BadRequestException;

public class ProtocolApiException extends BadRequestException {

    ProtocolApiException(Exception e) {
        super(e);
    }

    ProtocolApiException(String s) {
        super(s);
    }
}
