package productos.repositorio;

import java.util.List;

import productos.modelo.Categoria;
import repositorio.RepositorioString;

public interface RepositorioCategoriasAdHoc extends RepositorioString<Categoria> {

  public List<Categoria> getCategoriasRaiz();

  public List<Categoria> getSubcategorias(String idCategoriaPadre);
}
