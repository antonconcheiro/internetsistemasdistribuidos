package es.udc.ws.runfic.restservice.servlets;

import es.udc.ws.runfic.model.carrera.Carrera;
import es.udc.ws.runfic.model.carreraservice.CarreraServiceFactory;
import es.udc.ws.runfic.model.carreraservice.exceptions.*;
import es.udc.ws.runfic.model.inscripcion.Inscripcion;
import es.udc.ws.runfic.restservice.dto.CarreraToRestCarreraDtoConversor;
import es.udc.ws.runfic.restservice.dto.InscripcionToRestInscripcionDtoConversor;
import es.udc.ws.runfic.restservice.dto.RestCarreraDto;
import es.udc.ws.runfic.restservice.dto.RestInscripcionDto;
import es.udc.ws.runfic.restservice.json.JsonToExceptionConversor;
import es.udc.ws.runfic.restservice.json.JsonToRestCarreraDtoConversor;
import es.udc.ws.runfic.restservice.json.JsonToRestInscripcionDtoConversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.json.exceptions.ParsingException;
import es.udc.ws.util.servlet.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InscripcionServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String path = ServletUtils.normalizePath(req.getPathInfo());
        if (path != null && path.length() > 0) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    JsonToExceptionConversor.toInputValidationException(
                            new InputValidationException("Invalid Request: " + "invalid path " + path)),
                    null);
            return;
        }

        RestInscripcionDto inscripcionDto;
        try {
            inscripcionDto = JsonToRestInscripcionDtoConversor.toServiceInscripcionDto(req.getInputStream());
        } catch (ParsingException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST, JsonToExceptionConversor
                    .toInputValidationException(new InputValidationException(ex.getMessage())), null);
            return;
        }
        Inscripcion inscripcion = InscripcionToRestInscripcionDtoConversor.toInscripcion(inscripcionDto);
        try {
            inscripcion = CarreraServiceFactory.getService().addInscripcion(
                    inscripcion.getEmailUsuario(), inscripcion.getCarrera(), inscripcion.getTarjeta(), inscripcion.getFechaInscripcion());

        } catch (InputValidationException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    JsonToExceptionConversor.toInputValidationException(ex), null);
            return;
        } catch (MaxCapacityException e) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_GONE,
                    JsonToExceptionConversor.toMaxCapacityException(e), null);
        } catch (OutOfTimeException e){
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_GONE,
                    JsonToExceptionConversor.toOutOfTimeException(e), null);
        } catch (InstanceNotFoundException e) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NOT_FOUND,
                    JsonToExceptionConversor.toInstanceNotFoundException(e), null);
            return;
        } catch (AlreadyRegisteredException e) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_GONE,
                    JsonToExceptionConversor.toAlreadyRegisteredException(e), null);
        }

        inscripcionDto = InscripcionToRestInscripcionDtoConversor.toRestInscripcionDto(inscripcion);

        String carreraURL = ServletUtils.normalizePath(req.getRequestURL().toString()) + "/" + inscripcion.getInscripcionId();
        Map<String, String> headers = new HashMap<>(1);
        headers.put("Location", carreraURL);

        System.out.println("inscripcionDTO " + inscripcionDto);
        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_CREATED,
                JsonToRestInscripcionDtoConversor.toObjectNode(inscripcionDto), headers);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = ServletUtils.normalizePath(req.getPathInfo());
        if (path == null || path.length() == 0) {
            String mail = req.getParameter("mail");

            List<Inscripcion> inscripciones = null;
            Inscripcion inscripcion = null;

            try {
                if (mail != null) {
                    inscripciones = CarreraServiceFactory.getService().getInscripciones(mail);
                    if (inscripciones != null) {
                        List<RestInscripcionDto> inscripcionDtos = InscripcionToRestInscripcionDtoConversor.toRestInscripcionesDto(inscripciones);
                        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_OK,
                                JsonToRestInscripcionDtoConversor.toArrayNode(inscripcionDtos), null);
                    } else {
                        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NOT_FOUND, JsonToExceptionConversor.toInputValidationException(new InputValidationException(
                                "Invalid Request: " + "invalid mail")), null);
                    }
                }

            } catch (InstanceNotFoundException e) {
                ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NOT_FOUND, JsonToExceptionConversor.toInstanceNotFoundException(new InstanceNotFoundException(inscripcion, "inscripcion")), null);
            } catch (InputValidationException e) {
                ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                        JsonToExceptionConversor.toInputValidationException(e), null);
            }

        } else {
            String inscripcionIdAsString = path.substring(1);
            Long inscripcionId;
            try {
                inscripcionId = Long.valueOf(inscripcionIdAsString);
            } catch (NumberFormatException ex) {
                ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                        JsonToExceptionConversor.toInputValidationException(
                                new InputValidationException("Invalid Request: " + "invalid inscripcion id '" + inscripcionIdAsString)),
                        null);
                return;
            }
            Inscripcion inscripcion;
            try {
                inscripcion = CarreraServiceFactory.getService().findInscripcion(inscripcionId);
            } catch (InstanceNotFoundException ex) {
                ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NOT_FOUND,
                        JsonToExceptionConversor.toInstanceNotFoundException(ex), null);
                return;
            }

            RestInscripcionDto inscripcionDto = InscripcionToRestInscripcionDtoConversor.toRestInscripcionDto(inscripcion);

            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_OK,
                    JsonToRestInscripcionDtoConversor.toObjectNode(inscripcionDto), null);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String path = ServletUtils.normalizePath(req.getPathInfo());
        if (path == null || path.length() == 0) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST, JsonToExceptionConversor
                            .toInputValidationException(new InputValidationException("Invalid Request: " + "invalid inscripcion id")),
                    null);
            return;
        }
        String inscripcionIdAsString = path.substring(1);
        //String tarjetaAsString = ;
        Long inscripcionId;
        //String tarjeta;

        try {
            inscripcionId = Long.valueOf(inscripcionIdAsString);
        } catch (NumberFormatException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    JsonToExceptionConversor.toInputValidationException(new InputValidationException(
                            "Invalid Request: " + "invalid inscripcion id '" + inscripcionIdAsString + "'")),
                    null);
            return;
        }

        RestInscripcionDto inscripcionDto;
        try {
            inscripcionDto = JsonToRestInscripcionDtoConversor.toServiceInscripcionDto(req.getInputStream());
        } catch (ParsingException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST, JsonToExceptionConversor
                    .toInputValidationException(new InputValidationException(ex.getMessage())), null);
            return;

        }

        if (!inscripcionId.equals(inscripcionDto.getInscripcionId())) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST, JsonToExceptionConversor
                            .toInputValidationException(new InputValidationException("Invalid Request: " + "invalid movieId")),
                    null);
            return;
        }
        Inscripcion inscripcion = InscripcionToRestInscripcionDtoConversor.toInscripcion(inscripcionDto);

        try {
            CarreraServiceFactory.getService().recogerDorsal(inscripcion.getInscripcionId(),inscripcion.getTarjeta());
        } catch (InputValidationException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    JsonToExceptionConversor.toInputValidationException(ex), null);
            return;
        } catch (InstanceNotFoundException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NOT_FOUND,
                    JsonToExceptionConversor.toInstanceNotFoundException(ex), null);
            return;
        } catch (DorsalEntregadoException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_GONE,
                    JsonToExceptionConversor.toDorsalEntregadoException(ex), null);
            return;
        } catch (TarjetaNotEqualException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_FORBIDDEN,
                    JsonToExceptionConversor.toTarjetaNotEqualException(ex), null);
            return;
        }
        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NO_CONTENT, null, null);
    }
}
