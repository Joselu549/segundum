package auth;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.security.PermitAll;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import servicio.FactoriaServicios;
import usuarios.modelo.Usuario;
import usuarios.servicio.IServicioUsuarios;

@Path("auth")
public class ControladorAuth {

    private final IServicioUsuarios servicioUsuarios = FactoriaServicios.getServicio(IServicioUsuarios.class);

    // curl -X POST -H "Content-Type: application/x-www-form-urlencoded" -d
    // "username=juan@dominio.com&password=clave"
    // http://localhost:8080/api/auth/login

    @POST
    @Path("/login")
    @PermitAll
    public Response login(@FormParam("username") String username, @FormParam("password") String password) {

        Map<String, Object> claims = verificarCredenciales(username, password);
        if (claims != null) {
            String token = JwtUtils.generateToken(claims);
            return Response.ok(token).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Credenciales inválidas").build();
        }

    }

    private Map<String, Object> verificarCredenciales(String username, String password) {
        Usuario usuario = servicioUsuarios.login(username, password);
        if (usuario == null) {
            return null;
        }

        HashMap<String, Object> claims = new HashMap<String, Object>();
        // Use user id as subject so we can validate ownership in other endpoints
        claims.put("sub", usuario.getId());
        claims.put("email", usuario.getEmail());
        // All users have the role USUARIO
        claims.put("roles", "USUARIO");
        return claims;
    }
}