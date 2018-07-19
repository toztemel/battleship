package app.game.api.security;

import io.javalin.security.Role;

public enum BattleshipAPIRoles implements Role {
    ANYONE, USER, PROTOCOL, PROTOCOL_RESTRICTED
}
