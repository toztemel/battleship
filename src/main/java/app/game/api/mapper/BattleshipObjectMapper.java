package app.game.api.mapper;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

@Provider
public class BattleshipObjectMapper implements ContextResolver<ObjectMapper> {

    private final ObjectMapper defaultObjectMapper = createDefaultMapper();

    private static ObjectMapper createDefaultMapper() {
        return new ObjectMapper()
                .enable(SerializationFeature.INDENT_OUTPUT)
                .setSerializationInclusion(Include.NON_NULL);
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return defaultObjectMapper;
    }

    public ObjectMapper getDefaultObjectMapper() {
        return defaultObjectMapper;
    }
}
