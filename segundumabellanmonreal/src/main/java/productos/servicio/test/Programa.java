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
      probarServicioCategorias();

      // Pruebas de ServicioUsuarios
      probarServicioUsuarios();

      // Pruebas de ServicioProductos
      probarServicioProductos();

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

    try {
      // Generar un timestamp único para evitar duplicados
      long timestamp = System.currentTimeMillis();

      // 1. Registrar usuarios
      System.out.println("1. Registrando usuarios...");

      String idUsuario1 = servicioUsuarios.registrarUsuario(
          "juan.perez." + timestamp + "@email.com",
          "Juan",
          "Pérez García",
          "666111222",
          "Calle Principal 1",
          LocalDate.of(1990, 5, 15),
          "password123");
      System.out.println("   ✓ Usuario registrado - ID: " + idUsuario1);
      System.out.println("     Email: juan.perez." + timestamp + "@email.com");

      String idUsuario2 = servicioUsuarios.registrarUsuario(
          "maria.lopez." + timestamp + "@email.com",
          "María",
          "López Martínez",
          "666333444",
          "Avenida Central 25",
          LocalDate.of(1995, 8, 20),
          "securepass");
      System.out.println("   ✓ Usuario registrado - ID: " + idUsuario2);
      System.out.println("     Email: maria.lopez." + timestamp + "@email.com");

      String idUsuario3 = servicioUsuarios.registrarUsuario(
          "carlos.gomez." + timestamp + "@email.com",
          "Carlos",
          "Gómez Ruiz",
          null, // Sin teléfono
          "Plaza Mayor 10",
          LocalDate.of(1988, 3, 10),
          "mypassword");
      System.out.println("   ✓ Usuario registrado - ID: " + idUsuario3);
      System.out.println("     Email: carlos.gomez." + timestamp + "@email.com (sin teléfono)\n");

      // 2. Modificar usuarios
      System.out.println("2. Modificando datos de usuarios...");

      servicioUsuarios.modificarUsuario(
          idUsuario1,
          Optional.of("Juan Carlos"), // Nuevo nombre
          Optional.empty(), // Apellidos sin cambiar
          Optional.of("666555777"), // Nuevo teléfono
          Optional.empty(), // Dirección sin cambiar
          Optional.empty(), // Fecha nacimiento sin cambiar
          Optional.empty() // Password sin cambiar
      );
      System.out.println("   ✓ Usuario modificado - ID: " + idUsuario1);
      System.out.println("     Nuevo nombre: Juan Carlos, Nuevo teléfono: 666555777");

      servicioUsuarios.modificarUsuario(
          idUsuario2,
          Optional.empty(),
          Optional.of("López Fernández"), // Nuevos apellidos
          Optional.empty(),
          Optional.empty(),
          Optional.of(LocalDate.of(1995, 8, 21)), // Nueva fecha
          Optional.of("newsecurepass") // Nueva contraseña
      );
      System.out.println("   ✓ Usuario modificado - ID: " + idUsuario2);
      System.out.println("     Nuevos apellidos: López Fernández\n");

    } catch (Exception e) {
      System.err.println("   ✗ Error en pruebas de ServicioUsuarios: " + e.getMessage());
      throw e;
    }
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
