package arso.segundum.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "compraventas")
public class Compraventa {
    @Id
    private String id;
    private Producto producto;
    private Usuario vendedor;
    private Usuario comprador;
    private LocalDate fechaCompraventa;

    public Compraventa(Producto producto, Usuario vendedor, Usuario comprador) {
        this.producto = producto;
        this.vendedor = vendedor;
        this.comprador = comprador;
        this.fechaCompraventa = LocalDate.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Usuario getVendedor() {
        return vendedor;
    }

    public void setVendedor(Usuario vendedor) {
        this.vendedor = vendedor;
    }

    public Usuario getComprador() {
        return comprador;
    }

    public void setComprador(Usuario comprador) {
        this.comprador = comprador;
    }

    public LocalDate getFechaCompraventa() {
        return fechaCompraventa;
    }

    public void setFechaCompraventa(LocalDate fechaCompraventa) {
        this.fechaCompraventa = fechaCompraventa;
    }

    @Override
    public String toString() {
        return "Compraventa{" + "id='" + id + '\'' + ", producto=" + producto + ", vendedor=" + vendedor + ", comprador=" + comprador + ", fechaCompraventa=" + fechaCompraventa + '}';
    }
}