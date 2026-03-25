package arso.segundum.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "usuarios")
public class Usuario {
    @Id
    private String id;
    private String nombre;
    private String apellidos;

    public Usuario(String nombre, String apellidos) {
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    public void setNombre(String nombre, String apellidos) {
        if (nombre != null) this.nombre = nombre;
        if (apellidos != null) this.apellidos = apellidos;
    }

    public String getNombre() {
        return this.nombre + " " + this.apellidos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Usuario{" + "id='" + id + '\'' + ", nombre='" + nombre + '\'' + ", apellidos='" + apellidos + '\'' + '}';
    }
}