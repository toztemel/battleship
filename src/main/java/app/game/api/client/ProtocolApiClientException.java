package app.game.api.client;

import javax.ws.rs.BadRequestException;

public class ProtocolApiClientException extends BadRequestException {

    private String message;

    ProtocolApiClientException(String s) {
        super(s);
        message = s;
    }

    public String getUserMessage() {
        return message;
    }
}
