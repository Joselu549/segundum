package arso.segundum.puertos;

public interface IManejadorEventos {
    void usuarioCreado(String id, String email, String nombre, String apellidos);

    void usuarioModificado(String id, String nombre, String apellidos);
}