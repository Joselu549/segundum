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
//        Usuario usuario1 = new Usuario("José Luis", "Abellán Monreal");
//        usuario1.setId("1");
//        Usuario usuario2 = new Usuario("María del Mar", "Hernández Yagües");
//        usuario2.setId("2");
//        String id1 = this.repositorioUsuarios.save(usuario1).getId();
//        System.out.println("Guardado usuario 1");
//        String id2 = this.repositorioUsuarios.save(usuario2).getId();
//        System.out.println("Guardado usuario 2");
//        Producto producto = new Producto("Producto prueba", 23.67, "Recogida allí", id1);
//        producto.setId(Long.valueOf("123"));
//        Long idProducto = this.repositorioProductos.save(producto).getId();
//        System.out.println("Guardado producto");
//        this.servicioCompraventa.realizarCompraventa(idProducto, id2);
//        System.out.println("Realizada compraventa");
//        List<Compraventa> lista1 = this.servicioCompraventa.recuperarVentasUsuario(usuario1.getId());
//        lista1.forEach(compraventa -> System.out.println(compraventa.toString()));
//        List<Compraventa> lista2 = this.servicioCompraventa.recuperarVentasUsuario(usuario2.getId());
//        lista2.forEach(compraventa -> System.out.println(compraventa.toString()));
    }
}