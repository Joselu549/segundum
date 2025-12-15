package productos.web.usuario;

import java.io.Serializable;
import java.time.LocalDate;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import productos.servicio.IServicioUsuarios;
import servicio.FactoriaServicios;

@Named
@ViewScoped
public class RegistroUsuarioWeb implements Serializable {
  private static final long serialVersionUID = 1L;

  private String email;
  private String nombre;
  private String apellidos;
  private String telefono;
  private String direccion;
  private LocalDate fechaNacimiento;
  private String password;
  private String confirmarPassword;

  @Inject
  private FacesContext facesContext;

  private IServicioUsuarios servicioUsuarios;

  public RegistroUsuarioWeb() {
    servicioUsuarios = FactoriaServicios.getServicio(IServicioUsuarios.class);
  }

  public void registrar() {
    try {
      if (password == null || confirmarPassword == null || !password.equals(confirmarPassword)) {
        facesContext.addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Las contraseñas no coinciden"));
        return;
      }

      servicioUsuarios.registrarUsuario(email, nombre, apellidos, telefono, direccion, fechaNacimiento, password);
      facesContext.addMessage(null,
          new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro correcto", "Ahora puedes iniciar sesión"));
      facesContext.getExternalContext().getFlash().setKeepMessages(true);
      limpiarCampos();
    } catch (IllegalArgumentException e) {
      facesContext.addMessage(null,
          new FacesMessage(FacesMessage.SEVERITY_ERROR, "Datos inválidos", e.getMessage()));
    } catch (Exception e) {
      facesContext.addMessage(null,
          new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo registrar", e.getMessage()));
    }
  }

  private void limpiarCampos() {
    email = null;
    nombre = null;
    apellidos = null;
    telefono = null;
    direccion = null;
    fechaNacimiento = null;
    password = null;
    confirmarPassword = null;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getApellidos() {
    return apellidos;
  }

  public void setApellidos(String apellidos) {
    this.apellidos = apellidos;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public String getDireccion() {
    return direccion;
  }

  public void setDireccion(String direccion) {
    this.direccion = direccion;
  }

  public LocalDate getFechaNacimiento() {
    return fechaNacimiento;
  }

  public void setFechaNacimiento(LocalDate fechaNacimiento) {
    this.fechaNacimiento = fechaNacimiento;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getConfirmarPassword() {
    return confirmarPassword;
  }

  public void setConfirmarPassword(String confirmarPassword) {
    this.confirmarPassword = confirmarPassword;
  }
}
