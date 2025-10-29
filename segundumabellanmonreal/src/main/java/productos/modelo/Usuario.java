package productos.modelo;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import repositorio.Identificable;

@Entity
public class Usuario implements Identificable {
  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  private String id;
  private String email;
  private String nombre;
  private String apellidos;
  private String password;
  private LocalDate fechaNacimiento;
  private String telefono;
  private boolean esAdmin;

  public Usuario() {
  }

  public Usuario(String email, String nombre, String apellidos, String password,
      LocalDate fechaNacimiento, String telefono, boolean esAdmin) {
    this.email = email;
    this.nombre = nombre;
    this.apellidos = apellidos;
    this.password = password;
    this.fechaNacimiento = fechaNacimiento;
    this.telefono = telefono;
    this.esAdmin = esAdmin;
  }

  public String getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public String getNombre() {
    return nombre;
  }

  public String getApellidos() {
    return apellidos;
  }

  public String getPassword() {
    return password;
  }

  public LocalDate getFechaNacimiento() {
    return fechaNacimiento;
  }

  public String getTelefono() {
    return telefono;
  }

  public boolean isAdmin() {
    return esAdmin;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public void setApellidos(String apellidos) {
    this.apellidos = apellidos;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setFechaNacimiento(LocalDate fechaNacimiento) {
    this.fechaNacimiento = fechaNacimiento;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public void setIsAdmin(boolean esAdmin) {
    this.esAdmin = esAdmin;
  }
}
