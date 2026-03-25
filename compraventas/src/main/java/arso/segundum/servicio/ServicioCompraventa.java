package arso.segundum.servicio;

import arso.segundum.modelo.Compraventa;
import arso.segundum.repositorio.RepositorioCompraventa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ServicioCompraventa implements IServicioCompraventa {
    @Autowired
    private RepositorioCompraventa repositorioCompraventa;

    @Override
    public void realizarCompraventa(Long idProducto, String idComprador) {
//        Optional<Usuario> comprador = this.repositorioUsuarios.findById(idComprador);
//        if (!comprador.isPresent()) return;
//        Optional<Producto> producto = this.repositorioProductos.findById(idProducto);
//        if (!producto.isPresent()) return;
//        Optional<Usuario> vendedor = this.repositorioUsuarios.findById(producto.get().getIdVendedor());
//        if (!vendedor.isPresent()) return;
//        Compraventa nuevaCompraventa = new Compraventa(producto.get(), vendedor.get(), comprador.get());
//        this.repositorioCompraventa.save(nuevaCompraventa);
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