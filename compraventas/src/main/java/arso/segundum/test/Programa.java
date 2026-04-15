package arso.segundum.test;

import arso.segundum.servicio.ServicioCompraventa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Programa implements CommandLineRunner {
    @Autowired
    private ServicioCompraventa servicioCompraventa;
    
    @Override
    public void run(String... args) throws Exception {
    }
}