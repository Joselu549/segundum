package auth.dto;

public class CredencialesValidadasDTO {

    private String id;
    private String nombre;
    private String apellidos;
    private String email;
    private boolean esAdmin;

    public CredencialesValidadasDTO(String id, String nombre, String apellidos, String email, boolean esAdmin) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.esAdmin = esAdmin;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getEmail() {
        return email;
    }

    public boolean isEsAdmin() {
        return esAdmin;
    }
}
