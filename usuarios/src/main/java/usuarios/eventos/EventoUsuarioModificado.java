package usuarios.eventos;

public class EventoUsuarioModificado extends Evento {

    private String nombre;
    private String apellidos;

    public EventoUsuarioModificado(String id, String nombre, String apellidos) {
        super(id, "usuario-modificado");
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
}
