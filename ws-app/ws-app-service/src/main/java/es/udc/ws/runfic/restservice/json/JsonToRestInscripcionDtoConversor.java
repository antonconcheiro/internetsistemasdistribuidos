package es.udc.ws.runfic.restservice.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import es.udc.ws.runfic.restservice.dto.RestCarreraDto;
import es.udc.ws.runfic.restservice.dto.RestInscripcionDto;
import es.udc.ws.util.json.ObjectMapperFactory;
import es.udc.ws.util.json.exceptions.ParsingException;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class JsonToRestInscripcionDtoConversor {

	public static ObjectNode toObjectNode(RestInscripcionDto inscripcion) {

		ObjectNode inscripcionNode = JsonNodeFactory.instance.objectNode();

        if (inscripcion.getInscripcionId() != null) {
            inscripcionNode.put("inscripcionId", inscripcion.getInscripcionId());
        }
        inscripcionNode.put("carreraId", inscripcion.getCarreraId()).
        	put("fechaInscripcion", inscripcion.getFechaInscripcion().toString()).
            put("emailUsuario",inscripcion.getEmailUsuario()).
            put("tarjeta",inscripcion.getTarjeta()).
            put("dorsal",inscripcion.getDorsal());
        
        return inscripcionNode;
    }

    public static ArrayNode toArrayNode(List<RestInscripcionDto> inscripciones) {

        ArrayNode inscripcionesNode = JsonNodeFactory.instance.arrayNode();
        for (int i = 0; i < inscripciones.size(); i++) {
            RestInscripcionDto inscripcionDto = inscripciones.get(i);
            ObjectNode movieObject = toObjectNode(inscripcionDto);
            inscripcionesNode.add(movieObject);
        }

        return inscripcionesNode;
    }

    public static RestInscripcionDto toServiceInscripcionDto(InputStream jsonMovie) throws ParsingException {
        try {
            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            JsonNode rootNode = objectMapper.readTree(jsonMovie);
            System.out.println(rootNode);

            if (rootNode.getNodeType() != JsonNodeType.OBJECT) {
                throw new ParsingException("Unrecognized JSON (object expected)");
            } else {
                ObjectNode carreraObject = (ObjectNode) rootNode;

                JsonNode carreraIdNode = carreraObject.get("inscripcionId");
                Long inscripcionId = (carreraIdNode != null) ? carreraIdNode.longValue() : null;

                Long carreraId = carreraObject.get("carreraId").longValue();
                LocalDateTime fechaInscripcion = LocalDateTime.now();

                String emailUsuario =  carreraObject.get("emailUsuario").textValue().trim();
                String tarjeta =  carreraObject.get("tarjeta").textValue().trim();
                int dorsal =  carreraObject.get("dorsal").intValue();

                return new RestInscripcionDto(inscripcionId, carreraId, fechaInscripcion, emailUsuario,tarjeta, dorsal);
            }
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

}
