package es.udc.ws.runfic.restservice.servlets;

import es.udc.ws.runfic.model.carrera.Carrera;
import es.udc.ws.runfic.model.carreraservice.CarreraServiceFactory;
import es.udc.ws.runfic.model.carreraservice.exceptions.OutOfTimeException;
import es.udc.ws.runfic.restservice.dto.CarreraToRestCarreraDtoConversor;
import es.udc.ws.runfic.restservice.dto.RestCarreraDto;
import es.udc.ws.runfic.restservice.json.JsonToExceptionConversor;
import es.udc.ws.runfic.restservice.json.JsonToRestCarreraDtoConversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.json.exceptions.ParsingException;
import es.udc.ws.util.servlet.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarreraServlet extends HttpServlet {


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
        RestCarreraDto carreraDto;
        try {
            carreraDto = JsonToRestCarreraDtoConversor.toServiceCarreraDto(req.getInputStream());
        } catch (ParsingException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST, JsonToExceptionConversor
                    .toInputValidationException(new InputValidationException(ex.getMessage())), null);
            return;
        }
        Carrera carrera = CarreraToRestCarreraDtoConversor.toCarrera(carreraDto);
        try {
            carrera = CarreraServiceFactory.getService().addCarrera(carrera);
        } catch (InputValidationException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    JsonToExceptionConversor.toInputValidationException(ex), null);
            return;
        }
        carreraDto = CarreraToRestCarreraDtoConversor.toRestCarreraDto(carrera);

        String carreraURL = ServletUtils.normalizePath(req.getRequestURL().toString()) + "/" + carrera.getCarreraId();
        Map<String, String> headers = new HashMap<>(1);
        headers.put("Location", carreraURL);



        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_CREATED,
                JsonToRestCarreraDtoConversor.toObjectNode(carreraDto), headers);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String path = ServletUtils.normalizePath(req.getPathInfo());
        if (path == null || path.length() == 0) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST, JsonToExceptionConversor
                            .toInputValidationException(new InputValidationException("Invalid Request: " + "invalid carrera id")),
                    null);
            return;
        }
        String carreraIdAsString = path.substring(1);
        Long carreraId;
        try {
            carreraId = Long.valueOf(carreraIdAsString);
        } catch (NumberFormatException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    JsonToExceptionConversor.toInputValidationException(new InputValidationException(
                            "Invalid Request: " + "invalid carrera id '" + carreraIdAsString + "'")),
                    null);
            return;
        }

        RestCarreraDto carreraDto;
        try {
            carreraDto = JsonToRestCarreraDtoConversor.toServiceCarreraDto(req.getInputStream());
        } catch (ParsingException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST, JsonToExceptionConversor
                    .toInputValidationException(new InputValidationException(ex.getMessage())), null);
            return;

        }
        if (!carreraId.equals(carreraDto.getCarreraId())) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST, JsonToExceptionConversor
                            .toInputValidationException(new InputValidationException("Invalid Request: " + "invalid carreraId")),
                    null);
            return;
        }
        Carrera carrera = CarreraToRestCarreraDtoConversor.toCarrera(carreraDto);
        try {
            CarreraServiceFactory.getService().updateCarrera(carrera);
        } catch (InputValidationException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    JsonToExceptionConversor.toInputValidationException(ex), null);
            return;
        } catch (InstanceNotFoundException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NOT_FOUND,
                    JsonToExceptionConversor.toInstanceNotFoundException(ex), null);
            return;
        }
        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NO_CONTENT, null, null);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = ServletUtils.normalizePath(req.getPathInfo());
        if (path == null || path.length() == 0) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST, JsonToExceptionConversor
                            .toInputValidationException(new InputValidationException("Invalid Request: " + "invalid carrera id")),
                    null);
            return;
        }
        String carreraIdAsString = path.substring(1);
        Long carreraId;
        try {
            carreraId = Long.valueOf(carreraIdAsString);
        } catch (NumberFormatException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    JsonToExceptionConversor.toInputValidationException(new InputValidationException(
                            "Invalid Request: " + "invalid carrera id '" + carreraIdAsString + "'")),
                    null);

            return;
        }
        try {
            CarreraServiceFactory.getService().removeCarrera(carreraId);
        } catch (InstanceNotFoundException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NOT_FOUND,
                    JsonToExceptionConversor.toInstanceNotFoundException(ex), null);
            return;
        }
        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NO_CONTENT, null, null);
    }


    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = ServletUtils.normalizePath(req.getPathInfo());
        if (path == null || path.length() == 0) {

            LocalDate carreraDate;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String date = req.getParameter("fechaMax");
            String ciudad = req.getParameter("ciudad");

            if (date != null) {
                carreraDate = LocalDate.parse(date, formatter);
            } else {
                carreraDate = null;
            }

            List<Carrera> carreras = null;

            try {
                if (carreraDate == null) {
                    if(ciudad==null)
                        carreras = CarreraServiceFactory.getService().findCarreras(LocalDate.now().plusYears(1000));
                    else
                        carreras = CarreraServiceFactory.getService().findCarrerasC(LocalDate.now().plusYears(1000), ciudad);
                }else
                    carreras = CarreraServiceFactory.getService().findCarrerasC(carreraDate, ciudad);
            } catch (InputValidationException e) {
                ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST, JsonToExceptionConversor.toInputValidationException(new InputValidationException(
                        "Invalid Request: " + "invalid ciudad")), null);
            } catch (OutOfTimeException e){
                ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST, JsonToExceptionConversor.toInputValidationException(new InputValidationException(
                        "Invalid Request: " + "invalid fecha. No se puede buscar carreras anteriores a hoy.")), null);
            }
            System.out.println(carreras.size());
            if (carreras != null) {
                List<RestCarreraDto> carreraDtos = CarreraToRestCarreraDtoConversor.toRestCarreraDtos(carreras);
                ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_OK,
                        JsonToRestCarreraDtoConversor.toArrayNode(carreraDtos), null);
            }

        } else if (path != null && path.length() > 0) {
            String carreraIdAsString = path.substring(1);
            Carrera carrera = null;
            try {
                carrera = CarreraServiceFactory.getService().findCarrera(Long.parseLong(carreraIdAsString));
            } catch (InstanceNotFoundException e) {
                ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NOT_FOUND,
                        JsonToExceptionConversor.toInstanceNotFoundException(e), null);
            }

            if (carrera != null) {
                RestCarreraDto carreraDto = CarreraToRestCarreraDtoConversor.toRestCarreraDto(carrera);

                ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_OK,
                        JsonToRestCarreraDtoConversor.toObjectNode(carreraDto), null);
            } else {
                ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NOT_FOUND, JsonToExceptionConversor.toInputValidationException(new InputValidationException(
                        "Invalid Request: " + "invalid carrera id '" + carreraIdAsString + "'")), null);
            }

        } else {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    JsonToExceptionConversor.toInputValidationException(
                            new InputValidationException("Invalid Request: " + "invalid path " + path)),
                    null);
        }
    }
}
