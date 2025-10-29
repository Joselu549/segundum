package productos.repositorio;

import productos.modelo.Categoria;
import repositorio.RepositorioJPA;

public class RepositorioCategoriasJPA extends RepositorioJPA<Categoria> {

  @Override
  public Class<Categoria> getClase() {
    return Categoria.class;
  }

}
