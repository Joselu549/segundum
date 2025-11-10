package productos.servicio;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import productos.modelo.Categoria;
import productos.repositorio.RepositorioCategoriasAdHocJPA;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.RepositorioException;

public class ServicioCategorias implements IServicioCategorias {

  private RepositorioCategoriasAdHocJPA repositorioCategorias = FactoriaRepositorios
      .getRepositorio(Categoria.class);

  @Override
  public void cargarJerarquiaCategorias(String rutaFichero) {
    try {
      JAXBContext context = JAXBContext.newInstance(Categoria.class);
      Unmarshaller unmarshaller = context.createUnmarshaller();

      File file = new File(rutaFichero);
      Categoria categoria = (Categoria) unmarshaller.unmarshal(file);

      try {
        repositorioCategorias.getById(categoria.getId());
        System.out.println("La categoría principal " + categoria.getId() + " ya existe. No se cargará.");
        return;
      } catch (EntidadNoEncontrada e) {
      }

      configurarRelacionesPadreHijo(categoria, null);
      repositorioCategorias.add(categoria);

    } catch (JAXBException e) {
      throw new RuntimeException("Error al parsear el fichero XML: " + e.getMessage(), e);
    } catch (RepositorioException e) {
      throw new RuntimeException("Error al cargar la jerarquía de categorías: " + e.getMessage(), e);
    }
  }

  private void configurarRelacionesPadreHijo(Categoria categoria, Categoria padre) {
    categoria.setCategoria(padre);

    if (categoria.getSubCategorias() != null) {
      for (Categoria subcategoria : categoria.getSubCategorias()) {
        configurarRelacionesPadreHijo(subcategoria, categoria);
      }
    }
  }

  @Override
  public void modificarDescripcionCategoria(String id, String nuevaDescripcion) {
    try {
      Categoria categoria = repositorioCategorias.getById(id);
      categoria.setDescripcion(nuevaDescripcion);
      repositorioCategorias.update(categoria);

    } catch (EntidadNoEncontrada e) {
      throw new RuntimeException("Categoría no encontrada con id: " + id, e);
    } catch (RepositorioException e) {
      throw new RuntimeException("Error al modificar la categoría: " + e.getMessage(), e);
    }
  }

  @Override
  public List<Categoria> obtenerCategoriasRaiz() {
    try {
      return repositorioCategorias.getCategoriasRaiz();

    } catch (RepositorioException e) {
      throw new RuntimeException("Error al obtener categorías raíz: " + e.getMessage(), e);
    }
  }

  @Override
  public List<Categoria> obtenerSubCategorias(String id) {
    try {
      return repositorioCategorias.getSubcategorias(id);

    } catch (RepositorioException e) {
      throw new RuntimeException("Error al obtener subcategorías: " + e.getMessage(), e);
    }
  }
}
