package arso.segundum.servicio;

import arso.segundum.dto.CompraventaDTO;
import arso.segundum.dto.NombreUsuarioDTO;
import arso.segundum.dto.ProductoDTO;
import arso.segundum.exception.ErrorServicioExternoException;
import arso.segundum.exception.ProductoNoDisponibleException;
import arso.segundum.exception.RecursoNoEncontradoException;
import arso.segundum.modelo.Compraventa;
import arso.segundum.repositorio.RepositorioCompraventa;
import arso.segundum.retrofit.ProductosClient;
import arso.segundum.retrofit.UsuariosClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import retrofit2.Call;
import retrofit2.Response;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@Transactional
public class ServicioCompraventa implements IServicioCompraventa {
    @Autowired
    private RepositorioCompraventa repositorioCompraventa;
    @Autowired
    private UsuariosClient usuariosClient;
    @Autowired
    private ProductosClient productosClient;
    @Autowired
    private Validator validator;

    private <T> void validarRespuesta(T dto, String contexto) {
        Set<ConstraintViolation<T>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            String detalles = violations.stream()
                    .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                    .collect(Collectors.joining(", "));
            throw new ErrorServicioExternoException("Respuesta inválida de " + contexto + ": " + detalles);
        }
    }

    private <T> T ejecutarLlamada(Supplier<Call<T>> callSupplier, String mensajeNoEncontrado) {
        try {
            Response<T> response = callSupplier.get().execute();
            if (response.isSuccessful() && response.body() != null) {
                return response.body();
            }
            if (response.code() == 404) {
                throw new RecursoNoEncontradoException(mensajeNoEncontrado);
            }
            throw new ErrorServicioExternoException("Error en servicio externo - Código: " + response.code());
        } catch (IOException e) {
            throw new ErrorServicioExternoException("Error de comunicación con el servicio externo", e);
        }
    }

    private void ejecutarLlamadaVoid(Supplier<Call<Void>> callSupplier, String mensajeNoEncontrado) {
        try {
            Response<Void> response = callSupplier.get().execute();
            if (response.isSuccessful()) {
                return;
            }
            if (response.code() == 404) {
                throw new RecursoNoEncontradoException(mensajeNoEncontrado);
            }
            throw new ErrorServicioExternoException("Error en servicio externo - Código: " + response.code());
        } catch (IOException e) {
            throw new ErrorServicioExternoException("Error de comunicación con el servicio externo", e);
        }
    }

    @Override
    public Compraventa realizarCompraventa(Long idProducto, String idComprador) {
        ProductoDTO producto = obtenerProducto(idProducto);

        if (producto.isVendido()) {
            throw new ProductoNoDisponibleException("El producto " + idProducto + " ya ha sido vendido");
        }

        NombreUsuarioDTO nombreComprador = obtenerNombreUsuario(idComprador);
        NombreUsuarioDTO nombreVendedor = obtenerNombreUsuario(producto.getIdVendedor());

        Compraventa compraventa = crearEntidadCompraventa(producto, idComprador, nombreComprador.getFullName(), nombreVendedor.getFullName());
        Compraventa guardada = repositorioCompraventa.save(compraventa);

        ejecutarLlamadaVoid(() -> productosClient.marcarComoVendido(idProducto),
                "Producto no encontrado al marcar como vendido: " + idProducto);
        ejecutarLlamadaVoid(() -> usuariosClient.incrementarContadorCompras(idComprador),
                "Comprador no encontrado al incrementar contador: " + idComprador);
        ejecutarLlamadaVoid(() -> usuariosClient.incrementarContadorVentas(producto.getIdVendedor()),
                "Vendedor no encontrado al incrementar contador: " + producto.getIdVendedor());

        return guardada;
    }

    private ProductoDTO obtenerProducto(Long idProducto) {
        ProductoDTO producto = ejecutarLlamada(() -> productosClient.getProductoById(idProducto), "Producto no encontrado: " + idProducto);
        validarRespuesta(producto, "servicio de productos");
        return producto;
    }

    private NombreUsuarioDTO obtenerNombreUsuario(String idUsuario) {
        NombreUsuarioDTO usuario = ejecutarLlamada(() -> usuariosClient.getUsuarioById(idUsuario), "Usuario no encontrado: " + idUsuario);
        validarRespuesta(usuario, "servicio de usuarios");
        return usuario;
    }

    private Compraventa crearEntidadCompraventa(ProductoDTO producto, String idComprador, String nombreComprador, String nombreVendedor) {
        return new Compraventa(
                producto.getId(),
                producto.getTitulo(),
                producto.getPrecio(),
                producto.getLugarRecogida(),
                producto.getIdVendedor(),
                nombreVendedor,
                idComprador,
                nombreComprador,
                LocalDate.now()
        );
    }

    @Override
    public List<Compraventa> recuperarComprasUsuario(String idUsuario) {
        obtenerNombreUsuario(idUsuario);
        return this.repositorioCompraventa.findByCompradorId(idUsuario);
    }

    @Override
    public List<Compraventa> recuperarVentasUsuario(String idUsuario) {
        obtenerNombreUsuario(idUsuario);
        return this.repositorioCompraventa.findByVendedorId(idUsuario);
    }

    @Override
    public List<Compraventa> recuperarCompraventas(String idComprador, String idVendedor, Long idProducto) {
        obtenerNombreUsuario(idComprador);
        obtenerNombreUsuario(idVendedor);
        if (idProducto != null) {
            obtenerProducto(idProducto);
            return this.repositorioCompraventa.findByCompradorIdAndVendedorIdAndIdProducto(idComprador, idVendedor, idProducto);
        }
        return this.repositorioCompraventa.findByCompradorIdAndVendedorId(idComprador, idVendedor);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CompraventaDTO> getListadoPaginado(Pageable pageable) {
        return this.repositorioCompraventa.findAll(pageable).map(CompraventaDTO::fromEntity);
    }
}