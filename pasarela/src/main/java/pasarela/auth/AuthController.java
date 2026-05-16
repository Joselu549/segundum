package pasarela.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pasarela.client.UsuariosClient;
import pasarela.dto.TokenResponse;
import pasarela.dto.UsuarioLoginResponse;
import retrofit2.Response;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuariosClient usuariosClient;
    private final JwtUtils jwtUtils;

    public AuthController(UsuariosClient usuariosClient, JwtUtils jwtUtils) {
        this.usuariosClient = usuariosClient;
        this.jwtUtils = jwtUtils;
    }

    // curl -X POST -H "Content-Type: application/x-www-form-urlencoded"
    //   -d "username=juan@dominio.com&password=clave"
    //   http://localhost:8080/auth/login
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> login(
            @RequestParam("username") String username,
            @RequestParam("password") String password) {
        try {
            Response<UsuarioLoginResponse> respuesta =
                    usuariosClient.validarCredenciales(username, password).execute();

            if (!respuesta.isSuccessful() || respuesta.body() == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
            }

            UsuarioLoginResponse usuario = respuesta.body();
            String rol = usuario.isEsAdmin() ? "ADMIN" : "USUARIO";
            String nombreCompleto = usuario.getNombre() + " " + usuario.getApellidos();

            Map<String, Object> claims = new HashMap<>();
            claims.put("sub", usuario.getId());
            claims.put("email", usuario.getEmail());
            claims.put("roles", rol);

            String token = jwtUtils.generateToken(claims);

            return ResponseEntity.ok(new TokenResponse(token, usuario.getId(), nombreCompleto, rol));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }
}
