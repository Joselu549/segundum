package arso.segundum.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductoDTO {
    @NotNull(message = "El id del producto no puede ser nulo")
    @Positive(message = "El id del producto debe ser un número positivo")
    private Long id;

    @NotBlank(message = "El id del vendedor no puede estar vacío")
    private String idVendedor;

    @NotBlank(message = "El título del producto no puede estar vacío")
    private String titulo;

    @NotNull(message = "El precio del producto no puede ser nulo")
    @Positive(message = "El precio del producto debe ser un número positivo")
    private Double precio;

    private String lugarRecogida;
    private boolean vendido;

    public ProductoDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(String idVendedor) {
        this.idVendedor = idVendedor;
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