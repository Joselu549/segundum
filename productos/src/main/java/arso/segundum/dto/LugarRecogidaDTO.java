package arso.segundum.dto;

public class LugarRecogidaDTO {
    private double longitud;
    private double latitud;
    private String descripcion;

    public LugarRecogidaDTO(double longitud, double latitud, String descripcion) {
        this.longitud = longitud;
        this.latitud = latitud;
        this.descripcion = descripcion;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}