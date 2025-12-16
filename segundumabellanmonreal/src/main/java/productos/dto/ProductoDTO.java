package productos.dto;

import java.io.Serializable;

import productos.modelo.Estado;

public class ProductoDTO implements Serializable {
  private String id;
  private String titulo;
  private String descripcion;
  private double precio;
  private Estado estado;

  public ProductoDTO() {
  }

  public ProductoDTO(String id, String titulo, String descripcion, Double precio, Estado estado) {
    this.id = id;
    this.titulo = titulo;
    this.descripcion = descripcion;
    this.precio = precio;
    this.estado = estado;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
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

  public double getPrecio() {
    return precio;
  }

  public void setPrecio(double precio) {
    this.precio = precio;
  }

  public Estado getEstado() {
    return estado;
  }

  public void setEstado(Estado estado) {
    this.estado = estado;
  }
}
