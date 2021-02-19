package es.udc.ws.runfic.client.service.dto;

import java.time.LocalDateTime;

public class ClientInscripcionDto {

    private Long inscripcionId;
    private Long carreraId;
    private String emailUsuario;
    private String tarjeta;
    private int dorsal;
    private boolean entregado = false;

    public ClientInscripcionDto() {
    }

    public ClientInscripcionDto(Long inscripcionId, Long carreraId,
                         String emailUsuario, String tarjeta, int dorsal) {
        this.inscripcionId = inscripcionId;
        this.carreraId = carreraId;
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
        return this.carreraId;
    }

    public void setCarreraId(Long carreraId) {
        this.carreraId = carreraId;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public String getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(String tarjeta) {
        this.tarjeta = tarjeta;
    }

    public int getDorsal() { return dorsal; }

    public void setDorsal(int dorsal) { this.dorsal = dorsal; }

    public boolean getEntregado() { return entregado; }

    public void setEntregado(boolean entregado) { this.entregado = entregado; }

    @Override
    public String toString() {
        return "InscripcionDto [inscripcionId=" + inscripcionId + ", carreraId=" + carreraId
                + ", emailUsuario=" + emailUsuario + ", tarjeta=" + tarjeta
                + ", dorsal=" + dorsal + ", entregado=" + entregado + "]";
    }
}
