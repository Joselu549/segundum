package arso.segundum.servicio;

import java.util.List;
import java.util.Optional;

import arso.segundum.modelo.Estado;
import arso.segundum.modelo.LugarRecogida;
import arso.segundum.modelo.Producto;
import arso.segundum.modelo.Usuario;

public interface IServicioProductos {
    Long darDeAltaProducto(String titulo, String descripcion, double precio, Estado estado,
            String idCategoria, boolean envioDisponible, String idVendedor);

    LugarRecogida asignarLugarRecogida(Long idProducto, double longitud, double latitud,
            String descripcion);

    void modificarProducto(Long idProducto, Optional<Double> precio, Optional<String> descripcion);

    void addVisualizacionProducto(Long idProducto);

    List<ResumenProducto> getHistorialMes(int mes, int anio);

    List<Producto> buscarProductos(String idCategoria, String textoDescripcion,
            Estado estado, Double precioMaximo);

    Producto getProducto(Long idProducto) throws IllegalArgumentException;

    List<Producto> getProductosVendedor(String idVendedor) throws IllegalArgumentException;
}