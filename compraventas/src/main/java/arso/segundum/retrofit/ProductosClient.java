package arso.segundum.retrofit;

import arso.segundum.dto.ProductoDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public interface ProductosClient {
    @GET("products/{id}")
    Call<ProductoDTO> getProductoById(@Path("id") Long idProducto);

    @PATCH("products/{id}/vendido")
    Call<Void> marcarComoVendido(@Path("id") Long idProducto);
}