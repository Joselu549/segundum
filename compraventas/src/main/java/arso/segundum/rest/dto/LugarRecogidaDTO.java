package arso.segundum.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LugarRecogidaDTO {

    private Double longitud;
    private Double latitud;
    private String descripcion;

    public LugarRecogidaDTO() {
    }

    public LugarRecogidaDTO(Double longitud, Double latitud, String descripcion) {
        this.longitud = longitud;
        this.latitud = latitud;
        this.descripcion = descripcion;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Double getLatitud() {
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
