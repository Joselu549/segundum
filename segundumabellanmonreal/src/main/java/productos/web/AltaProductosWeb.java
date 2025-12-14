package productos.web;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import productos.modelo.Estado;
import productos.servicio.IServicioProductos;
import servicio.FactoriaServicios;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class AltaProductosWeb implements Serializable {
  private String titulo;
  private String descripcion;
  private Double precio;
  private Estado estado;
  private String idCategoria;
  private boolean envioDisponible;
  private String idVendedor;

  @Inject
  private FacesContext facesContext;

  private IServicioProductos servicioProductos;

  public AltaProductosWeb() {
    servicioProductos = FactoriaServicios.getServicio(IServicioProductos.class);
    precio = null;
    envioDisponible = false;
  }

  public void altaProducto() {
    try {
      double precioValue = (precio != null) ? precio : 0.0;
      String idProducto = servicioProductos.darDeAltaProducto(
          titulo, descripcion, precioValue, estado, idCategoria, envioDisponible, idVendedor);

      facesContext.addMessage(null,
          new FacesMessage(FacesMessage.SEVERITY_INFO, "Alta de producto exitosa",
              "Producto " + idProducto + " creado correctamente"));
      facesContext.getExternalContext().getFlash().setKeepMessages(true);
      try {
        facesContext.getExternalContext().redirect("detail.xhtml?id=" + idProducto);
      } catch (IOException e) {
        facesContext.addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se ha podido navegar"));
        e.printStackTrace();
      }
    } catch (IllegalArgumentException e) {
      facesContext.addMessage(null,
          new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error de validación", e.getMessage()));
    } catch (Exception e) {
      facesContext.addMessage(null,
          new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al dar de alta el producto",
              e.getMessage()));
      e.printStackTrace();
    }
  }

  public Estado[] getEstadosDisponibles() {
    return Estado.values();
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

  public String getIdVendedor() {
    return idVendedor;
  }

  public void setIdVendedor(String idVendedor) {
    this.idVendedor = idVendedor;
  }
}
