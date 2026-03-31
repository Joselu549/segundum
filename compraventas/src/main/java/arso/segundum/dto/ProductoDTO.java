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

    public String getIdVendedor() {
        return idVendedor;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Double getPrecio() {
        return precio;
    }

    public String getLugarRecogida() {
        return lugarRecogida;
    }
}