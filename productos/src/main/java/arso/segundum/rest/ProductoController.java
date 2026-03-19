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
    public ResponseEntity<List<ProductoDTO>> getProductos(@RequestParam @Nullable String idCategoria, @RequestParam @Nullable String descripcion, @RequestParam @Nullable Estado estado, @RequestParam @Nullable Double precio) {
        List<Producto> productos = this.servicioProductos.buscarProductos(idCategoria, descripcion, estado, precio);
        List<ProductoDTO> result = productos.stream().map(ProductoDTO::fromEntity).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ProductoDTO getProductosById(@PathVariable Long id) {
        Producto producto = this.servicioProductos.getProducto(id);
        return ProductoDTO.fromEntity(producto);
    }

    @GetMapping("/historial")
    public List<ResumenProducto> getHistorialProductos(@RequestParam @Nullable Integer mes, @RequestParam @Nullable Integer anio) {
       return this.servicioProductos.getHistorialMes(mes, anio);
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