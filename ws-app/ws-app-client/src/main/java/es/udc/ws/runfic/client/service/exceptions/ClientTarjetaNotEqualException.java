package es.udc.ws.runfic.client.service.exceptions;

public class ClientTarjetaNotEqualException extends Exception{
    private Long inscripcionId;
    private String tarjeta;

    public ClientTarjetaNotEqualException(Long inscripcionId,String tarjeta) {
        super("La tarjeta" + tarjeta +" no coincide con la inscripcion "+inscripcionId);
        this.inscripcionId=inscripcionId;
        this.tarjeta=tarjeta;
    }

    public Long getInscripcionId() { return inscripcionId; }

    public void setInscripcionId(Long codigoDorsal) { this.inscripcionId = inscripcionId; }

    public String getTarjeta() { return tarjeta; }

    public void setTarjeta(String tarjeta) { this.tarjeta = tarjeta; }
}
