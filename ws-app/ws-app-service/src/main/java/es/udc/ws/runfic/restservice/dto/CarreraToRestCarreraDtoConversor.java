package es.udc.ws.runfic.restservice.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import es.udc.ws.runfic.model.carrera.Carrera;


public class CarreraToRestCarreraDtoConversor {

	public static List<RestCarreraDto> toRestCarreraDtos(List<Carrera> carreras) {
		List<RestCarreraDto> carreraDtos = new ArrayList<>(carreras.size());
		for (int i = 0; i < carreras.size(); i++) {
			Carrera carrera = carreras.get(i);
			carreraDtos.add(toRestCarreraDto(carrera));
		}
		return carreraDtos;
	}

	public static RestCarreraDto toRestCarreraDto(Carrera carrera) {
		return new RestCarreraDto(carrera.getCarreraId(), carrera.getCiudad(), carrera.getDescripcion(), carrera.getFechaCarrera(),
				carrera.getPrecioCarrera(),carrera.getMaxParticipantes(), carrera.getNumeroInscritos());
	}

	public static Carrera toCarrera(RestCarreraDto carrera) {
		return new Carrera(carrera.getCarreraId(), carrera.getCiudad(), carrera.getDescripcion(), carrera.getFechaCarrera(), carrera.getPrecioCarrera(),
				carrera.getMaxParticipantes(), LocalDateTime.now(), carrera.getNumeroInscritos());
	}

}
