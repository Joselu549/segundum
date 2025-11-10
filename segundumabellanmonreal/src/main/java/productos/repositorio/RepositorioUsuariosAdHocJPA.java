package productos.repositorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

import productos.modelo.Usuario;
import repositorio.RepositorioException;
import utils.EntityManagerHelper;

public class RepositorioUsuariosAdHocJPA extends RepositorioUsuariosJPA implements RepositorioUsuariosAdHoc {

  @Override
  public List<Usuario> getUsuariosPorNombre(String nombre) throws RepositorioException {
    try {
      EntityManager em = EntityManagerHelper.getEntityManager();
      TypedQuery<Usuario> query = em.createNamedQuery("Usuario.getUsuariosPorNombre", Usuario.class);
      query.setHint(QueryHints.REFRESH, HintValues.TRUE);
      query.setParameter("nombre", "%" + nombre + "%");
      return query.getResultList();
    } catch (RuntimeException e) {
      e.printStackTrace();
      throw new RepositorioException("Error al obtener usuarios por nombre", e);
    } finally {
      EntityManagerHelper.closeEntityManager();
    }
  }

  @Override
  public List<Usuario> getUsuariosPorEmail(String email) throws RepositorioException {
    try {
      EntityManager em = EntityManagerHelper.getEntityManager();
      TypedQuery<Usuario> query = em.createNamedQuery("Usuario.getUsuariosPorEmail", Usuario.class);
      query.setHint(QueryHints.REFRESH, HintValues.TRUE);
      query.setParameter("email", email);
      return query.getResultList();
    } catch (RuntimeException e) {
      e.printStackTrace();
      throw new RepositorioException("Error al obtener usuarios por email", e);
    } finally {
      EntityManagerHelper.closeEntityManager();
    }
  }

  @Override
  public List<Usuario> getUsuariosPorTelefono(String telefono) throws RepositorioException {
    try {
      EntityManager em = EntityManagerHelper.getEntityManager();
      TypedQuery<Usuario> query = em.createNamedQuery("Usuario.getUsuariosPorTelefono", Usuario.class);
      query.setHint(QueryHints.REFRESH, HintValues.TRUE);
      query.setParameter("telefono", telefono);
      return query.getResultList();
    } catch (RuntimeException e) {
      e.printStackTrace();
      throw new RepositorioException("Error al obtener usuarios por teléfono", e);
    } finally {
      EntityManagerHelper.closeEntityManager();
    }
  }

  @Override
  public List<Usuario> getUsuariosAdmin(boolean esAdmin) throws RepositorioException {
    try {
      EntityManager em = EntityManagerHelper.getEntityManager();
      TypedQuery<Usuario> query = em.createNamedQuery("Usuario.getUsuariosAdmin", Usuario.class);
      query.setHint(QueryHints.REFRESH, HintValues.TRUE);
      query.setParameter("esAdmin", esAdmin);
      return query.getResultList();
    } catch (RuntimeException e) {
      e.printStackTrace();
      throw new RepositorioException("Error al obtener usuarios admin", e);
    } finally {
      EntityManagerHelper.closeEntityManager();
    }
  }

}
