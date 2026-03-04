package arso.segundum.servicio;

import java.util.List;

import arso.segundum.modelo.Categoria;

public interface IServicioCategorias {
    void cargarJerarquiaCategorias(String rutaFichero);

    void modificarDescripcionCategoria(String id, String nuevaDescripcion);

    List<Categoria> obtenerCategoriasRaiz();

    List<Categoria> obtenerSubCategorias(String id);
}
