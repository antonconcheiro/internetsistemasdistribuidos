package es.udc.ws.runfic.model.carreraservice.exceptions;

public class DorsalEntregadoException extends Exception{

    private Long inscripcionId;

    public DorsalEntregadoException(Long inscripcionId) {
        super("El dorsal de la inscripcion " + inscripcionId+ " ya ha sido entregado");
        this.inscripcionId = inscripcionId;
    }

    public Long getInscripcionId() { return inscripcionId; }

    public void setInscripcionId(Long inscripcionId) { this.inscripcionId = inscripcionId; }
}
