package es.udc.ws.runfic.client.service.rest.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import es.udc.ws.runfic.client.service.dto.ClientCarreraDto;
import es.udc.ws.runfic.client.service.dto.ClientInscripcionDto;
import es.udc.ws.util.json.ObjectMapperFactory;
import es.udc.ws.util.json.exceptions.ParsingException;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class JsonToClientInscripcionDtoConversor {

    public static ObjectNode toObjectNode(ClientInscripcionDto inscripcion) throws IOException {

        ObjectNode inscripcionObject = JsonNodeFactory.instance.objectNode();

        if (inscripcion.getInscripcionId() != null) {
            inscripcionObject.put("inscripcionId", inscripcion.getInscripcionId());
        }

        inscripcionObject.put("carreraId", inscripcion.getCarreraId()).
                put("emailUsuario", inscripcion.getEmailUsuario()).
                put("tarjeta", inscripcion.getTarjeta()).
                put("dorsal", inscripcion.getDorsal()).
                put("entregado", inscripcion.getEntregado());

        return inscripcionObject;
    }

    public static ClientInscripcionDto toClientInscripcionDto(InputStream jsonInscripcion) throws ParsingException {
        try {
            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            JsonNode rootNode = objectMapper.readTree(jsonInscripcion);
            if (rootNode.getNodeType() != JsonNodeType.OBJECT) {
                throw new ParsingException("Unrecognized JSON (object expected)");
            } else {
                return toClientInscripcionDto(rootNode);
            }
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    public static List<ClientInscripcionDto> toClientInscripcionDtos(InputStream jsonInscripcion) throws ParsingException {
        try {
            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            JsonNode rootNode = objectMapper.readTree(jsonInscripcion);
            if (rootNode.getNodeType() != JsonNodeType.ARRAY) {
                throw new ParsingException("Unrecognized JSON (array expected)");
            } else {
                ArrayNode inscripcionArray = (ArrayNode) rootNode;
                List<ClientInscripcionDto> inscripcionDtos = new ArrayList<>(inscripcionArray.size());
                for (JsonNode inscripcionNode : inscripcionArray) {
                    inscripcionDtos.add(toClientInscripcionDto(inscripcionNode));
                }

                return inscripcionDtos;
            }
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    private static ClientInscripcionDto toClientInscripcionDto(JsonNode inscripcionNode) throws ParsingException {
        if (inscripcionNode.getNodeType() != JsonNodeType.OBJECT) {
            throw new ParsingException("Unrecognized JSON (object expected)");
        } else {
            ObjectNode inscripcionObject = (ObjectNode) inscripcionNode;

            JsonNode inscripcionIdNode = inscripcionObject.get("inscripcionId");
            Long inscripcionId = (inscripcionIdNode != null) ? inscripcionIdNode.longValue() : null;

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            Long carreraId = inscripcionObject.get("carreraId").longValue();
            String email = inscripcionObject.get("emailUsuario").textValue().trim();
            String tarjeta = inscripcionObject.get("tarjeta").textValue().trim();
            LocalDateTime fechaInscripcion = LocalDateTime.parse(inscripcionObject.get("fechaInscripcion").textValue().trim());
            int dorsal = inscripcionObject.get("dorsal").intValue();


            return new ClientInscripcionDto(inscripcionId, carreraId, email,tarjeta,dorsal);
        }
    }

}
