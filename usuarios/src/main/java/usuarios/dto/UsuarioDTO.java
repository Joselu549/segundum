package usuarios.dto;

import usuarios.modelo.Usuario;

public class UsuarioDTO {
    private String id;
    private String email;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String fechaNacimiento;
    private String password;
    private int contadorCompras;
    private int contadorVentas;

    public UsuarioDTO() {
    }

    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.email = usuario.getEmail();
        this.nombre = usuario.getNombre();
        this.apellidos = usuario.getApellidos();
        this.telefono = usuario.getTelefono();
        this.fechaNacimiento = usuario.getFechaNacimiento().toString();
        this.password = usuario.getPassword();
        this.contadorCompras = usuario.getContadorCompras();
        this.contadorVentas = usuario.getContadorVentas();
    }

    public String getId() { return id; }

    public String getEmail() {
        return email;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getPassword() {
        return password;
    }

    public int getContadorCompras() {
        return contadorCompras;
    }

    public int getContadorVentas() {
        return contadorVentas;
    }
}