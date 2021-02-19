package es.udc.ws.runfic.model.carreraservice;

import es.udc.ws.runfic.model.carrera.Carrera;
import es.udc.ws.runfic.model.carrera.SqlCarreraDao;
import es.udc.ws.runfic.model.carrera.SqlCarreraDaoFactory;
import es.udc.ws.runfic.model.carreraservice.exceptions.*;
import es.udc.ws.runfic.model.inscripcion.Inscripcion;
import es.udc.ws.runfic.model.inscripcion.SqlInscripcionDao;
import es.udc.ws.runfic.model.inscripcion.SqlInscripcionDaoFactory;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.sql.DataSourceLocator;

import javax.sql.DataSource;
import java.nio.channels.AlreadyBoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static es.udc.ws.runfic.model.util.ModelConstants.*;

public class CarreraServiceImpl implements CarreraService {

    private final DataSource dataSource;
    private SqlCarreraDao carreraDao = null;
    private SqlInscripcionDao inscripcionDao = null;

    public CarreraServiceImpl() {
        dataSource = DataSourceLocator.getDataSource(CARRERA_DATA_SOURCE);
        carreraDao = SqlCarreraDaoFactory.getDao();
        inscripcionDao = SqlInscripcionDaoFactory.getDao();
    }


    private void validateInscripcion(Inscripcion inscripcion, Long carreraId) throws InputValidationException {
        PropertyValidator.validateMandatoryString("email", inscripcion.getEmailUsuario());
        PropertyValidator.validateMandatoryString("tarjeta", inscripcion.getTarjeta());
    }

