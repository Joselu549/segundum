package arso.segundum.rest;

import arso.segundum.dto.LugarRecogidaDTO;
import arso.segundum.dto.ProductoDTO;
import arso.segundum.modelo.LugarRecogida;
import arso.segundum.modelo.Producto;
import arso.segundum.servicio.IServicioProductos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductoController {

    private final IServicioProductos servicioProductos;

    @Autowired
    public ProductoController(IServicioProductos servicioProductos) {
        this.servicioProductos = servicioProductos;
    }

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> getProductos() {
        List<Producto> productos = this.servicioProductos.buscarProductos(null, null, null, null);
        List<ProductoDTO> result = productos.stream().map(ProductoDTO::fromEntity).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ProductoDTO getProductosById(@PathVariable Long id) {
        Producto producto = this.servicioProductos.getProducto(id);
        return ProductoDTO.fromEntity(producto);
    }

    @PatchMapping("/{id}/visualizations")
    public ResponseEntity<Void> addVisualizaciones(@PathVariable Long id) {
        this.servicioProductos.addVisualizacionProducto(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Void> darDeAltaProducto(@RequestBody ProductoDTO nuevoProducto) {
        Long id = this.servicioProductos.darDeAltaProducto(nuevoProducto.getTitulo(), nuevoProducto.getDescripcion(), nuevoProducto.getPrecio(), nuevoProducto.getEstado(), nuevoProducto.getIdCategoria(), nuevoProducto.isEnvioDisponible(), nuevoProducto.getIdVendedor());
        URI nuevaURL = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(nuevaURL).build();
    }

    @PostMapping("/{id}/lugares")
    public ResponseEntity<Void> asignarLugarRecogida(@PathVariable Long id, @RequestBody LugarRecogidaDTO lugarRecogidaDTO) {
        this.servicioProductos.asignarLugarRecogida(id, lugarRecogidaDTO.getLongitud(), lugarRecogidaDTO.getLatitud(), lugarRecogidaDTO.getDescripcion());
        return ResponseEntity.noContent().build();
    }
}