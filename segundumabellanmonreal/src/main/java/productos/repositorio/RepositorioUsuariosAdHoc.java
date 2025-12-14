package productos.repositorio;

import productos.modelo.Usuario;
import repositorio.RepositorioString;
import java.util.List;
import repositorio.RepositorioException;

public interface RepositorioUsuariosAdHoc extends RepositorioString<Usuario> {
  public List<Usuario> getUsuariosPorNombre(String nombreLike) throws RepositorioException;

  public List<Usuario> getUsuariosPorEmail(String email) throws RepositorioException;

  public List<Usuario> getUsuariosPorTelefono(String telefono) throws RepositorioException;

  public List<Usuario> getUsuariosAdmin(boolean esAdmin) throws RepositorioException;
}
