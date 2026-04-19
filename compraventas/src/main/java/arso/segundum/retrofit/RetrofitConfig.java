package arso.segundum.retrofit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
public class RetrofitConfig {
    @Value("${microservicios.usuarios.url}")
    private String usuariosUrl;

    @Value("${microservicios.productos.url}")
    private String productosUrl;

    @Bean
    public UsuariosClient userClient() {
        return new Retrofit.Builder()
                .baseUrl(usuariosUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .build()
                .create(UsuariosClient.class);
    }

    @Bean
    public ProductosClient paymentClient() {
        return new Retrofit.Builder()
                .baseUrl(productosUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .build()
                .create(ProductosClient.class);
    }
}