package productos.repositorio;

import productos.modelo.Usuario;
import repositorio.RepositorioJPA;

public class RepositorioUsuariosJPA extends RepositorioJPA<Usuario> {

  @Override
  public Class<Usuario> getClase() {
    return Usuario.class;
  }

}
