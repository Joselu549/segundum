package productos.servicio;

import java.time.LocalDate;
import java.util.Optional;

public interface IServicioUsuarios {
  String registrarUsuario(String email, String nombre, String apellidos, String telefono,
      String direccion, LocalDate fechaNacimiento, String password);

  void modificarUsuario(String id, Optional<String> nombre, Optional<String> apellidos,
      Optional<String> telefono, Optional<String> direccion, Optional<LocalDate> fechaNacimiento,
      Optional<String> password);
}
