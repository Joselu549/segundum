package arso.segundum.dto;

public class ProductoDTO {
    private Long id;
    private String idVendedor;
    private String titulo;
    private Double precio;
    private String lugarRecogida;

    public ProductoDTO(Long id, String idVendedor, String titulo, Double precio, String lugarRecogida) {
        this.id = id;
        this.idVendedor = idVendedor;
        this.titulo = titulo;
        this.precio = precio;
        this.lugarRecogida = lugarRecogida;
    }
}