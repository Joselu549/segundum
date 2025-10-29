package productos.servicio;

import java.time.LocalDate;
import java.util.Optional;

import productos.repositorio.RepositiorioUsuariosJPA;
import repositorio.FactoriaRepositorios;

public class ServicioUsuarios implements IServicioUsuarios {

  RepositiorioUsuariosJPA repositorioUsuarios = FactoriaRepositorios.getRepositorio(RepositiorioUsuariosJPA.class);

  @Override
  public String registrarUsuario(String email, String nombre, String apellidos, String telefono,
      String direccion, LocalDate fechaNacimiento, String password) {
    // TODO: Auto-generated method stub
    return "";
  }

  @Override
  public void modificarUsuario(String id, Optional<String> nombre, Optional<String> apellidos,
      Optional<String> telefono, Optional<String> direccion,
      Optional<LocalDate> fechaNacimiento, Optional<String> password) {
    // TODO: Auto-generated method stub
  }
}
