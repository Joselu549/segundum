package arso.segundum.rest;

import arso.segundum.dto.CompraventaDTO;
import arso.segundum.modelo.Compraventa;
import arso.segundum.servicio.IServicioCompraventa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Validated
public class CompraventaController implements CompraventasApi {

    @Autowired
    private IServicioCompraventa servicioCompraventa;
    @Autowired
    private PagedResourcesAssembler<CompraventaDTO> pagedResourcesAssembler;

    public CompraventaController(IServicioCompraventa servicioCompraventa) {
        this.servicioCompraventa = servicioCompraventa;
    }

    @Override
    public PagedModel<EntityModel<CompraventaDTO>> getCompraventasPaginado(
            @RequestParam int page,
            @RequestParam int size) {
        Pageable paginacion = PageRequest.of(page, size, Sort.by("fechaCompraventa").descending());
        Page<CompraventaDTO> resultado = servicioCompraventa.getListadoPaginado(paginacion);
        return pagedResourcesAssembler.toModel(resultado);
    }

    @Override
    public ResponseEntity<Void> realizarCompraventa(
            @RequestParam Long idProducto,
            @RequestParam String idComprador) throws Exception {
        Compraventa compraventa = servicioCompraventa.realizarCompraventa(idProducto, idComprador);
        URI nuevaURL = ServletUriComponentsBuilder.fromCurrentRequest()
                .replacePath("/compraventas/{idComprador}/{idVendedor}")
                .replaceQueryParam("idComprador")
                .buildAndExpand(compraventa.getIdComprador(), compraventa.getIdVendedor())
                .toUri();
        return ResponseEntity.created(nuevaURL).build();
    }

    @Override
    public ResponseEntity<List<CompraventaDTO>> recuperarComprasUsuario(@PathVariable String idUsuario) {
        List<CompraventaDTO> compras = servicioCompraventa.recuperarComprasUsuario(idUsuario)
                .stream()
                .map(CompraventaDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(compras);
    }

    @Override
    public ResponseEntity<List<CompraventaDTO>> recuperarVentasUsuario(@PathVariable String idUsuario) {
        List<CompraventaDTO> ventas = servicioCompraventa.recuperarVentasUsuario(idUsuario)
                .stream()
                .map(CompraventaDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ventas);
    }

    @Override
    public ResponseEntity<List<CompraventaDTO>> recuperarCompraventas(
            @PathVariable String idComprador,
            @PathVariable String idVendedor,
            @RequestParam(required = false) Long idProducto) {
        List<CompraventaDTO> compraventas = servicioCompraventa
                .recuperarCompraventas(idComprador, idVendedor, idProducto)
                .stream()
                .map(CompraventaDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(compraventas);
    }
}