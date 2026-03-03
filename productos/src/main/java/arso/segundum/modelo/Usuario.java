package arso.segundum.modelo;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Usuario {

    @Id
    private String idUsuario;
    private String email;
    private String nombre;
    private String apellidos;

    public Usuario() {
    }

    public Usuario(String idUsuario, String email, String nombre, String apellidos) {
        this.idUsuario = idUsuario;
        this.email = email;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
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

    @Override
    public String toString() {
        return "Usuario [idUsuario=" + idUsuario + ", email=" + email
                + ", nombre=" + nombre + ", apellidos=" + apellidos + "]";
    }
}
