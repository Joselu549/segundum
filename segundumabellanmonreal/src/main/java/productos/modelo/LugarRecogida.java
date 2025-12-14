package productos.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class LugarRecogida {
  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  private String id;
  @Lob
  private String descripcion;
  private double longitud;
  private double latitud;

  public LugarRecogida() {
  }

  public LugarRecogida(String descripcion, double longitud, double latitud) {
    this.descripcion = descripcion;
    this.longitud = longitud;
    this.latitud = latitud;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public double getLongitud() {
    return longitud;
  }

  public double getLatitud() {
    return latitud;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public void setLongitud(int longitud) {
    this.longitud = longitud;
  }

  public void setLatitud(int latitud) {
    this.latitud = latitud;
  }
}
