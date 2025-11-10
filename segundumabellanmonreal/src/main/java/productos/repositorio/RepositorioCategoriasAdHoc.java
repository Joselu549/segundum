package productos.repositorio;

import java.util.List;

import productos.modelo.Categoria;
import repositorio.RepositorioException;
import repositorio.RepositorioString;

public interface RepositorioCategoriasAdHoc extends RepositorioString<Categoria> {

  public List<Categoria> getCategoriasRaiz() throws RepositorioException;

  public List<Categoria> getSubcategorias(String idCategoriaPadre) throws RepositorioException;
}
