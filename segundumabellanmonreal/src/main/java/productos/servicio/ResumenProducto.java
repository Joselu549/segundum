package productos.servicio;

public class ResumenProducto {
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
