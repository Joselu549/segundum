package usuarios.rest;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import io.jsonwebtoken.Claims;
import repositorio.EntidadNoEncontrada;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import servicio.FactoriaServicios;
import usuarios.dto.NombreUsuarioDTO;
import usuarios.dto.UsuarioDTO;
import usuarios.modelo.Usuario;
import usuarios.rest.Listado.UsuarioResumenExtendido;
import usuarios.servicio.IServicioUsuarios;
import usuarios.servicio.UsuarioResumen;

@Path("users")
public class UsuarioController {
    private final IServicioUsuarios servicioUsuarios = FactoriaServicios.getServicio(IServicioUsuarios.class);

    @Context
    private UriInfo uriInfo;

    @Context
    private HttpServletRequest servletRequest;

    @GET
    @Path("/nombres/{id}")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerNombreUsuario(@PathParam("id") String id) throws Exception {
        Usuario usuario = this.servicioUsuarios.obtenerUsuarioPorId(id);
        NombreUsuarioDTO nombreUsuarioDTO = new NombreUsuarioDTO(obtenerNombreCompleto(usuario));
        return Response.ok(nombreUsuarioDTO).build();
    }

    private String obtenerNombreCompleto(Usuario usuario) {
        return usuario.getNombre() + " " + usuario.getApellidos();
    }

    @GET
    @RolesAllowed("USUARIO")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerUsuarios() {
        List<UsuarioResumen> usuarios = servicioUsuarios.obtenerTodosLosUsuarios();
        List<UsuarioResumenExtendido> usuariosExtendidos = usuarios.stream().map(resumen -> {
            UsuarioResumenExtendido usuarioExtendido = new UsuarioResumenExtendido();
            usuarioExtendido.setResumen(resumen);
            usuarioExtendido.setUrl(uriInfo.getAbsolutePathBuilder().path(resumen.getId()).build().toString());
            return usuarioExtendido;
        }).collect(Collectors.toList());

        Listado listado = new Listado();
        listado.setUsuarios(usuariosExtendidos);
        return Response.ok(listado).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("USUARIO")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerUsuario(@PathParam("id") String id) throws Exception {
        Usuario usuario = servicioUsuarios.obtenerUsuarioPorId(id);
        UsuarioDTO usuarioDTO = new UsuarioDTO(usuario);
        return Response.status(Response.Status.OK).entity(usuarioDTO).build();
    }

    @POST
    @PermitAll
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

    @PUT
    @Path("/{id}")
    @RolesAllowed("USUARIO")
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response modificarUsuario(@PathParam("id") String id, UsuarioDTO usuario) {
        try {
            Claims claims = (Claims) servletRequest.getAttribute("claims");
            if (claims == null || claims.getSubject() == null || !id.equals(claims.getSubject())) {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("No tienes permiso para modificar este usuario").build();
            }

            if (usuario.getEmail() != null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Error: el campo 'email' no se puede modificar")
                        .build();
            }

            if (usuario.getNombre() == null && usuario.getApellidos() == null &&
                    usuario.getPassword() == null && usuario.getFechaNacimiento() == null &&
                    usuario.getTelefono() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Error: debe proporcionar al menos un campo para modificar (nombre, apellidos, password, fechaNacimiento, telefono)")
                        .build();
            }

            if (usuario.getNombre() != null) {
                servicioUsuarios.modificarNombre(id, usuario.getNombre());
            }
            if (usuario.getApellidos() != null) {
                servicioUsuarios.modificarApellidos(id, usuario.getApellidos());
            }
            if (usuario.getPassword() != null) {
                servicioUsuarios.modificarPassword(id, usuario.getPassword());
            }
            if (usuario.getFechaNacimiento() != null) {
                LocalDate fechaNacimiento = LocalDate.parse(usuario.getFechaNacimiento());
                servicioUsuarios.modificarFechaNacimiento(id, fechaNacimiento);
            }
            if (usuario.getTelefono() != null) {
                servicioUsuarios.modificarTelefono(id, usuario.getTelefono());
            }
            return Response.noContent().build();
        } catch (EntidadNoEncontrada e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Usuario no encontrado: " + id)
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error al modificar el usuario: " + e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al modificar el usuario: " + e.getMessage())
                    .build();
        }
    }
}