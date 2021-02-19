package es.udc.ws.runfic.thriftservice;

import es.udc.ws.runfic.model.carrera.Carrera;
import es.udc.ws.runfic.model.carreraservice.CarreraServiceFactory;
import es.udc.ws.runfic.model.carreraservice.exceptions.*;
import es.udc.ws.runfic.model.inscripcion.Inscripcion;
import es.udc.ws.runfic.thrift.*;

import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import org.apache.thrift.TException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ThriftCarreraServiceImpl implements ThriftCarreraService.Iface {

    static DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;

    @Override
    public long addCarrera(ThriftCarreraDto carrera) throws ThriftInputValidationException, TException {

        Carrera carreraDto = CarreraToThriftCarreraDtoConversor.toCarrera(carrera);

        try {
            return CarreraServiceFactory.getService().addCarrera(carreraDto).getCarreraId();
        } catch (InputValidationException e) {
            throw new ThriftInputValidationException(e.getMessage());
        }

    }

    @Override
    public ThriftCarreraDto findCarrera(long carreraId) throws TException {
        Carrera carrera = null;

        try {
            carrera = CarreraServiceFactory.getService().findCarrera(carreraId);

        } catch (InstanceNotFoundException e) {
            throw new ThriftInstanceNotFoundException(e.getInstanceId().toString(),e.getInstanceType());
        }

        return CarreraToThriftCarreraDtoConversor.toThriftCarreraDto(carrera);
    }

    @Override
    public List<ThriftCarreraDto> findCarreras(String fechaMax, String ciudad) throws ThriftInputValidationException, TException {

        var date = LocalDate.parse(fechaMax,formatter);
        List<Carrera> carreras = null;

        try {
            carreras = CarreraServiceFactory.getService().findCarrerasC(date , ciudad);

        } catch (InputValidationException e) {
            throw new ThriftInputValidationException(e.getMessage());
        } catch (OutOfTimeException e) {
            throw new ThriftOutOfTimeException();
        }

        return CarreraToThriftCarreraDtoConversor.toThriftCarreraDtos(carreras);
    }

    @Override
    public long addInscripcion(ThriftInscripcionDto inscripcion) throws ThriftInputValidationException, ThriftInstanceNotFoundException, ThriftMaxCapacityException, ThriftOutOfTimeException, ThriftAlreadyRegisteredException, TException {
        Inscripcion inscripcionDto = InscripcionToThriftInscripcionDtoConversor.toInscripcion(inscripcion);

        try {
            return CarreraServiceFactory.getService().addInscripcion(inscripcionDto.getEmailUsuario(),inscripcionDto.getCarrera(),inscripcionDto.getTarjeta(),inscripcionDto.getFechaInscripcion()).getInscripcionId();
        } catch (InputValidationException e) {
            throw new ThriftInputValidationException(e.getMessage());
        } catch (AlreadyRegisteredException e) {
            throw new ThriftAlreadyRegisteredException(e.getMessage());
        } catch (InstanceNotFoundException e) {
            throw new ThriftInstanceNotFoundException();
        } catch (OutOfTimeException e) {
            throw new ThriftOutOfTimeException();
        } catch (MaxCapacityException e) {
            throw new ThriftMaxCapacityException();
        }
    }


    @Override
    public int recogerDorsal(long inscripcionId, String tarjeta) throws ThriftInputValidationException, ThriftInstanceNotFoundException, ThriftClientDorsalEntregadoException, ThriftClientTarjetaNotEqualException, TException {

        try {
            return CarreraServiceFactory.getService().recogerDorsal(inscripcionId, tarjeta);

        } catch (DorsalEntregadoException e) {
            throw new ThriftClientDorsalEntregadoException();
        } catch (TarjetaNotEqualException e) {
            throw new ThriftClientTarjetaNotEqualException();
        } catch (InputValidationException e) {
            throw new ThriftInputValidationException(e.getMessage());
        } catch (InstanceNotFoundException e) {
            throw new ThriftInstanceNotFoundException();
        }
    }

    @Override
    public ThriftInscripcionDto findInscripcion(long inscripcionId) throws ThriftInstanceNotFoundException, TException {
        Inscripcion inscripcion = null;

        try {
            inscripcion = CarreraServiceFactory.getService().findInscripcion(inscripcionId);

        } catch (InstanceNotFoundException e) {
            throw new ThriftInstanceNotFoundException(e.getInstanceId().toString(),e.getInstanceType());
        }

        return InscripcionToThriftInscripcionDtoConversor.toThriftInscripcionDto(inscripcion);
    }

    @Override
    public List<ThriftInscripcionDto> findInscripciones(String email) throws ThriftInputValidationException, TException {
        List<Inscripcion> inscripciones = null;

        try {
            inscripciones = CarreraServiceFactory.getService().getInscripciones(email);

        } catch (InputValidationException e) {
            throw new ThriftInputValidationException(e.getMessage());
        } catch (InstanceNotFoundException e) {
            throw new ThriftInstanceNotFoundException();
        }

        return InscripcionToThriftInscripcionDtoConversor.toThriftInscripcionesDtos(inscripciones);
    }
    
}
