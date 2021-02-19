package es.udc.ws.runfic.client.service.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ClientCarreraDto {

    private Long carreraId;
    private String ciudad;
    private String descripcion;
    private LocalDateTime fechaCarrera;
    private float precioCarrera;
    private int plazasDisp;

    public ClientCarreraDto() {
    }

    public ClientCarreraDto(Long carreraId, String ciudad, String descripcion, LocalDateTime fechaCarrera,
                          float precioCarrera, int plazasDisp) {
        this.carreraId = carreraId;
        this.ciudad = ciudad;
        this.descripcion = descripcion;
        this.fechaCarrera = fechaCarrera;
        this.precioCarrera = precioCarrera;
        this.plazasDisp = plazasDisp;
    }

    public Long getCarreraId() { return this.carreraId; }
    public void setCarreraId(Long carreraId) { this.carreraId = carreraId; }

    public String getCiudad() { return this.ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public String getDescripcion() { return this.descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public LocalDateTime getFechaCarrera() { return this.fechaCarrera; }
    public void setFechaCarrera(LocalDateTime fechaCarrera) { this.fechaCarrera = fechaCarrera; }

    public float getPrecioCarrera() { return this.precioCarrera; }
    public void setPrecioCarrera(float precioCarrera) { this.precioCarrera = precioCarrera; }

    public int getPlazasDisp() { return plazasDisp; }
    public void setPlazasDisp(int plazasDisp) { this.plazasDisp = plazasDisp; }

    @Override
    public String toString() {
        return "CarreraDto [carreraId=" + carreraId + ", ciudad=" + ciudad
                + ", descripcion=" + descripcion + ", fecha carrera=" + fechaCarrera + ", precio= "+precioCarrera
                + ", plazas disponibles=" +plazasDisp+"]";
    }
}
