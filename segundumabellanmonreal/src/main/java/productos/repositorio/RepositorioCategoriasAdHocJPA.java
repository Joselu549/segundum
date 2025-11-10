package productos.repositorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

import productos.modelo.Categoria;
import repositorio.RepositorioException;
import utils.EntityManagerHelper;

public class RepositorioCategoriasAdHocJPA extends RepositorioCategoriasJPA implements RepositorioCategoriasAdHoc {

  @Override
  public List<Categoria> getCategoriasRaiz() throws RepositorioException {
    try {
      EntityManager em = EntityManagerHelper.getEntityManager();
      TypedQuery<Categoria> query = em.createNamedQuery("Categoria.getCategoriasRaiz", Categoria.class);
      query.setHint(QueryHints.REFRESH, HintValues.TRUE);
      return query.getResultList();
    } catch (RuntimeException e) {
      e.printStackTrace();
      throw new RepositorioException("Error al obtener categorías raíz", e);
    } finally {
      EntityManagerHelper.closeEntityManager();
    }
  }

  @Override
  public List<Categoria> getSubcategorias(String idCategoriaPadre) throws RepositorioException {
    try {
      EntityManager em = EntityManagerHelper.getEntityManager();
      TypedQuery<Categoria> query = em.createNamedQuery("Categoria.getSubcategorias", Categoria.class);
      query.setHint(QueryHints.REFRESH, HintValues.TRUE);
      query.setParameter("idCategoriaPadre", idCategoriaPadre);
      return query.getResultList();
    } catch (RuntimeException e) {
      e.printStackTrace();
      throw new RepositorioException("Error al obtener subcategorías", e);
    } finally {
      EntityManagerHelper.closeEntityManager();
    }
  }

}
