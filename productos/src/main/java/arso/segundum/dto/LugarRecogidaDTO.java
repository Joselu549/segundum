package arso.segundum.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Schema(description = "DTO para el lugar de recogida")
public class LugarRecogidaDTO {

    @Schema(description = "Longitud en grados de la posición del lugar", example = "56.89764", minimum = "-90.0", maximum = "90.0")
    @NotNull
    @DecimalMin("-90.0") @DecimalMax("90.0")
    private Double longitud;

    @Schema(description = "Latitud en grados de la posición del lugar", example = "56.89764", minimum = "-180.0", maximum = "180.0")
    @NotNull
    @DecimalMin("-180.0") @DecimalMax("180.0")
    private Double latitud;

    @Schema(description = "Descripción añadida del lugar de recogida")
    private String descripcion;

    public LugarRecogidaDTO(Double longitud, Double latitud, String descripcion) {
        this.longitud = longitud;
        this.latitud = latitud;
        this.descripcion = descripcion;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}