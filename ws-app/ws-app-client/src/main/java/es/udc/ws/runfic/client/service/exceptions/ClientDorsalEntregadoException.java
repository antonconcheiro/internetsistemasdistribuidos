package es.udc.ws.runfic.client.service.exceptions;

import java.time.LocalDateTime;

public class ClientDorsalEntregadoException extends Exception{

    private Long inscripcionId;

    public ClientDorsalEntregadoException(Long inscripcionId) {
        super("El dorsal de la inscripcion \"" + inscripcionId
                + "\" ya ha sido entregado \")");
        this.inscripcionId=inscripcionId;
    }

    public Long getInscripcionId() { return inscripcionId; }

    public void setInscripcionId(Long codigoDorsal) { this.inscripcionId = inscripcionId; }
}
