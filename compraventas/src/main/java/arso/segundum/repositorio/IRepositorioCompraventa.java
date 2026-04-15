package arso.segundum.repositorio;

import arso.segundum.modelo.Compraventa;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface IRepositorioCompraventa {
    @Query("{ 'idComprador': ?0 }")
    List<Compraventa> findByCompradorId(String idComprador);

    @Query("{ 'idVendedor': ?0 }")
    List<Compraventa> findByVendedorId(String idVendedor);

    @Query("{ 'idComprador': ?0, 'idVendedor': ?1 }")
    List<Compraventa> findByCompradorIdAndVendedorId(String idComprador, String idVendedor);
}