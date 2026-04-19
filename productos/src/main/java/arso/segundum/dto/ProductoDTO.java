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

    @Schema(description = "Descripción del producto")
    private String descripcion;

    @Schema(description = "Precio del producto", example = "20.50", minimum = "0.01")
    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor que 0")
    private Double precio;

    @Schema(description = "Estado del producto")
    private Estado estado;

    @Schema(description = "ID de la categoría del producto")
    private String idCategoria;

    @Schema(description = "Indica si el vendedor ofrece envío")
    private boolean envioDisponible;

    @Schema(description = "ID del vendedor del producto")
    @NotNull(message = "El id de vendedor es obligatorio")
    private String idVendedor;

    @Schema(description = "Lugar de recogida del producto")
    @NotBlank(message = "El lugar de recogida no debe de estar vacío")
    private String lugarRecogida;

    @Schema(description = "Indica si el producto ya ha sido vendido")
    private boolean vendido;

    public ProductoDTO() {
    }

    public ProductoDTO(Long id, String titulo, Double precio, String idVendedor, String lugarRecogida) {
        this.id = id;
        this.titulo = titulo;
        this.precio = precio;
        this.idVendedor = idVendedor;
        this.lugarRecogida = lugarRecogida;
    }

    public static ProductoDTO fromEntity(Producto producto) {
        ProductoDTO dto = new ProductoDTO();
        dto.id = producto.getId();
        dto.titulo = producto.getTitulo();
        dto.descripcion = producto.getDescripcion();
        dto.precio = producto.getPrecio();
        dto.estado = producto.getEstado();
        dto.idCategoria = producto.getCategoria() != null ? producto.getCategoria().getId() : null;
        dto.envioDisponible = producto.isEnvioDisponible();
        dto.idVendedor = producto.getVendedor().getIdUsuario();
        if (producto.getLugarRecogida() != null) {
            arso.segundum.modelo.LugarRecogida lr = producto.getLugarRecogida();
            dto.lugarRecogida = lr.getDescripcion() + " (" + lr.getLatitud() + ", " + lr.getLongitud() + ")";
        }
        dto.vendido = producto.isVendido();
        return dto;
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

    public boolean isEnvioDisponible() {
        return envioDisponible;
    }

    public void setEnvioDisponible(boolean envioDisponible) {
        this.envioDisponible = envioDisponible;
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

    public boolean isVendido() {
        return vendido;
    }

    public void setVendido(boolean vendido) {
        this.vendido = vendido;
    }
}