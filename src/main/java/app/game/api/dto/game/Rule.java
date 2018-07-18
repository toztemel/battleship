package app.game.api.dto.game;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Rule {
    @JsonProperty("standard")
    STANDARD,
    @JsonProperty("super_charge")
    SUPER_CHARGE,
    @JsonProperty("desperation")
    DESPERATION,
    @JsonProperty("x_shot")
    X_SHOT
}
