package productos.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import repositorio.Identificable;

@Entity
@XmlRootElement(name = "categoria")
public class Categoria implements Identificable {

  @Id
  private String id;
  private String nombre;
  private String descripcion;
  private String ruta;
  @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
  private List<Categoria> subCategorias;
  @ManyToOne
  private Categoria categoria;

  public Categoria() {
  }

  public Categoria(String nombre, String descripcion, String ruta,
      List<Categoria> subCategorias) {
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.ruta = ruta;
    this.subCategorias = subCategorias;
  }

  @XmlAttribute
  public String getId() {
    return id;
  }

  @XmlElement(name = "nombre")
  public String getNombre() {
    return nombre;
  }

  @XmlElement(name = "descripcion")
  public String getDescripcion() {
    return descripcion;
  }

  @XmlAttribute
  public String getRuta() {
    return ruta;
  }

  @XmlElement(name = "categoria")
  public List<Categoria> getSubCategorias() {
    return subCategorias;
  }

  @XmlTransient
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

  public void setSubCategorias(List<Categoria> subCategorias) {
    this.subCategorias = subCategorias;
  }

  public void setCategoria(Categoria categoria) {
    this.categoria = categoria;
  }
}
