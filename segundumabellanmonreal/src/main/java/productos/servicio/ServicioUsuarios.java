package productos.servicio;

import java.time.LocalDate;
import java.util.Optional;

import productos.modelo.Usuario;
import productos.repositorio.RepositiorioUsuariosJPA;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.RepositorioException;

public class ServicioUsuarios implements IServicioUsuarios {

  RepositiorioUsuariosJPA repositorioUsuarios = FactoriaRepositorios.getRepositorio(Usuario.class);

  @Override
  public String registrarUsuario(String email, String nombre, String apellidos, String telefono,
      String direccion, LocalDate fechaNacimiento, String password) {
    try {
      Usuario usuario = new Usuario(email, nombre, apellidos, password, fechaNacimiento, telefono, false);
      String id = repositorioUsuarios.add(usuario);
      return id;
    } catch (RepositorioException e) {
      throw new RuntimeException("Error al registrar el usuario: " + e.getMessage(), e);
    }
  }

  @Override
  public void modificarUsuario(String id, Optional<String> nombre, Optional<String> apellidos,
      Optional<String> telefono, Optional<String> direccion,
      Optional<LocalDate> fechaNacimiento, Optional<String> password) {
    try {
      Usuario usuario = repositorioUsuarios.getById(id);
      if (nombre.isPresent()) {
        usuario.setNombre(nombre.get());
      }
      if (apellidos.isPresent()) {
        usuario.setApellidos(apellidos.get());
      }
      if (telefono.isPresent()) {
        usuario.setTelefono(telefono.get());
      }
      if (fechaNacimiento.isPresent()) {
        usuario.setFechaNacimiento(fechaNacimiento.get());
      }
      if (password.isPresent()) {
        usuario.setPassword(password.get());
      }
      repositorioUsuarios.update(usuario);

    } catch (EntidadNoEncontrada e) {
      throw new RuntimeException("Usuario no encontrado con id: " + id, e);
    } catch (RepositorioException e) {
      throw new RuntimeException("Error al modificar el usuario: " + e.getMessage(), e);
    }
  }
}
