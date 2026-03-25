package arso.segundum.repositorio;

import arso.segundum.modelo.Compraventa;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface IRepositorioCompraventa {
    @Query("{ 'comprador._id': ?0 }")
    List<Compraventa> findByCompradorId(String idComprador);

    @Query("{ 'vendedor._id': ?0 }")
    List<Compraventa> findByVendedorId(String idVendedor);

    @Query("{ 'comprador._id': ?0, 'vendedor._id': ?1 }")
    List<Compraventa> findByCompradorIdAndVendedorId(String idComprador, String idVendedor);
}