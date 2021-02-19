package es.udc.ws.runfic.test.model.carreraservice;

import es.udc.ws.runfic.model.carrera.Carrera;
import es.udc.ws.runfic.model.carreraservice.CarreraService;
import es.udc.ws.runfic.model.carreraservice.CarreraServiceFactory;
import es.udc.ws.runfic.model.carreraservice.exceptions.*;
import es.udc.ws.runfic.model.inscripcion.Inscripcion;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.sql.DataSourceLocator;
import es.udc.ws.util.sql.SimpleDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.awt.image.BandCombineOp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static es.udc.ws.runfic.model.util.ModelConstants.CARRERA_DATA_SOURCE;
import static org.junit.jupiter.api.Assertions.*;

public class CarreraServiceTest {

    private final long NON_EXISTENT_CARRERA_ID = -1;
    private final long NON_EXISTENT_INSCRIPCION_ID = -1;
    private final String NON_EXISTENT_CODIGO_DORSAL = "zzzzz00000";
    private final String VALID_CODIGO_DORSAL = "aaaaa11111";
    private final String INVALID_CODIGO_DORSAL = "aaaa111111";
    private final LocalDateTime FECHA_CARRERA = LocalDateTime.now().plusDays(2).withNano(0);
    private final LocalDateTime FECHA_FUTURA = LocalDateTime.now().plusDays(4).withNano(0);
    private final String EMAIL_USUARIO = "wachin@weon.com";
    private final String BAD_EMAIL_USUARIO = "wachin@weon";
    private final String TARJETA_VALID = "8572640192847564";
    private final String TARJETA_INVALID = "9504938571028571";
    private final String TARJETA_INVALID_INPUT = "gjfir571028571";

    private static CarreraService carreraService = null;


    @BeforeAll
    public static void init() {

        /*
         * Create a simple data source and add it to "DataSourceLocator" (this
         * is needed to test "es.udc.ws.runfic.model.carreraservice.CarreraService"
         */
        DataSource dataSource = new SimpleDataSource();

        /* Add "dataSource" to "DataSourceLocator".*/
        DataSourceLocator.addDataSource(CARRERA_DATA_SOURCE, dataSource);

        carreraService = CarreraServiceFactory.getService();

    }

    private Carrera getValidCarrera(String name) {
        return new Carrera(name, "Carrera description", FECHA_CARRERA, 5.F,1000);
    }

    private Carrera getValidCarrera() {
        return getValidCarrera("ciudadA");
    }

    private Carrera createCarrera(Carrera carrera) {

        Carrera addedCarrera = null;
        try {
            addedCarrera = carreraService.addCarrera(carrera);
        } catch (InputValidationException e) {
            throw new RuntimeException(e);
        }
        return addedCarrera;

    }

