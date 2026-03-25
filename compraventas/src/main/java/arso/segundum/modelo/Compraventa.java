package arso.segundum.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "compraventas")
public class Compraventa {
    @Id
    private String id;
    private String idProducto;
    private String titulo;
    private Double precio;
    private String recogida;
    private String idVendedor;
    private String nombreVendedor;
    private String idComprador;
    private String nombreComprador;
    private LocalDate fechaCompraventa;

    public Compraventa(String idProducto, String titulo, Double precio, String recogida, String idVendedor, String nombreVendedor, String idComprador, String nombreComprador, LocalDate fechaCompraventa) {
        this.idProducto = idProducto;
        this.titulo = titulo;
        this.precio = precio;
        this.recogida = recogida;
        this.idVendedor = idVendedor;
        this.nombreVendedor = nombreVendedor;
        this.idComprador = idComprador;
        this.nombreComprador = nombreComprador;
        this.fechaCompraventa = fechaCompraventa;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
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

    @Override
    public String toString() {
        return "Compraventa{" + "id='" + id + '\'' + ", idProducto='" + idProducto + '\'' + ", titulo='" + titulo + '\'' + ", precio=" + precio + ", recogida='" + recogida + '\'' + ", idVendedor='" + idVendedor + '\'' + ", nombreVendedor='" + nombreVendedor + '\'' + ", idComprador='" + idComprador + '\'' + ", nombreComprador='" + nombreComprador + '\'' + ", fechaCompraventa=" + fechaCompraventa + '}';
    }
}