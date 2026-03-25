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

    @Schema(description = "Precio del producto", example = "20.50", minimum = "0.01")
    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor que 0")
    private Double precio;

    @Schema(description = "ID del vendedor del producto")
    @NotNull(message = "El id de vendedor es obligatorio")
    private String idVendedor;

    @Schema(description = "Lugar de recogida del producto")
    @NotBlank(message = "El lugar de recogida no debe de estar vacío")
    private String lugarRecogida;

    public ProductoDTO(Long id, String titulo, Double precio, String idVendedor, String lugarRecogida) {
        this.id = id;
        this.titulo = titulo;
        this.precio = precio;
        this.idVendedor = idVendedor;
        this.lugarRecogida = lugarRecogida;
    }

    public static ProductoDTO fromEntity(Producto producto) {
        return new ProductoDTO(producto.getId(), producto.getTitulo(), producto.getPrecio(), producto.getVendedor().getIdUsuario(), producto.getLugarRecogida().toString());
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

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(String idVendedor) {
        this.idVendedor = idVendedor;
    }

    public String getLugarRecogida() {
        return lugarRecogida;
    }

    public void setLugarRecogida(String lugarRecogida) {
        this.lugarRecogida = lugarRecogida;
    }
}