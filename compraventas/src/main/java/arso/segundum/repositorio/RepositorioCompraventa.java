package arso.segundum.repositorio;

import arso.segundum.modelo.Compraventa;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RepositorioCompraventa extends IRepositorioCompraventa, MongoRepository<Compraventa, String> {
}