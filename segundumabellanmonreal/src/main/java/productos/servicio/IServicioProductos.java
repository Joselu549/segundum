package productos.servicio;

import java.util.List;
import java.util.Optional;

import productos.modelo.Estado;
import productos.modelo.LugarRecogida;
import productos.modelo.Producto;

public interface IServicioProductos {
  String darDeAltaProducto(String titulo, String descripcion, double precio, Estado estado,
      String idCategoria, boolean envioDisponible, String idVendedor);

  LugarRecogida asignarLugarRecogida(String idProducto, int longitud, int latitud,
      String descripcion);

  void modificarProducto(String idProducto, Optional<Double> precio, Optional<String> descripcion);

  void addVisualizacionProducto(String idProducto);

  List<ResumenProducto> getHistorialMes(int mes, int anio);

  List<Producto> buscarProductos(String idCategoria, String textoDescripcion,
      Estado estado, Double precioMaximo);
}
