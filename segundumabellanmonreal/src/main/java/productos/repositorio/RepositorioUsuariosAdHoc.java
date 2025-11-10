package productos.repositorio;

import java.util.List;

import productos.modelo.Usuario;
import repositorio.RepositorioException;
import repositorio.RepositorioString;

public interface RepositorioUsuariosAdHoc extends RepositorioString<Usuario> {

  public List<Usuario> getUsuariosPorNombre(String nombre) throws RepositorioException;

  public List<Usuario> getUsuariosPorEmail(String email) throws RepositorioException;

  public List<Usuario> getUsuariosPorTelefono(String telefono) throws RepositorioException;

  public List<Usuario> getUsuariosAdmin(boolean esAdmin) throws RepositorioException;
}
