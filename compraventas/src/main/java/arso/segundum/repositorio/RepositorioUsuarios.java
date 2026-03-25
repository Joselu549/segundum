package arso.segundum.repositorio;

import arso.segundum.modelo.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RepositorioUsuarios extends IRepositorioUsuarios, MongoRepository<Usuario, String> {
}