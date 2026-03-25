package arso.segundum.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "productos")
public class Producto {
    @Id
    private Long id;
    private String titulo;
    private Double precio;
    private String recogida;
    private String idVendedor;

    public Producto(String titulo, Double precio, String recogida, String idVendedor) {
        this.titulo = titulo;
        this.precio = precio;
        this.recogida = recogida;
        this.idVendedor = idVendedor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Producto{" + "id=" + id + ", titulo='" + titulo + '\'' + ", precio=" + precio + ", recogida='" + recogida + '\'' + ", idVendedor='" + idVendedor + '\'' + '}';
    }
}