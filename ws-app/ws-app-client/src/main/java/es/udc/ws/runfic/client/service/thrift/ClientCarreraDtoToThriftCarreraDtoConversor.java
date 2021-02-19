package es.udc.ws.runfic.client.service.thrift;

import es.udc.ws.runfic.client.service.dto.ClientCarreraDto;
import es.udc.ws.runfic.thrift.ThriftCarreraDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ClientCarreraDtoToThriftCarreraDtoConversor {

    static DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    public static ThriftCarreraDto toThriftCarreraDto(

            ClientCarreraDto clientCarreraDto) {
        Long carreraId = clientCarreraDto.getCarreraId();


        return new ThriftCarreraDto(
            carreraId == null ? -1 : carreraId.longValue(),
            clientCarreraDto.getCiudad(),
            clientCarreraDto.getDescripcion(),
            clientCarreraDto.getFechaCarrera().toString(),
            clientCarreraDto.getPrecioCarrera(),
            clientCarreraDto.getPlazasDisp());


    }

    public static List<ClientCarreraDto> toClientCarreraDto(List<ThriftCarreraDto> carreras) {

        List<ClientCarreraDto> clientCarreraDtos = new ArrayList<>(carreras.size());

        for (ThriftCarreraDto carrera : carreras) {
            clientCarreraDtos.add(toClientCarreraDto(carrera));
        }
        return clientCarreraDtos;

    }

    public static ClientCarreraDto toClientCarreraDto(ThriftCarreraDto carrera) {

        return new ClientCarreraDto(
                carrera.getCarreraId(),
                carrera.getCiudad(),
                carrera.getDescripcion(),
                LocalDateTime.parse(carrera.getFechaCarrera(),formatter),
                (float) carrera.getPrecio(),
                carrera.getPlazasDisp());

    }

}
