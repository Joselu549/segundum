package arso.segundum.puertos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import arso.segundum.repositorio.RepositorioCompraventa;

@Component
public class ManejadorEventos {

    @Autowired
    private RepositorioCompraventa repositorioCompraventa;

    public void usuarioModificado(String id, String nombre, String apellidos) {
        String fullName = nombre + " " + apellidos;

        repositorioCompraventa.findByVendedorId(id).forEach(c -> {
            c.setNombreVendedor(fullName);
            repositorioCompraventa.save(c);
        });

        repositorioCompraventa.findByCompradorId(id).forEach(c -> {
            c.setNombreComprador(fullName);
            repositorioCompraventa.save(c);
        });

        System.out.println("usuario modificado: " + id + " (" + fullName + ")");
    }
}
