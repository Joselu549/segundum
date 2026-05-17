package usuarios.eventos;

public class EventoUsuarioCreado extends Evento {

    private String email;
    private String nombre;
    private String apellidos;

    public EventoUsuarioCreado(String id, String email, String nombre, String apellidos) {
        super(id, "usuario-creado");
        this.email = email;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getEmail() {
        return this.email;
    }

    public String getApellidos() {
        return this.apellidos;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
}