package es.udc.ws.runfic.client.service.rest.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import es.udc.ws.runfic.client.service.dto.ClientCarreraDto;
import es.udc.ws.runfic.client.service.exceptions.*;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.json.ObjectMapperFactory;
import es.udc.ws.util.json.exceptions.ParsingException;

import java.io.InputStream;
import java.time.LocalDateTime;
import es.udc.ws.util.json.exceptions.ParsingException;
public class JsonToClientExceptionConversor {

    public static Exception fromBadRequestErrorCode(InputStream ex) throws ParsingException {
        try {
            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            JsonNode rootNode = objectMapper.readTree(ex);
            if (rootNode.getNodeType() != JsonNodeType.OBJECT) {
                throw new ParsingException("Unrecognized JSON (object expected)");
            } else {
                String errorType = rootNode.get("errorType").textValue();
                if (errorType.equals("InputValidation")) {
                    return toInputValidationException(rootNode);
                } else {
                    throw new ParsingException("Unrecognized error type: " + errorType);
                }
            }
        } catch (ParsingException e) {
            throw e;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    private static InputValidationException toInputValidationException(JsonNode rootNode) {
        String message = rootNode.get("message").textValue();
        return new InputValidationException(message);
    }

    public static Exception fromNotFoundErrorCode(InputStream ex) throws ParsingException {
        try {
            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            JsonNode rootNode = objectMapper.readTree(ex);
            if (rootNode.getNodeType() != JsonNodeType.OBJECT) {
                throw new ParsingException("Unrecognized JSON (object expected)");
            } else {
                String errorType = rootNode.get("errorType").textValue();
                if (errorType.equals("InstanceNotFound")) {
                    return toInstanceNotFoundException(rootNode);
                } else {
                    throw new ParsingException("Unrecognized error type: " + errorType);
                }
            }
        } catch (ParsingException e) {
            throw e;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    private static InstanceNotFoundException toInstanceNotFoundException(JsonNode rootNode) {
        String instanceId = rootNode.get("instanceId").textValue();
        String instanceType = rootNode.get("instanceType").textValue();
        return new InstanceNotFoundException(instanceId, instanceType);
    }

    public static Exception fromForbiddenErrorCode(InputStream ex) throws ParsingException {
        try {
            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            JsonNode rootNode = objectMapper.readTree(ex);
            if (rootNode.getNodeType() != JsonNodeType.OBJECT) {
                throw new ParsingException("Unrecognized JSON (object expected)");
            } else {
                String errorType = rootNode.get("errorType").textValue();
                if (errorType.equals("TarjetaNotEqual")) {
                    return toTarjetaNotEqualException(rootNode);
                } else {
                    throw new ParsingException("Unrecognized error type: " + errorType);
                }
            }
        } catch (ParsingException e) {
            throw e;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    public static Exception fromGoneErrorCode(InputStream ex) throws ParsingException {
        try {
            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            JsonNode rootNode = objectMapper.readTree(ex);
            if (rootNode.getNodeType() != JsonNodeType.OBJECT) {
                throw new ParsingException("Unrecognized JSON (object expected)");
            } else {
                String errorType = rootNode.get("errorType").textValue();
                if (errorType.equals("DorsalEntregado")) {
                    return toDorsalEntregadoException(rootNode);
                }else if(errorType.equals("MaxCapacity")) {
                    return toMaxCapacityException(rootNode);
                }else if(errorType.equals("OutOfTime")) {
                    return toOutOfTimeException(rootNode);
                }else if(errorType.equals("AlreadyRegistered")){
                    return toAlreadyRegisteredException(rootNode);
                } else {
                    throw new ParsingException("Unrecognized error type: " + errorType);
                }
            }
        } catch (ParsingException e) {
            throw e;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    private static AlreadyRegisteredException toAlreadyRegisteredException(JsonNode rootNode) {
        String message = rootNode.get("message").textValue();
        return new AlreadyRegisteredException(message);
    }

    private static ClientDorsalEntregadoException toDorsalEntregadoException(JsonNode rootNode) {
        Long inscripcionId = rootNode.get("inscripcionId").longValue();
        return new ClientDorsalEntregadoException(inscripcionId);
    }

    private static ClientTarjetaNotEqualException toTarjetaNotEqualException(JsonNode rootNode) {
        Long inscripcionId = rootNode.get("inscripcionId").longValue();
        String tarjeta = rootNode.get("tarjeta").textValue().trim();
        return new ClientTarjetaNotEqualException(inscripcionId,tarjeta);
    }

    private static MaxCapacityException toMaxCapacityException(JsonNode rootNode) {
        int maxCapacity = rootNode.get("capacity").intValue();
        return new MaxCapacityException(maxCapacity);
    }

    private static OutOfTimeException toOutOfTimeException(JsonNode rootNode) {
        return new OutOfTimeException();
    }
}
