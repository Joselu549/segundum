package arso.segundum.servicio;

import arso.segundum.modelo.Compraventa;

import java.util.List;

public interface IServicioCompraventa {
    void realizarCompraventa(Long idProducto, String idComprador) throws Exception;
    List<Compraventa> recuperarComprasUsuario(String idUsuario);
    List<Compraventa> recuperarVentasUsuario(String idUsuario);
    List<Compraventa> recuperarCompraventas(String idComprador, String idVendedor);
}