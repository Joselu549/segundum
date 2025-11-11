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
import productos.repositorio.RepositorioUsuariosJPA;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.RepositorioException;

public class ServicioProductos implements IServicioProductos {

  private RepositorioProductosAdHocJPA repositorioProductos = FactoriaRepositorios
      .getRepositorio(Producto.class);
  private RepositorioCategoriasAdHocJPA repositorioCategorias = FactoriaRepositorios
      .getRepositorio(Categoria.class);
  private RepositorioUsuariosJPA repositorioUsuarios = FactoriaRepositorios
      .getRepositorio(Usuario.class);

  @Override
  public String darDeAltaProducto(String titulo, String descripcion, double precio, Estado estado,
      String idCategoria, boolean envioDisponible, String idVendedor) {

    // Validar título
    if (titulo == null || titulo.trim().isEmpty()) {
      throw new IllegalArgumentException("El título no puede ser nulo o vacío");
    }
    if (titulo.length() > 200) {
      throw new IllegalArgumentException("El título no puede superar los 200 caracteres");
    }

    // Validar descripción
    if (descripcion == null || descripcion.trim().isEmpty()) {
      throw new IllegalArgumentException("La descripción no puede ser nula o vacía");
    }
    if (descripcion.length() > 2000) {
      throw new IllegalArgumentException("La descripción no puede superar los 2000 caracteres");
    }

    // Validar precio
    if (precio < 0) {
      throw new IllegalArgumentException("El precio no puede ser negativo");
    }
    if (precio > 1000000) {
      throw new IllegalArgumentException("El precio no puede superar 1.000.000€");
    }

    // Validar estado
    if (estado == null) {
      throw new IllegalArgumentException("El estado no puede ser nulo");
    }

    // Validar idCategoria
    if (idCategoria == null || idCategoria.trim().isEmpty()) {
      throw new IllegalArgumentException("El ID de categoría no puede ser nulo o vacío");
    }

    // Validar idVendedor
    if (idVendedor == null || idVendedor.trim().isEmpty()) {
      throw new IllegalArgumentException("El ID de vendedor no puede ser nulo o vacío");
    }

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

    // Validar idProducto
    if (idProducto == null || idProducto.trim().isEmpty()) {
      throw new IllegalArgumentException("El ID de producto no puede ser nulo o vacío");
    }

    // Validar coordenadas
    if (longitud < -180 || longitud > 180) {
      throw new IllegalArgumentException("La longitud debe estar entre -180 y 180");
    }
    if (latitud < -90 || latitud > 90) {
      throw new IllegalArgumentException("La latitud debe estar entre -90 y 90");
    }

    // Validar descripción
    if (descripcion == null || descripcion.trim().isEmpty()) {
      throw new IllegalArgumentException("La descripción del lugar no puede ser nula o vacía");
    }
    if (descripcion.length() > 500) {
      throw new IllegalArgumentException("La descripción del lugar no puede superar los 500 caracteres");
    }

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

    // Validar idProducto
    if (idProducto == null || idProducto.trim().isEmpty()) {
      throw new IllegalArgumentException("El ID de producto no puede ser nulo o vacío");
    }

    // Validar precio si está presente
    if (precio.isPresent()) {
      if (precio.get() < 0) {
        throw new IllegalArgumentException("El precio no puede ser negativo");
      }
      if (precio.get() > 1000000) {
        throw new IllegalArgumentException("El precio no puede superar 1.000.000€");
      }
    }

    // Validar descripción si está presente
    if (descripcion.isPresent()) {
      if (descripcion.get() == null || descripcion.get().trim().isEmpty()) {
        throw new IllegalArgumentException("La descripción no puede ser nula o vacía");
      }
      if (descripcion.get().length() > 2000) {
        throw new IllegalArgumentException("La descripción no puede superar los 2000 caracteres");
      }
    }

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

    // Validar idProducto
    if (idProducto == null || idProducto.trim().isEmpty()) {
      throw new IllegalArgumentException("El ID de producto no puede ser nulo o vacío");
    }

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

    // Validar mes
    if (mes < 1 || mes > 12) {
      throw new IllegalArgumentException("El mes debe estar entre 1 y 12");
    }

    // Validar año
    if (anio < 2000 || anio > 2100) {
      throw new IllegalArgumentException("El año debe estar entre 2000 y 2100");
    }

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
  public List<Producto> buscarProductos(String idCategoria,
      String textoDescripcion, Estado estado, Double precioMaximo) {

    // Validar precioMaximo si no es nulo
    if (precioMaximo != null && precioMaximo < 0) {
      throw new IllegalArgumentException("El precio máximo no puede ser negativo");
    }

    try {

      List<Producto> resultado = repositorioProductos.buscarProductos(
          idCategoria, textoDescripcion, estado, precioMaximo);

      return resultado;

    } catch (RepositorioException e) {
      throw new RuntimeException("Error al buscar productos: " + e.getMessage(), e);
    }
  }

}
