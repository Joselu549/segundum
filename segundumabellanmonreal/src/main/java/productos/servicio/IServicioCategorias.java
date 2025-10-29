package productos.servicio;

import java.util.List;

import productos.modelo.Categoria;

public interface IServicioCategorias {
  void cargarJerarquiaCategorias(String rutaFichero);

  void modificarDescripcionCategoria(String id, String nuevaDescripcion);

  List<Categoria> obtenerCategoriasRaiz();

  List<Categoria> obtenerSubCategorias(String id);
}
