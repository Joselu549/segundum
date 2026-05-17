package usuarios.servicio;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;
import usuarios.modelo.Usuario;

public interface IServicioUsuarios {
    String registrarUsuario(String email, String nombre, String apellidos, String telefono,
            String direccion, LocalDate fechaNacimiento, String password)
            throws IllegalArgumentException, RepositorioException, IOException;

    void modificarNombre(String id, String nombre) throws EntidadNoEncontrada, RepositorioException, IOException;

    void modificarApellidos(String id, String apellidos) throws EntidadNoEncontrada, RepositorioException, IOException;

    void modificarPassword(String id, String password) throws EntidadNoEncontrada, RepositorioException;

    void modificarFechaNacimiento(String id, LocalDate fechaNacimiento)
            throws EntidadNoEncontrada, RepositorioException;

    void modificarTelefono(String id, String telefono) throws EntidadNoEncontrada, RepositorioException;

    Usuario login(String email, String password) throws RepositorioException;

    List<UsuarioResumen> obtenerTodosLosUsuarios() throws RepositorioException;

    Usuario obtenerUsuarioPorId(String id) throws EntidadNoEncontrada, RepositorioException;

    void incrementarContadorCompras(String id) throws EntidadNoEncontrada, RepositorioException;

    void incrementarContadorVentas(String id) throws EntidadNoEncontrada, RepositorioException;
}