package arso.segundum.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import arso.segundum.modelo.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, String> {

    // Categorías raíz: las que no tienen padre
    @Query("SELECT c FROM Categoria c WHERE c.categoriaPadre IS NULL")
    List<Categoria> findCategoriasRaiz();

    // Subcategorías directas de una categoría padre
    @Query("SELECT c FROM Categoria c WHERE c.categoriaPadre.id = :idPadre")
    List<Categoria> findSubcategorias(@Param("idPadre") String idPadre);
}
