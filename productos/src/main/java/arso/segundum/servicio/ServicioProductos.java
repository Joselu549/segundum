package arso.segundum.servicio;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import arso.segundum.modelo.Categoria;
import arso.segundum.modelo.Estado;
import arso.segundum.modelo.LugarRecogida;
import arso.segundum.modelo.Producto;
import arso.segundum.modelo.Usuario;
import arso.segundum.repositorio.CategoriaRepository;
import arso.segundum.repositorio.ProductoRepository;
import arso.segundum.repositorio.UsuarioRepository;

@Service
@Transactional
public class ServicioProductos implements IServicioProductos {

    @Autowired
    private ProductoRepository repositorioProductos;

    @Autowired
    private CategoriaRepository repositorioCategorias;

    @Autowired
    private UsuarioRepository repositorioUsuarios;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long darDeAltaProducto(String titulo, String descripcion, double precio, Estado estado,
            String idCategoria, boolean envioDisponible, String idVendedor) {
        validarParametros(titulo, descripcion, precio, estado, idCategoria);

        Usuario usuario = repositorioUsuarios.findById(idVendedor)
                .orElseThrow(() -> new RuntimeException("Vendedor no encontrado: " + idVendedor));

        // Validar vendedor
        if (usuario.getIdUsuario() == null || usuario.getIdUsuario().trim().isEmpty()) {
            throw new IllegalArgumentException("El vendedor y su ID no pueden ser nulos o vacíos");
        }

        Categoria categoria = repositorioCategorias.findById(idCategoria)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada: " + idCategoria));

        Producto producto = new Producto(titulo, descripcion, precio, estado, LocalDateTime.now(),
                categoria, 0, envioDisponible, null, usuario);

        Producto guardado = repositorioProductos.save(producto);
        return guardado.getId();
    }

    private void validarParametros(String titulo, String descripcion, double precio, Estado estado,
                                   String idCategoria) {
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
    }

    @Override
    public LugarRecogida asignarLugarRecogida(Long idProducto, double longitud, double latitud,
            String descripcion) {

        if (idProducto == null) {
            throw new IllegalArgumentException("El ID de producto no puede ser nulo");
        }

        if (longitud < -180 || longitud > 180) {
            throw new IllegalArgumentException("La longitud debe estar entre -180 y 180");
        }
        if (latitud < -90 || latitud > 90) {
            throw new IllegalArgumentException("La latitud debe estar entre -90 y 90");
        }

        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción del lugar no puede ser nula o vacía");
        }
        if (descripcion.length() > 500) {
            throw new IllegalArgumentException("La descripción del lugar no puede superar los 500 caracteres");
        }

        Producto producto = repositorioProductos.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + idProducto));

        LugarRecogida lugar = producto.setLugarRecogida(descripcion, longitud, latitud);
        repositorioProductos.save(producto);
        return lugar;
    }

    @Override
    public void modificarProducto(Long idProducto, Optional<Double> precio,
            Optional<String> descripcion) {

        if (idProducto == null) {
            throw new IllegalArgumentException("El ID de producto no puede ser nulo");
        }

        if (precio.isPresent()) {
            if (precio.get() < 0) {
                throw new IllegalArgumentException("El precio no puede ser negativo");
            }
            if (precio.get() > 1000000) {
                throw new IllegalArgumentException("El precio no puede superar 1.000.000€");
            }
        }

        if (descripcion.isPresent()) {
            if (descripcion.get().trim().isEmpty()) {
                throw new IllegalArgumentException("La descripción no puede ser vacía");
            }
            if (descripcion.get().length() > 2000) {
                throw new IllegalArgumentException("La descripción no puede superar los 2000 caracteres");
            }
        } else {
            throw new IllegalArgumentException("La descripción no puede ser nula");
        }

        Producto producto = repositorioProductos.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + idProducto));

        precio.ifPresent(producto::setPrecio);
        descripcion.ifPresent(producto::setDescripcion);

        repositorioProductos.save(producto);
    }

    @Override
    public void addVisualizacionProducto(Long idProducto) {

        if (idProducto == null) {
            throw new IllegalArgumentException("El ID de producto no puede ser nulo");
        }

        Producto producto = repositorioProductos.findById(idProducto)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con id: " + idProducto));

        producto.setVisualizaciones(producto.getVisualizaciones() + 1);
        repositorioProductos.save(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResumenProducto> getHistorialMes(Integer mes, Integer anio) {
        validarMesAnio(mes, anio);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        return repositorioProductos.findByMesAnio(mes, anio)
                .stream()
                .map(p -> new ResumenProducto(
                        p.getId(),
                        p.getTitulo(),
                        p.getPrecio(),
                        p.getFechaPublicacion().format(formatter),
                        p.getCategoria().getNombre(),
                        p.getVisualizaciones()))
                .collect(Collectors.toList());
    }

    private void validarMesAnio(Integer mes, Integer anio) {
        if (mes != null && (mes < 1 || mes > 12))
            throw new IllegalArgumentException("El mes debe estar entre 1 y 12");
        if (anio != null && (anio < 2000 || anio > 2100))
            throw new IllegalArgumentException("El año debe estar entre 2000 y 2100");
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> buscarProductos(String idCategoria,
            String textoDescripcion, Estado estado, Double precioMaximo) {

        if (precioMaximo != null && precioMaximo < 0) {
            throw new IllegalArgumentException("El precio máximo no puede ser negativo");
        }

        // Construir consulta JPQL dinámica, equivalente a la búsqueda del proyecto
        // original
        StringBuilder jpql = new StringBuilder("SELECT p FROM Producto p WHERE 1=1");

        String rutaCategoria = null;
        if (idCategoria != null && !idCategoria.isEmpty()) {
            Categoria categoria = repositorioCategorias.findById(idCategoria).orElse(null);
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

        TypedQuery<Producto> query = entityManager.createQuery(jpql.toString(), Producto.class);

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

//    @Override
//    @Transactional(readOnly = true)
//    public List<Producto> getProductosVendedor(String idVendedor) {
//
//        if (idVendedor == null || idVendedor.trim().isEmpty()) {
//            throw new IllegalArgumentException("El ID de vendedor no puede ser nulo o vacío");
//        }
//
//        return repositorioProductos.findByIdVendedor(idVendedor);
//    }

    @Override
    @Transactional(readOnly = true)
    public Producto getProducto(Long idProducto) {

        if (idProducto == null) {
            throw new IllegalArgumentException("El ID de producto no puede ser nulo");
        }

        return repositorioProductos.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + idProducto));
    }

    @Override
    public Page<ResumenProducto> getListadoPaginado(Pageable pageable) {
        return this.repositorioProductos.findAll(pageable).map(producto -> new ResumenProducto(producto.getId(), producto.getTitulo(), producto.getPrecio(), producto.getFechaPublicacion().toString(), producto.getCategoria().getNombre(), producto.getVisualizaciones()));
    }
}