package arso.segundum.dto;

import arso.segundum.modelo.Estado;
import arso.segundum.modelo.Producto;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Schema(description = "DTO de la entidad Producto")
public class ProductoDTO {
    @Schema(description = "Identificador del producto")
    private Long id;

    @Schema(description = "Título del producto", example = "Batería completa")
    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    @Schema(description = "Descripción del producto", example = "Ejemplo de batería completa muy compacta")
    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @Schema(description = "Precio del producto", example = "20.50", minimum = "0.01")
    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor que 0")
    private Double precio;

    @Schema(description = "Estado del producto", example = "COMO_NUEVO", type = "Estado")
    @NotNull(message = "El estado es obligatorio")
    private Estado estado;

    @Schema(description = "ID de la categoría del producto")
    @NotNull(message = "El id de categoría es obligatorio")
    private String idCategoria;

    @Schema(description = "ID del vendedor del producto")
    @NotNull(message = "El id de vendedor es obligatorio")
    private String idVendedor;

    @Schema(description = "Booleano para indicar si el envío está disponible")
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