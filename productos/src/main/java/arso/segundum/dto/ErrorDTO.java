package arso.segundum.dto;

import java.util.List;// DTO de error uniforme

public class ErrorDTO {

    private final String mensaje;
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