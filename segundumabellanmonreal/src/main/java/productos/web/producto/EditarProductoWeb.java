package productos.web.producto;

import java.io.Serializable;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import productos.dto.ProductoDTO;
import productos.modelo.Estado;
import productos.servicio.IServicioProductos;
import productos.web.usuario.SesionUsuarioWeb;
import servicio.FactoriaServicios;

@Named
@ViewScoped
public class EditarProductoWeb implements Serializable {
  private static final long serialVersionUID = 1L;

  private String idProducto;
  private String titulo;
  private String descripcion;
  private Double precio;
  private Estado estado;
  private String idCategoria;
  private boolean envioDisponible;

  @Inject
  private FacesContext facesContext;

  @Inject
  private SesionUsuarioWeb sesionUsuarioWeb;

  private IServicioProductos servicioProductos;

  public EditarProductoWeb() {
    servicioProductos = FactoriaServicios.getServicio(IServicioProductos.class);
  }

  @PostConstruct
  public void init() {
    // El idProducto se pasa como parámetro desde la vista
  }

  public void cargarProducto() {
    try {
      if (idProducto == null || idProducto.trim().isEmpty()) {
        facesContext.addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "ID de producto no válido"));
        return;
      }

      ProductoDTO producto = servicioProductos.getProducto(idProducto);
      titulo = producto.getTitulo();
      descripcion = producto.getDescripcion();
      precio = producto.getPrecio();

    } catch (Exception e) {
      facesContext.addMessage(null,
          new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al cargar el producto", e.getMessage()));
    }
  }

  public void actualizarProducto() {
    try {
      if (sesionUsuarioWeb == null || sesionUsuarioWeb.getUsuarioActual() == null) {
        throw new IllegalArgumentException("Debes iniciar sesión para editar un producto");
      }

      Optional<Double> precioOpt = (precio != null) ? Optional.of(precio) : Optional.empty();
      Optional<String> descOpt = (descripcion != null && !descripcion.trim().isEmpty())
          ? Optional.of(descripcion)
          : Optional.empty();

      servicioProductos.modificarProducto(idProducto, precioOpt, descOpt);

      facesContext.addMessage(null,
          new FacesMessage(FacesMessage.SEVERITY_INFO, "Producto actualizado",
              "El producto se ha actualizado correctamente"));
      facesContext.getExternalContext().getFlash().setKeepMessages(true);
      facesContext.getExternalContext()
          .redirect(
              facesContext.getExternalContext().getRequestContextPath() + "/productos/detail.xhtml?id=" + idProducto);
    } catch (IllegalArgumentException e) {
      facesContext.addMessage(null,
          new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error de validación", e.getMessage()));
    } catch (Exception e) {
      facesContext.addMessage(null,
          new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al actualizar el producto",
              e.getMessage()));
    }
  }

  public Estado[] getEstadosDisponibles() {
    return Estado.values();
  }

  // Getters y Setters
  public String getIdProducto() {
    return idProducto;
  }

  public void setIdProducto(String idProducto) {
    this.idProducto = idProducto;
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

  public Double getPrecio() {
    return precio;
  }

  public void setPrecio(Double precio) {
    this.precio = precio;
  }

  public Estado getEstado() {
    return estado;
  }

  public void setEstado(Estado estado) {
    this.estado = estado;
  }

  public String getIdCategoria() {
    return idCategoria;
  }

  public void setIdCategoria(String idCategoria) {
    this.idCategoria = idCategoria;
  }

  public boolean isEnvioDisponible() {
    return envioDisponible;
  }

  public void setEnvioDisponible(boolean envioDisponible) {
    this.envioDisponible = envioDisponible;
  }
}
