package productos.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LugarRecogida {
  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  private String id;
  private String descripcion;
  private int longitud;
  private int latitud;

  public LugarRecogida() {
  }

  public LugarRecogida(String descripcion, int longitud, int latitud) {
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

  public int getLongitud() {
    return longitud;
  }

  public int getLatitud() {
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
