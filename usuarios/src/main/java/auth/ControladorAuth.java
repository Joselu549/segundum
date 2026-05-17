package auth;

import javax.annotation.security.PermitAll;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import auth.dto.CredencialesValidadasDTO;
import servicio.FactoriaServicios;
import usuarios.modelo.Usuario;
import usuarios.servicio.IServicioUsuarios;

@Path("auth")
public class ControladorAuth {

    private final IServicioUsuarios servicioUsuarios = FactoriaServicios.getServicio(IServicioUsuarios.class);

    // Endpoint público llamado por la pasarela vía Retrofit.
    // Valida credenciales y devuelve datos del usuario; la generación del JWT es
    // responsabilidad de la pasarela.
    //
    // curl -X POST -H "Content-Type: application/x-www-form-urlencoded"
    // -d "username=juan@dominio.com&password=clave"
    // http://localhost:61071/auth/login
    @POST
    @Path("/login")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("username") String username, @FormParam("password") String password) {
        try {
            Usuario usuario = servicioUsuarios.login(username, password);
            CredencialesValidadasDTO dto = new CredencialesValidadasDTO(
                    usuario.getId(),
                    usuario.getNombre(),
                    usuario.getApellidos(),
                    usuario.getEmail(),
                    usuario.isAdmin());
            return Response.ok(dto).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Credenciales inválidas").build();
        }
    }
}
