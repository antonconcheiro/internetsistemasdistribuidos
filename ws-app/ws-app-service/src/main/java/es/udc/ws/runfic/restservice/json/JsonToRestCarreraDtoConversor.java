package es.udc.ws.runfic.restservice.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import es.udc.ws.runfic.restservice.dto.RestCarreraDto;
import es.udc.ws.util.json.ObjectMapperFactory;
import es.udc.ws.util.json.exceptions.ParsingException;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class JsonToRestCarreraDtoConversor {

	public static ObjectNode toObjectNode(RestCarreraDto carrera) {

		ObjectNode carreraObject = JsonNodeFactory.instance.objectNode();

		if (carrera.getCarreraId() != null) {
			carreraObject.put("carreraId", carrera.getCarreraId());
		}

		carreraObject.put("ciudad", carrera.getCiudad()).
		    put("descripcion", carrera.getDescripcion()).
			put("fechaCarrera", carrera.getFechaCarrera().toString()).
			put("precioCarrera",carrera.getPrecioCarrera()).
			put("maxParticipantes", carrera.getMaxParticipantes()).
			put("numeroInscritos", carrera.getNumeroInscritos());

		return carreraObject;
	}

	public static ArrayNode toArrayNode(List<RestCarreraDto> carreras) {

		ArrayNode carrerasNode = JsonNodeFactory.instance.arrayNode();
		for (int i = 0; i < carreras.size(); i++) {
			RestCarreraDto movieDto = carreras.get(i);
			ObjectNode movieObject = toObjectNode(movieDto);
			carrerasNode.add(movieObject);
		}

		return carrerasNode;
	}

	public static RestCarreraDto toServiceCarreraDto(InputStream jsonMovie) throws ParsingException {
		try {
			ObjectMapper objectMapper = ObjectMapperFactory.instance();
			JsonNode rootNode = objectMapper.readTree(jsonMovie);

			if (rootNode.getNodeType() != JsonNodeType.OBJECT) {
				throw new ParsingException("Unrecognized JSON (object expected)");
			} else {
				ObjectNode carreraObject = (ObjectNode) rootNode;

				JsonNode carreraIdNode = carreraObject.get("carreraId");
				Long carreraId = (carreraIdNode != null) ? carreraIdNode.longValue() : null;

				String ciudad = carreraObject.get("ciudad").textValue().trim();

				DateTimeFormatter formatter1 = DateTimeFormatter.ISO_DATE_TIME;
				LocalDateTime fecha = LocalDateTime.parse(carreraObject.get("fechaCarrera").textValue().trim(), formatter1);
				String description = carreraObject.get("descripcion").textValue().trim();
				float precioCarrera = carreraObject.get("precioCarrera").floatValue();
				int maxParticipantes =  carreraObject.get("maxParticipantes").intValue();
				int numeroInscritos =  carreraObject.get("numeroInscritos").intValue();

				return new RestCarreraDto(carreraId, ciudad, description, fecha,precioCarrera, maxParticipantes, numeroInscritos);
			}
		} catch (ParsingException ex) {
			throw ex;
		} catch (Exception e) {
			throw new ParsingException(e);
		}
	}

}
