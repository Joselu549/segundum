package arso.segundum.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import arso.segundum.modelo.LugarRecogida;

@Repository
public interface LugarRecogidaRepository extends JpaRepository<LugarRecogida, Long> {

    Optional<LugarRecogida> findByLongitudAndLatitudAndDescripcion(
            double longitud, double latitud, String descripcion);
}
