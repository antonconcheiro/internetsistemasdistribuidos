package es.udc.ws.runfic.restservice.dto;

import java.time.LocalDateTime;

public class RestInscripcionDto {

    private Long inscripcionId;
    private Long carreraId;
    private LocalDateTime fechaInscripcion;
    private String emailUsuario;
    private String tarjeta;
    private int dorsal;

    public RestInscripcionDto() {
    }

    public RestInscripcionDto(Long inscripcionId, Long carreraId, LocalDateTime fechaInscripcion,
                              String emailUsuario, String tarjeta, int dorsal) {
        this.fechaInscripcion = fechaInscripcion;
        this.carreraId = carreraId;
        this.inscripcionId = inscripcionId;
        this.emailUsuario = emailUsuario;
        this.tarjeta = tarjeta;
        this.dorsal = dorsal;
    }

    public Long getInscripcionId() {
        return inscripcionId;
    }

    public void setInscripcionId(Long inscripcionId) {
        this.inscripcionId = inscripcionId;
    }

    public Long getCarreraId() {
        return carreraId;
    }

    public void setCarreraId(Long carreraId) {
        this.carreraId = carreraId;
    }

    public LocalDateTime getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(LocalDateTime fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public String getEmailUsuario() { return emailUsuario; }

    public void setEmailUsuario(String emailUsuario) { this.emailUsuario = emailUsuario; }

    public String getTarjeta() { return tarjeta; }

    public void setTarjeta(String tarjeta) { this.tarjeta = tarjeta; }

    public int getDorsal() { return dorsal; }

    public void setDorsal(int dorsal) { this.dorsal = dorsal; }
}
