package app.game.api.controller;

import javax.ws.rs.BadRequestException;

public class UserApiException extends BadRequestException {

    UserApiException(Exception e) {
        super(e);
    }

    UserApiException(String s) {
        super(s);
    }
}
