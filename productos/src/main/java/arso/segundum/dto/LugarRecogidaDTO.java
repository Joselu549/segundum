package arso.segundum.dto;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

public class LugarRecogidaDTO {
    @NotNull
    @DecimalMin("-90.0") @DecimalMax("90.0")
    private Double longitud;
    @NotNull
    @DecimalMin("-180.0") @DecimalMax("180.0")
    private Double latitud;
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