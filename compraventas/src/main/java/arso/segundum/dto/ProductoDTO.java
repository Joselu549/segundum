package arso.segundum.dto;

public class ProductoDTO {
    private Long id;
    private String idVendedor;
    private String titulo;
    private Double precio;
    private String lugarRecogida;

    public ProductoDTO() {
    }

    public ProductoDTO(Long id, String idVendedor, String titulo, Double precio, String lugarRecogida) {
        this.id = id;
        this.idVendedor = idVendedor;
        this.titulo = titulo;
        this.precio = precio;
        this.lugarRecogida = lugarRecogida;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(String idVendedor) {
        this.idVendedor = idVendedor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getLugarRecogida() {
        return lugarRecogida;
    }

    public void setLugarRecogida(String lugarRecogida) {
        this.lugarRecogida = lugarRecogida;
    }
}