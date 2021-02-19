package es.udc.ws.runfic.thriftservice;

import es.udc.ws.runfic.model.carrera.Carrera;
import es.udc.ws.runfic.model.inscripcion.Inscripcion;
import es.udc.ws.runfic.thrift.ThriftCarreraDto;
import es.udc.ws.runfic.thrift.ThriftInscripcionDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class InscripcionToThriftInscripcionDtoConversor {

    static DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    public static Inscripcion toInscripcion(ThriftInscripcionDto inscripcion) {

        return new Inscripcion(inscripcion.getInscripcionId(), inscripcion.getEmailUsuario(), inscripcion.getCarreraId(),
                inscripcion.getTarjeta(), inscripcion.getDorsal(), LocalDateTime.now(), inscripcion.isEntregado());
    }

    public static List<ThriftInscripcionDto> toThriftInscripcionesDtos(List<Inscripcion> inscripciones) {

        List<ThriftInscripcionDto> dtos = new ArrayList<>(inscripciones.size());

        for (Inscripcion inscripcion : inscripciones) {
            dtos.add(toThriftInscripcionDto(inscripcion));
        }
        return dtos;

    }

    public static ThriftInscripcionDto toThriftInscripcionDto(Inscripcion inscripcion) {

        return new ThriftInscripcionDto(inscripcion.getInscripcionId(), inscripcion.getCarrera(), inscripcion.getEmailUsuario(),
                inscripcion.getTarjeta(), inscripcion.getDorsal(), inscripcion.getEntregado());

    }
}
