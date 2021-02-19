package es.udc.ws.runfic.restservice.dto;


import es.udc.ws.runfic.model.carrera.Carrera;
import es.udc.ws.runfic.model.inscripcion.Inscripcion;

import java.util.ArrayList;
import java.util.List;

public class InscripcionToRestInscripcionDtoConversor {

    public static RestInscripcionDto toRestInscripcionDto(Inscripcion inscripcion) {
        return new RestInscripcionDto(inscripcion.getInscripcionId(), inscripcion.getCarrera(), inscripcion
                .getFechaInscripcion(), inscripcion.getEmailUsuario()
                ,inscripcion.getTarjeta(),inscripcion.getDorsal());
    }

    public static List<RestInscripcionDto> toRestInscripcionesDto(List<Inscripcion> inscripciones) {
        List<RestInscripcionDto> inscripcionDtos = new ArrayList<>(inscripciones.size());
        for (int i = 0; i < inscripciones.size(); i++) {
            Inscripcion inscripcion = inscripciones.get(i);
            inscripcionDtos.add(toRestInscripcionDto(inscripcion));
        }
        return inscripcionDtos;
    }

    public static Inscripcion toInscripcion(RestInscripcionDto inscripcion) {
        return new Inscripcion(inscripcion.getInscripcionId(), inscripcion.getEmailUsuario(),
                inscripcion.getCarreraId(),inscripcion.getTarjeta(),inscripcion.getDorsal(),inscripcion.getFechaInscripcion());
    }

}
