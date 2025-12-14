package productos.web;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import productos.dto.ProductoDTO;
import productos.servicio.IServicioProductos;
import servicio.FactoriaServicios;

@Named
@ViewScoped
public class ProductoDetailWeb implements Serializable {
  private String idProducto;
  private IServicioProductos servicioProductos;
  private ProductoDTO producto;

  @Inject
  protected FacesContext facesContext;

  public ProductoDetailWeb() {
    servicioProductos = FactoriaServicios.getServicio(IServicioProductos.class);
  }

  public void load() {
    try {
      producto = servicioProductos.getProducto(idProducto);
    } catch (Exception e) {
      facesContext.addMessage(null,
          new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al cargar el producto", e.getMessage()));
    }
  }

  public String getIdProducto() {
    return idProducto;
  }

  public void setIdProducto(String idProducto) {
    this.idProducto = idProducto;
  }

  public ProductoDTO getProducto() {
    return producto;
  }
}
