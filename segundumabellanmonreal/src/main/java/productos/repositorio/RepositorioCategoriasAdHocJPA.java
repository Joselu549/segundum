package productos.repositorio;

import java.util.List;

import productos.modelo.Categoria;

public class RepositorioCategoriasAdHocJPA extends RepositorioCategoriasJPA implements RepositorioCategoriasAdHoc {

  @Override
  public List<Categoria> getCategoriasRaiz() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getCategoriasRaiz'");
  }

  @Override
  public List<Categoria> getSubcategorias(String idCategoriaPadre) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getSubcategorias'");
  }

}
