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

  List<Producto> buscarProductos(Optional<String> idCategoria, Optional<String> textoDescripcion,
      Optional<Estado> estado, Optional<Double> precioMaximo);

  // Clase interna para el resumen de productos
  public static class ResumenProducto {
    private String id;
    private String titulo;
    private double precio;
    private String fechaPublicacion;
    private String nombreCategoria;
    private int numeroVisualizaciones;

    public ResumenProducto(String id, String titulo, double precio, String fechaPublicacion,
        String nombreCategoria, int numeroVisualizaciones) {
      this.id = id;
      this.titulo = titulo;
      this.precio = precio;
      this.fechaPublicacion = fechaPublicacion;
      this.nombreCategoria = nombreCategoria;
      this.numeroVisualizaciones = numeroVisualizaciones;
    }

    // Getters
    public String getId() {
      return id;
    }

    public String getTitulo() {
      return titulo;
    }

    public double getPrecio() {
      return precio;
    }

    public String getFechaPublicacion() {
      return fechaPublicacion;
    }

    public String getNombreCategoria() {
      return nombreCategoria;
    }

    public int getNumeroVisualizaciones() {
      return numeroVisualizaciones;
    }
  }
}
