package arso.segundum.retrofit;

import arso.segundum.dto.ProductoDTO;
import arso.segundum.dto.UsuarioDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UsuariosClient {
    @GET("users/{id}")
    Call<UsuarioDTO> getUsuarioById(@Path("id") String idUsuario);
}