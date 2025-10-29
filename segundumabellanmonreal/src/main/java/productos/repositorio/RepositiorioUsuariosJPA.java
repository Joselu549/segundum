package productos.repositorio;

import productos.modelo.Usuario;
import repositorio.RepositorioJPA;

public class RepositiorioUsuariosJPA extends RepositorioJPA<Usuario> {

  @Override
  public Class<Usuario> getClase() {
    return Usuario.class;
  }

}
