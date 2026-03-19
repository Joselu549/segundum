package arso.segundum.rest;

import arso.segundum.dto.LugarRecogidaDTO;
import arso.segundum.dto.ProductoDTO;
import arso.segundum.modelo.Estado;
import arso.segundum.modelo.Producto;
import arso.segundum.servicio.IServicioProductos;
import arso.segundum.servicio.ResumenProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
@Validated
public class ProductoController {

    private final IServicioProductos servicioProductos;

    public ProductoController(IServicioProductos servicioProductos) {
        this.servicioProductos = servicioProductos;
    }

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> getProductos(
            @RequestParam(required = false) String idCategoria,
            @RequestParam(required = false) String descripcion,
            @RequestParam(required = false) Estado estado,
            @RequestParam(required = false) @PositiveOrZero Double precio) {

        List<ProductoDTO> result = servicioProductos.buscarProductos(idCategoria, descripcion, estado, precio)
                .stream()
                .map(ProductoDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> getProductosById(@PathVariable @Positive Long id) {
        // Si no existe, el servicio lanza EntityNotFoundException → 404 via advice
        return ResponseEntity.ok(ProductoDTO.fromEntity(servicioProductos.getProducto(id)));
    }

    @GetMapping("/historial")
    public ResponseEntity<List<ResumenProducto>> getHistorialProductos(
            @RequestParam(required = false) @Min(1) @Max(12) Integer mes,
            @RequestParam(required = false) @Min(2000) @Max(2100) Integer anio) {

        return ResponseEntity.ok(servicioProductos.getHistorialMes(mes, anio));
    }

    @PatchMapping("/{id}/visualizations")
    public ResponseEntity<Void> addVisualizaciones(@PathVariable @Positive Long id) {
        servicioProductos.addVisualizacionProducto(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Void> darDeAltaProducto(@RequestBody @Valid ProductoDTO nuevoProducto) {
        Long id = servicioProductos.darDeAltaProducto(
                nuevoProducto.getTitulo(),
                nuevoProducto.getDescripcion(),
                nuevoProducto.getPrecio(),
                nuevoProducto.getEstado(),
                nuevoProducto.getIdCategoria(),
                nuevoProducto.isEnvioDisponible(),
                nuevoProducto.getIdVendedor());

        URI nuevaURL = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(nuevaURL).build();
    }

    @PostMapping("/{id}/lugares")
    public ResponseEntity<Void> asignarLugarRecogida(
            @PathVariable @Positive Long id,
            @RequestBody @Valid LugarRecogidaDTO lugarRecogidaDTO) {

        servicioProductos.asignarLugarRecogida(
                id,
                lugarRecogidaDTO.getLongitud(),
                lugarRecogidaDTO.getLatitud(),
                lugarRecogidaDTO.getDescripcion());
        return ResponseEntity.noContent().build();
    }
}