package productos.web.usuario;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import productos.dto.UsuarioDTO;
import productos.servicio.IServicioUsuarios;
import servicio.FactoriaServicios;

@Named
@SessionScoped
public class SesionUsuarioWeb implements Serializable {
  private static final long serialVersionUID = 1L;
  private String email;
  private String password;
  private UsuarioDTO usuarioActual;

  @Inject
  private FacesContext facesContext;

  private IServicioUsuarios servicioUsuarios;

  public SesionUsuarioWeb() {
    servicioUsuarios = FactoriaServicios.getServicio(IServicioUsuarios.class);
  }

  public String login() {
    try {
      usuarioActual = servicioUsuarios.login(email, password);
      password = null;
      facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Inicio de sesión correcto",
          "Bienvenido " + usuarioActual.getNombre()));
      facesContext.getExternalContext().getFlash().setKeepMessages(true);
      return "/index.xhtml?faces-redirect=true";

    } catch (IllegalArgumentException e) {
      facesContext.addMessage(null,
          new FacesMessage(FacesMessage.SEVERITY_ERROR, "Credenciales inválidas", e.getMessage()));
      return null;
    } catch (Exception e) {
      facesContext.addMessage(null,
          new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo iniciar sesión", e.getMessage()));
      return null;
    }
  }

  public boolean isAutenticado() {
    return usuarioActual != null;
  }

  public String logout() {
    usuarioActual = null;
    email = null;
    password = null;
    try {
      facesContext.getExternalContext().invalidateSession();
      facesContext.getExternalContext()
          .redirect(facesContext.getExternalContext().getRequestContextPath() + "/index.xhtml");
    } catch (Exception e) {
      System.out.println("Error al redirigir tras el logout: " + e.getMessage());
    }
    return null;
  }

  public UsuarioDTO getUsuarioActual() {
    return usuarioActual;
  }

  public void setUsuarioActual(UsuarioDTO usuario) {
    this.usuarioActual = usuario;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
