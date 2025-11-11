package productos.repositorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

import productos.modelo.Estado;
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

  @Override
  public List<Producto> getProductosPorMesAnio(int mes, int anio) throws RepositorioException {
    try {
      EntityManager em = EntityManagerHelper.getEntityManager();
      TypedQuery<Producto> query = em.createNamedQuery("Producto.getProductosPorMesAnio", Producto.class);
      query.setHint(QueryHints.REFRESH, HintValues.TRUE);
      query.setParameter("mes", mes);
      query.setParameter("anio", anio);
      return query.getResultList();
    } catch (RuntimeException e) {
      e.printStackTrace();
      throw new RepositorioException("Error al obtener productos por mes y año", e);
    } finally {
      EntityManagerHelper.closeEntityManager();
    }
  }

  @Override
  public List<Producto> buscarProductos(String idCategoria, String textoDescripcion,
      Estado estado, Double precioMaximo) throws RepositorioException {
    try {
      EntityManager em = EntityManagerHelper.getEntityManager();

      // Construir consulta JPQL dinámica
      StringBuilder jpql = new StringBuilder("SELECT p FROM Producto p WHERE 1=1");

      if (idCategoria != null && !idCategoria.isEmpty()) {
        jpql.append(" AND p.categoria.id = :idCategoria");
      }
      if (textoDescripcion != null && !textoDescripcion.isEmpty()) {
        jpql.append(" AND LOWER(p.descripcion) LIKE :textoDescripcion");
      }
      if (estado != null) {
        jpql.append(" AND p.estado = :estado");
      }
      if (precioMaximo != null) {
        jpql.append(" AND p.precio <= :precioMaximo");
      }

      TypedQuery<Producto> query = em.createQuery(jpql.toString(), Producto.class);
      query.setHint(QueryHints.REFRESH, HintValues.TRUE);

      // Establecer parámetros
      if (idCategoria != null && !idCategoria.isEmpty()) {
        query.setParameter("idCategoria", idCategoria);
      }
      if (textoDescripcion != null && !textoDescripcion.isEmpty()) {
        query.setParameter("textoDescripcion", "%" + textoDescripcion.toLowerCase() + "%");
      }
      if (estado != null) {
        query.setParameter("estado", estado);
      }
      if (precioMaximo != null) {
        query.setParameter("precioMaximo", precioMaximo);
      }

      return query.getResultList();
    } catch (RuntimeException e) {
      e.printStackTrace();
      throw new RepositorioException("Error al buscar productos", e);
    } finally {
      EntityManagerHelper.closeEntityManager();
    }
  }

}
