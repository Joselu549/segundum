package productos.repositorio;

import java.util.List;

import productos.modelo.Usuario;

public class RepositorioUsuariosAdHocJPA extends RepositorioUsuariosJPA implements RepositorioUsuariosAdHoc {

  @Override
  public List<Usuario> getUsuariosPorNombre(String nombre) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getUsuariosPorNombre'");
  }

  @Override
  public List<Usuario> getUsuariosPorEmail(String email) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getUsuariosPorEmail'");
  }

  @Override
  public List<Usuario> getUsuariosPorTelefono(String telefono) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getUsuariosPorTelefono'");
  }

  @Override
  public List<Usuario> getUsuariosAdmin(boolean esAdmin) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getUsuariosAdmin'");
  }

}
