package arso.segundum.rest.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class AltaCompraventaDTO {
    @NotNull(message = "El id del producto es obligatorio")
    @Positive(message = "El id del producto debe ser un número positivo")
    private Long idProducto;

    @NotBlank(message = "El id del comprador es obligatorio")
    private String idComprador;

    public AltaCompraventaDTO() {
    }

    public Long getIdProducto() {
        return this.idProducto;
    }

    public String getIdComprador() {
        return this.idComprador;
    }
}
