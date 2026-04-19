package arso.segundum.rest;

import arso.segundum.dto.CompraventaDTO;
import arso.segundum.dto.ErrorDTO;
import io.swagger.v3.oas.annotations.Operation;
import javax.validation.constraints.Positive;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Compraventas", description = "Gestión de compraventas")
@RequestMapping("/compraventas")
public interface CompraventasApi {

    @Operation(summary = "Obtener compraventas paginado", description = "Obtiene compraventas de forma paginada")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Página obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Parámetros de paginación inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    })
    @GetMapping("/pages")
    PagedModel<EntityModel<CompraventaDTO>> getCompraventasPaginado(
            @RequestParam int page,
            @RequestParam int size);

    @Operation(summary = "Realizar compraventa", description = "Registra la compraventa de un producto por un comprador")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Compraventa realizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "404", description = "Producto o usuario no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "502", description = "Error de comunicación con servicio externo",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    })
    @PostMapping
    ResponseEntity<Void> realizarCompraventa(
            @RequestParam @Positive Long idProducto,
            @RequestParam String idComprador) throws Exception;

    @Operation(summary = "Recuperar compras de un usuario", description = "Obtiene todas las compras realizadas por un usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de compras obtenido correctamente"),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    })
    @GetMapping("/compras/{idUsuario}")
    ResponseEntity<List<CompraventaDTO>> recuperarComprasUsuario(@PathVariable String idUsuario);

    @Operation(summary = "Recuperar ventas de un usuario", description = "Obtiene todas las ventas realizadas por un usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de ventas obtenido correctamente"),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    })
    @GetMapping("/ventas/{idUsuario}")
    ResponseEntity<List<CompraventaDTO>> recuperarVentasUsuario(@PathVariable String idUsuario);

    @Operation(summary = "Recuperar compraventas entre comprador y vendedor", description = "Obtiene todas las compraventas entre un comprador y un vendedor concretos, con filtro opcional por producto")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de compraventas obtenido correctamente"),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    })
    @GetMapping("/{idComprador}/{idVendedor}")
    ResponseEntity<List<CompraventaDTO>> recuperarCompraventas(
            @PathVariable String idComprador,
            @PathVariable String idVendedor,
            @RequestParam(required = false) Long idProducto);
}