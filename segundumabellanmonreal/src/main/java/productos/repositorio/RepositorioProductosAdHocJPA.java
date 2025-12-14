package productos.repositorio;

import java.util.List;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

import productos.modelo.Categoria;
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
    EntityManager em = null;
    try {
      em = EntityManagerHelper.getEntityManager();
      Categoria categoria = em.find(Categoria.class, idCategoria);
      if (categoria == null) {
        throw new RepositorioException("Categoría no encontrada: " + idCategoria);
      }

      String ruta = categoria.getRuta();
      String jpql = "SELECT p FROM Producto p WHERE p.categoria.id = :idCategoria";
      if (ruta != null && !ruta.isEmpty()) {
        jpql += " OR p.categoria.ruta LIKE :rutaCategoria";
      }

      TypedQuery<Producto> query = em.createQuery(jpql, Producto.class);
      query.setHint(QueryHints.REFRESH, HintValues.TRUE);
      query.setParameter("idCategoria", idCategoria);
      if (ruta != null && !ruta.isEmpty()) {
        query.setParameter("rutaCategoria", ruta + "%");
      }

      return query.getResultList();
    } catch (RuntimeException e) {
      e.printStackTrace();
      throw new RepositorioException("Error al obtener productos por categoría", e);
    } finally {
      EntityManagerHelper.closeEntityManager();
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

      String rutaCategoria = null;
      if (idCategoria != null && !idCategoria.isEmpty()) {
        Categoria categoria = em.find(Categoria.class, idCategoria);
        if (categoria != null && categoria.getRuta() != null && !categoria.getRuta().isEmpty()) {
          rutaCategoria = categoria.getRuta() + "%";
        }
        jpql.append(" AND (p.categoria.id = :idCategoria");
        if (rutaCategoria != null) {
          jpql.append(" OR p.categoria.ruta LIKE :rutaCategoria");
        }
        jpql.append(")");
      }
      if (textoDescripcion != null && !textoDescripcion.isEmpty()) {
        jpql.append(" AND LOWER(p.descripcion) LIKE :textoDescripcion");
      }
      if (estado != null) {
        jpql.append(" AND p.estado IN :estadosPermitidos");
      }
      if (precioMaximo != null) {
        jpql.append(" AND p.precio <= :precioMaximo");
      }

      TypedQuery<Producto> query = em.createQuery(jpql.toString(), Producto.class);
      query.setHint(QueryHints.REFRESH, HintValues.TRUE);

      // Establecer parámetros
      if (idCategoria != null && !idCategoria.isEmpty()) {
        query.setParameter("idCategoria", idCategoria);
        if (rutaCategoria != null) {
          query.setParameter("rutaCategoria", rutaCategoria);
        }
      }
      if (textoDescripcion != null && !textoDescripcion.isEmpty()) {
        query.setParameter("textoDescripcion", "%" + textoDescripcion.toLowerCase() + "%");
      }
      if (estado != null) {
        query.setParameter("estadosPermitidos", obtenerEstadosMejoresOIguales(estado));
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

  private List<Estado> obtenerEstadosMejoresOIguales(Estado estadoBase) {
    List<Estado> estados = new ArrayList<>();
    if (estadoBase == null) {
      return estados;
    }

    Estado[] orden = Estado.values();
    int idx = estadoBase.ordinal();
    for (int i = 0; i <= idx && i < orden.length; i++) {
      estados.add(orden[i]);
    }
    return estados;
  }

}
