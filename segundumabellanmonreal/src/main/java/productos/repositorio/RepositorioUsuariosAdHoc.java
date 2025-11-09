package productos.repositorio;

import java.util.List;

import productos.modelo.Usuario;
import repositorio.RepositorioString;

public interface RepositorioUsuariosAdHoc extends RepositorioString<Usuario> {

  public List<Usuario> getUsuariosPorNombre(String nombre);

  public List<Usuario> getUsuariosPorEmail(String email);

  public List<Usuario> getUsuariosPorTelefono(String telefono);

  public List<Usuario> getUsuariosAdmin(boolean esAdmin);
}
