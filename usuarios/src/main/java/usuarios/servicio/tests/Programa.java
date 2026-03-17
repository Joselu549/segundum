package usuarios.servicio.tests;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import servicio.FactoriaServicios;
import usuarios.servicio.IServicioUsuarios;

public class Programa {

    private static IServicioUsuarios servicioUsuarios;

    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("PRUEBAS DE SERVICIO DE USUARIOS - SEGUNDUM");
        System.out.println("===========================================\n");
        servicioUsuarios = FactoriaServicios.getServicio(IServicioUsuarios.class);
        try {
            servicioUsuarios.registrarUsuario("prueba@test.com", "Prueba", "Tests",
                    "618699281", "Calle prueba de ahora",
                    LocalDate.now().minusYears(40),
                    "contras3nya");
        } catch (Exception e) {
            System.out.println("Error al registrar el usuario: " + e.getMessage());
        }
        System.out.println(servicioUsuarios.obtenerTodosLosUsuarios().stream().map(u -> u.getEmail())
                .reduce((a, b) -> a + "\n" + b).orElse("No hay usuarios"));

        System.out.println("===========================================");
        System.out.println("PRUEBAS DE SERVICIO DE AUTH - SEGUNDUM");
        System.out.println("===========================================\n");

        Map<String, Object> claims = new HashMap<String, Object>(); // el cuerpo del token
        claims.put("sub", "Juan");
        Date caducidad = Date.from(Instant.now().plusSeconds(1)); // 1 hora de validez
        String token = Jwts.builder()
                .setClaims(claims).signWith(SignatureAlgorithm.HS256, "secreto")
                .setExpiration(caducidad).compact();
        System.out.println(token);
        Claims claims2 = Jwts.parser()
                .setSigningKey("secreto")
                .parseClaimsJws(token)
                .getBody();

        System.out.println(claims2.getSubject());

    }
}
