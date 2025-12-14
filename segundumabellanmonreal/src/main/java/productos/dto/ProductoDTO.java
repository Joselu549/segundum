package productos.dto;

import java.io.Serializable;

public class ProductoDTO implements Serializable {
  private String id;
  private String titulo;
  private String descripcion;
  private double precio;

  public ProductoDTO() {
  }

  public ProductoDTO(String id, String titulo, String descripcion, Double precio) {
    this.id = id;
    this.titulo = titulo;
    this.descripcion = descripcion;
    this.precio = precio;
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
}
