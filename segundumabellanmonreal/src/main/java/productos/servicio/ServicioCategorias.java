package productos.servicio;

import java.util.List;

import productos.modelo.Categoria;
import productos.repositorio.RepositorioCategoriasJPA;
import repositorio.FactoriaRepositorios;

public class ServicioCategorias implements IServicioCategorias {

  private RepositorioCategoriasJPA repositorioCategorias = FactoriaRepositorios
      .getRepositorio(RepositorioCategoriasJPA.class);

  @Override
  public void cargarJerarquiaCategorias(String rutaFichero) {
    // TODO Auto-generated method stub

  }

  @Override
  public void modificarDescripcionCategoria(String id, String nuevaDescripcion) {
    // TODO Auto-generated method stub

  }

  @Override
  public List<Categoria> obtenerCategoriasRaiz() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Categoria> obtenerSubCategorias(String id) {
    // TODO Auto-generated method stub
    return null;
  }

}
