package arso.segundum.retrofit;

import arso.segundum.dto.NombreUsuarioDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UsuariosClient {
    @GET("users/nombres/{id}")
    Call<NombreUsuarioDTO> getUsuarioById(@Path("id") String idUsuario);
}