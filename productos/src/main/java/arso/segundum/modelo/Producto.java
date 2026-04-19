package arso.segundum.modelo;

import java.time.LocalDateTime;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    @Lob
    private String descripcion;
    private double precio;

    @Enumerated(EnumType.STRING)
    private Estado estado;
    private LocalDateTime fechaPublicacion;
    @ManyToOne
    private Categoria categoria;
    private int visualizaciones;
    private boolean envioDisponible;
    @OneToOne(cascade = CascadeType.ALL)
    private LugarRecogida lugarRecogida;

    @ManyToOne
    private Usuario vendedor;
    private boolean vendido = false;

    public Producto() {
    }

    public Producto(String titulo, String descripcion, double precio, Estado estado,
            LocalDateTime fechaPublicacion, Categoria categoria, int visualizaciones,
            boolean envioDisponible, LugarRecogida lugarRecogida, Usuario vendedor) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.estado = estado;
        this.fechaPublicacion = fechaPublicacion;
        this.categoria = categoria;
        this.visualizaciones = visualizaciones;
        this.envioDisponible = envioDisponible;
        this.lugarRecogida = lugarRecogida;
        this.vendedor = vendedor;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public Estado getEstado() {
        return estado;
    }

    public LocalDateTime getFechaPublicacion() {
        return fechaPublicacion;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public int getVisualizaciones() {
        return visualizaciones;
    }

    public boolean isEnvioDisponible() {
        return envioDisponible;
    }

    public LugarRecogida getLugarRecogida() {
        return lugarRecogida;
    }

    public Usuario getVendedor() {
        return vendedor;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public void setFechaPublicacion(LocalDateTime fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void setVisualizaciones(int visualizaciones) {
        this.visualizaciones = visualizaciones;
    }

    public void setEnvioDisponible(boolean envioDisponible) {
        this.envioDisponible = envioDisponible;
    }

    public void setLugarRecogida(LugarRecogida lugarRecogida) {
        this.lugarRecogida = lugarRecogida;
    }

    public LugarRecogida setLugarRecogida(String descripcion, double longitud, double latitud) {
        LugarRecogida lugar = new LugarRecogida(descripcion, longitud, latitud);
        this.lugarRecogida = lugar;
        return lugar;
    }

    public void setVendedor(Usuario vendedor) {
        this.vendedor = vendedor;
    }

    public boolean isVendido() {
        return vendido;
    }

    public void setVendido(boolean vendido) {
        this.vendido = vendido;
    }
}