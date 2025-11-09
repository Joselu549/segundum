package productos.repositorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

import productos.modelo.Producto;
import repositorio.RepositorioException;
import utils.EntityManagerHelper;

public class RepositorioProductosAdHocJPA extends RepositorioProductosJPA implements RepositorioProductosAdHoc {

  @Override
  public Class<Producto> getClase() {
    return Producto.class;
  }

  @Override
  public List<Producto> getProductosEnVenta() throws RepositorioException {
    try {
      EntityManager em = EntityManagerHelper.getEntityManager();
      TypedQuery<Producto> query = em.createNamedQuery("Producto.getProductosEnVenta", Producto.class);
      query.setHint(QueryHints.REFRESH, HintValues.TRUE);
      return query.getResultList();
    } catch (RuntimeException e) {
      e.printStackTrace();
      throw new RepositorioException("Error al obtener productos en venta", e);
    }
  }

  @Override
  public List<Producto> getProductosPorCategoria(String idCategoria) throws RepositorioException {
    try {
      EntityManager em = EntityManagerHelper.getEntityManager();
      TypedQuery<Producto> query = em.createNamedQuery("Producto.getProductosPorCategoria", Producto.class);
      query.setHint(QueryHints.REFRESH, HintValues.TRUE);
      query.setParameter("idCategoria", idCategoria);
      return query.getResultList();
    } catch (RuntimeException e) {
      e.printStackTrace();
      throw new RepositorioException("Error al obtener productos por categoría", e);
    }
  }

  @Override
  public List<Producto> getProductosDestacados() throws RepositorioException {
    try {
      EntityManager em = EntityManagerHelper.getEntityManager();
      TypedQuery<Producto> query = em.createNamedQuery("Producto.getProductosDestacados", Producto.class);
      query.setHint(QueryHints.REFRESH, HintValues.TRUE);
      return query.getResultList();
    } catch (RuntimeException e) {
      e.printStackTrace();
      throw new RepositorioException("Error al obtener productos destacados", e);
    }
  }

}
