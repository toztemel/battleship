package app.game.api.controller;

import javax.ws.rs.BadRequestException;

public class UserApiException extends BadRequestException {

    private String message;

    UserApiException(Exception e) {
        super(e);
        message = e.getMessage();
    }

    UserApiException(String s) {
        super(s);
        message = s;
    }

    public String getUserMessage() {
        return message;
    }
}
