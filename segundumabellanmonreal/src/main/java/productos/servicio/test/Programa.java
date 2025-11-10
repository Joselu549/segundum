package productos.servicio.test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import productos.modelo.Categoria;
import productos.modelo.Estado;
import productos.modelo.LugarRecogida;
import productos.modelo.Producto;
import productos.servicio.IServicioCategorias;
import productos.servicio.IServicioProductos;
import productos.servicio.ResumenProducto;
import productos.servicio.IServicioUsuarios;
import servicio.FactoriaServicios;

public class Programa {

  private static IServicioUsuarios servicioUsuarios;
  private static IServicioProductos servicioProductos;
  private static IServicioCategorias servicioCategorias;

  public static void main(String[] args) {

    System.out.println("===========================================");
    System.out.println("PRUEBAS DE SERVICIOS - SEGUNDUM");
    System.out.println("===========================================\n");

    // Inicializar servicios
    servicioUsuarios = FactoriaServicios.getServicio(IServicioUsuarios.class);
    servicioProductos = FactoriaServicios.getServicio(IServicioProductos.class);
    servicioCategorias = FactoriaServicios.getServicio(IServicioCategorias.class);

    try {
      // Pruebas de ServicioCategorias
      // probarServicioCategorias();

      // Pruebas de ServicioUsuarios
      probarServicioUsuarios();

      // Pruebas de ServicioProductos
      // probarServicioProductos();

      System.out.println("\n===========================================");
      System.out.println("TODAS LAS PRUEBAS COMPLETADAS EXITOSAMENTE");
      System.out.println("===========================================");

    } catch (Exception e) {
      System.err.println("\n*** ERROR EN LAS PRUEBAS ***");
      System.err.println("Mensaje: " + e.getMessage());
      e.printStackTrace();
    }
  }

  private static void probarServicioCategorias() {
    System.out.println("\n--- PRUEBAS DE SERVICIO CATEGORIAS ---\n");

    try {
      // 1. Cargar jerarquía de categorías desde XML
      System.out.println("1. Cargando jerarquía de categorías desde XML...");
      String rutaXML = "c:/Users/josel/Repositorios Git/segundum/segundumabellanmonreal/xml/Arte_y_ocio.xml";
      servicioCategorias.cargarJerarquiaCategorias(rutaXML);
      System.out.println("   ✓ Jerarquía cargada correctamente desde: " + rutaXML);

      // Intentar cargar de nuevo (no debe duplicar)
      System.out.println("   Intentando cargar la misma jerarquía de nuevo...");
      servicioCategorias.cargarJerarquiaCategorias(rutaXML);
      System.out.println("   ✓ Sistema previene carga duplicada correctamente\n");

      // 2. Obtener categorías raíz
      System.out.println("2. Obteniendo categorías raíz...");
      List<Categoria> categoriasRaiz = servicioCategorias.obtenerCategoriasRaiz();
      System.out.println("   ✓ Categorías raíz encontradas: " + categoriasRaiz.size());
      for (Categoria cat : categoriasRaiz) {
        System.out.println("     - ID: " + cat.getId() + ", Nombre: " + cat.getNombre());
      }
      System.out.println();

      // 3. Obtener subcategorías de una categoría
      if (!categoriasRaiz.isEmpty()) {
        String idCategoriaRaiz = categoriasRaiz.get(0).getId();
        System.out.println("3. Obteniendo subcategorías de la categoría: " + idCategoriaRaiz);
        List<Categoria> subcategorias = servicioCategorias.obtenerSubCategorias(idCategoriaRaiz);
        System.out.println("   ✓ Subcategorías encontradas: " + subcategorias.size());
        for (int i = 0; i < Math.min(5, subcategorias.size()); i++) {
          Categoria sub = subcategorias.get(i);
          System.out.println("     - ID: " + sub.getId() + ", Nombre: " + sub.getNombre());
        }
        if (subcategorias.size() > 5) {
          System.out.println("     ... y " + (subcategorias.size() - 5) + " más");
        }
        System.out.println();

        // 4. Modificar descripción de una categoría
        System.out.println("4. Modificando descripción de una categoría...");
        String idCategoria = idCategoriaRaiz;
        String nuevaDescripcion = "Descripción actualizada en prueba - " +
            java.time.LocalDateTime.now();
        servicioCategorias.modificarDescripcionCategoria(idCategoria, nuevaDescripcion);
        System.out.println("   ✓ Descripción modificada para categoría ID: " + idCategoria);
        System.out.println("   Nueva descripción: " + nuevaDescripcion + "\n");
      }

    } catch (Exception e) {
      System.err.println("   ✗ Error en pruebas de ServicioCategorias: " + e.getMessage());
      throw e;
    }
  }