    @Override
    public Carrera addCarrera(Carrera carrera) throws InputValidationException {
        carrera.setAltaCarrera(LocalDateTime.now());

        if(carrera.getFechaCarrera().isBefore(LocalDateTime.now())){
            throw new InputValidationException("Fecha carrera inválida. No se pueden añadir carreras previas a hoy");
        }
        validateCarrera(carrera);
        try (Connection connection = dataSource.getConnection()) {

            try {
                /* Prepare connection. */
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

                /* Do work. */
                Carrera createdCarrera = carreraDao.create(connection, carrera);

                /* Commit. */
                connection.commit();

                return createdCarrera;

            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            } catch (RuntimeException | Error e) {
                connection.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Carrera findCarrera(Long carreraId) throws InstanceNotFoundException {
        try (Connection connection = dataSource.getConnection()) {
            return carreraDao.find(connection, carreraId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Carrera> findCarreras(LocalDate fechaMax) throws InputValidationException, OutOfTimeException {
        if(fechaMax.isBefore(LocalDate.now())){
            throw new OutOfTimeException();
        }
        try (Connection connection = dataSource.getConnection()) {
            return carreraDao.findCarreras(connection, fechaMax);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Carrera> findCarrerasC(LocalDate fechaMax, String ciudad) throws InputValidationException, OutOfTimeException {
        if(fechaMax.isBefore(LocalDate.now())){
            throw new OutOfTimeException();
        }
        PropertyValidator.validateMandatoryString("ciudad", ciudad);
        try (Connection connection = dataSource.getConnection()) {
            return carreraDao.findCarrerasC(connection, fechaMax, ciudad);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Inscripcion findInscripcion(Long inscripcionId) throws InstanceNotFoundException {
        try (Connection connection = dataSource.getConnection()) {
            return inscripcionDao.find(connection, inscripcionId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Inscripcion addInscripcion(String emailUsuario, Long carreraId, String tarjeta, LocalDateTime creationTime) throws InputValidationException, InstanceNotFoundException, MaxCapacityException, AlreadyRegisteredException, OutOfTimeException {
        int dorsal;
        PropertyValidator.validateCreditCard(tarjeta);
        PropertyValidator.validateEmail(emailUsuario);

        try (Connection connection = dataSource.getConnection()) {
            try {
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

                Carrera carrera = carreraDao.find(connection, carreraId);
                LocalDateTime fechaCarrera = carrera.getFechaCarrera();
                List<Inscripcion> inscripcions = inscripcionDao.findInscripciones(connection, emailUsuario);

                for (Inscripcion inscripcion: inscripcions) {
                    if (inscripcion.getCarrera() == carrera.getCarreraId()) {
                        throw new AlreadyRegisteredException(emailUsuario,carreraId);
                    }
                }

                if (carrera.getNumeroInscritos() < carrera.getMaxParticipantes()) {
                    dorsal = carrera.getNumeroInscritos() + 1;
                    carrera.incrementarInscritos();
                } else throw new MaxCapacityException(carrera.getMaxParticipantes());

                Inscripcion inscripcion = new Inscripcion(emailUsuario, carreraId, tarjeta, dorsal, creationTime,false);
                validateInscripcion(inscripcion, carreraId);

                if (creationTime.isAfter(fechaCarrera.minusDays(1))) {
                    throw new OutOfTimeException();
                } else {
                    Inscripcion createdInscripcion = inscripcionDao.create(connection, inscripcion);
                    carreraDao.update(connection, carrera);
                    connection.commit();
                    updateCarrera(carrera);
                    return createdInscripcion;
                }
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            } catch (RuntimeException | Error e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeInscripcion(Long inscripcionId) throws InstanceNotFoundException {
        try (Connection connection = dataSource.getConnection()) {
            try {

                /* Prepare connection. */
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

                Inscripcion inscripcion = inscripcionDao.find(connection, inscripcionId);
                Carrera carrera = carreraDao.find(connection, inscripcion.getCarrera());
                carrera.decrementarInscritos();

                /* Do work. */
                inscripcionDao.remove(connection, inscripcionId);
                carreraDao.update(connection, carrera);

                /* Commit. */
                connection.commit();

            } catch (InstanceNotFoundException e) {
                connection.commit();
                throw e;
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            } catch (RuntimeException | Error e) {
                connection.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Inscripcion> getInscripciones(String emailUsuario) throws InstanceNotFoundException,InputValidationException {
        PropertyValidator.validateEmail(emailUsuario);

        try (Connection connection = dataSource.getConnection()) {
            return inscripcionDao.findInscripciones(connection, emailUsuario);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int recogerDorsal(Long inscripcionId, String tarjeta)
            throws InstanceNotFoundException,DorsalEntregadoException, TarjetaNotEqualException, InputValidationException {

        PropertyValidator.validateCodigoDorsal(inscripcionId);
        PropertyValidator.validateCreditCard(tarjeta);

        try (Connection connection = dataSource.getConnection()) {
            try {

                /* Prepare connection. */
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

                /* Do work. */
                Inscripcion inscripcion = inscripcionDao.find(connection, inscripcionId);
                if(inscripcion.getTarjeta().equals(tarjeta)){
                    if(inscripcion.getEntregado()){
                        throw new DorsalEntregadoException(inscripcionId);
                    }else {
                        inscripcion.setEntregado(true);
                        inscripcionDao.update(connection, inscripcion);
                        /* Commit.*/
                        connection.commit();
                        return inscripcion.getDorsal();
                    }
                }else{
                    throw new TarjetaNotEqualException(inscripcionId,tarjeta);
                }
            } catch (InstanceNotFoundException e) {
                connection.commit();
                throw e;
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            } catch (RuntimeException | Error e) {
                connection.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateCarrera(Carrera carrera) throws InputValidationException, InstanceNotFoundException {


        try (Connection connection = dataSource.getConnection()) {

            try {

                /* Prepare connection. */
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

                /* Do work. */
                carreraDao.update(connection, carrera);

                /* Commit. */
                connection.commit();

            } catch (InstanceNotFoundException e) {
                connection.commit();
                throw e;
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            } catch (RuntimeException | Error e) {
                connection.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void validateCarrera(Carrera carrera) throws InputValidationException{
        PropertyValidator.validateMandatoryString("ciudad", carrera.getCiudad());
        PropertyValidator.validateLong("maxParticipantes", carrera.getMaxParticipantes(), 0, MAX_PARTICIPANTES);
        PropertyValidator.validateMandatoryString("descripcion", carrera.getDescripcion());
        PropertyValidator.validateDouble("precioCarrera", carrera.getPrecioCarrera(), 0, MAX_PRECIO_CARRERA);
        PropertyValidator.validateDouble("maxParticipantes", carrera.getMaxParticipantes(), 1, MAX_PARTICIPANTES);
    }

    @Override
    public void removeCarrera(Long carreraId) throws InstanceNotFoundException {

        try (Connection connection = dataSource.getConnection()) {

            try {

                /* Prepare connection. */
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

                /* Do work. */
                carreraDao.remove(connection, carreraId);

                /* Commit. */
                connection.commit();

            } catch (InstanceNotFoundException e) {
                connection.commit();
                throw e;
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            } catch (RuntimeException | Error e) {
                connection.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
