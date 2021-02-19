package es.udc.ws.runfic.restservice.dto;

import java.time.LocalDateTime;

public class RestCarreraDto {

    private Long carreraId;
    private String ciudad;
    private String descripcion;
    private LocalDateTime fechaCarrera;
    private float precioCarrera;
    private int maxParticipantes;
    private int numeroInscritos;

    public RestCarreraDto() {
    }

    public RestCarreraDto(Long carreraId, String ciudad, String descripcion,
                          LocalDateTime fechaCarrera, float precioCarrera,int maxParticipantes, int numeroInscritos) {

        this.carreraId = carreraId;
        this.ciudad = ciudad;
        this.descripcion = descripcion;
        this.fechaCarrera = fechaCarrera;
        this.precioCarrera = precioCarrera;
        this.maxParticipantes = maxParticipantes;
        this.numeroInscritos = numeroInscritos;
    }

    public Long getCarreraId() {
        return carreraId;
    }

    public void setCarreraId(Long carreraId) { this.carreraId = carreraId; }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaCarrera() { return fechaCarrera; }

    public void setFechaCarrera(LocalDateTime fechaCarrera) {
        this.fechaCarrera = fechaCarrera;
    }


    public float getPrecioCarrera() { return precioCarrera; }

    public void setPrecioCarrera(float precioCarrera) { this.precioCarrera = precioCarrera; }

    public int getMaxParticipantes() {
        return maxParticipantes;
    }

    public void setMaxParticipantes(int maxParticipantes) {
        this.maxParticipantes = maxParticipantes;
    }

    public int getNumeroInscritos() {
        return numeroInscritos;
    }

    public void setNumeroInscritos(int numeroInscritos) {
        this.numeroInscritos = numeroInscritos;
    }

    @Override
    public String toString() {
        return "CarreraDto [carreraId=" + carreraId
                +  ", ciudad=" + ciudad
                + ", descripcion=" + descripcion
                + ", fecha=" + fechaCarrera
                + ", precio=" + precioCarrera
                + ", maxParticipantes=" + maxParticipantes
                + ", numeroInscritos=" + numeroInscritos + "]";
    }

}
