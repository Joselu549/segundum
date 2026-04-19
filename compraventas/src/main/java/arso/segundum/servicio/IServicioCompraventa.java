package arso.segundum.servicio;

import arso.segundum.dto.CompraventaDTO;
import arso.segundum.modelo.Compraventa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IServicioCompraventa {
    Compraventa realizarCompraventa(Long idProducto, String idComprador) throws Exception;
    List<Compraventa> recuperarComprasUsuario(String idUsuario);
    List<Compraventa> recuperarVentasUsuario(String idUsuario);
    List<Compraventa> recuperarCompraventas(String idComprador, String idVendedor, Long idProducto);
    Page<CompraventaDTO> getListadoPaginado(Pageable pageable);
}