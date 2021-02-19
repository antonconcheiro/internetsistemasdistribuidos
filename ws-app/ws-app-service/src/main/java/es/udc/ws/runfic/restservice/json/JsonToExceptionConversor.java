package es.udc.ws.runfic.restservice.json;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import es.udc.ws.runfic.model.carreraservice.exceptions.*;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public class JsonToExceptionConversor {

    public static ObjectNode toInputValidationException(InputValidationException ex) {
        ObjectNode exceptionObject = JsonNodeFactory.instance.objectNode();

        exceptionObject.put("errorType", "InputValidation");
        exceptionObject.put("message", ex.getMessage());

        return exceptionObject;
    }

    public static ObjectNode toAlreadyRegisteredException(AlreadyRegisteredException ex) {
        ObjectNode exceptionObject = JsonNodeFactory.instance.objectNode();

        exceptionObject.put("errorType", "AlreadyRegistered");
        exceptionObject.put("message", ex.getMessage());

        return exceptionObject;
    }


    public static ObjectNode toInstanceNotFoundException(InstanceNotFoundException ex) {

        ObjectNode exceptionObject = JsonNodeFactory.instance.objectNode();
        ObjectNode dataObject = JsonNodeFactory.instance.objectNode();

        exceptionObject.put("errorType", "InstanceNotFound");
        exceptionObject.put("instanceId", (ex.getInstanceId() != null) ?
                ex.getInstanceId().toString() : null);
        exceptionObject.put("instanceType",
				ex.getInstanceType().substring(ex.getInstanceType().lastIndexOf('.') + 1));

        return exceptionObject;
    }

    public static ObjectNode toDorsalEntregadoException(DorsalEntregadoException ex) {

        ObjectNode exceptionObject = JsonNodeFactory.instance.objectNode();

        exceptionObject.put("errorType", "DorsalEntregado");
        exceptionObject.put("inscripcionId", (ex.getInscripcionId()!= null) ?
                ex.getInscripcionId() : null);

        return exceptionObject;
    }

    public static ObjectNode toTarjetaNotEqualException(TarjetaNotEqualException ex) {

        ObjectNode exceptionObject = JsonNodeFactory.instance.objectNode();

        exceptionObject.put("errorType", "TarjetaNotEqual");
        exceptionObject.put("inscripcionId", (ex.getInscripcionId()!= null) ?
                ex.getInscripcionId() : null);
        exceptionObject.put("tarjeta", (ex.getTarjeta()!= null) ?
                ex.getTarjeta() : null);

        return exceptionObject;
    }

    public static ObjectNode toMaxCapacityException(MaxCapacityException ex) {

        ObjectNode exceptionObject = JsonNodeFactory.instance.objectNode();

        exceptionObject.put("errorType", "MaxCapacity");
        exceptionObject.put("capacity", ex.getMaxCapacity());

        return exceptionObject;
    }

    public static ObjectNode toOutOfTimeException(OutOfTimeException ex) {

        ObjectNode exceptionObject = JsonNodeFactory.instance.objectNode();

        exceptionObject.put("errorType", "OutOfTime");

        return exceptionObject;
    }

}
