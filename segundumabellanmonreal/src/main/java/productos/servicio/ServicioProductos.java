package productos.servicio;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import productos.modelo.Categoria;
import productos.modelo.Estado;
import productos.modelo.LugarRecogida;
import productos.modelo.Producto;
import productos.modelo.Usuario;
import productos.repositorio.RepositorioCategoriasJPA;
import productos.repositorio.RepositorioProductosJPA;
import productos.repositorio.RepositiorioUsuariosJPA;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.RepositorioException;

public class ServicioProductos implements IServicioProductos {

  private RepositorioProductosJPA repositorioProductos = FactoriaRepositorios
      .getRepositorio(Producto.class);
  private RepositorioCategoriasJPA repositorioCategorias = FactoriaRepositorios
      .getRepositorio(Categoria.class);
  private RepositiorioUsuariosJPA repositorioUsuarios = FactoriaRepositorios
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
      List<Producto> productos = repositorioProductos.getAll();
      List<Producto> productosMes = productos.stream()
          .filter(p -> p.getFechaPublicacion().getYear() == anio
              && p.getFechaPublicacion().getMonthValue() == mes)
          .sorted((p1, p2) -> Integer.compare(p2.getVisualizaciones(), p1.getVisualizaciones()))
          .collect(Collectors.toList());

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
      List<Producto> productos = repositorioProductos.getAll();
      List<Producto> resultado = productos.stream()
          .filter(p -> {
            if (!idCategoria.isPresent()) {
              return true;
            }
            List<String> idsCategoriasValidas = obtenerIdsCategoriaYDescendientes(idCategoria.get());
            return idsCategoriasValidas.contains(p.getCategoria().getId());
          })
          .filter(p -> {
            if (!textoDescripcion.isPresent()) {
              return true;
            }
            String texto = textoDescripcion.get().toLowerCase();
            return p.getDescripcion().toLowerCase().contains(texto);
          })
          .filter(p -> {
            if (!estado.isPresent()) {
              return true;
            }
            return compararEstados(p.getEstado(), estado.get()) >= 0;
          })
          .filter(p -> {
            if (!precioMaximo.isPresent()) {
              return true;
            }
            return p.getPrecio() <= precioMaximo.get();
          })
          .collect(Collectors.toList());

      return resultado;

    } catch (RepositorioException e) {
      throw new RuntimeException("Error al buscar productos: " + e.getMessage(), e);
    }
  }

  private List<String> obtenerIdsCategoriaYDescendientes(String idCategoria) {
    List<String> ids = new ArrayList<>();
    ids.add(idCategoria);

    try {
      List<Categoria> todasCategorias = repositorioCategorias.getAll();
      Categoria categoriaInicial = null;
      for (Categoria cat : todasCategorias) {
        if (cat.getId().equals(idCategoria)) {
          categoriaInicial = cat;
          break;
        }
      }

      if (categoriaInicial != null) {
        buscarSubcategoriasRecursivo(categoriaInicial.getId(), todasCategorias, ids);
      }
    } catch (RepositorioException e) {
    }

    return ids;
  }

  private void buscarSubcategoriasRecursivo(String idCategoriaPadre,
      List<Categoria> todasCategorias, List<String> idsAcumulados) {
    todasCategorias.stream()
        .filter(cat -> cat.getCategoria() != null && cat.getCategoria().getId().equals(idCategoriaPadre))
        .map(Categoria::getId)
        .filter(id -> !idsAcumulados.contains(id))
        .forEach(id -> {
          idsAcumulados.add(id);
          buscarSubcategoriasRecursivo(id, todasCategorias, idsAcumulados);
        });
  }

  private int compararEstados(Estado estado1, Estado estado2) {
    int valor1 = getValorEstado(estado1);
    int valor2 = getValorEstado(estado2);
    return valor1 - valor2;
  }

  private int getValorEstado(Estado estado) {
    switch (estado) {
      case NUEVO:
        return 5;
      case COMO_NUEVO:
        return 4;
      case BUEN_ESTADO:
        return 3;
      case ACEPTABLE:
        return 2;
      case PIEZAS:
        return 1;
      default:
        return 0;
    }
  }
}
