package es.udc.ws.runfic.client.service;

import es.udc.ws.runfic.client.service.dto.ClientCarreraDto;
import es.udc.ws.runfic.client.service.dto.ClientInscripcionDto;
import es.udc.ws.runfic.client.service.exceptions.*;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ClientCarreraService {

    Long addCarrera(ClientCarreraDto carrera)
            throws InputValidationException;

    ClientCarreraDto findCarrera(Long carreraId) throws InstanceNotFoundException;

    List<ClientCarreraDto> findCarreras(LocalDate fechaMax, String ciudad) throws InputValidationException;

    Long addInscripcion(ClientInscripcionDto inscripcion)
            throws InputValidationException, MaxCapacityException, OutOfTimeException, AlreadyRegisteredException, InstanceNotFoundException;

    int recogerDorsal(Long inscripcionId, String tarjeta)
            throws InputValidationException, InstanceNotFoundException, ClientDorsalEntregadoException, ClientTarjetaNotEqualException;

    ClientInscripcionDto findInscripcion(Long inscripcionId) throws InstanceNotFoundException;

    List<ClientInscripcionDto> findInscripciones(String email) throws InputValidationException;

}
