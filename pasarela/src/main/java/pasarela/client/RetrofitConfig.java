package pasarela.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class RetrofitConfig {

    @Value("${usuarios.url}")
    private String usuariosUrl;

    @Bean
    public UsuariosClient usuariosClient() {
        String baseUrl = usuariosUrl.endsWith("/") ? usuariosUrl : usuariosUrl + "/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(UsuariosClient.class);
    }
}
