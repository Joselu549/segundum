package productos.modelo;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import repositorio.Identificable;

@Entity
@XmlRootElement(name = "categoria")
@NamedQueries({
    @NamedQuery(name = "Categoria.getCategoriasRaiz", query = "SELECT c FROM Categoria c WHERE c.categoriaPadre IS NULL"),
    @NamedQuery(name = "Categoria.getSubcategorias", query = "SELECT c FROM Categoria c WHERE c.categoriaPadre.id = :idCategoriaPadre")
})
public class Categoria implements Identificable {

  @Id
  private String id;
  private String nombre;
  @Lob
  private String descripcion;
  private String ruta;
  @OneToMany(mappedBy = "categoriaPadre", cascade = CascadeType.ALL)
  private List<Categoria> subCategorias;
  @ManyToOne
  private Categoria categoriaPadre;

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
  public Categoria getCategoriaPadre() {
    return categoriaPadre;
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

  public void setCategoriaPadre(Categoria categoriaPadre) {
    this.categoriaPadre = categoriaPadre;
  }
}
