package usuarios.servicio;

import java.time.LocalDate;
import java.util.List;

import usuarios.modelo.Usuario;

public interface IServicioUsuarios {
    String registrarUsuario(String email, String nombre, String apellidos, String telefono,
            String direccion, LocalDate fechaNacimiento, String password) throws IllegalArgumentException;

    void modificarNombre(String id, String nombre);

    void modificarApellidos(String id, String apellidos);

    void modificarPassword(String id, String password);

    void modificarFechaNacimiento(String id, LocalDate fechaNacimiento);

    void modificarTelefono(String id, String telefono);

    Usuario login(String email, String password);

    List<UsuarioResumen> obtenerTodosLosUsuarios();

    Usuario obtenerUsuarioPorId(String id);
}