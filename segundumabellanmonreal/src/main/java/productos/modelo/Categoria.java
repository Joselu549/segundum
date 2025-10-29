package productos.modelo;

import java.util.LinkedList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import repositorio.Identificable;

@Entity
public class Categoria implements Identificable {

  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  private String id;
  private String nombre;
  private String descripcion;
  private String ruta;
  @OneToMany(mappedBy = "categoria")
  private LinkedList<Categoria> subCategorias;
  @ManyToOne
  private Categoria categoria;

  public Categoria() {
  }

  public Categoria(String nombre, String descripcion, String ruta,
      LinkedList<Categoria> subCategorias) {
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.ruta = ruta;
    this.subCategorias = subCategorias;
  }

  public String getId() {
    return id;
  }

  public String getNombre() {
    return nombre;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public String getRuta() {
    return ruta;
  }

  public LinkedList<Categoria> getSubCategorias() {
    return subCategorias;
  }

  public Categoria getCategoria() {
    return categoria;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public void setRuta(String ruta) {
    this.ruta = ruta;
  }

  public void setSubCategorias(LinkedList<Categoria> subCategorias) {
    this.subCategorias = subCategorias;
  }

  public void setCategoria(Categoria categoria) {
    this.categoria = categoria;
  }
}
