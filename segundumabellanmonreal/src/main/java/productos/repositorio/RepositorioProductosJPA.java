package productos.repositorio;

import productos.modelo.Producto;
import repositorio.RepositorioJPA;

public class RepositorioProductosJPA extends RepositorioJPA<Producto> {

  @Override
  public Class<Producto> getClase() {
    return Producto.class;
  }

}
