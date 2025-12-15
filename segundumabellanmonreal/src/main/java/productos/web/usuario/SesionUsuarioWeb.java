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

  public void login() {
    try {
      usuarioActual = servicioUsuarios.login(email, password);
      password = null;
      facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Inicio de sesión correcto",
          "Bienvenido " + usuarioActual.getNombre()));
      facesContext.getExternalContext().getFlash().setKeepMessages(true);

    } catch (IllegalArgumentException e) {
      facesContext.addMessage(null,
          new FacesMessage(FacesMessage.SEVERITY_ERROR, "Credenciales inválidas", e.getMessage()));
    } catch (Exception e) {
      facesContext.addMessage(null,
          new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo iniciar sesión", e.getMessage()));
    }
  }

  public boolean isAutenticado() {
    return usuarioActual != null;
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
