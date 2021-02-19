package es.udc.ws.runfic.client.service.rest.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import es.udc.ws.runfic.client.service.dto.ClientCarreraDto;
import es.udc.ws.util.json.ObjectMapperFactory;
import es.udc.ws.util.json.exceptions.ParsingException;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class JsonToClientCarreraDtoConversor {

    public static ObjectNode toObjectNode(ClientCarreraDto carrera) throws IOException {

        ObjectNode carreraObject = JsonNodeFactory.instance.objectNode();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        if (carrera.getCarreraId() != null) {
            carreraObject.put("carreraId", carrera.getCarreraId());
        }

        carreraObject.put("ciudad", carrera.getCiudad()).
                put("descripcion", carrera.getDescripcion()).
                put("fechaCarrera", carrera.getFechaCarrera().format(formatter)).
                put("precioCarrera", carrera.getPrecioCarrera()).
                put("maxParticipantes", carrera.getPlazasDisp()).
                put("numeroInscritos", 0);

        return carreraObject;
    }

    public static ClientCarreraDto toClientCarreraDto(InputStream jsonCarrera) throws ParsingException {
        try {
            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            JsonNode rootNode = objectMapper.readTree(jsonCarrera);
            if (rootNode.getNodeType() != JsonNodeType.OBJECT) {
                throw new ParsingException("Unrecognized JSON (object expected)");
            } else {
                return toClientCarreraDto(rootNode);
            }
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    public static List<ClientCarreraDto> toClientCarreraDtos(InputStream jsonCarrera) throws ParsingException {
        try {
            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            JsonNode rootNode = objectMapper.readTree(jsonCarrera);
            if (rootNode.getNodeType() != JsonNodeType.ARRAY) {
                throw new ParsingException("Unrecognized JSON (array expected)");
            } else {

                ArrayNode carrerasArray = (ArrayNode) rootNode;
                List<ClientCarreraDto> carreraDtos = new ArrayList<>(carrerasArray.size());
                for (JsonNode carreraNode : carrerasArray) {
                    carreraDtos.add(toClientCarreraDto(carreraNode));
                }

                return carreraDtos;
            }
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    private static ClientCarreraDto toClientCarreraDto(JsonNode carreraNode) throws ParsingException {
        if (carreraNode.getNodeType() != JsonNodeType.OBJECT) {
            throw new ParsingException("Unrecognized JSON (object expected)");
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            ObjectNode carreraObject = (ObjectNode) carreraNode;

            JsonNode carreraIdNode = carreraObject.get("carreraId");
            Long carreraId = (carreraIdNode != null) ? carreraIdNode.longValue() : null;

            String ciudad = carreraObject.get("ciudad").textValue().trim();
            String description = carreraObject.get("descripcion").textValue().trim();
            LocalDateTime fechacarrera = LocalDateTime.parse(carreraObject.get("fechaCarrera").textValue().trim(),formatter);
            float preciocarrera = carreraObject.get("precioCarrera").floatValue();
            int maxParticipantes = carreraObject.get("maxParticipantes").asInt();
            int numeroInscritos = carreraObject.get("numeroInscritos").asInt();
            int plazasDisp=maxParticipantes-numeroInscritos;

            return new ClientCarreraDto(carreraId, ciudad, description, fechacarrera, preciocarrera,
                    plazasDisp);
        }
    }
}
