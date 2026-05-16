package arso.segundum.rest.dto;

import arso.segundum.modelo.Compraventa;

import java.time.LocalDate;

public class CompraventaDTO {
    private String id;
    private Long idProducto;
    private String titulo;
    private Double precio;
    private String recogida;
    private String idVendedor;
    private String nombreVendedor;
    private String idComprador;
    private String nombreComprador;
    private LocalDate fechaCompraventa;

    public CompraventaDTO() {
    }

    public static CompraventaDTO fromEntity(Compraventa compraventa) {
        CompraventaDTO dto = new CompraventaDTO();
        dto.id = compraventa.getId();
        dto.idProducto = compraventa.getIdProducto();
        dto.titulo = compraventa.getTitulo();
        dto.precio = compraventa.getPrecio();
        dto.recogida = compraventa.getRecogida();
        dto.idVendedor = compraventa.getIdVendedor();
        dto.nombreVendedor = compraventa.getNombreVendedor();
        dto.idComprador = compraventa.getIdComprador();
        dto.nombreComprador = compraventa.getNombreComprador();
        dto.fechaCompraventa = compraventa.getFechaCompraventa();
        return dto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
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

    public String getRecogida() {
        return recogida;
    }

    public void setRecogida(String recogida) {
        this.recogida = recogida;
    }

    public String getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(String idVendedor) {
        this.idVendedor = idVendedor;
    }

    public String getNombreVendedor() {
        return nombreVendedor;
    }

    public void setNombreVendedor(String nombreVendedor) {
        this.nombreVendedor = nombreVendedor;
    }

    public String getIdComprador() {
        return idComprador;
    }

    public void setIdComprador(String idComprador) {
        this.idComprador = idComprador;
    }

    public String getNombreComprador() {
        return nombreComprador;
    }

    public void setNombreComprador(String nombreComprador) {
        this.nombreComprador = nombreComprador;
    }

    public LocalDate getFechaCompraventa() {
        return fechaCompraventa;
    }

    public void setFechaCompraventa(LocalDate fechaCompraventa) {
        this.fechaCompraventa = fechaCompraventa;
    }
}
