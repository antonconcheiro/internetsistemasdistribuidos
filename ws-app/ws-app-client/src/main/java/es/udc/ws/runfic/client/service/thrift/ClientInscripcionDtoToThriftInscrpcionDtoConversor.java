package es.udc.ws.runfic.client.service.thrift;

import es.udc.ws.runfic.client.service.dto.ClientCarreraDto;
import es.udc.ws.runfic.client.service.dto.ClientInscripcionDto;
import es.udc.ws.runfic.thrift.ThriftCarreraDto;
import es.udc.ws.runfic.thrift.ThriftInscripcionDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ClientInscripcionDtoToThriftInscrpcionDtoConversor {

    static DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    public static ThriftInscripcionDto toThriftInscripcionDto(
            ClientInscripcionDto clientInscripcionDto) {

        Long inscripcionId = clientInscripcionDto.getInscripcionId();
        return new ThriftInscripcionDto(
                inscripcionId == null ? -1 : inscripcionId.longValue(),
                clientInscripcionDto.getCarreraId(),
                clientInscripcionDto.getEmailUsuario(),
                clientInscripcionDto.getTarjeta(),
                clientInscripcionDto.getDorsal(),
                clientInscripcionDto.getEntregado());

    }

    public static List<ClientInscripcionDto> toClientInscripcionDto(List<ThriftInscripcionDto> inscripciones) {

        List<ClientInscripcionDto> clientInscripcionDtos = new ArrayList<>(inscripciones.size());

        for (ThriftInscripcionDto inscripcion : inscripciones) {
            clientInscripcionDtos.add(toClientInscripcionDto(inscripcion));
        }
        return clientInscripcionDtos;

    }

    private static ClientInscripcionDto toClientInscripcionDto(ThriftInscripcionDto inscripcion) {
        return new ClientInscripcionDto(
                inscripcion.getInscripcionId(),
                inscripcion.getCarreraId(),
                inscripcion.getEmailUsuario(),
                inscripcion.getTarjeta(),
                inscripcion.getDorsal());

    }
}