  private static void probarServicioUsuarios() {
    System.out.println("\n--- PRUEBAS DE SERVICIO USUARIOS ---\n");
    long timestamp = System.currentTimeMillis();

    // ===== 1. REGISTROS VÁLIDOS =====
    System.out.println("1. Registrando usuarios válidos...");
    String idUsuario1 = null;
    String idUsuario2 = null;
    String idUsuario3 = null;

    try {
      idUsuario1 = servicioUsuarios.registrarUsuario("juan.perez." + timestamp + "@email.com", "Juan", "Pérez García",
          "666111222", "Calle Principal 1", LocalDate.of(1990, 5, 15), "password123");
      System.out.println("   ✓ Usuario 1 registrado - ID: " + idUsuario1);
    } catch (Exception e) {
      System.err.println("   ✗ Error: " + e.getMessage());
    }

    try {
      idUsuario2 = servicioUsuarios.registrarUsuario("maria.lopez." + timestamp + "@email.com", "María",
          "López Martínez", "666333444", "Avenida Central 25", LocalDate.of(1995, 8, 20), "securepass456");
      System.out.println("   ✓ Usuario 2 registrado - ID: " + idUsuario2);
    } catch (Exception e) {
      System.err.println("   ✗ Error: " + e.getMessage());
    }

    try {
      idUsuario3 = servicioUsuarios.registrarUsuario("carlos.gomez." + timestamp + "@email.com", "Carlos", "Gómez Ruiz",
          "+34666777888", "Plaza Mayor 10", LocalDate.of(1988, 3, 10), "mypassword789");
      System.out.println("   ✓ Usuario 3 registrado - ID: " + idUsuario3);
    } catch (Exception e) {
      System.err.println("   ✗ Error: " + e.getMessage());
    }

    // ===== 2. VALIDACIONES DE EMAIL =====
    System.out.println("\n2. Validaciones de EMAIL...");

    try {
      servicioUsuarios.registrarUsuario(null, "Test", "Usuario", "666000000", "Dir", LocalDate.of(1990, 1, 1),
          "password123");
      System.err.println("   ✗ Email nulo: No lanzó excepción");
    } catch (IllegalArgumentException e) {
      System.out.println("   ✓ Email nulo: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("   ✗ Email nulo: Error inesperado");
    }

    try {
      servicioUsuarios.registrarUsuario("", "Test", "Usuario", "666000000", "Dir", LocalDate.of(1990, 1, 1),
          "password123");
      System.err.println("   ✗ Email vacío: No lanzó excepción");
    } catch (IllegalArgumentException e) {
      System.out.println("   ✓ Email vacío: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("   ✗ Email vacío: Error inesperado");
    }

    try {
      servicioUsuarios.registrarUsuario("   ", "Test", "Usuario", "666000000", "Dir", LocalDate.of(1990, 1, 1),
          "password123");
      System.err.println("   ✗ Email espacios: No lanzó excepción");
    } catch (IllegalArgumentException e) {
      System.out.println("   ✓ Email espacios: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("   ✗ Email espacios: Error inesperado");
    }

    try {
      servicioUsuarios.registrarUsuario("emailsinArroba.com", "Test", "Usuario", "666000000", "Dir",
          LocalDate.of(1990, 1, 1), "password123");
      System.err.println("   ✗ Email sin @: No lanzó excepción");
    } catch (IllegalArgumentException e) {
      System.out.println("   ✓ Email sin @: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("   ✗ Email sin @: Error inesperado");
    }

    try {
      servicioUsuarios.registrarUsuario("email@", "Test", "Usuario", "666000000", "Dir", LocalDate.of(1990, 1, 1),
          "password123");
      System.err.println("   ✗ Email sin dominio: No lanzó excepción");
    } catch (IllegalArgumentException e) {
      System.out.println("   ✓ Email sin dominio: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("   ✗ Email sin dominio: Error inesperado");
    }

    try {
      servicioUsuarios.registrarUsuario("email@dominio", "Test", "Usuario", "666000000", "Dir",
          LocalDate.of(1990, 1, 1), "password123");
      System.err.println("   ✗ Email sin extensión: No lanzó excepción");
    } catch (IllegalArgumentException e) {
      System.out.println("   ✓ Email sin extensión: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("   ✗ Email sin extensión: Error inesperado");
    }

    // ===== 3. VALIDACIONES DE NOMBRE =====
    System.out.println("\n3. Validaciones de NOMBRE...");

    try {
      servicioUsuarios.registrarUsuario("test@email.com", null, "Usuario", "666000000", "Dir", LocalDate.of(1990, 1, 1),
          "password123");
      System.err.println("   ✗ Nombre nulo: No lanzó excepción");
    } catch (IllegalArgumentException e) {
      System.out.println("   ✓ Nombre nulo: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("   ✗ Nombre nulo: Error inesperado");
    }

    try {
      servicioUsuarios.registrarUsuario("test@email.com", "", "Usuario", "666000000", "Dir", LocalDate.of(1990, 1, 1),
          "password123");
      System.err.println("   ✗ Nombre vacío: No lanzó excepción");
    } catch (IllegalArgumentException e) {
      System.out.println("   ✓ Nombre vacío: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("   ✗ Nombre vacío: Error inesperado");
    }

    try {
      servicioUsuarios.registrarUsuario("test@email.com", "   ", "Usuario", "666000000", "Dir",
          LocalDate.of(1990, 1, 1), "password123");
      System.err.println("   ✗ Nombre espacios: No lanzó excepción");
    } catch (IllegalArgumentException e) {
      System.out.println("   ✓ Nombre espacios: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("   ✗ Nombre espacios: Error inesperado");
    }

    // ===== 4. VALIDACIONES DE APELLIDOS =====
    System.out.println("\n4. Validaciones de APELLIDOS...");

    try {
      servicioUsuarios.registrarUsuario("test@email.com", "Test", null, "666000000", "Dir", LocalDate.of(1990, 1, 1),
          "password123");
      System.err.println("   ✗ Apellidos nulos: No lanzó excepción");
    } catch (IllegalArgumentException e) {
      System.out.println("   ✓ Apellidos nulos: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("   ✗ Apellidos nulos: Error inesperado");
    }

    try {
      servicioUsuarios.registrarUsuario("test@email.com", "Test", "", "666000000", "Dir", LocalDate.of(1990, 1, 1),
          "password123");
      System.err.println("   ✗ Apellidos vacíos: No lanzó excepción");
    } catch (IllegalArgumentException e) {
      System.out.println("   ✓ Apellidos vacíos: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("   ✗ Apellidos vacíos: Error inesperado");
    }

    try {
      servicioUsuarios.registrarUsuario("test@email.com", "Test", "   ", "666000000", "Dir", LocalDate.of(1990, 1, 1),
          "password123");
      System.err.println("   ✗ Apellidos espacios: No lanzó excepción");
    } catch (IllegalArgumentException e) {
      System.out.println("   ✓ Apellidos espacios: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("   ✗ Apellidos espacios: Error inesperado");
    }

    // ===== 5. VALIDACIONES DE PASSWORD =====
    System.out.println("\n5. Validaciones de PASSWORD...");

    try {
      servicioUsuarios.registrarUsuario("test@email.com", "Test", "Usuario", "666000000", "Dir",
          LocalDate.of(1990, 1, 1), null);
      System.err.println("   ✗ Password nula: No lanzó excepción");
    } catch (IllegalArgumentException e) {
      System.out.println("   ✓ Password nula: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("   ✗ Password nula: Error inesperado");
    }

    try {
      servicioUsuarios.registrarUsuario("test@email.com", "Test", "Usuario", "666000000", "Dir",
          LocalDate.of(1990, 1, 1), "");
      System.err.println("   ✗ Password vacía: No lanzó excepción");
    } catch (IllegalArgumentException e) {
      System.out.println("   ✓ Password vacía: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("   ✗ Password vacía: Error inesperado");
    }

    try {
      servicioUsuarios.registrarUsuario("test@email.com", "Test", "Usuario", "666000000", "Dir",
          LocalDate.of(1990, 1, 1), "   ");
      System.err.println("   ✗ Password espacios: No lanzó excepción");
    } catch (IllegalArgumentException e) {
      System.out.println("   ✓ Password espacios: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("   ✗ Password espacios: Error inesperado");
    }

    try {
      servicioUsuarios.registrarUsuario("test@email.com", "Test", "Usuario", "666000000", "Dir",
          LocalDate.of(1990, 1, 1), "pass123");
      System.err.println("   ✗ Password corta: No lanzó excepción");
    } catch (IllegalArgumentException e) {
      System.out.println("   ✓ Password corta: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("   ✗ Password corta: Error inesperado");
    }

    try {
      servicioUsuarios.registrarUsuario("test@email.com", "Test", "Usuario", "666000000", "Dir",
          LocalDate.of(1990, 1, 1), "1234567");
      System.err.println("   ✗ Password 7 chars: No lanzó excepción");
    } catch (IllegalArgumentException e) {
      System.out.println("   ✓ Password 7 chars: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("   ✗ Password 7 chars: Error inesperado");
    }

    // ===== 6. VALIDACIONES DE FECHA DE NACIMIENTO =====
    System.out.println("\n6. Validaciones de FECHA DE NACIMIENTO...");

    try {
      servicioUsuarios.registrarUsuario("test@email.com", "Test", "Usuario", "666000000", "Dir", null, "password123");
      System.err.println("   ✗ Fecha nula: No lanzó excepción");
    } catch (IllegalArgumentException e) {
      System.out.println("   ✓ Fecha nula: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("   ✗ Fecha nula: Error inesperado");
    }

    try {
      servicioUsuarios.registrarUsuario("test@email.com", "Test", "Usuario", "666000000", "Dir",
          LocalDate.now().plusDays(1), "password123");
      System.err.println("   ✗ Fecha futura: No lanzó excepción");
    } catch (IllegalArgumentException e) {
      System.out.println("   ✓ Fecha futura: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("   ✗ Fecha futura: Error inesperado");
    }

    try {
      servicioUsuarios.registrarUsuario("test@email.com", "Test", "Usuario", "666000000", "Dir",
          LocalDate.of(2050, 1, 1), "password123");
      System.err.println("   ✗ Fecha 2050: No lanzó excepción");
    } catch (IllegalArgumentException e) {
      System.out.println("   ✓ Fecha 2050: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("   ✗ Fecha 2050: Error inesperado");
    }

    try {
      servicioUsuarios.registrarUsuario("test@email.com", "Test", "Usuario", "666000000", "Dir",
          LocalDate.now().minusYears(17), "password123");
      System.err.println("   ✗ 17 años: No lanzó excepción");
    } catch (IllegalArgumentException e) {
      System.out.println("   ✓ 17 años: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("   ✗ 17 años: Error inesperado");
    }

    try {
      servicioUsuarios.registrarUsuario("test@email.com", "Test", "Usuario", "666000000", "Dir",
          LocalDate.now().minusYears(10), "password123");
      System.err.println("   ✗ 10 años: No lanzó excepción");
    } catch (IllegalArgumentException e) {
      System.out.println("   ✓ 10 años: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("   ✗ 10 años: Error inesperado");
    }

    try {
      servicioUsuarios.registrarUsuario("test@email.com", "Test", "Usuario", "666000000", "Dir",
          LocalDate.now().minusYears(18).plusDays(1), "password123");
      System.err.println("   ✗ Casi 18: No lanzó excepción");
    } catch (IllegalArgumentException e) {
      System.out.println("   ✓ Casi 18: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("   ✗ Casi 18: Error inesperado");
    }

    // ===== 7. VALIDACIONES DE TELÉFONO =====
    System.out.println("\n7. Validaciones de TELÉFONO...");

    try {
      servicioUsuarios.registrarUsuario("test@email.com", "Test", "Usuario", null, "Dir", LocalDate.of(1990, 1, 1),
          "password123");
      System.err.println("   ✗ Teléfono nulo: No lanzó excepción");
    } catch (IllegalArgumentException e) {
      System.out.println("   ✓ Teléfono nulo: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("   ✗ Teléfono nulo: Error inesperado");
    }

    try {
      servicioUsuarios.registrarUsuario("test@email.com", "Test", "Usuario", "", "Dir", LocalDate.of(1990, 1, 1),
          "password123");
      System.err.println("   ✗ Teléfono vacío: No lanzó excepción");
    } catch (IllegalArgumentException e) {
      System.out.println("   ✓ Teléfono vacío: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("   ✗ Teléfono vacío: Error inesperado");
    }

    try {
      servicioUsuarios.registrarUsuario("test@email.com", "Test", "Usuario", "   ", "Dir", LocalDate.of(1990, 1, 1),
          "password123");
      System.err.println("   ✗ Teléfono espacios: No lanzó excepción");
    } catch (IllegalArgumentException e) {
      System.out.println("   ✓ Teléfono espacios: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("   ✗ Teléfono espacios: Error inesperado");
    }

    try {
      servicioUsuarios.registrarUsuario("test@email.com", "Test", "Usuario", "666AAA111", "Dir",
          LocalDate.of(1990, 1, 1), "password123");
      System.err.println("   ✗ Teléfono con letras: No lanzó excepción");
    } catch (IllegalArgumentException e) {
      System.out.println("   ✓ Teléfono con letras: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("   ✗ Teléfono con letras: Error inesperado");
    }

    try {
      servicioUsuarios.registrarUsuario("test@email.com", "Test", "Usuario", "12345678", "Dir",
          LocalDate.of(1990, 1, 1), "password123");
      System.err.println("   ✗ Teléfono corto: No lanzó excepción");
    } catch (IllegalArgumentException e) {
      System.out.println("   ✓ Teléfono corto: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("   ✗ Teléfono corto: Error inesperado");
    }

    try {
      servicioUsuarios.registrarUsuario("test@email.com", "Test", "Usuario", "1234567890123456", "Dir",
          LocalDate.of(1990, 1, 1), "password123");
      System.err.println("   ✗ Teléfono largo: No lanzó excepción");
    } catch (IllegalArgumentException e) {
      System.out.println("   ✓ Teléfono largo: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("   ✗ Teléfono largo: Error inesperado");
    }

    try {
      servicioUsuarios.registrarUsuario("test@email.com", "Test", "Usuario", "666-111-222", "Dir",
          LocalDate.of(1990, 1, 1), "password123");
      System.err.println("   ✗ Teléfono con guiones: No lanzó excepción");
    } catch (IllegalArgumentException e) {
      System.out.println("   ✓ Teléfono con guiones: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("   ✗ Teléfono con guiones: Error inesperado");
    }

    // ===== 8. MODIFICACIONES =====
    System.out.println("\n8. Modificaciones de usuarios...");

    if (idUsuario1 != null) {
      try {
        servicioUsuarios.modificarNombre(idUsuario1, "Juan Carlos");
        System.out.println("   ✓ Nombre modificado");
      } catch (Exception e) {
        System.err.println("   ✗ Error al modificar nombre: " + e.getMessage());
      }

      try {
        servicioUsuarios.modificarApellidos(idUsuario1, "Pérez Fernández");
        System.out.println("   ✓ Apellidos modificados");
      } catch (Exception e) {
        System.err.println("   ✗ Error al modificar apellidos: " + e.getMessage());
      }

      try {
        servicioUsuarios.modificarPassword(idUsuario1, "newpass12345");
        System.out.println("   ✓ Password modificada");
      } catch (Exception e) {
        System.err.println("   ✗ Error al modificar password: " + e.getMessage());
      }

      try {
        servicioUsuarios.modificarFechaNacimiento(idUsuario1, LocalDate.of(1991, 6, 20));
        System.out.println("   ✓ Fecha nacimiento modificada");
      } catch (Exception e) {
        System.err.println("   ✗ Error al modificar fecha: " + e.getMessage());
      }

      try {
        servicioUsuarios.modificarTelefono(idUsuario1, "666555777");
        System.out.println("   ✓ Teléfono modificado");
      } catch (Exception e) {
        System.err.println("   ✗ Error al modificar teléfono: " + e.getMessage());
      }
    }

    // ===== 9. MODIFICACIONES CON ID INEXISTENTE =====
    System.out.println("\n9. Modificaciones con ID inexistente...");

    try {
      servicioUsuarios.modificarNombre("ID_INEXISTENTE", "Nombre");
      System.err.println("   ✗ Modificar nombre: No lanzó excepción");
    } catch (RuntimeException e) {
      System.out.println("   ✓ Modificar nombre: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("   ✗ Modificar nombre: Error inesperado");
    }

    try {
      servicioUsuarios.modificarApellidos("ID_INEXISTENTE", "Apellidos");
      System.err.println("   ✗ Modificar apellidos: No lanzó excepción");
    } catch (RuntimeException e) {
      System.out.println("   ✓ Modificar apellidos: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("   ✗ Modificar apellidos: Error inesperado");
    }

    try {
      servicioUsuarios.modificarPassword("ID_INEXISTENTE", "password123");
      System.err.println("   ✗ Modificar password: No lanzó excepción");
    } catch (RuntimeException e) {
      System.out.println("   ✓ Modificar password: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("   ✗ Modificar password: Error inesperado");
    }

    try {
      servicioUsuarios.modificarFechaNacimiento("ID_INEXISTENTE", LocalDate.of(1990, 1, 1));
      System.err.println("   ✗ Modificar fecha: No lanzó excepción");
    } catch (RuntimeException e) {
      System.out.println("   ✓ Modificar fecha: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("   ✗ Modificar fecha: Error inesperado");
    }

    try {
      servicioUsuarios.modificarTelefono("ID_INEXISTENTE", "666111222");
      System.err.println("   ✗ Modificar teléfono: No lanzó excepción");
    } catch (RuntimeException e) {
      System.out.println("   ✓ Modificar teléfono: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("   ✗ Modificar teléfono: Error inesperado");
    }

    System.out.println("\n✓ TODAS LAS PRUEBAS COMPLETADAS\n");
  }

  private static void probarServicioProductos() {
    System.out.println("\n--- PRUEBAS DE SERVICIO PRODUCTOS ---\n");

    try {
      // Obtener datos necesarios para las pruebas
      List<Categoria> categorias = servicioCategorias.obtenerCategoriasRaiz();
      if (categorias.isEmpty()) {
        System.out.println("   ⚠ No hay categorías disponibles, saltando pruebas de productos");
        return;
      }

      String idCategoria = categorias.get(0).getId();

      // Generar timestamp único para vendedores
      long timestamp = System.currentTimeMillis();

      // Crear usuarios para las pruebas
      String idVendedor1 = servicioUsuarios.registrarUsuario(
          "vendedor1." + timestamp + "@email.com", "Vendedor", "Uno", "666000111",
          "Calle Venta 1", LocalDate.of(1985, 1, 1), "pass1");

      String idVendedor2 = servicioUsuarios.registrarUsuario(
          "vendedor2." + timestamp + "@email.com", "Vendedor", "Dos", "666000222",
          "Calle Venta 2", LocalDate.of(1986, 2, 2), "pass2");

      // 1. Dar de alta productos
      System.out.println("1. Dando de alta productos...");

      String idProducto1 = servicioProductos.darDeAltaProducto(
          "Guitarra acústica Yamaha",
          "Guitarra acústica en excelente estado, cuerdas nuevas",
          150.00,
          Estado.COMO_NUEVO,
          idCategoria,
          true,
          idVendedor1);
      System.out.println("   ✓ Producto creado - ID: " + idProducto1);
      System.out.println("     Título: Guitarra acústica Yamaha");

      String idProducto2 = servicioProductos.darDeAltaProducto(
          "Piano digital Casio",
          "Piano digital con 88 teclas, perfecto para principiantes",
          300.00,
          Estado.BUEN_ESTADO,
          idCategoria,
          false,
          idVendedor1);
      System.out.println("   ✓ Producto creado - ID: " + idProducto2);
      System.out.println("     Título: Piano digital Casio");

      String idProducto3 = servicioProductos.darDeAltaProducto(
          "Batería completa",
          "Batería acústica completa con platillos, ideal para empezar",
          450.00,
          Estado.ACEPTABLE,
          idCategoria,
          false,
          idVendedor2);
      System.out.println("   ✓ Producto creado - ID: " + idProducto3);
      System.out.println("     Título: Batería completa\n");

      // 2. Asignar lugar de recogida
      System.out.println("2. Asignando lugar de recogida a productos...");

      LugarRecogida lugar1 = servicioProductos.asignarLugarRecogida(
          idProducto1,
          -3, // longitud
          40, // latitud
          "Centro comercial Plaza Norte, entrada principal");
      System.out.println("   ✓ Lugar asignado a producto " + idProducto1);
      System.out.println("     Ubicación: " + lugar1.getDescripcion());

      LugarRecogida lugar2 = servicioProductos.asignarLugarRecogida(
          idProducto3,
          -3,
          40,
          "Estación de metro Ciudad Universitaria");
      System.out.println("   ✓ Lugar asignado a producto " + idProducto3);
      System.out.println("     Ubicación: " + lugar2.getDescripcion() + "\n");

      // 3. Modificar productos
      System.out.println("3. Modificando datos de productos...");

      servicioProductos.modificarProducto(
          idProducto1,
          Optional.of(140.00), // Nuevo precio
          Optional.of("Guitarra acústica en excelente estado, cuerdas nuevas, con funda incluida"));
      System.out.println("   ✓ Producto modificado - ID: " + idProducto1);
      System.out.println("     Nuevo precio: 140.00€, descripción actualizada");

      servicioProductos.modificarProducto(
          idProducto2,
          Optional.of(280.00), // Solo precio
          Optional.empty());
      System.out.println("   ✓ Producto modificado - ID: " + idProducto2);
      System.out.println("     Nuevo precio: 280.00€\n");

      // 4. Añadir visualizaciones
      System.out.println("4. Añadiendo visualizaciones a productos...");

      for (int i = 0; i < 10; i++) {
        servicioProductos.addVisualizacionProducto(idProducto1);
      }
      System.out.println("   ✓ 10 visualizaciones añadidas a producto " + idProducto1);

      for (int i = 0; i < 5; i++) {
        servicioProductos.addVisualizacionProducto(idProducto2);
      }
      System.out.println("   ✓ 5 visualizaciones añadidas a producto " + idProducto2);

      for (int i = 0; i < 15; i++) {
        servicioProductos.addVisualizacionProducto(idProducto3);
      }
      System.out.println("   ✓ 15 visualizaciones añadidas a producto " + idProducto3 + "\n");

      // 5. Obtener historial del mes
      System.out.println("5. Obteniendo historial del mes actual...");

      int mesActual = java.time.LocalDate.now().getMonthValue();
      int anioActual = java.time.LocalDate.now().getYear();

      List<ResumenProducto> historial = servicioProductos.getHistorialMes(mesActual, anioActual);
      System.out.println("   ✓ Productos del mes " + mesActual + "/" + anioActual + ": " + historial.size());
      System.out.println("   Ordenados por visualizaciones (mayor a menor):");

      for (ResumenProducto resumen : historial) {
        System.out.println("     - " + resumen.getTitulo());
        System.out.println("       ID: " + resumen.getId() +
            ", Precio: " + resumen.getPrecio() + "€" +
            ", Visualizaciones: " + resumen.getNumeroVisualizaciones());
        System.out.println("       Categoría: " + resumen.getNombreCategoria());
        System.out.println("       Fecha: " + resumen.getFechaPublicacion());
      }
      System.out.println();

      // 6. Buscar productos con diferentes filtros
      System.out.println("6. Buscando productos con filtros...");

      // Búsqueda por precio máximo
      List<Producto> resultados1 = servicioProductos.buscarProductos(
          Optional.empty(),
          Optional.empty(),
          Optional.empty(),
          Optional.of(200.00));
      System.out.println("   ✓ Productos con precio <= 200€: " + resultados1.size());
      for (Producto p : resultados1) {
        System.out.println("     - " + p.getTitulo() + " (" + p.getPrecio() + "€)");
      }

      // Búsqueda por texto en descripción
      List<Producto> resultados2 = servicioProductos.buscarProductos(
          Optional.empty(),
          Optional.of("guitarra"),
          Optional.empty(),
          Optional.empty());
      System.out.println("   ✓ Productos con 'guitarra' en descripción: " + resultados2.size());
      for (Producto p : resultados2) {
        System.out.println("     - " + p.getTitulo());
      }

      // Búsqueda por estado (BUEN_ESTADO o mejor)
      List<Producto> resultados3 = servicioProductos.buscarProductos(
          Optional.empty(),
          Optional.empty(),
          Optional.of(Estado.BUEN_ESTADO),
          Optional.empty());
      System.out.println("   ✓ Productos en BUEN_ESTADO o mejor: " + resultados3.size());
      for (Producto p : resultados3) {
        System.out.println("     - " + p.getTitulo() + " (Estado: " + p.getEstado() + ")");
      }

      // Búsqueda combinada
      List<Producto> resultados4 = servicioProductos.buscarProductos(
          Optional.of(idCategoria),
          Optional.empty(),
          Optional.of(Estado.ACEPTABLE),
          Optional.of(500.00));
      System.out.println("   ✓ Productos en categoría " + idCategoria +
          ", estado >= ACEPTABLE, precio <= 500€: " + resultados4.size());
      for (Producto p : resultados4) {
        System.out.println("     - " + p.getTitulo() + " (" + p.getPrecio() + "€, " +
            p.getEstado() + ")");
      }

    } catch (Exception e) {
      System.err.println("   ✗ Error en pruebas de ServicioProductos: " + e.getMessage());
      throw e;
    }
  }
}
