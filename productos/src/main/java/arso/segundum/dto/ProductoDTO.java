package arso.segundum.dto;

import arso.segundum.modelo.Estado;
import arso.segundum.modelo.Producto;
import org.springframework.http.ResponseEntity;

public class ProductoDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private double precio;
    private Estado estado;
    private String idCategoria;
    private String idVendedor;
    private boolean envioDisponible;

    public ProductoDTO(Long id, String titulo, String descripcion,
                       double precio, Estado estado, String idCategoria,
                       String idVendedor, boolean envioDisponible) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.estado = estado;
        this.idCategoria = idCategoria;
        this.idVendedor = idVendedor;
        this.envioDisponible = envioDisponible;
    }

    public static ProductoDTO fromEntity(Producto producto) {
        return new ProductoDTO(producto.getId(), producto.getTitulo(), producto.getDescripcion(),
                producto.getPrecio(), producto.getEstado(), producto.getCategoria().getId(),
                producto.getVendedor().getIdUsuario(), producto.isEnvioDisponible());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(String idVendedor) {
        this.idVendedor = idVendedor;
    }

    public boolean isEnvioDisponible() {
        return envioDisponible;
    }

    public void setEnvioDisponible(boolean envioDisponible) {
        this.envioDisponible = envioDisponible;
    }
}