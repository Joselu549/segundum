package arso.segundum.repositorio;

import arso.segundum.modelo.Producto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RepositorioProductos extends IRepositorioProductos, MongoRepository<Producto, Long> {
}