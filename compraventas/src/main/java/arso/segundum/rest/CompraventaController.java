package arso.segundum.rest;

import arso.segundum.dto.CompraventaDTO;
import arso.segundum.modelo.Compraventa;
import arso.segundum.servicio.IServicioCompraventa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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

    public CompraventaController(IServicioCompraventa servicioCompraventa) {
        this.servicioCompraventa = servicioCompraventa;
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
    public CollectionModel<EntityModel<CompraventaDTO>> recuperarComprasUsuario(@PathVariable String idUsuario) {
        List<EntityModel<CompraventaDTO>> compras = servicioCompraventa.recuperarComprasUsuario(idUsuario)
                .stream()
                .map(compraventa -> {
                    CompraventaDTO dto = CompraventaDTO.fromEntity(compraventa);
                    return EntityModel.of(dto,
                            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CompraventaController.class)
                                    .recuperarCompraventas(dto.getIdComprador(), dto.getIdVendedor(),
                                            dto.getIdProducto()))
                                    .withSelfRel(),
                            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CompraventaController.class)
                                    .recuperarComprasUsuario(idUsuario))
                                    .withRel("compras"),
                            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CompraventaController.class)
                                    .recuperarVentasUsuario(dto.getIdVendedor()))
                                    .withRel("ventas-vendedor"));
                })
                .collect(Collectors.toList());

        return CollectionModel.of(compras,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CompraventaController.class)
                        .recuperarComprasUsuario(idUsuario))
                        .withSelfRel());
    }

    @Override
    public CollectionModel<EntityModel<CompraventaDTO>> recuperarVentasUsuario(@PathVariable String idUsuario) {
        List<EntityModel<CompraventaDTO>> ventas = servicioCompraventa.recuperarVentasUsuario(idUsuario)
                .stream()
                .map(compraventa -> {
                    CompraventaDTO dto = CompraventaDTO.fromEntity(compraventa);
                    return EntityModel.of(dto,
                            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CompraventaController.class)
                                    .recuperarCompraventas(dto.getIdComprador(), dto.getIdVendedor(),
                                            dto.getIdProducto()))
                                    .withSelfRel(),
                            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CompraventaController.class)
                                    .recuperarVentasUsuario(idUsuario))
                                    .withRel("ventas"),
                            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CompraventaController.class)
                                    .recuperarComprasUsuario(dto.getIdComprador()))
                                    .withRel("compras-comprador"));
                })
                .collect(Collectors.toList());

        return CollectionModel.of(ventas,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CompraventaController.class)
                        .recuperarVentasUsuario(idUsuario))
                        .withSelfRel());
    }

    @Override
    public CollectionModel<EntityModel<CompraventaDTO>> recuperarCompraventas(
            @PathVariable String idComprador,
            @PathVariable String idVendedor,
            @RequestParam(required = false) Long idProducto) {
        List<EntityModel<CompraventaDTO>> compraventas = servicioCompraventa
                .recuperarCompraventas(idComprador, idVendedor, idProducto)
                .stream()
                .map(compraventa -> {
                    CompraventaDTO dto = CompraventaDTO.fromEntity(compraventa);
                    return EntityModel.of(dto,
                            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CompraventaController.class)
                                    .recuperarCompraventas(dto.getIdComprador(), dto.getIdVendedor(),
                                            dto.getIdProducto()))
                                    .withSelfRel(),
                            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CompraventaController.class)
                                    .recuperarComprasUsuario(dto.getIdComprador()))
                                    .withRel("compras-comprador"),
                            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CompraventaController.class)
                                    .recuperarVentasUsuario(dto.getIdVendedor()))
                                    .withRel("ventas-vendedor"));
                })
                .collect(Collectors.toList());

        return CollectionModel.of(compraventas,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CompraventaController.class)
                        .recuperarCompraventas(idComprador, idVendedor, idProducto))
                        .withSelfRel());
    }
}