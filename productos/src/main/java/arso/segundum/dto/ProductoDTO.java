package arso.segundum.dto;

import arso.segundum.modelo.Estado;
import arso.segundum.modelo.Producto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class ProductoDTO {
    private Long id;

    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor que 0")
    private Double precio;

    @NotNull(message = "El estado es obligatorio")
    private Estado estado;

    @NotNull(message = "El id de categoría es obligatorio")
    private String idCategoria;

    @NotNull(message = "El id de vendedor es obligatorio")
    private String idVendedor;

    @NotNull(message = "El envío disponible es obligatorio")
    private Boolean envioDisponible;

    public ProductoDTO(Long id, String titulo, String descripcion,
                       Double precio, Estado estado, String idCategoria,
                       String idVendedor, Boolean envioDisponible) {
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

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
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

    public Boolean isEnvioDisponible() {
        return envioDisponible;
    }

    public void setEnvioDisponible(Boolean envioDisponible) {
        this.envioDisponible = envioDisponible;
    }
}