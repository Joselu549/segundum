package productos.repositorio;

import java.util.List;

import productos.modelo.Producto;
import repositorio.RepositorioException;
import repositorio.RepositorioString;

public interface RepositorioProductosAdHoc extends RepositorioString<Producto> {

  public List<Producto> getProductosEnVenta() throws RepositorioException;

  public List<Producto> getProductosPorCategoria(String idCategoria) throws RepositorioException;

  public List<Producto> getProductosDestacados() throws RepositorioException;

  public List<Producto> getProductosPorMesAnio(int mes, int anio) throws RepositorioException;

  public List<Producto> buscarProductos(String idCategoria, String textoDescripcion,
      String estado, Double precioMaximo) throws RepositorioException;

}
