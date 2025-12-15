package productos.web.producto;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import productos.dto.ProductoDTO;
import productos.servicio.IServicioProductos;
import productos.web.usuario.SesionUsuarioWeb;
import servicio.FactoriaServicios;

@Named
@ViewScoped
public class MisProductosWeb implements Serializable {
  private static final long serialVersionUID = 1L;

  private List<ProductoDTO> productos;

  private IServicioProductos servicioProductos;

  @Inject
  private FacesContext facesContext;

  @Inject
  private SesionUsuarioWeb sesionUsuarioWeb;

  public MisProductosWeb() {
    servicioProductos = FactoriaServicios.getServicio(IServicioProductos.class);
  }

  @PostConstruct
  public void init() {
    cargarProductos();
  }

  public void cargarProductos() {
    try {
      if (sesionUsuarioWeb == null || sesionUsuarioWeb.getUsuarioActual() == null) {
        productos = Collections.emptyList();
        facesContext.addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_WARN, "Sesión requerida",
                "Inicia sesión para ver tus productos"));
        return;
      }
      String idVendedor = sesionUsuarioWeb.getUsuarioActual().getId();
      productos = servicioProductos.getProductosVendedor(idVendedor);
    } catch (IllegalArgumentException e) {
      productos = Collections.emptyList();
      facesContext.addMessage(null,
          new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
    } catch (Exception e) {
      productos = Collections.emptyList();
      facesContext.addMessage(null,
          new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudieron cargar los productos",
              e.getMessage()));
    }
  }

  public List<ProductoDTO> getProductos() {
    return productos;
  }
}
