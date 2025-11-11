package productos.modelo;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

import repositorio.Identificable;

@Entity
@NamedQueries({
    @NamedQuery(name = "Producto.getProductosPorCategoria", query = "SELECT p FROM Producto p WHERE p.categoria.id = :idCategoria"),
    @NamedQuery(name = "Producto.getProductosEnVenta", query = "SELECT p FROM Producto p WHERE p.vendedor IS NOT NULL ORDER BY p.fechaPublicacion DESC"),
    @NamedQuery(name = "Producto.getProductosDestacados", query = "SELECT p FROM Producto p ORDER BY p.visualizaciones DESC"),
    @NamedQuery(name = "Producto.getProductosPorMesAnio", query = "SELECT p FROM Producto p WHERE FUNCTION('MONTH', p.fechaPublicacion) = :mes AND FUNCTION('YEAR', p.fechaPublicacion) = :anio ORDER BY p.visualizaciones DESC")
})
public class Producto implements Identificable {

  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  private String id;
  private String titulo;
  @Lob
  private String descripcion;
  private double precio;

  @Enumerated(EnumType.STRING)
  private Estado estado;
  private LocalDateTime fechaPublicacion;
  @OneToOne
  private Categoria categoria;
  private int visualizaciones;
  private boolean envioDisponible;
  @OneToOne(cascade = CascadeType.ALL)
  private LugarRecogida lugarRecogida;
  @OneToOne
  private Usuario vendedor;

  public Producto() {
  }

  public Producto(String titulo, String descripcion, double precio, Estado estado,
      LocalDateTime fechaPublicacion, Categoria categoria, int visualizaciones,
      boolean envioDisponible, LugarRecogida lugarRecogida, Usuario vendedor) {
    this.titulo = titulo;
    this.descripcion = descripcion;
    this.precio = precio;
    this.estado = estado;
    this.fechaPublicacion = fechaPublicacion;
    this.categoria = categoria;
    this.visualizaciones = visualizaciones;
    this.envioDisponible = envioDisponible;
    this.lugarRecogida = lugarRecogida;
    this.vendedor = vendedor;
  }

  @Override
  public String getId() {
    return id;
  }

  public String getTitulo() {
    return titulo;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public double getPrecio() {
    return precio;
  }

  public Estado getEstado() {
    return estado;
  }

  public LocalDateTime getFechaPublicacion() {
    return fechaPublicacion;
  }

  public Categoria getCategoria() {
    return categoria;
  }

  public int getVisualizaciones() {
    return visualizaciones;
  }

  public boolean isEnvioDisponible() {
    return envioDisponible;
  }

  public LugarRecogida getLugarRecogida() {
    return lugarRecogida;
  }

  public Usuario getVendedor() {
    return vendedor;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public void setPrecio(double precio) {
    this.precio = precio;
  }

  public void setEstado(Estado estado) {
    this.estado = estado;
  }

  public void setFechaPublicacion(LocalDateTime fechaPublicacion) {
    this.fechaPublicacion = fechaPublicacion;
  }

  public void setCategoria(Categoria categoria) {
    this.categoria = categoria;
  }

  public void setVisualizaciones(int visualizaciones) {
    this.visualizaciones = visualizaciones;
  }

  public void setEnvioDisponible(boolean envioDisponible) {
    this.envioDisponible = envioDisponible;
  }

  public void setLugarRecogida(LugarRecogida lugarRecogida) {
    this.lugarRecogida = lugarRecogida;
  }

  public void setVendedor(Usuario vendedor) {
    this.vendedor = vendedor;
  }

  @Override
  public String toString() {
    return "Producto [id=" + id + ", titulo=" + titulo + ", descripcion=" + descripcion + ", precio="
        + precio + ", estado=" + estado + ", fechaPublicacion=" + fechaPublicacion + ", categoria="
        + categoria + ", visualizaciones=" + visualizaciones + ", envioDisponible="
        + envioDisponible + ", lugarRecogida=" + lugarRecogida + ", vendedor=" + vendedor + "]";
  }
}
