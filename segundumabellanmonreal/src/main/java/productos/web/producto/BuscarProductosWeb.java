package productos.web.producto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import productos.modelo.Categoria;
import productos.modelo.Estado;
import productos.modelo.Producto;
import productos.servicio.IServicioCategorias;
import productos.servicio.IServicioProductos;
import servicio.FactoriaServicios;

@Named
@ViewScoped
public class BuscarProductosWeb implements Serializable {
  private static final long serialVersionUID = 1L;

  private String idCategoria;
  private String textoDescripcion;
  private Estado estado;
  private Double precioMaximo;

  private List<Producto> resultados;
  private List<Categoria> categoriasRaiz;

  @Inject
  private FacesContext facesContext;

  private IServicioProductos servicioProductos;
  private IServicioCategorias servicioCategorias;

  public BuscarProductosWeb() {
    servicioProductos = FactoriaServicios.getServicio(IServicioProductos.class);
    servicioCategorias = FactoriaServicios.getServicio(IServicioCategorias.class);
    resultados = new ArrayList<>();
  }

  @PostConstruct
  public void init() {
    try {
      categoriasRaiz = servicioCategorias.obtenerCategoriasRaiz();
    } catch (Exception e) {
      categoriasRaiz = new ArrayList<>();
      facesContext.addMessage(null,
          new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia",
              "No se pudieron cargar las categorías"));
    }
  }

  public void buscar() {
    try {
      resultados = servicioProductos.buscarProductos(idCategoria, textoDescripcion, estado, precioMaximo);
      
      if (resultados.isEmpty()) {
        facesContext.addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Sin resultados",
                "No se encontraron productos con los criterios especificados"));
      }
    } catch (IllegalArgumentException e) {
      facesContext.addMessage(null,
          new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error de validación", e.getMessage()));
    } catch (Exception e) {
      facesContext.addMessage(null,
          new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al buscar productos",
              e.getMessage()));
    }
  }

  public void limpiar() {
    idCategoria = null;
    textoDescripcion = null;
    estado = null;
    precioMaximo = null;
    resultados = new ArrayList<>();
  }

  public Estado[] getEstadosDisponibles() {
    return Estado.values();
  }

  // Getters y Setters
  public String getIdCategoria() {
    return idCategoria;
  }

  public void setIdCategoria(String idCategoria) {
    this.idCategoria = idCategoria;
  }

  public String getTextoDescripcion() {
    return textoDescripcion;
  }

  public void setTextoDescripcion(String textoDescripcion) {
    this.textoDescripcion = textoDescripcion;
  }

  public Estado getEstado() {
    return estado;
  }

  public void setEstado(Estado estado) {
    this.estado = estado;
  }

  public Double getPrecioMaximo() {
    return precioMaximo;
  }

  public void setPrecioMaximo(Double precioMaximo) {
    this.precioMaximo = precioMaximo;
  }

  public List<Producto> getResultados() {
    return resultados;
  }

  public List<Categoria> getCategoriasRaiz() {
    return categoriasRaiz;
  }
}
