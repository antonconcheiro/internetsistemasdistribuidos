package es.udc.ws.runfic.client.service.thrift;

import es.udc.ws.runfic.client.service.ClientCarreraService;
import es.udc.ws.runfic.client.service.dto.ClientCarreraDto;
import es.udc.ws.runfic.client.service.dto.ClientInscripcionDto;
import es.udc.ws.runfic.client.service.exceptions.*;

import es.udc.ws.runfic.thrift.ThriftInputValidationException;
import es.udc.ws.runfic.thrift.ThriftInstanceNotFoundException;
import es.udc.ws.runfic.thrift.ThriftMaxCapacityException;

import es.udc.ws.runfic.thrift.ThriftCarreraService;

import es.udc.ws.util.configuration.ConfigurationParametersManager;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.THttpClient;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ThriftClientCarreraService implements ClientCarreraService {

    private final static String ENDPOINT_ADDRESS_PARAMETER =
            "ThriftClientCarreraService.endpointAddress";

    private final static String endpointAddress =
            ConfigurationParametersManager.getParameter(ENDPOINT_ADDRESS_PARAMETER);

    @Override
    public Long addCarrera(ClientCarreraDto carrera) throws InputValidationException {

        ThriftCarreraService.Client client = getClient();
        TTransport transport = client.getInputProtocol().getTransport();

        try  {

            transport.open();
            return client.addCarrera(ClientCarreraDtoToThriftCarreraDtoConversor.toThriftCarreraDto(carrera));

        } catch (ThriftInputValidationException e) {
            throw new InputValidationException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            transport.close();
        }

    }

    @Override
    public ClientCarreraDto findCarrera(Long carreraId) throws InstanceNotFoundException {
        ThriftCarreraService.Client client = getClient();
        TTransport transport = client.getInputProtocol().getTransport();

        try  {

            transport.open();

            return ClientCarreraDtoToThriftCarreraDtoConversor.toClientCarreraDto(client.findCarrera(carreraId));

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            transport.close();
        }
    }

    @Override
    public List<ClientCarreraDto> findCarreras(LocalDate fechaMax, String ciudad) throws InputValidationException {
        ThriftCarreraService.Client client = getClient();
        TTransport transport = client.getInputProtocol().getTransport();

        try  {

            transport.open();

            return ClientCarreraDtoToThriftCarreraDtoConversor.toClientCarreraDto(client.findCarreras(fechaMax.toString(), ciudad));

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            transport.close();
        }
    }

    @Override
    public Long addInscripcion(ClientInscripcionDto inscripcion) throws InputValidationException, MaxCapacityException, OutOfTimeException, AlreadyRegisteredException, InstanceNotFoundException {
        ThriftCarreraService.Client client = getClient();
        TTransport transport = client.getInputProtocol().getTransport();
        try  {

            transport.open();

            return client.addInscripcion(ClientInscripcionDtoToThriftInscrpcionDtoConversor.toThriftInscripcionDto(inscripcion));

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            transport.close();
        }
    }

    @Override
    public int recogerDorsal(Long inscripcionId, String tarjeta) throws InputValidationException, InstanceNotFoundException, ClientDorsalEntregadoException, ClientTarjetaNotEqualException {
        ThriftCarreraService.Client client = getClient();
        TTransport transport = client.getInputProtocol().getTransport();
        try  {

            transport.open();

            return client.recogerDorsal(inscripcionId,tarjeta);

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            transport.close();
        }
    }

    @Override
    public ClientInscripcionDto findInscripcion(Long inscripcionId) throws InstanceNotFoundException {
        return null;
    }

    @Override
    public List<ClientInscripcionDto> findInscripciones(String email) throws InputValidationException {
        ThriftCarreraService.Client client = getClient();
        TTransport transport = client.getInputProtocol().getTransport();
        try  {

            transport.open();

            return ClientInscripcionDtoToThriftInscrpcionDtoConversor.toClientInscripcionDto(client.findInscripciones(email));

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            transport.close();
        }
    }

    private ThriftCarreraService.Client getClient() {

        try {

            TTransport transport = new THttpClient(endpointAddress);
            TProtocol protocol = new TBinaryProtocol(transport);

            return new ThriftCarreraService.Client(protocol);

        } catch (TTransportException e) {
            throw new RuntimeException(e);
        }

    }

}
