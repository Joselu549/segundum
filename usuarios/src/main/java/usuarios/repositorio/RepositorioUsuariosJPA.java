package usuarios.repositorio;

import repositorio.RepositorioJPA;
import usuarios.modelo.Usuario;

public class RepositorioUsuariosJPA extends RepositorioJPA<Usuario> {

  @Override
  public Class<Usuario> getClase() {
    return Usuario.class;
  }

}
