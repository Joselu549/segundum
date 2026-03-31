package arso.segundum.servicio;

import arso.segundum.dto.ProductoDTO;
import arso.segundum.dto.UsuarioDTO;
import arso.segundum.modelo.Compraventa;
import arso.segundum.repositorio.RepositorioCompraventa;
import arso.segundum.retrofit.ProductosClient;
import arso.segundum.retrofit.UsuariosClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Supplier;

@Service
@Transactional
public class ServicioCompraventa implements IServicioCompraventa {
    @Autowired
    private RepositorioCompraventa repositorioCompraventa;
    @Autowired
    private UsuariosClient usuariosClient;
    @Autowired
    private ProductosClient productosClient;

    private <T> T ejecutarLlamada(Supplier<Call<T>> callSupplier, String mensajeError) {
        try {
            Response<T> response = callSupplier.get().execute();
            if (response.isSuccessful() && response.body() != null) {
                return response.body();
            }
            throw new RuntimeException(mensajeError + " - Código: " + response.code());
        } catch (IOException e) {
            throw new RuntimeException("Error de comunicación con el servicio externo", e);
        }
    }

    @Override
    public void realizarCompraventa(Long idProducto, String idComprador) {
        ProductoDTO producto = obtenerProducto(idProducto);
        UsuarioDTO comprador = obtenerUsuario(idComprador);
        UsuarioDTO vendedor = obtenerUsuario(producto.getIdVendedor());

        Compraventa compraventa = crearEntidadCompraventa(producto, idComprador, comprador, vendedor);

        repositorioCompraventa.save(compraventa);
    }

    private ProductoDTO obtenerProducto(Long idProducto) {
        return ejecutarLlamada(() -> productosClient.getProductoById(idProducto), "Producto no encontrado: " + idProducto);
    }

    private UsuarioDTO obtenerUsuario(String idUsuario) {
        return ejecutarLlamada(() -> usuariosClient.getUsuarioById(idUsuario), "Usuario no encontrado: " + idUsuario);
    }

    private Compraventa crearEntidadCompraventa(ProductoDTO producto, String idComprador, UsuarioDTO comprador, UsuarioDTO vendedor) {
        return new Compraventa(
                producto.getId(),
                producto.getTitulo(),
                producto.getPrecio(),
                producto.getLugarRecogida(),
                producto.getIdVendedor(),
                vendedor.getNombre(),
                idComprador,
                comprador.getNombre(),
                LocalDate.now()
        );
    }

    @Override
    public List<Compraventa> recuperarComprasUsuario(String idUsuario) {
        return this.repositorioCompraventa.findByCompradorId(idUsuario);
    }

    @Override
    public List<Compraventa> recuperarVentasUsuario(String idUsuario) {
        return this.repositorioCompraventa.findByVendedorId(idUsuario);
    }

    @Override
    public List<Compraventa> recuperarCompraventas(String idComprador, String idVendedor) {
        return this.repositorioCompraventa.findByCompradorIdAndVendedorId(idComprador, idVendedor);
    }
}