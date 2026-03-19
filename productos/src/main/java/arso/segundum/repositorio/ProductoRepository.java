package arso.segundum.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import arso.segundum.modelo.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Productos de una categoría concreta
    @Query("SELECT p FROM Producto p WHERE p.categoria.id = :idCategoria")
    List<Producto> findByCategoria(@Param("idCategoria") String idCategoria);

    // Productos de una categoría y sus subcategorías (por ruta)
    @Query("SELECT p FROM Producto p WHERE p.categoria.id = :idCategoria OR p.categoria.ruta LIKE :rutaCategoria")
    List<Producto> findByCategoriaConRuta(@Param("idCategoria") String idCategoria,
            @Param("rutaCategoria") String rutaCategoria);

    // Productos en venta (que tienen vendedor), ordenados por fecha
    @Query("SELECT p FROM Producto p WHERE p.vendedor.idUsuario IS NOT NULL ORDER BY p.fechaPublicacion DESC")
    List<Producto> findProductosEnVenta();

    // Productos destacados, ordenados por visualizaciones
    @Query("SELECT p FROM Producto p ORDER BY p.visualizaciones DESC")
    List<Producto> findProductosDestacados();

    // Productos por mes
    @Query("SELECT p FROM Producto p WHERE FUNCTION('MONTH', p.fechaPublicacion) = :mes ORDER BY p.visualizaciones DESC")
    List<Producto> findByMes(@Param("mes") int mes);

    // Productos por año
    @Query("SELECT p FROM Producto p WHERE FUNCTION('YEAR', p.fechaPublicacion) = :anio ORDER BY p.visualizaciones DESC")
    List<Producto> findByAnio(@Param("anio") int anio);

    // Productos por mes y año
    @Query("SELECT p FROM Producto p WHERE " +
            "(:mes IS NULL OR FUNCTION('MONTH', p.fechaPublicacion) = :mes) AND " +
            "(:anio IS NULL OR FUNCTION('YEAR', p.fechaPublicacion) = :anio) " +
            "ORDER BY p.visualizaciones DESC")
    List<Producto> findByMesAnio(@Param("mes") Integer mes, @Param("anio") Integer anio);

    // Productos de un vendedor
    @Query("SELECT p FROM Producto p WHERE p.vendedor.idUsuario = :idVendedor ORDER BY p.fechaPublicacion DESC")
    List<Producto> findByIdVendedor(@Param("idVendedor") String idVendedor);
}