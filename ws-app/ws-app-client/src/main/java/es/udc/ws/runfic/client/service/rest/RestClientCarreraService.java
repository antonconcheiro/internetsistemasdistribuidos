package es.udc.ws.runfic.client.service.rest;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.udc.ws.runfic.client.service.ClientCarreraService;
import es.udc.ws.runfic.client.service.dto.ClientCarreraDto;
import es.udc.ws.runfic.client.service.dto.ClientInscripcionDto;
import es.udc.ws.runfic.client.service.exceptions.*;
import es.udc.ws.runfic.client.service.rest.json.JsonToClientCarreraDtoConversor;
import es.udc.ws.runfic.client.service.rest.json.JsonToClientExceptionConversor;
import es.udc.ws.runfic.client.service.rest.json.JsonToClientInscripcionDtoConversor;
import es.udc.ws.util.configuration.ConfigurationParametersManager;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.json.ObjectMapperFactory;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RestClientCarreraService implements ClientCarreraService {

    private final static String ENDPOINT_ADDRESS_PARAMETER = "RestClientCarreraService.endpointAddress";
    private String endpointAddress;

    @Override
    public Long addCarrera(ClientCarreraDto carrera) throws InputValidationException {

        try {
            HttpResponse response = Request.Post(getEndpointAddress() + "carrera").
                    bodyStream(toInputStream(carrera), ContentType.create("application/json")).
                    execute().returnResponse();

            validateStatusCode(HttpStatus.SC_CREATED, response);

            return JsonToClientCarreraDtoConversor.toClientCarreraDto(response.getEntity().getContent()).getCarreraId();

        } catch (InputValidationException e) {
            throw e;
        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }

    @Override
    public ClientCarreraDto findCarrera(Long carreraId) throws InstanceNotFoundException {

        try {
            HttpResponse response = Request.Get(getEndpointAddress() + "carrera/"
                    + carreraId).execute().returnResponse();

            validateStatusCode(HttpStatus.SC_OK, response);

            return JsonToClientCarreraDtoConversor.toClientCarreraDto(response.getEntity()
                    .getContent());


        }catch (InstanceNotFoundException e){
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<ClientCarreraDto> findCarreras(LocalDate fechaMax, String ciudad) throws InputValidationException {

        try {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            HttpResponse response = Request.Get(getEndpointAddress() + "carrera/"
                    + "?fechaMax=" + URLEncoder.encode(fechaMax.format(formatter), "UTF-8")
                    + "&ciudad=" + URLEncoder.encode(ciudad, "UTF-8")).execute().returnResponse();

            validateStatusCode(HttpStatus.SC_OK, response);

            return JsonToClientCarreraDtoConversor.toClientCarreraDtos(response.getEntity()
                    .getContent());
        }catch (InputValidationException e){
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Long addInscripcion(ClientInscripcionDto inscripcion) throws InputValidationException, MaxCapacityException, OutOfTimeException, AlreadyRegisteredException, InstanceNotFoundException {
        try {
            HttpResponse response = Request.Post(getEndpointAddress() + "inscripcion").
                    bodyStream(toInputStream(inscripcion), ContentType.create("application/json")).
                    execute().returnResponse();

            validateStatusCode(HttpStatus.SC_CREATED, response);

            return JsonToClientInscripcionDtoConversor.toClientInscripcionDto(response.getEntity().getContent()).getInscripcionId();

        } catch (InputValidationException e) {
            throw e;
        }catch (MaxCapacityException e) {
            throw new MaxCapacityException(e.getMaxCapacity());
        }catch (OutOfTimeException e) {
            throw new OutOfTimeException();
        }catch (AlreadyRegisteredException e) {
            throw new AlreadyRegisteredException(e.getMail());
        }catch (InstanceNotFoundException e){
            throw new InstanceNotFoundException(e.getInstanceId(),e.getInstanceType());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int recogerDorsal(Long inscripcionId, String tarjeta)
            throws InputValidationException, InstanceNotFoundException, ClientDorsalEntregadoException, ClientTarjetaNotEqualException {
        try{
            ClientInscripcionDto inscripcionDto = this.findInscripcion(inscripcionId);
            inscripcionDto.setTarjeta(tarjeta);

            HttpResponse response1 = Request.Put(getEndpointAddress() + "inscripcion/" + inscripcionDto.getInscripcionId()).
                    bodyStream(toInputStream(inscripcionDto), ContentType.create("application/json")).
                    execute().returnResponse();

            validateStatusCode(HttpStatus.SC_NO_CONTENT, response1);

            inscripcionDto = this.findInscripcion(inscripcionId);

            return inscripcionDto.getDorsal();

        }catch (InputValidationException e) {
            throw e;
        }catch (InstanceNotFoundException e) {
            throw e;
        }catch (ClientDorsalEntregadoException e) {
            throw new ClientDorsalEntregadoException(inscripcionId);
        }catch (ClientTarjetaNotEqualException e){
            throw new ClientTarjetaNotEqualException(inscripcionId,tarjeta);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ClientInscripcionDto findInscripcion(Long inscripcionId) throws InstanceNotFoundException{
        try{
            HttpResponse response = Request.Get(getEndpointAddress() + "inscripcion/" + inscripcionId).
                    execute().returnResponse();

            validateStatusCode(HttpStatus.SC_OK, response);

            return JsonToClientInscripcionDtoConversor.toClientInscripcionDto(response.getEntity()
                    .getContent());
        }catch (InstanceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ClientInscripcionDto> findInscripciones(String email) throws InputValidationException {
        try {
            HttpResponse response = Request.Get(getEndpointAddress() + "inscripcion?mail=" + email).
                    execute().returnResponse();

            validateStatusCode(HttpStatus.SC_OK, response);

            return JsonToClientInscripcionDtoConversor.toClientInscripcionDtos(response.getEntity()
                    .getContent());
        }catch (InputValidationException e){
            throw e;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private synchronized String getEndpointAddress() {
        if (endpointAddress == null) {
            endpointAddress = ConfigurationParametersManager
                    .getParameter(ENDPOINT_ADDRESS_PARAMETER);
        }
        return endpointAddress;
    }

    private InputStream toInputStream(ClientCarreraDto carrera) {

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            objectMapper.writer(new DefaultPrettyPrinter()).writeValue(outputStream,
                    JsonToClientCarreraDtoConversor.toObjectNode(carrera));

            return new ByteArrayInputStream(outputStream.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private InputStream toInputStream(ClientInscripcionDto inscripcion) {

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            objectMapper.writer(new DefaultPrettyPrinter()).writeValue(outputStream,
                    JsonToClientInscripcionDtoConversor.toObjectNode(inscripcion));

            return new ByteArrayInputStream(outputStream.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void validateStatusCode(int successCode, HttpResponse response) throws Exception {
        try {
            int statusCode = response.getStatusLine().getStatusCode();
            /* Success? */
            System.out.println("code " + statusCode);
            if (statusCode == successCode) {
                return;
            }

            /* Handler error. */
            switch (statusCode) {

                case HttpStatus.SC_NOT_FOUND:
                    throw JsonToClientExceptionConversor.fromNotFoundErrorCode(
                            response.getEntity().getContent());

                case HttpStatus.SC_BAD_REQUEST:
                    throw JsonToClientExceptionConversor.fromBadRequestErrorCode(
                            response.getEntity().getContent());

                case HttpStatus.SC_FORBIDDEN:
                    throw JsonToClientExceptionConversor.fromForbiddenErrorCode(
                            response.getEntity().getContent());

                case HttpStatus.SC_GONE:
                    throw JsonToClientExceptionConversor.fromGoneErrorCode(
                            response.getEntity().getContent());

                default:
                    throw new RuntimeException("HTTP error; status code = "
                            + statusCode);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
