package arso.segundum.auth;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Map;

@Component
public class JwtUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public Map<String, Object> decodePayload(String token) throws Exception {
        String[] parts = token.split("\\.");
        byte[] payloadBytes = Base64.getUrlDecoder().decode(parts[1]);
        return MAPPER.readValue(payloadBytes, new TypeReference<Map<String, Object>>() {});
    }
}
