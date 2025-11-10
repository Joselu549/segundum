package productos.servicio;

import java.time.LocalDate;

public interface IServicioUsuarios {
  String registrarUsuario(String email, String nombre, String apellidos, String telefono,
      String direccion, LocalDate fechaNacimiento, String password) throws IllegalArgumentException;

  void modificarNombre(String id, String nombre);

  void modificarApellidos(String id, String apellidos);

  void modificarPassword(String id, String password);

  void modificarFechaNacimiento(String id, LocalDate fechaNacimiento);

  void modificarTelefono(String id, String telefono);
}
