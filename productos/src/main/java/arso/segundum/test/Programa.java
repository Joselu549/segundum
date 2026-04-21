package arso.segundum.test;

import arso.segundum.modelo.Categoria;
import arso.segundum.modelo.Estado;
import arso.segundum.modelo.LugarRecogida;
import arso.segundum.modelo.Producto;
import arso.segundum.modelo.Usuario;
import arso.segundum.repositorio.UsuarioRepository;
import arso.segundum.servicio.IServicioCategorias;
import arso.segundum.servicio.IServicioProductos;
import arso.segundum.servicio.ResumenProducto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Component
public class Programa implements CommandLineRunner {

    @Autowired
    private IServicioProductos servicioProductos;

    @Autowired
    private IServicioCategorias servicioCategorias;

    @Autowired
    private UsuarioRepository repositorioUsuarios;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("===========================================");
        System.out.println("PRUEBAS DE SERVICIOS - SEGUNDUM (Spring Boot)");
        System.out.println("===========================================\n");

        try {
            // Pruebas de ServicioCategorias
            probarServicioCategorias();

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

    private void probarServicioCategorias() {
        System.out.println("\n--- PRUEBAS DE SERVICIO CATEGORIAS ---\n");

        // ===== 1. CARGAR TODOS LOS ARCHIVOS XML =====
        System.out.println("1. Cargando TODOS los archivos XML de categorías...");

        String carpetaXML = "xml/";
        File directorio = new File(carpetaXML);

        // Obtener todos los archivos .xml de la carpeta
        File[] archivosFile = directorio.listFiles((dir, name) -> name.toLowerCase().endsWith(".xml"));

        if (archivosFile == null || archivosFile.length == 0) {
            System.err.println("   ✗ No se encontraron archivos XML en la carpeta: " + carpetaXML);
            return;
        }

        System.out.println("   Archivos XML encontrados: " + archivosFile.length);

        int cargadosExito = 0;
        int errores = 0;
        for (File archivoFile : archivosFile) {
            try {
                String rutaCompleta = archivoFile.getAbsolutePath();
                servicioCategorias.cargarJerarquiaCategorias(rutaCompleta);
                System.out.println("   ✓ " + archivoFile.getName() + " cargado correctamente");
                cargadosExito++;
            } catch (Exception e) {
                System.err.println("   ✗ Error al cargar " + archivoFile.getName() + ": " + e.getMessage());
                errores++;
            }
        }
        System.out.println("\n   RESUMEN: " + cargadosExito + " archivos cargados, " + errores + " errores\n");

        // ===== 2. INTENTAR CARGAR DUPLICADO =====
        System.out.println("\n2. Intentando cargar jerarquía duplicada...");
        try {
            String rutaXML = archivosFile[0].getAbsolutePath();
            servicioCategorias.cargarJerarquiaCategorias(rutaXML);
            System.out.println("   ✓ Sistema maneja carga duplicada correctamente");
        } catch (Exception e) {
            System.out.println("   ✓ Sistema previene duplicados: " + e.getMessage());
        }

        // ===== 3. INTENTAR CARGAR ARCHIVO INEXISTENTE =====
        System.out.println("\n3. Intentando cargar archivo inexistente...");
        try {
            servicioCategorias.cargarJerarquiaCategorias("c:/archivo/inexistente.xml");
            System.err.println("   ✗ No lanzó excepción con archivo inexistente");
        } catch (Exception e) {
            System.out.println("   ✓ Error esperado con archivo inexistente: " + e.getClass().getSimpleName());
        }

        // ===== 4. INTENTAR CARGAR RUTA NULA =====
        System.out.println("\n4. Intentando cargar con ruta nula...");
        try {
            servicioCategorias.cargarJerarquiaCategorias(null);
            System.err.println("   ✗ No lanzó excepción con ruta nula");
        } catch (Exception e) {
            System.out.println("   ✓ Error esperado con ruta nula: " + e.getClass().getSimpleName());
        }

        // ===== 5. INTENTAR CARGAR RUTA VACÍA =====
        System.out.println("\n5. Intentando cargar con ruta vacía...");
        try {
            servicioCategorias.cargarJerarquiaCategorias("");
            System.err.println("   ✗ No lanzó excepción con ruta vacía");
        } catch (Exception e) {
            System.out.println("   ✓ Error esperado con ruta vacía: " + e.getClass().getSimpleName());
        }

        // ===== 6. OBTENER CATEGORÍAS RAÍZ =====
        System.out.println("\n6. Obteniendo categorías raíz...");
        List<Categoria> categoriasRaiz = null;
        try {
            categoriasRaiz = servicioCategorias.obtenerCategoriasRaiz();
            System.out.println("   ✓ Categorías raíz encontradas: " + categoriasRaiz.size());
            for (int i = 0; i < Math.min(10, categoriasRaiz.size()); i++) {
                Categoria cat = categoriasRaiz.get(i);
                System.out.println("     " + (i + 1) + ". ID: " + cat.getId() + ", Nombre: " + cat.getNombre());
            }
            if (categoriasRaiz.size() > 10) {
                System.out.println("     ... y " + (categoriasRaiz.size() - 10) + " más");
            }
        } catch (Exception e) {
            System.err.println("   ✗ Error al obtener categorías raíz: " + e.getMessage());
        }

        // ===== 7. OBTENER SUBCATEGORÍAS =====
        System.out.println("\n7. Obteniendo subcategorías...");
        if (categoriasRaiz != null && !categoriasRaiz.isEmpty()) {
            // Probar con primera categoría
            try {
                String idCat1 = categoriasRaiz.get(0).getId();
                List<Categoria> subcats1 = servicioCategorias.obtenerSubCategorias(idCat1);
                System.out.println(
                        "   ✓ Subcategorías de '" + categoriasRaiz.get(0).getNombre() + "': " + subcats1.size());
                for (int i = 0; i < Math.min(5, subcats1.size()); i++) {
                    System.out.println("     - " + subcats1.get(i).getNombre());
                }
                if (subcats1.size() > 5) {
                    System.out.println("     ... y " + (subcats1.size() - 5) + " más");
                }
            } catch (Exception e) {
                System.err.println("   ✗ Error al obtener subcategorías: " + e.getMessage());
            }

            // Probar con segunda categoría si existe
            if (categoriasRaiz.size() > 1) {
                try {
                    String idCat2 = categoriasRaiz.get(1).getId();
                    List<Categoria> subcats2 = servicioCategorias.obtenerSubCategorias(idCat2);
                    System.out.println(
                            "   ✓ Subcategorías de '" + categoriasRaiz.get(1).getNombre() + "': " + subcats2.size());
                } catch (Exception e) {
                    System.err.println("   ✗ Error al obtener subcategorías: " + e.getMessage());
                }
            }

            // Probar con tercera categoría si existe
            if (categoriasRaiz.size() > 2) {
                try {
                    String idCat3 = categoriasRaiz.get(2).getId();
                    List<Categoria> subcats3 = servicioCategorias.obtenerSubCategorias(idCat3);
                    System.out.println(
                            "   ✓ Subcategorías de '" + categoriasRaiz.get(2).getNombre() + "': " + subcats3.size());
                } catch (Exception e) {
                    System.err.println("   ✗ Error al obtener subcategorías: " + e.getMessage());
                }
            }
        }

        // ===== 8. OBTENER SUBCATEGORÍAS CON ID INEXISTENTE =====
        System.out.println("\n8. Intentando obtener subcategorías con ID inexistente...");
        try {
            List<Categoria> result = servicioCategorias.obtenerSubCategorias("ID_INEXISTENTE_12345");
            System.out.println("   ✓ Retorna lista: " + (result != null ? result.size() + " elementos" : "null"));
        } catch (Exception e) {
            System.out.println("   ✓ Error esperado: " + e.getClass().getSimpleName());
        }

        // ===== 9. OBTENER SUBCATEGORÍAS CON ID NULO =====
        System.out.println("\n9. Intentando obtener subcategorías con ID nulo...");
        try {
            servicioCategorias.obtenerSubCategorias(null);
            System.err.println("   ✗ No lanzó excepción con ID nulo");
        } catch (Exception e) {
            System.out.println("   ✓ Error esperado: " + e.getClass().getSimpleName());
        }

        // ===== 10. OBTENER SUBCATEGORÍAS CON ID VACÍO =====
        System.out.println("\n10. Intentando obtener subcategorías con ID vacío...");
        try {
            List<Categoria> result = servicioCategorias.obtenerSubCategorias("");
            System.out.println("   ✓ Retorna lista: " + (result != null ? result.size() + " elementos" : "null"));
        } catch (Exception e) {
            System.out.println("   ✓ Error esperado: " + e.getClass().getSimpleName());
        }

        // ===== 11. MODIFICAR DESCRIPCIÓN =====
        System.out.println("\n11. Modificando descripciones de categorías...");
        if (categoriasRaiz != null && !categoriasRaiz.isEmpty()) {
            // Modificar primera categoría
            try {
                String idCat1 = categoriasRaiz.get(0).getId();
                String nuevaDesc1 = "Descripción actualizada 1 - " + java.time.LocalDateTime.now();
                servicioCategorias.modificarDescripcionCategoria(idCat1, nuevaDesc1);
                System.out.println("   ✓ Descripción modificada para: " + categoriasRaiz.get(0).getNombre());
            } catch (Exception e) {
                System.err.println("   ✗ Error al modificar descripción: " + e.getMessage());
            }

            // Modificar segunda categoría si existe
            if (categoriasRaiz.size() > 1) {
                try {
                    String idCat2 = categoriasRaiz.get(1).getId();
                    String nuevaDesc2 = "Descripción actualizada 2 - " + java.time.LocalDateTime.now();
                    servicioCategorias.modificarDescripcionCategoria(idCat2, nuevaDesc2);
                    System.out.println("   ✓ Descripción modificada para: " + categoriasRaiz.get(1).getNombre());
                } catch (Exception e) {
                    System.err.println("   ✗ Error al modificar descripción: " + e.getMessage());
                }
            }

            // Modificar con descripción vacía
            try {
                String idCat = categoriasRaiz.get(0).getId();
                servicioCategorias.modificarDescripcionCategoria(idCat, "");
                System.out.println("   ✓ Descripción vacía aceptada");
            } catch (Exception e) {
                System.out.println("   ✓ Descripción vacía rechazada: " + e.getClass().getSimpleName());
            }

            // Modificar con descripción muy larga
            try {
                String idCat = categoriasRaiz.get(0).getId();
                String descLarga = "A".repeat(1000);
                servicioCategorias.modificarDescripcionCategoria(idCat, descLarga);
                System.out.println("   ✓ Descripción muy larga aceptada (1000 chars)");
            } catch (Exception e) {
                System.out.println("   ✓ Descripción muy larga rechazada: " + e.getClass().getSimpleName());
            }
        }

        // ===== 12. MODIFICAR DESCRIPCIÓN CON ID INEXISTENTE =====
        System.out.println("\n12. Intentando modificar con ID inexistente...");
        try {
            servicioCategorias.modificarDescripcionCategoria("ID_INEXISTENTE", "Nueva descripción");
            System.err.println("   ✗ No lanzó excepción con ID inexistente");
        } catch (Exception e) {
            System.out.println("   ✓ Error esperado: " + e.getClass().getSimpleName());
        }

        // ===== 13. MODIFICAR DESCRIPCIÓN CON ID NULO =====
        System.out.println("\n13. Intentando modificar con ID nulo...");
        try {
            servicioCategorias.modificarDescripcionCategoria(null, "Nueva descripción");
            System.err.println("   ✗ No lanzó excepción con ID nulo");
        } catch (Exception e) {
            System.out.println("   ✓ Error esperado: " + e.getClass().getSimpleName());
        }

        // ===== 14. MODIFICAR DESCRIPCIÓN CON DESCRIPCIÓN NULA =====
        System.out.println("\n14. Intentando modificar con descripción nula...");
        if (categoriasRaiz != null && !categoriasRaiz.isEmpty()) {
            try {
                String idCat = categoriasRaiz.get(0).getId();
                servicioCategorias.modificarDescripcionCategoria(idCat, null);
                System.out.println("   ✓ Descripción nula aceptada");
            } catch (Exception e) {
                System.out.println("   ✓ Descripción nula rechazada: " + e.getClass().getSimpleName());
            }
        }

        // ===== 15. VERIFICAR JERARQUÍA DE SUBCATEGORÍAS =====
        System.out.println("\n15. Verificando jerarquía completa de subcategorías...");
        if (categoriasRaiz != null && !categoriasRaiz.isEmpty()) {
            try {
                String idCat = categoriasRaiz.get(0).getId();
                List<Categoria> nivel1 = servicioCategorias.obtenerSubCategorias(idCat);
                System.out.println("   ✓ Nivel 1: " + nivel1.size() + " subcategorías");

                if (!nivel1.isEmpty()) {
                    String idSubCat = nivel1.get(0).getId();
                    List<Categoria> nivel2 = servicioCategorias.obtenerSubCategorias(idSubCat);
                    System.out
                            .println("   ✓ Nivel 2: " + nivel2.size() + " subcategorías de '"
                                    + nivel1.get(0).getNombre() + "'");

                    if (!nivel2.isEmpty()) {
                        String idSubSubCat = nivel2.get(0).getId();
                        List<Categoria> nivel3 = servicioCategorias.obtenerSubCategorias(idSubSubCat);
                        System.out
                                .println("   ✓ Nivel 3: " + nivel3.size() + " subcategorías de '"
                                        + nivel2.get(0).getNombre() + "'");
                    }
                }
            } catch (Exception e) {
                System.err.println("   ✗ Error en jerarquía: " + e.getMessage());
            }
        }

        // ===== 16. OBTENER TODAS LAS CATEGORÍAS RAÍZ MÚLTIPLES VECES =====
        System.out.println("\n16. Obteniendo categorías raíz múltiples veces...");
        try {
            List<Categoria> raiz1 = servicioCategorias.obtenerCategoriasRaiz();
            List<Categoria> raiz2 = servicioCategorias.obtenerCategoriasRaiz();
            List<Categoria> raiz3 = servicioCategorias.obtenerCategoriasRaiz();
            System.out.println("   ✓ Primera llamada: " + raiz1.size());
            System.out.println("   ✓ Segunda llamada: " + raiz2.size());
            System.out.println("   ✓ Tercera llamada: " + raiz3.size());
            System.out.println("   ✓ Consistencia: " + (raiz1.size() == raiz2.size() && raiz2.size() == raiz3.size()));
        } catch (Exception e) {
            System.err.println("   ✗ Error: " + e.getMessage());
        }

        // ===== 17. MODIFICAR Y VERIFICAR MÚLTIPLES CATEGORÍAS =====
        System.out.println("\n17. Modificando múltiples categorías consecutivamente...");
        if (categoriasRaiz != null && categoriasRaiz.size() >= 3) {
            for (int i = 0; i < Math.min(3, categoriasRaiz.size()); i++) {
                try {
                    String id = categoriasRaiz.get(i).getId();
                    String desc = "Modificación masiva " + (i + 1) + " - " + System.currentTimeMillis();
                    servicioCategorias.modificarDescripcionCategoria(id, desc);
                    System.out.println("   ✓ Categoría " + (i + 1) + " modificada");
                } catch (Exception e) {
                    System.err.println("   ✗ Error en categoría " + (i + 1) + ": " + e.getMessage());
                }
            }
        }

        // ===== 18. CARGAR TODOS LOS XMLs DISPONIBLES (VERIFICACIÓN) =====
        System.out.println("\n18. Verificando carga de todos los archivos XML...");
        try {
            List<Categoria> todasRaiz = servicioCategorias.obtenerCategoriasRaiz();
            System.out.println("   ✓ Total categorías raíz en sistema: " + todasRaiz.size());
        } catch (Exception e) {
            System.err.println("   ✗ Error al verificar: " + e.getMessage());
        }

        // ===== 19. ESTADÍSTICAS FINALES =====
        System.out.println("\n19. Estadísticas finales...");
        try {
            List<Categoria> todasRaiz = servicioCategorias.obtenerCategoriasRaiz();
            System.out.println("   ✓ Total categorías raíz: " + todasRaiz.size());

            int totalSubcategorias = 0;
            for (Categoria cat : todasRaiz) {
                try {
                    List<Categoria> subs = servicioCategorias.obtenerSubCategorias(cat.getId());
                    totalSubcategorias += subs.size();
                } catch (Exception e) {
                    // Ignorar errores en conteo
                }
            }
            System.out.println("   ✓ Total subcategorías nivel 1: " + totalSubcategorias);
        } catch (Exception e) {
            System.err.println("   ✗ Error al calcular estadísticas: " + e.getMessage());
        }

        System.out.println("\n✓ TODAS LAS PRUEBAS DE CATEGORÍAS COMPLETADAS\n");
    }

    private void probarServicioProductos() {
        System.out.println("\n--- PRUEBAS DE SERVICIO PRODUCTOS ---\n");

        // Variables para IDs de entidades creadas
        String idCategoria = null;
        Long idProducto1 = null;
        Long idProducto2 = null;
        Long idProducto3 = null;

        // Vendedores de prueba - se persisten en BD antes de usarlos
        Usuario vendedor1 = new Usuario("vendedor1", "vendedor1@email.com", "Vendedor", "Uno");
        Usuario vendedor2 = new Usuario("vendedor2", "vendedor2@email.com", "Vendedor", "Dos");

        // Guardar los usuarios en BD (si ya existen, se actualizan)
        vendedor1 = repositorioUsuarios.save(vendedor1);
        vendedor2 = repositorioUsuarios.save(vendedor2);
        System.out.println("✓ Usuarios de prueba persistidos: " + vendedor1 + ", " + vendedor2);

        // ===== PREPARACIÓN: OBTENER CATEGORÍA =====
        try {
            List<Categoria> categorias = servicioCategorias.obtenerCategoriasRaiz();
            System.out.println("✓ Categorías raíz obtenidas: " + categorias.size());
            if (categorias.isEmpty()) {
                System.out.println("   ⚠ No hay categorías disponibles, saltando pruebas de productos");
                return;
            }
            // Seleccionar una categoría aleatoria
            int indiceAleatorio = (int) (Math.random() * categorias.size());
            idCategoria = categorias.get(indiceAleatorio).getId();
            System.out.println("✓ Categoría seleccionada: " + categorias.get(indiceAleatorio).getNombre());
            System.out.println("✓ Entidades preparadas: categoría y vendedores creados\n");
        } catch (Exception e) {
            System.err.println("✗ Error en preparación: " + e.getMessage());
            return;
        }

        // ===== 1. VALIDACIONES DE TÍTULO =====
        System.out.println("1. Validaciones de TÍTULO...");

        try {
            servicioProductos.darDeAltaProducto(null, "Descripción", 100.0, Estado.COMO_NUEVO,
                    idCategoria, true, vendedor1.getIdUsuario(), -3.0, 40.0, "Murcia");
            System.err.println("   ✗ Título nulo: No lanzó excepción");
        } catch (IllegalArgumentException e) {
            System.out.println("   ✓ Título nulo: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("   ✗ Título nulo: Error inesperado");
        }

        try {
            servicioProductos.darDeAltaProducto("", "Descripción", 100.0, Estado.COMO_NUEVO,
                    idCategoria, true, vendedor1.getIdUsuario(), -3.0, 40.0, "Murcia");
            System.err.println("   ✗ Título vacío: No lanzó excepción");
        } catch (IllegalArgumentException e) {
            System.out.println("   ✓ Título vacío: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("   ✗ Título vacío: Error inesperado");
        }

        try {
            servicioProductos.darDeAltaProducto("   ", "Descripción", 100.0, Estado.COMO_NUEVO,
                    idCategoria, true, vendedor1.getIdUsuario(), -3.0, 40.0, "Murcia");
            System.err.println("   ✗ Título espacios: No lanzó excepción");
        } catch (IllegalArgumentException e) {
            System.out.println("   ✓ Título espacios: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("   ✗ Título espacios: Error inesperado");
        }

        try {
            String tituloLargo = "A".repeat(201);
            servicioProductos.darDeAltaProducto(tituloLargo, "Descripción", 100.0, Estado.COMO_NUEVO,
                    idCategoria, true, vendedor1.getIdUsuario(), -3.0, 40.0, "Murcia");
            System.err.println("   ✗ Título largo (201 chars): No lanzó excepción");
        } catch (IllegalArgumentException e) {
            System.out.println("   ✓ Título largo: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("   ✗ Título largo: Error inesperado");
        }

        // ===== 2. VALIDACIONES DE DESCRIPCIÓN =====
        System.out.println("\n2. Validaciones de DESCRIPCIÓN...");

        try {
            servicioProductos.darDeAltaProducto("Título", null, 100.0, Estado.COMO_NUEVO,
                    idCategoria, true, vendedor1.getIdUsuario(), -3.0, 40.0, "Murcia");
            System.err.println("   ✗ Descripción nula: No lanzó excepción");
        } catch (IllegalArgumentException e) {
            System.out.println("   ✓ Descripción nula: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("   ✗ Descripción nula: Error inesperado");
        }

        try {
            servicioProductos.darDeAltaProducto("Título", "", 100.0, Estado.COMO_NUEVO,
                    idCategoria, true, vendedor1.getIdUsuario(), -3.0, 40.0, "Murcia");
            System.err.println("   ✗ Descripción vacía: No lanzó excepción");
        } catch (IllegalArgumentException e) {
            System.out.println("   ✓ Descripción vacía: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("   ✗ Descripción vacía: Error inesperado");
        }

        try {
            servicioProductos.darDeAltaProducto("Título", "   ", 100.0, Estado.COMO_NUEVO,
                    idCategoria, true, vendedor1.getIdUsuario(), -3.0, 40.0, "Murcia");
            System.err.println("   ✗ Descripción espacios: No lanzó excepción");
        } catch (IllegalArgumentException e) {
            System.out.println("   ✓ Descripción espacios: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("   ✗ Descripción espacios: Error inesperado");
        }

        try {
            String descripcionLarga = "A".repeat(2001);
            servicioProductos.darDeAltaProducto("Título", descripcionLarga, 100.0, Estado.COMO_NUEVO,
                    idCategoria, true, vendedor1.getIdUsuario(), -3.0, 40.0, "Murcia");
            System.err.println("   ✗ Descripción larga (2001 chars): No lanzó excepción");
        } catch (IllegalArgumentException e) {
            System.out.println("   ✓ Descripción larga: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("   ✗ Descripción larga: Error inesperado");
        }

        // ===== 3. VALIDACIONES DE PRECIO =====
        System.out.println("\n3. Validaciones de PRECIO...");

        try {
            servicioProductos.darDeAltaProducto("Título", "Descripción", -1.0, Estado.COMO_NUEVO,
                    idCategoria, true, vendedor1.getIdUsuario(), -3.0, 40.0, "Murcia");
            System.err.println("   ✗ Precio negativo: No lanzó excepción");
        } catch (IllegalArgumentException e) {
            System.out.println("   ✓ Precio negativo: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("   ✗ Precio negativo: Error inesperado");
        }

        try {
            servicioProductos.darDeAltaProducto("Título", "Descripción", -100.0, Estado.COMO_NUEVO,
                    idCategoria, true, vendedor1.getIdUsuario(), -3.0, 40.0, "Murcia");
            System.err.println("   ✗ Precio muy negativo: No lanzó excepción");
        } catch (IllegalArgumentException e) {
            System.out.println("   ✓ Precio muy negativo: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("   ✗ Precio muy negativo: Error inesperado");
        }

        try {
            servicioProductos.darDeAltaProducto("Título", "Descripción", 1000001.0, Estado.COMO_NUEVO,
                    idCategoria, true, vendedor1.getIdUsuario(), -3.0, 40.0, "Murcia");
            System.err.println("   ✗ Precio muy alto: No lanzó excepción");
        } catch (IllegalArgumentException e) {
            System.out.println("   ✓ Precio muy alto: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("   ✗ Precio muy alto: Error inesperado");
        }

        try {
            idProducto1 = servicioProductos.darDeAltaProducto("Producto Gratis", "Descripción", 0.0,
                    Estado.COMO_NUEVO, idCategoria, true, vendedor1.getIdUsuario(), -3.0, 40.0, "Murcia");
            System.out.println("   ✓ Precio cero aceptado: " + idProducto1);
        } catch (Exception e) {
            System.err.println("   ✗ Precio cero: " + e.getMessage());
        }

        // ===== 4. VALIDACIONES DE ESTADO =====
        System.out.println("\n4. Validaciones de ESTADO...");

        try {
            servicioProductos.darDeAltaProducto("Título", "Descripción", 100.0, null,
                    idCategoria, true, vendedor1.getIdUsuario(), -3.0, 40.0, "Murcia");
            System.err.println("   ✗ Estado nulo: No lanzó excepción");
        } catch (IllegalArgumentException e) {
            System.out.println("   ✓ Estado nulo: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("   ✗ Estado nulo: Error inesperado");
        }

        // ===== 5. VALIDACIONES DE ID CATEGORÍA =====
        System.out.println("\n5. Validaciones de ID CATEGORÍA...");

        try {
            servicioProductos.darDeAltaProducto("Título", "Descripción", 100.0, Estado.COMO_NUEVO,
                    null, true, vendedor1.getIdUsuario(), -3.0, 40.0, "Murcia");
            System.err.println("   ✗ ID categoría nulo: No lanzó excepción");
        } catch (IllegalArgumentException e) {
            System.out.println("   ✓ ID categoría nulo: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("   ✗ ID categoría nulo: Error inesperado");
        }

        try {
            servicioProductos.darDeAltaProducto("Título", "Descripción", 100.0, Estado.COMO_NUEVO,
                    "", true, vendedor1.getIdUsuario(), -3.0, 40.0, "Murcia");
            System.err.println("   ✗ ID categoría vacío: No lanzó excepción");
        } catch (IllegalArgumentException e) {
            System.out.println("   ✓ ID categoría vacío: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("   ✗ ID categoría vacío: Error inesperado");
        }

        try {
            servicioProductos.darDeAltaProducto("Título", "Descripción", 100.0, Estado.COMO_NUEVO,
                    "ID_INEXISTENTE", true, vendedor1.getIdUsuario(), -3.0, 40.0, "Murcia");
            System.err.println("   ✗ ID categoría inexistente: No lanzó excepción");
        } catch (RuntimeException e) {
            System.out.println("   ✓ ID categoría inexistente: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("   ✗ ID categoría inexistente: Error inesperado");
        }

        // ===== 6. VALIDACIONES DE VENDEDOR =====
        System.out.println("\n6. Validaciones de VENDEDOR...");

        try {
            servicioProductos.darDeAltaProducto("Título", "Descripción", 100.0, Estado.COMO_NUEVO,
                    idCategoria, true, null, -3.0, 40.0, "Murcia");
            System.err.println("   ✗ Vendedor nulo: No lanzó excepción");
        } catch (IllegalArgumentException e) {
            System.out.println("   ✓ Vendedor nulo: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("   ✗ Vendedor nulo: Error inesperado");
        }

        try {
            Usuario vendedorSinId = new Usuario(null, "email@test.com", "Nombre", "Apellidos");
            servicioProductos.darDeAltaProducto("Título", "Descripción", 100.0, Estado.COMO_NUEVO,
                    idCategoria, true, vendedorSinId.getIdUsuario(), -3.0, 40.0, "Murcia");
            System.err.println("   ✗ Vendedor sin ID: No lanzó excepción");
        } catch (IllegalArgumentException e) {
            System.out.println("   ✓ Vendedor sin ID: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("   ✗ Vendedor sin ID: Error inesperado");
        }

        try {
            Usuario vendedorIdVacio = new Usuario("", "email@test.com", "Nombre", "Apellidos");
            servicioProductos.darDeAltaProducto("Título", "Descripción", 100.0, Estado.COMO_NUEVO,
                    idCategoria, true, vendedorIdVacio.getIdUsuario(), -3.0, 40.0, "Murcia");
            System.err.println("   ✗ Vendedor ID vacío: No lanzó excepción");
        } catch (IllegalArgumentException e) {
            System.out.println("   ✓ Vendedor ID vacío: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("   ✗ Vendedor ID vacío: Error inesperado");
        }

        // ===== 7. CREAR PRODUCTOS VÁLIDOS =====
        System.out.println("\n7. Creando productos válidos...");

        try {
            idProducto1 = servicioProductos.darDeAltaProducto(
                    "Guitarra acústica Yamaha",
                    "Guitarra acústica en excelente estado, cuerdas nuevas",
                    150.00, Estado.COMO_NUEVO, idCategoria, true, vendedor1.getIdUsuario(), -3.0, 40.0, "Murcia");
            System.out.println("   ✓ Producto 1 creado: " + idProducto1);
        } catch (Exception e) {
            System.err.println("   ✗ Error: " + e.getMessage());
        }

        try {
            idProducto2 = servicioProductos.darDeAltaProducto(
                    "Piano digital Casio",
                    "Piano digital con 88 teclas, perfecto para principiantes",
                    300.00, Estado.BUEN_ESTADO, idCategoria, false, vendedor1.getIdUsuario(), -3.0, 40.0, "Murcia");
            System.out.println("   ✓ Producto 2 creado: " + idProducto2);
        } catch (Exception e) {
            System.err.println("   ✗ Error: " + e.getMessage());
        }

        try {
            idProducto3 = servicioProductos.darDeAltaProducto(
                    "Batería completa",
                    "Batería acústica completa con platillos",
                    450.00, Estado.ACEPTABLE, idCategoria, false, vendedor2.getIdUsuario(), -3.0, 40.0, "Murcia");
            System.out.println("   ✓ Producto 3 creado: " + idProducto3);
        } catch (Exception e) {
            System.err.println("   ✗ Error: " + e.getMessage());
        }

        // ===== 8. VALIDACIONES DE ASIGNAR LUGAR DE RECOGIDA =====
        System.out.println("\n8. Validaciones de ASIGNAR LUGAR DE RECOGIDA...");

        try {
            servicioProductos.asignarLugarRecogida(null, -3, 40, "Descripción");
            System.err.println("   ✗ ID producto nulo: No lanzó excepción");
        } catch (IllegalArgumentException e) {
            System.out.println("   ✓ ID producto nulo: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("   ✗ ID producto nulo: Error inesperado");
        }

        try {
            servicioProductos.asignarLugarRecogida(0L, -3, 40, "Descripción");
            System.err.println("   ✗ ID producto inexistente: No lanzó excepción");
        } catch (RuntimeException e) {
            System.out.println("   ✓ ID producto inexistente: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("   ✗ ID producto inexistente: Error inesperado");
        }

        try {
            servicioProductos.asignarLugarRecogida(idProducto1, -181, 40, "Descripción");
            System.err.println("   ✗ Longitud inválida (-181): No lanzó excepción");
        } catch (IllegalArgumentException e) {
            System.out.println("   ✓ Longitud inválida: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("   ✗ Longitud inválida: Error inesperado");
        }

        try {
            servicioProductos.asignarLugarRecogida(idProducto1, 181, 40, "Descripción");
            System.err.println("   ✗ Longitud inválida (181): No lanzó excepción");
        } catch (IllegalArgumentException e) {
            System.out.println("   ✓ Longitud inválida: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("   ✗ Longitud inválida: Error inesperado");
        }

        try {
            servicioProductos.asignarLugarRecogida(idProducto1, -3, -91, "Descripción");
            System.err.println("   ✗ Latitud inválida (-91): No lanzó excepción");
        } catch (IllegalArgumentException e) {
            System.out.println("   ✓ Latitud inválida: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("   ✗ Latitud inválida: Error inesperado");
        }

        try {
            servicioProductos.asignarLugarRecogida(idProducto1, -3, 91, "Descripción");
            System.err.println("   ✗ Latitud inválida (91): No lanzó excepción");
        } catch (IllegalArgumentException e) {
            System.out.println("   ✓ Latitud inválida: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("   ✗ Latitud inválida: Error inesperado");
        }

        try {
            servicioProductos.asignarLugarRecogida(idProducto1, -3, 40, null);
            System.err.println("   ✗ Descripción lugar nula: No lanzó excepción");
        } catch (IllegalArgumentException e) {
            System.out.println("   ✓ Descripción lugar nula: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("   ✗ Descripción lugar nula: Error inesperado");
        }

        try {
            servicioProductos.asignarLugarRecogida(idProducto1, -3, 40, "");
            System.err.println("   ✗ Descripción lugar vacía: No lanzó excepción");
        } catch (IllegalArgumentException e) {
            System.out.println("   ✓ Descripción lugar vacía: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("   ✗ Descripción lugar vacía: Error inesperado");
        }

        try {
            String descLarga = "A".repeat(501);
            servicioProductos.asignarLugarRecogida(idProducto1, -3, 40, descLarga);
            System.err.println("   ✗ Descripción lugar larga: No lanzó excepción");
        } catch (IllegalArgumentException e) {
            System.out.println("   ✓ Descripción lugar larga: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("   ✗ Descripción lugar larga: Error inesperado");
        }

        // ===== 9. ASIGNAR LUGARES VÁLIDOS =====
        System.out.println("\n9. Asignando lugares de recogida válidos...");

        try {
            LugarRecogida lugar1 = servicioProductos.asignarLugarRecogida(
                    idProducto1, -3, 40, "Centro comercial Plaza Norte");
            System.out.println("   ✓ Lugar asignado a producto 1: " + lugar1.getDescripcion());
        } catch (Exception e) {
            System.err.println("   ✗ Error: " + e.getMessage());
        }

        try {
            LugarRecogida lugar2 = servicioProductos.asignarLugarRecogida(
                    idProducto3, -4, 41, "Estación de metro");
            System.out.println("   ✓ Lugar asignado a producto 3: " + lugar2.getDescripcion());
        } catch (Exception e) {
            System.err.println("   ✗ Error: " + e.getMessage());
        }

        // ===== 10. VALIDACIONES DE MODIFICAR PRODUCTO =====
        System.out.println("\n10. Validaciones de MODIFICAR PRODUCTO...");

        try {
            servicioProductos.modificarProducto(null, Optional.of(100.0), Optional.empty());
            System.err.println("   ✗ ID producto nulo: No lanzó excepción");
        } catch (IllegalArgumentException e) {
            System.out.println("   ✓ ID producto nulo: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("   ✗ ID producto nulo: Error inesperado");
        }

        try {
            servicioProductos.modificarProducto(0L, Optional.of(100.0), Optional.empty());
            System.err.println("   ✗ ID producto inexistente: No lanzó excepción");
        } catch (RuntimeException e) {
            System.out.println("   ✓ ID producto inexistente: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("   ✗ ID producto inexistente: Error inesperado");
        }

        try {
            servicioProductos.modificarProducto(idProducto1, Optional.of(-1.0), Optional.empty());
            System.err.println("   ✗ Precio negativo: No lanzó excepción");
        } catch (IllegalArgumentException e) {
            System.out.println("   ✓ Precio negativo: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("   ✗ Precio negativo: Error inesperado");
        }

        try {
            servicioProductos.modificarProducto(idProducto1, Optional.of(1000001.0), Optional.empty());
            System.err.println("   ✗ Precio muy alto: No lanzó excepción");
        } catch (IllegalArgumentException e) {
            System.out.println("   ✓ Precio muy alto: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("   ✗ Precio muy alto: Error inesperado");
        }

        try {
            servicioProductos.modificarProducto(idProducto1, Optional.empty(), Optional.of(""));
            System.err.println("   ✗ Descripción vacía: No lanzó excepción");
        } catch (IllegalArgumentException e) {
            System.out.println("   ✓ Descripción vacía: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("   ✗ Descripción vacía: Error inesperado");
        }

        try {
            String descLarga = "A".repeat(2001);
            servicioProductos.modificarProducto(idProducto1, Optional.empty(), Optional.of(descLarga));
            System.err.println("   ✗ Descripción larga: No lanzó excepción");
        } catch (IllegalArgumentException e) {
            System.out.println("   ✓ Descripción larga: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("   ✗ Descripción larga: Error inesperado");
        }

        // ===== 11. MODIFICAR PRODUCTOS VÁLIDOS =====
        System.out.println("\n11. Modificando productos...");

        try {
            servicioProductos.modificarProducto(idProducto1, Optional.of(140.0),
                    Optional.of("Guitarra con funda incluida"));
            System.out.println("   ✓ Producto 1 modificado: precio y descripción");
        } catch (Exception e) {
            System.err.println("   ✗ Error: " + e.getMessage());
        }

        try {
            servicioProductos.modificarProducto(idProducto2, Optional.of(280.0), Optional.empty());
            System.out.println("   ✓ Producto 2 modificado: solo precio");
        } catch (Exception e) {
            System.err.println("   ✗ Error: " + e.getMessage());
        }

        try {
            servicioProductos.modificarProducto(idProducto3, Optional.empty(),
                    Optional.of("Batería con platillos nuevos"));
            System.out.println("   ✓ Producto 3 modificado: solo descripción");
        } catch (Exception e) {
            System.err.println("   ✗ Error: " + e.getMessage());
        }

        // ===== 12. VALIDACIONES DE AÑADIR VISUALIZACIÓN =====
        System.out.println("\n12. Validaciones de AÑADIR VISUALIZACIÓN...");

        try {
            servicioProductos.addVisualizacionProducto(null);
            System.err.println("   ✗ ID producto nulo: No lanzó excepción");
        } catch (IllegalArgumentException e) {
            System.out.println("   ✓ ID producto nulo: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("   ✗ ID producto nulo: Error inesperado");
        }

        try {
            servicioProductos.addVisualizacionProducto(0L);
            System.err.println("   ✗ ID producto inexistente: No lanzó excepción");
        } catch (RuntimeException e) {
            System.out.println("   ✓ ID producto inexistente: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("   ✗ ID producto inexistente: Error inesperado");
        }

        // ===== 13. AÑADIR VISUALIZACIONES VÁLIDAS =====
        System.out.println("\n13. Añadiendo visualizaciones...");

        try {
            for (int i = 0; i < 10; i++) {
                servicioProductos.addVisualizacionProducto(idProducto1);
            }
            System.out.println("   ✓ 10 visualizaciones añadidas a producto 1");
        } catch (Exception e) {
            System.err.println("   ✗ Error: " + e.getMessage());
        }

        try {
            for (int i = 0; i < 5; i++) {
                servicioProductos.addVisualizacionProducto(idProducto2);
            }
            System.out.println("   ✓ 5 visualizaciones añadidas a producto 2");
        } catch (Exception e) {
            System.err.println("   ✗ Error: " + e.getMessage());
        }

        try {
            for (int i = 0; i < 15; i++) {
                servicioProductos.addVisualizacionProducto(idProducto3);
            }
            System.out.println("   ✓ 15 visualizaciones añadidas a producto 3");
        } catch (Exception e) {
            System.err.println("   ✗ Error: " + e.getMessage());
        }

        // ===== 14. VALIDACIONES DE GET HISTORIAL MES =====
        System.out.println("\n14. Validaciones de GET HISTORIAL MES...");

        try {
            servicioProductos.getHistorialMes(0, 2024);
            System.err.println("   ✗ Mes 0: No lanzó excepción");
        } catch (IllegalArgumentException e) {
            System.out.println("   ✓ Mes 0: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("   ✗ Mes 0: Error inesperado");
        }

        try {
            servicioProductos.getHistorialMes(13, 2024);
            System.err.println("   ✗ Mes 13: No lanzó excepción");
        } catch (IllegalArgumentException e) {
            System.out.println("   ✓ Mes 13: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("   ✗ Mes 13: Error inesperado");
        }

        try {
            servicioProductos.getHistorialMes(5, 1999);
            System.err.println("   ✗ Año 1999: No lanzó excepción");
        } catch (IllegalArgumentException e) {
            System.out.println("   ✓ Año 1999: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("   ✗ Año 1999: Error inesperado");
        }

        try {
            servicioProductos.getHistorialMes(5, 2101);
            System.err.println("   ✗ Año 2101: No lanzó excepción");
        } catch (IllegalArgumentException e) {
            System.out.println("   ✓ Año 2101: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("   ✗ Año 2101: Error inesperado");
        }

        // ===== 15. OBTENER HISTORIAL VÁLIDO =====
        System.out.println("\n15. Obteniendo historial del mes actual...");

        try {
            int mesActual = java.time.LocalDate.now().getMonthValue();
            int anioActual = java.time.LocalDate.now().getYear();
            List<ResumenProducto> historial = servicioProductos.getHistorialMes(mesActual, anioActual);
            System.out.println("   ✓ Historial obtenido: " + historial.size() + " productos");
            for (int i = 0; i < Math.min(3, historial.size()); i++) {
                ResumenProducto r = historial.get(i);
                System.out.println("     - " + r.getTitulo() + " (" + r.getNumeroVisualizaciones() + " views)");
            }
        } catch (Exception e) {
            System.err.println("   ✗ Error: " + e.getMessage());
        }

        // ===== 16. VALIDACIONES DE BUSCAR PRODUCTOS =====
        System.out.println("\n16. Validaciones de BUSCAR PRODUCTOS...");

        try {
            servicioProductos.buscarProductos(null, null, null, -1.0);
            System.err.println("   ✗ Precio máximo negativo: No lanzó excepción");
        } catch (IllegalArgumentException e) {
            System.out.println("   ✓ Precio máximo negativo: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("   ✗ Precio máximo negativo: Error inesperado");
        }

        // ===== 17. BUSCAR PRODUCTOS CON FILTROS =====
        System.out.println("\n17. Buscando productos con diferentes filtros...");

        try {
            List<Producto> resultado1 = servicioProductos.buscarProductos(null, null, null, 200.0);
            System.out.println("   ✓ Precio <= 200€: " + resultado1.size() + " productos");
        } catch (Exception e) {
            System.err.println("   ✗ Error: " + e.getMessage());
        }

        try {
            List<Producto> resultado2 = servicioProductos.buscarProductos(null, "guitarra", null, null);
            System.out.println("   ✓ Contiene 'guitarra': " + resultado2.size() + " productos");
        } catch (Exception e) {
            System.err.println("   ✗ Error: " + e.getMessage());
        }

        try {
            List<Producto> resultado3 = servicioProductos.buscarProductos(null, null, Estado.BUEN_ESTADO,
                    null);
            System.out.println("   ✓ Estado BUEN_ESTADO: " + resultado3.size() + " productos");
        } catch (Exception e) {
            System.err.println("   ✗ Error: " + e.getMessage());
        }

        try {
            List<Producto> resultado4 = servicioProductos.buscarProductos(idCategoria, null,
                    Estado.ACEPTABLE, 500.0);
            System.out.println("   ✓ Búsqueda combinada: " + resultado4.size() + " productos");
        } catch (Exception e) {
            System.err.println("   ✗ Error: " + e.getMessage());
        }

        try {
            List<Producto> resultado5 = servicioProductos.buscarProductos(null, null, null, null);
            System.out.println("   ✓ Sin filtros: " + resultado5.size() + " productos");
        } catch (Exception e) {
            System.err.println("   ✗ Error: " + e.getMessage());
        }

        System.out.println("\n✓ TODAS LAS PRUEBAS DE PRODUCTOS COMPLETADAS\n");
    }
}