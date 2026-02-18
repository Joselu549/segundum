package usuarios.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import java.time.LocalDate;

import servicio.FactoriaServicios;
import usuarios.dto.UsuarioDTO;
import usuarios.servicio.IServicioUsuarios;

@Path("users")
public class UsuarioController {
  private final IServicioUsuarios servicioUsuarios = FactoriaServicios.getServicio(IServicioUsuarios.class);

  @Context
  private UriInfo uriInfo;

  @POST
  @Consumes({ MediaType.APPLICATION_JSON })
  public Response registrarUsuario(UsuarioDTO usuario) throws Exception {
    try {
      LocalDate fechaNacimiento = LocalDate.parse(usuario.getFechaNacimiento());

      String id = servicioUsuarios.registrarUsuario(
          usuario.getEmail(),
          usuario.getNombre(),
          usuario.getApellidos(),
          usuario.getTelefono(),
          "No hay dirección",
          fechaNacimiento,
          usuario.getPassword());
      return Response.created(this.uriInfo.getAbsolutePathBuilder().path(id).build()).build();
    } catch (IllegalArgumentException e) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity("Error al registrar el usuario: " + e.getMessage())
          .build();
    } catch (Exception e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
          .entity("Error al registrar el usuario: " + e.getMessage())
          .build();
    }
  }
}