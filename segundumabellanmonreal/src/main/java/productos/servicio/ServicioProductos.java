package productos.servicio;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import productos.modelo.Categoria;
import productos.modelo.Estado;
import productos.modelo.LugarRecogida;
import productos.modelo.Producto;
import productos.modelo.Usuario;
import productos.repositorio.RepositorioCategoriasAdHocJPA;
import productos.repositorio.RepositorioProductosAdHocJPA;
import productos.repositorio.RepositorioUsuariosAdHocJPA;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.RepositorioException;

public class ServicioProductos implements IServicioProductos {

  private RepositorioProductosAdHocJPA repositorioProductos = FactoriaRepositorios
      .getRepositorio(Producto.class);
  private RepositorioCategoriasAdHocJPA repositorioCategorias = FactoriaRepositorios
      .getRepositorio(Categoria.class);
  private RepositorioUsuariosAdHocJPA repositorioUsuarios = FactoriaRepositorios
      .getRepositorio(Usuario.class);

  @Override
  public String darDeAltaProducto(String titulo, String descripcion, double precio, Estado estado,
      String idCategoria, boolean envioDisponible, String idVendedor) {
    try {
      Categoria categoria = repositorioCategorias.getById(idCategoria);
      Usuario vendedor = repositorioUsuarios.getById(idVendedor);

      Producto producto = new Producto(titulo, descripcion, precio, estado, LocalDateTime.now(),
          categoria, 0, envioDisponible, null, vendedor);

      String id = repositorioProductos.add(producto);
      return id;

    } catch (EntidadNoEncontrada e) {
      throw new RuntimeException("Categoría o usuario no encontrado: " + e.getMessage(), e);
    } catch (RepositorioException e) {
      throw new RuntimeException("Error al dar de alta el producto: " + e.getMessage(), e);
    }
  }

  @Override
  public LugarRecogida asignarLugarRecogida(String idProducto, int longitud, int latitud,
      String descripcion) {
    try {
      Producto producto = repositorioProductos.getById(idProducto);
      LugarRecogida lugar = new LugarRecogida(descripcion, longitud, latitud);
      producto.setLugarRecogida(lugar);
      repositorioProductos.update(producto);
      return lugar;

    } catch (EntidadNoEncontrada e) {
      throw new RuntimeException("Producto no encontrado con id: " + idProducto, e);
    } catch (RepositorioException e) {
      throw new RuntimeException("Error al asignar lugar de recogida: " + e.getMessage(), e);
    }
  }

  @Override
  public void modificarProducto(String idProducto, Optional<Double> precio,
      Optional<String> descripcion) {
    try {
      Producto producto = repositorioProductos.getById(idProducto);

      if (precio.isPresent()) {
        producto.setPrecio(precio.get());
      }
      if (descripcion.isPresent()) {
        producto.setDescripcion(descripcion.get());
      }

      repositorioProductos.update(producto);

    } catch (EntidadNoEncontrada e) {
      throw new RuntimeException("Producto no encontrado con id: " + idProducto, e);
    } catch (RepositorioException e) {
      throw new RuntimeException("Error al modificar el producto: " + e.getMessage(), e);
    }
  }

  @Override
  public void addVisualizacionProducto(String idProducto) {
    try {
      Producto producto = repositorioProductos.getById(idProducto);
      producto.setVisualizaciones(producto.getVisualizaciones() + 1);
      repositorioProductos.update(producto);

    } catch (EntidadNoEncontrada e) {
      throw new RuntimeException("Producto no encontrado con id: " + idProducto, e);
    } catch (RepositorioException e) {
      throw new RuntimeException("Error al añadir visualización: " + e.getMessage(), e);
    }
  }

  @Override
  public List<ResumenProducto> getHistorialMes(int mes, int anio) {
    try {
      List<Producto> productosMes = repositorioProductos.getProductosPorMesAnio(mes, anio);

      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
      List<ResumenProducto> resumenes = new ArrayList<>();

      productosMes.forEach(p -> resumenes.add(new ResumenProducto(
          p.getId(),
          p.getTitulo(),
          p.getPrecio(),
          p.getFechaPublicacion().format(formatter),
          p.getCategoria().getNombre(),
          p.getVisualizaciones())));

      return resumenes;

    } catch (RepositorioException e) {
      throw new RuntimeException("Error al obtener historial del mes: " + e.getMessage(), e);
    }
  }

  @Override
  public List<Producto> buscarProductos(Optional<String> idCategoria,
      Optional<String> textoDescripcion, Optional<Estado> estado, Optional<Double> precioMaximo) {
    try {
      // Preparar parámetros para la consulta AdHoc
      String categoriaParam = idCategoria.isPresent() ? idCategoria.get() : null;
      String descripcionParam = textoDescripcion.isPresent() ? textoDescripcion.get() : null;
      String estadoParam = estado.isPresent() ? estado.get().name() : null;
      Double precioParam = precioMaximo.isPresent() ? precioMaximo.get() : null;

      // Usar el método AdHoc que ejecuta la consulta JPQL en la BD
      List<Producto> resultado = repositorioProductos.buscarProductos(
          categoriaParam, descripcionParam, estadoParam, precioParam);

      return resultado;

    } catch (RepositorioException e) {
      throw new RuntimeException("Error al buscar productos: " + e.getMessage(), e);
    }
  }

}
