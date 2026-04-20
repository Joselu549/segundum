package arso.segundum.puertos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import arso.segundum.modelo.Usuario;
import arso.segundum.repositorio.UsuarioRepository;

@Component
public class ManejadorEventos {

    @Autowired
    private UsuarioRepository repositorioUsuarios;

    public void usuarioCreado(String id, String email, String nombre, String apellidos) {
        Usuario usuario = new Usuario(id, email, nombre, apellidos);
        this.repositorioUsuarios.save(usuario);
        System.out.println("usuario creado: " + id);
    }

    public void usuarioModificado(String id, String nombre, String apellidos) {
        repositorioUsuarios.findById(id).ifPresent(usuario -> {
            usuario.setNombre(nombre);
            usuario.setApellidos(apellidos);
            repositorioUsuarios.save(usuario);
            System.out.println("usuario modificado: " + id);
        });
    }

}
