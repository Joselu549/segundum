package usuarios.dto;

import usuarios.modelo.Usuario;

public class UsuarioDTO {
    private String email;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String fechaNacimiento;
    private String password;

    public UsuarioDTO() {
    }

    public UsuarioDTO(Usuario usuario) {
        this.email = usuario.getEmail();
        this.nombre = usuario.getNombre();
        this.apellidos = usuario.getApellidos();
        this.telefono = usuario.getTelefono();
        this.fechaNacimiento = usuario.getFechaNacimiento().toString();
        this.password = usuario.getPassword();
    }

    public UsuarioDTO(String email, String nombre, String apellidos, String telefono,
            String fechaNacimiento, String password) {
        this.email = email;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.password = password;
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

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
