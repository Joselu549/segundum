package arso.segundum.rest;

import arso.segundum.dto.ErrorDTO;
import arso.segundum.dto.LugarRecogidaDTO;
import arso.segundum.dto.ProductoDTO;
import arso.segundum.modelo.Estado;
import arso.segundum.servicio.ResumenProducto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Tag(name = "Productos", description = "Gestión de productos")
@RequestMapping("/products")
public interface ProductosApi {

    @Operation(summary = "Obtener productos", description = "Obtiene todos los productos filtrando por propiedades")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente"),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    })
    @GetMapping
    ResponseEntity<List<ProductoDTO>> getProductos(
            @RequestParam(required = false) String idCategoria,
            @RequestParam(required = false) String descripcion,
            @RequestParam(required = false) Estado estado,
            @RequestParam(required = false) @PositiveOrZero Double precio) throws Exception;

    @Operation(summary = "Obtener productos paginado", description = "Obtiene productos de forma paginada")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Página obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Parámetros de paginación inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    })
    @GetMapping("/pages")
    PagedModel<EntityModel<ResumenProducto>> getProductosPaginado(
            @RequestParam @PositiveOrZero int page,
            @RequestParam @Positive @Max(100) int size) throws Exception;

    @Operation(summary = "Obtener producto por id", description = "Obtiene un producto por id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "400", description = "ID inválido",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    })
    @GetMapping("/{id}")
    EntityModel<ProductoDTO> getProductosById(@PathVariable @Positive Long id) throws Exception;

    @Operation(summary = "Obtener historial", description = "Obtiene el historial de todos los productos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Historial obtenido correctamente"),
            @ApiResponse(responseCode = "400", description = "Mes o año fuera de rango",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    })
    @GetMapping("/historial")
    ResponseEntity<List<ResumenProducto>> getHistorialProductos(
            @RequestParam(required = false) @Min(1) @Max(12) Integer mes,
            @RequestParam(required = false) @Min(2000) @Max(2100) Integer anio) throws Exception;

    @Operation(summary = "Añade una visualización", description = "Añade una visualización a un producto dado un ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Visualización añadida correctamente"),
            @ApiResponse(responseCode = "400", description = "ID inválido",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    })
    @PatchMapping("/{id}/visualizations")
    ResponseEntity<Void> addVisualizaciones(@PathVariable @Positive Long id) throws Exception;

    @Operation(summary = "Da de alta un producto", description = "Da de alta un producto dadas unas propiedades. El idLugarRecogida debe corresponder a un lugar ya existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Producto creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos del producto inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "404", description = "Lugar de recogida no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    })
    @PostMapping
    ResponseEntity<Void> darDeAltaProducto(@RequestBody @Valid ProductoDTO nuevoProducto) throws Exception;

    @Operation(summary = "Crea un lugar de recogida", description = "Crea un lugar de recogida y devuelve su ubicación")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Lugar de recogida creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos del lugar inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    })
    @PostMapping("/lugares-recogida")
    ResponseEntity<Void> crearLugarRecogida(@RequestBody @Valid LugarRecogidaDTO lugarRecogidaDTO) throws Exception;

    @Operation(summary = "Asigna un lugar de recogida", description = "Asigna un lugar de recogida a un producto")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Lugar de recogida asignado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    })
    @PostMapping("/{id}/lugares")
    ResponseEntity<Void> asignarLugarRecogida(
            @PathVariable @Positive Long id,
            @RequestBody @Valid LugarRecogidaDTO lugarRecogidaDTO) throws Exception;

    @Operation(summary = "Marcar producto como vendido", description = "Marca un producto como vendido")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Producto marcado como vendido"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    })
    @PatchMapping("/{id}/vendido")
    ResponseEntity<Void> marcarComoVendido(@PathVariable @Positive Long id) throws Exception;
}