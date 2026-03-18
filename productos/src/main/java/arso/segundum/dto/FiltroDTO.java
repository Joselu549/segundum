package arso.segundum.dto;

import arso.segundum.modelo.Estado;

public class FiltroDTO {
    private String idCategoria;
    private String descripcion;
    private Estado estado;
    private double precio;

    public FiltroDTO(String idCategoria, String descripcion, Estado estado, double precio) {
        this.idCategoria = idCategoria;
        this.descripcion = descripcion;
        this.estado = estado;
        this.precio = precio;
    }

    public String getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}