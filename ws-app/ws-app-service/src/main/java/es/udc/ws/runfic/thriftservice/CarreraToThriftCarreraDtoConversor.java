package es.udc.ws.runfic.thriftservice;

import es.udc.ws.runfic.model.carrera.Carrera;
import es.udc.ws.runfic.thrift.ThriftCarreraDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CarreraToThriftCarreraDtoConversor {

    static DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    public static Carrera toCarrera(ThriftCarreraDto carrera) {
        return new Carrera(carrera.getCarreraId(), carrera.getCiudad(), carrera.getDescripcion(),
                LocalDateTime.parse(carrera.getFechaCarrera(),formatter), (float) carrera.getPrecio(), carrera.getPlazasDisp());
    }

    public static List<ThriftCarreraDto> toThriftCarreraDtos(List<Carrera> carreras) {

        List<ThriftCarreraDto> dtos = new ArrayList<>(carreras.size());

        for (Carrera carrera : carreras) {
            dtos.add(toThriftCarreraDto(carrera));
        }
        return dtos;

    }

    public static ThriftCarreraDto toThriftCarreraDto(Carrera carrera) {

        return new ThriftCarreraDto(carrera.getCarreraId(), carrera.getCiudad(), carrera.getDescripcion(),
                carrera.getFechaCarrera().toString(), carrera.getPrecioCarrera(), carrera.getMaxParticipantes());

    }

}
