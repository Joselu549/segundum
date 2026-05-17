package arso.segundum.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "DTO para indicar los errores")
public class ErrorDTO {

    @Schema(description = "Mensaje indicando el error")
    private final String mensaje;
    @Schema(description = "Lista de errores en caso de ser más de uno")
    private final List<String> detalles;

    public ErrorDTO(String mensaje) {
        this.mensaje = mensaje;
        this.detalles = List.of();
    }

    public ErrorDTO(String mensaje, List<String> detalles) {
        this.mensaje = mensaje;
        this.detalles = detalles;
    }

    public String getMensaje() {
        return mensaje;
    }

    public List<String> getDetalles() {
        return detalles;
    }
}
