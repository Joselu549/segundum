package arso.segundum.retrofit;

import arso.segundum.rest.dto.NombreUsuarioDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public interface UsuariosClient {
    @GET("users/nombres/{id}")
    Call<NombreUsuarioDTO> getUsuarioById(@Path("id") String idUsuario);

    @PATCH("users/{id}/compras")
    Call<Void> incrementarContadorCompras(@Path("id") String idUsuario);

    @PATCH("users/{id}/ventas")
    Call<Void> incrementarContadorVentas(@Path("id") String idUsuario);
}