package app.game.api.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

@Provider
public class BattleshipObjectMapper implements ContextResolver<ObjectMapper> {

    private final ObjectMapper defaultObjectMapper;

    public BattleshipObjectMapper() {
        defaultObjectMapper = createDefaultMapper();
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return getDefaultObjectMapper();
    }

    public ObjectMapper getDefaultObjectMapper() {
        return defaultObjectMapper;
    }

    private ObjectMapper createDefaultMapper() {
        final ObjectMapper result = new ObjectMapper();
        result.enable(SerializationFeature.INDENT_OUTPUT);
        return result;
    }
}
