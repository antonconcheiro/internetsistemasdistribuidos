package es.udc.ws.runfic.client.service.exceptions;

public class OutOfTimeException extends Exception{

    public OutOfTimeException() {
        super("No se ha podido completar la inscrpción debido a que la carrera comienza en menos de 24 horas");
    }

}
