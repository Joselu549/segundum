package arso.segundum.modelo;

public class ResumenProducto {
    private Long id;
    private String titulo;
    private double precio;
    private String fechaPublicacion;
    private String nombreCategoria;
    private int numeroVisualizaciones;

    public ResumenProducto(Long id, String titulo, double precio, String fechaPublicacion,
            String nombreCategoria, int numeroVisualizaciones) {
        this.id = id;
        this.titulo = titulo;
        this.precio = precio;
        this.fechaPublicacion = fechaPublicacion;
        this.nombreCategoria = nombreCategoria;
        this.numeroVisualizaciones = numeroVisualizaciones;
    }

    public Long getId() {
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
