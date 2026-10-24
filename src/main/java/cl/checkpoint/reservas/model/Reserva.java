package cl.checkpoint.reservas.model;

import jakarta.persistence.*;

@Entity
public class Reserva {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long clienteId;
  private String espacio;
  private String fecha;
  private String horario;
  private String estado;

  public Long getId() {
    return id;
  }

  public Long getClienteId() {
    return clienteId;
  }

  public void setClienteId(Long value) {
    this.clienteId = value;
  }

  public String getEspacio() {
    return espacio;
  }

  public void setEspacio(String value) {
    this.espacio = value;
  }

  public String getFecha() {
    return fecha;
  }

  public void setFecha(String value) {
    this.fecha = value;
  }

  public String getHorario() {
    return horario;
  }

  public void setHorario(String value) {
    this.horario = value;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String value) {
    this.estado = value;
  }
}
