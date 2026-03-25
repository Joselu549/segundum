package arso.segundum.retrofit;

import arso.segundum.dto.ProductoDTO;
import arso.segundum.dto.UsuarioDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CompraventasClient {

    @GET("users/{id}")
    Call<UsuarioDTO> getUsuarioById(@Path("id") String idUsuario);

    @GET("products/{id}")
    Call<ProductoDTO> getProductoById(@Path("id") String idProducto);
}