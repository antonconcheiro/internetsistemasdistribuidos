package es.udc.ws.runfic.model.carreraservice.exceptions;

public class TarjetaNotEqualException extends Exception{
    private String tarjeta;
    private Long inscripcionId;


    public TarjetaNotEqualException(Long inscripcionId,String tarjeta) {
        super("La tarjeta \"" + tarjeta+ "\" no coincide con la inscripcion "+inscripcionId);
        this.inscripcionId = inscripcionId;
        this.tarjeta = tarjeta;
    }

    public Long getInscripcionId() { return inscripcionId; }

    public String getTarjeta() {return tarjeta;}

    public void setInscripcionId(Long inscripcionId) { this.inscripcionId = inscripcionId; }

    public void setTarjeta(String tarjeta) {this.tarjeta = tarjeta;}
}