    private void removeCarrera(Long carreraId) {

        try {
            carreraService.removeCarrera(carreraId);
        } catch (InstanceNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private void removeInscripcion(Long inscripcionId) {

        try {
            carreraService.removeInscripcion(inscripcionId);
        } catch (InstanceNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    @Test
    public void testAddCarreraAndFindCarrera() throws InputValidationException, InstanceNotFoundException {

        Carrera carrera = getValidCarrera();
        Carrera addedCarrera = null;

        try {
            // Create Carrera
            LocalDateTime beforeCreationDate = LocalDateTime.now().withNano(0);
            addedCarrera = carreraService.addCarrera(carrera);

            //System.out.println(addedCarrera.getCarreraId());
            // Find Carrera
            Carrera foundCarrera = carreraService.findCarrera(addedCarrera.getCarreraId());

            assertEquals(addedCarrera, foundCarrera);
            assertEquals(foundCarrera.getCiudad(),carrera.getCiudad());
            assertEquals(foundCarrera.getDescripcion(),carrera.getDescripcion());
            assertTrue((foundCarrera.getFechaCarrera().isAfter(beforeCreationDate))
                    && (foundCarrera.getFechaCarrera().compareTo(carrera.getFechaCarrera()) == 0));
            assertEquals(foundCarrera.getPrecioCarrera(),carrera.getPrecioCarrera());
            assertEquals(foundCarrera.getMaxParticipantes(),carrera.getMaxParticipantes());
            assertTrue(foundCarrera.getAltaCarrera().compareTo(carrera.getAltaCarrera()) == 0);
            assertEquals(foundCarrera.getNumeroInscritos(),carrera.getNumeroInscritos());
        } finally {
            // Clear Database
            if (addedCarrera!=null) {
                removeCarrera(addedCarrera.getCarreraId());
            }
        }
    }


    @Test
    public void testFindCarreras() throws InputValidationException, OutOfTimeException {

        Carrera addedCarrera1;
        Carrera addedCarrera2;
        Carrera addedCarrera3;
        Carrera addedCarrera4;
        // Add carreras
        List<Carrera> todasCarreras = new LinkedList<>();
        List<Carrera> corunaCarreras = new LinkedList<>();
        List<Carrera> ferrolCarreras = new LinkedList<>();

        Carrera carrera1 = getValidCarrera();
        carrera1.setCiudad("coruna");
        addedCarrera1 = carreraService.addCarrera(carrera1);
        todasCarreras.add(addedCarrera1);
        corunaCarreras.add(addedCarrera1);

        Carrera carrera2 = getValidCarrera();
        carrera2.setCiudad("coruna");
        addedCarrera2 = carreraService.addCarrera(carrera2);
        todasCarreras.add(addedCarrera2);
        corunaCarreras.add(addedCarrera2);

        Carrera carrera3 = getValidCarrera();
        carrera3.setCiudad("coruna");
        addedCarrera3 = carreraService.addCarrera(carrera3);
        todasCarreras.add(addedCarrera3);
        corunaCarreras.add(addedCarrera3);

        Carrera carrera4= getValidCarrera();
        carrera4.setCiudad("ferrol");
        addedCarrera4 = carreraService.addCarrera(carrera4);
        todasCarreras.add(addedCarrera4);
        ferrolCarreras.add(addedCarrera4);


        try {
            List<Carrera> foundCarreras;
            foundCarreras = carreraService.findCarreras(FECHA_FUTURA.toLocalDate());
            assertEquals(4, foundCarreras.size());
            for (int i = 0; i < foundCarreras.size(); i++) {
                assertTrue(todasCarreras.get(i).equals(foundCarreras.get(i)));
            }

            foundCarreras = carreraService.findCarrerasC(FECHA_FUTURA.toLocalDate(),"ferrol");
            assertEquals(1, foundCarreras.size());
            for (int i = 0; i < foundCarreras.size(); i++) {
                assertTrue(ferrolCarreras.get(i).equals(foundCarreras.get(i)));
            }

            foundCarreras = carreraService.findCarrerasC(FECHA_FUTURA.toLocalDate(),"coruna");
            assertEquals(3, foundCarreras.size());
            for (int i = 0; i < foundCarreras.size(); i++) {
                assertTrue(corunaCarreras.get(i).equals(foundCarreras.get(i)));
            }

        } finally {
            // Clear Database
            for (Carrera carrera : todasCarreras) {
                removeCarrera(carrera.getCarreraId());
            }
        }

    }

    @Test
    public void testFindNonExistentCarrera() {
        assertThrows(InstanceNotFoundException.class, () -> carreraService.findCarrera(NON_EXISTENT_CARRERA_ID));
    }

    @Test
    public void testRecogerDorsalValido()
            throws InputValidationException, MaxCapacityException, InstanceNotFoundException, DorsalEntregadoException, TarjetaNotEqualException, AlreadyRegisteredException, OutOfTimeException {
        Carrera carrera = createCarrera(getValidCarrera());
        Inscripcion inscripcion = null;
        try {
            inscripcion = carreraService.addInscripcion(EMAIL_USUARIO,carrera.getCarreraId(),TARJETA_VALID, LocalDateTime.now());
            assertFalse(inscripcion.getEntregado());
            int dorsal = carreraService.recogerDorsal(inscripcion.getInscripcionId(),TARJETA_VALID);
            inscripcion = carreraService.findInscripcion(inscripcion.getInscripcionId());
            assertTrue(inscripcion.getEntregado());
            assertEquals(dorsal,inscripcion.getDorsal());
        } finally {
            if(inscripcion!=null){
                removeInscripcion(inscripcion.getInscripcionId());
            }
            removeCarrera(carrera.getCarreraId());
        }
    }

    @Test
    public void testRecogerDorsalTarjetaMal()
            throws InputValidationException, MaxCapacityException, InstanceNotFoundException, DorsalEntregadoException, TarjetaNotEqualException, AlreadyRegisteredException, OutOfTimeException {
        Carrera carrera = createCarrera(getValidCarrera());
        Inscripcion inscripcion = null;
        try {
            inscripcion = carreraService.addInscripcion(EMAIL_USUARIO,carrera.getCarreraId(),TARJETA_VALID, LocalDateTime.now());
            Inscripcion finalInscripcion = inscripcion;
            assertThrows(TarjetaNotEqualException.class,()->carreraService.recogerDorsal(finalInscripcion.getInscripcionId(),TARJETA_INVALID));
        } finally {
            if(inscripcion!=null){
                removeInscripcion(inscripcion.getInscripcionId());
            }
            removeCarrera(carrera.getCarreraId());
        }
    }

    @Test
    public void recogerDorsalEntregado()
            throws InputValidationException, MaxCapacityException, InstanceNotFoundException, DorsalEntregadoException, TarjetaNotEqualException, AlreadyRegisteredException, OutOfTimeException {
        Carrera carrera = createCarrera(getValidCarrera());
        Inscripcion inscripcion = null;
        try {
            inscripcion = carreraService.addInscripcion( EMAIL_USUARIO,carrera.getCarreraId(),TARJETA_VALID, LocalDateTime.now());
            assertFalse(inscripcion.getEntregado());
            Long inscripcionId = inscripcion.getInscripcionId();
            int dorsal = carreraService.recogerDorsal(inscripcionId,TARJETA_VALID);
            inscripcion = carreraService.findInscripcion(inscripcionId);
            assertTrue(inscripcion.getEntregado());
            assertThrows(DorsalEntregadoException.class,()->carreraService.recogerDorsal(inscripcionId,TARJETA_VALID));
        } finally {
            if(inscripcion!=null){
                removeInscripcion(inscripcion.getInscripcionId());
            }
            removeCarrera(carrera.getCarreraId());
        }
    }

    @Test
    public void testAddAndFindInscripcion()
            throws InstanceNotFoundException, InputValidationException, AlreadyRegisteredException, MaxCapacityException, OutOfTimeException {

        Carrera carrera = createCarrera(getValidCarrera());
        Inscripcion inscripcion = null;

        try {

            inscripcion = carreraService.addInscripcion(EMAIL_USUARIO,carrera.getCarreraId(),TARJETA_VALID, LocalDateTime.now().withNano(0));

            // Find sale
            Inscripcion foundInscripcion = carreraService.findInscripcion(inscripcion.getInscripcionId());

            // Check sale
            assertEquals(inscripcion, foundInscripcion);
            assertEquals(inscripcion.getEmailUsuario(),foundInscripcion.getEmailUsuario());
            assertEquals(inscripcion.getCarrera(),foundInscripcion.getCarrera());
            assertEquals(inscripcion.getEntregado(),foundInscripcion.getEntregado());
            assertTrue(inscripcion.getFechaInscripcion().compareTo(foundInscripcion.getFechaInscripcion()) == 0);
            assertEquals(inscripcion.getFechaInscripcion(), foundInscripcion.getFechaInscripcion());
            assertEquals(inscripcion.getDorsal(),foundInscripcion.getDorsal());
            assertEquals(inscripcion.getTarjeta(),foundInscripcion.getTarjeta());

        } finally {
            if (inscripcion != null) {
                removeInscripcion(inscripcion.getInscripcionId());
            }
            removeCarrera(carrera.getCarreraId());
        }

    }


    @Test
    public void testAddAndFindInscripciones()
            throws InstanceNotFoundException, InputValidationException, AlreadyRegisteredException, MaxCapacityException, OutOfTimeException {

        Carrera carrera1 = createCarrera(getValidCarrera());
        Carrera carrera2 = createCarrera(getValidCarrera());
        Inscripcion inscripcion1 = null;
        Inscripcion inscripcion2 = null;
        Inscripcion inscripcion3 = null;
        List<Inscripcion> inscripcions = new ArrayList<>();
        try {

            inscripcion1 = carreraService.addInscripcion(EMAIL_USUARIO,carrera1.getCarreraId(),TARJETA_VALID, LocalDateTime.now().withNano(0));
            inscripcions.add(inscripcion1);
            inscripcion2 = carreraService.addInscripcion("juan_perez@hotmail.uk",carrera1.getCarreraId(),TARJETA_VALID, LocalDateTime.now().withNano(0));
            inscripcions.add(inscripcion2);
            inscripcion3 = carreraService.addInscripcion(EMAIL_USUARIO,carrera2.getCarreraId(),TARJETA_VALID, LocalDateTime.now().withNano(0));
            inscripcions.add(inscripcion3);

            List<Inscripcion> foundInscripciones = carreraService.getInscripciones(EMAIL_USUARIO);


            // Check inscripcions
            assertEquals(2, foundInscripciones.size());
            assertEquals(inscripcions.get(0), foundInscripciones.get(0));

            foundInscripciones = carreraService.getInscripciones("pepe@mail.es");
            assertEquals(0, foundInscripciones.size());

        } finally {

            for (Inscripcion inscripcion : inscripcions) {
                removeInscripcion(inscripcion.getInscripcionId());
            }

            removeCarrera(carrera1.getCarreraId());
            removeCarrera(carrera2.getCarreraId());
        }

    }


    @Test
    void testCreateInvalidInscripcion() {
        Carrera carrera = createCarrera(getValidCarrera());
        carrera.setNumeroInscritos(1000);
        Carrera carreraMax = createCarrera(carrera);

        //Not valid inscripcion date
        assertThrows(OutOfTimeException.class, () -> {
            Inscripcion inscripcion = carreraService.addInscripcion(EMAIL_USUARIO,carrera.getCarreraId(),TARJETA_VALID, LocalDateTime.now().plusDays(1).plusHours(12).withNano(0));
            removeInscripcion(inscripcion.getInscripcionId());
        });

        //Not valid credit card
        assertThrows(InputValidationException.class, () -> {
            Inscripcion inscripcion = carreraService.addInscripcion(EMAIL_USUARIO,carrera.getCarreraId(),"", LocalDateTime.now().withNano(0));
            removeInscripcion(inscripcion.getInscripcionId());
        });

        //Null mail
        assertThrows(InputValidationException.class, () -> {
            Inscripcion inscripcion = carreraService.addInscripcion("",carrera.getCarreraId(),TARJETA_VALID, LocalDateTime.now().withNano(0));
            removeInscripcion(inscripcion.getInscripcionId());
        });

        //Bad mail mail
        assertThrows(InputValidationException.class, () -> {
            Inscripcion inscripcion = carreraService.addInscripcion(BAD_EMAIL_USUARIO,carrera.getCarreraId(),TARJETA_VALID, LocalDateTime.now().withNano(0));
            removeInscripcion(inscripcion.getInscripcionId());
        });

        //Non existent carrera
        assertThrows(InstanceNotFoundException.class, () -> {
            Inscripcion inscripcion = carreraService.addInscripcion(EMAIL_USUARIO, NON_EXISTENT_CARRERA_ID, TARJETA_VALID, LocalDateTime.now().withNano(0));
            removeInscripcion(inscripcion.getInscripcionId());
        });

        //Max capacity in carrera
        assertThrows(MaxCapacityException.class, () -> {
            Inscripcion inscripcion = carreraService.addInscripcion(EMAIL_USUARIO, carreraMax.getCarreraId(), TARJETA_VALID, LocalDateTime.now().withNano(0));
            removeInscripcion(inscripcion.getInscripcionId());
        });

        //Input Validation Exception con tarjeta mal
        assertThrows(InputValidationException.class, () -> {
            Inscripcion inscripcion = carreraService.addInscripcion(EMAIL_USUARIO,carrera.getCarreraId(),TARJETA_INVALID_INPUT, LocalDateTime.now().withNano(0));
            removeInscripcion(inscripcion.getInscripcionId());
        });

        //Already registered
        assertThrows(AlreadyRegisteredException.class, () -> {
            Inscripcion inscripcion = carreraService.addInscripcion(EMAIL_USUARIO,carrera.getCarreraId(),TARJETA_VALID, LocalDateTime.now().withNano(0));
            Inscripcion inscripcion2 = carreraService.addInscripcion(EMAIL_USUARIO,carrera.getCarreraId(),TARJETA_VALID, LocalDateTime.now().withNano(0));
            removeInscripcion(inscripcion.getInscripcionId());
            removeInscripcion(inscripcion2.getInscripcionId());
        });

        removeCarrera(carreraMax.getCarreraId());
        removeCarrera(carrera.getCarreraId());
    }

    @Test
    public void testFindNonExistentInscripcion() {
        assertThrows(InstanceNotFoundException.class, () -> carreraService.findInscripcion(NON_EXISTENT_INSCRIPCION_ID));
    }

    @Test
    public void testRemoveNonExistentInscripcion() {
        assertThrows(InstanceNotFoundException.class, () -> carreraService.removeInscripcion(NON_EXISTENT_INSCRIPCION_ID));
    }

}
