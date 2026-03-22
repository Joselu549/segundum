package arso.segundum.rest;

import arso.segundum.dto.LugarRecogidaDTO;
import arso.segundum.dto.ProductoDTO;
import arso.segundum.modelo.Estado;
import arso.segundum.servicio.IServicioProductos;
import arso.segundum.servicio.ResumenProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
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
@Validated
public class ProductoController implements ProductosApi {

    @Autowired
    private final IServicioProductos servicioProductos;
    @Autowired
    private PagedResourcesAssembler<ResumenProducto> pagedResourcesAssembler;

    public ProductoController(IServicioProductos servicioProductos) {
        this.servicioProductos = servicioProductos;
    }

    @Override
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

    @Override
    public PagedModel<EntityModel<ResumenProducto>> getEncuestasPaginado(
            @RequestParam int page,
            @RequestParam int size) throws Exception {
        Pageable paginacion =
                PageRequest.of(page, size, Sort.by("id").ascending());

        Page<ResumenProducto> resultado = this.servicioProductos.getListadoPaginado(paginacion);

        return this.pagedResourcesAssembler.toModel(resultado);
    }

    @Override
    public EntityModel<ProductoDTO> getProductosById(@PathVariable @Positive Long id) throws Exception {
        //TODO: Arreglar que ahora no devuelve el error de no encontrado correctamente
        ProductoDTO productoDTO = ProductoDTO.fromEntity(servicioProductos.getProducto(id));
        EntityModel<ProductoDTO> model = EntityModel.of(productoDTO);
        model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductoController.class).getProductosById(id)).withSelfRel());
        return model;
    }

    @Override
    public ResponseEntity<List<ResumenProducto>> getHistorialProductos(
            @RequestParam(required = false) @Min(1) @Max(12) Integer mes,
            @RequestParam(required = false) @Min(2000) @Max(2100) Integer anio) throws Exception {

        return ResponseEntity.ok(servicioProductos.getHistorialMes(mes, anio));
    }

    @Override
    public ResponseEntity<Void> addVisualizaciones(@PathVariable @Positive Long id) throws Exception {
        servicioProductos.addVisualizacionProducto(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> darDeAltaProducto(@RequestBody @Valid ProductoDTO nuevoProducto) throws Exception {
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

    @Override
    public ResponseEntity<Void> asignarLugarRecogida(
            @PathVariable @Positive Long id,
            @RequestBody @Valid LugarRecogidaDTO lugarRecogidaDTO) throws Exception {

        servicioProductos.asignarLugarRecogida(
                id,
                lugarRecogidaDTO.getLongitud(),
                lugarRecogidaDTO.getLatitud(),
                lugarRecogidaDTO.getDescripcion());
        return ResponseEntity.noContent().build();
    }
}