package usuarios.servicio.tests;

import java.time.LocalDate;

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
            servicioUsuarios.registrarUsuario("prueba@test.com", "Pruebaa", "Tests",
                    "618699281", "Callle",
                    LocalDate.now().minusYears(40),
                    "contras3nya");
        } catch (Exception e) {
            System.out.println("Error al registrar el usuario: " + e.getMessage());
        }
        System.out.println(servicioUsuarios.obtenerTodosLosUsuarios().stream().map(u -> u.getEmail())
                .reduce((a, b) -> a + "\n" + b).orElse("No hay usuarios"));
    }
}
