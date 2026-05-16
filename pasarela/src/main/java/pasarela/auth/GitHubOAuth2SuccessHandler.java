package pasarela.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class GitHubOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JwtUtils jwtUtils;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        DefaultOAuth2User usuario = (DefaultOAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = usuario.getAttributes();

        String login = (String) attributes.get("login");
        String email = (String) attributes.getOrDefault("email", "");

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", login);
        claims.put("email", email);
        claims.put("roles", "USUARIO");

        String token = jwtUtils.generateToken(claims);

        response.setContentType("application/json;charset=UTF-8");
        objectMapper.writeValue(response.getWriter(), Map.of("token", token, "usuario", login, "rol", "USUARIO"));
    }
}
