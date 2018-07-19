package app.game.api;

import io.javalin.security.Role;

public enum MyRole implements Role {
    ANYONE, USER, PROTOCOL
}
