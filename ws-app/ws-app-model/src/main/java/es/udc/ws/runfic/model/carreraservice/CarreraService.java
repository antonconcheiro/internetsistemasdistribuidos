package es.udc.ws.runfic.model.carreraservice;

import es.udc.ws.runfic.model.carrera.Carrera;
import es.udc.ws.runfic.model.carreraservice.exceptions.*;
import es.udc.ws.runfic.model.inscripcion.Inscripcion;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface CarreraService {

    Carrera addCarrera(Carrera carrera) throws InputValidationException;

    void updateCarrera(Carrera carrera) throws InputValidationException,
            InstanceNotFoundException;

    void removeCarrera(Long carreraId) throws InstanceNotFoundException;
    
    Carrera findCarrera(Long carreraId) throws InstanceNotFoundException;

    List<Carrera> findCarreras(LocalDate fechaMax) throws InputValidationException, OutOfTimeException;

    List<Carrera> findCarrerasC(LocalDate fechaMax, String ciudad) throws InputValidationException, OutOfTimeException;

    Inscripcion findInscripcion(Long inscripcionId)
            throws InstanceNotFoundException;

    Inscripcion addInscripcion(String emailUsuario, Long carrera, String tarjeta, LocalDateTime creationTime)
            throws InputValidationException, InstanceNotFoundException, MaxCapacityException, AlreadyRegisteredException, OutOfTimeException;

    void removeInscripcion(Long inscripcionId)
            throws InstanceNotFoundException;

    List<Inscripcion> getInscripciones(String emailUsuario)
            throws InstanceNotFoundException,InputValidationException;

    int recogerDorsal(Long codigoDorsal, String tarjeta)
        throws InstanceNotFoundException,DorsalEntregadoException, TarjetaNotEqualException, InputValidationException;

}
