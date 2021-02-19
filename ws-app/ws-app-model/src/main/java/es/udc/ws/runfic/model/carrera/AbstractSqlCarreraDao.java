package es.udc.ws.runfic.model.carrera;

import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSqlCarreraDao implements SqlCarreraDao{

    protected AbstractSqlCarreraDao() {
    }

    @Override
    public Carrera find(Connection connection, Long carreraId)
            throws InstanceNotFoundException{

        /* Create "queryString". */
        String queryString = "SELECT ciudad, descripcion, fechaCarrera, precioCarrera,"
                + "maxParticipantes, altaCarrera, numeroInscritos FROM Carrera WHERE carreraId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, carreraId.longValue());

            /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new InstanceNotFoundException(carreraId,Carrera.class.getName());
            }

            /* Get results. */
            i = 1;
            String ciudad = resultSet.getString(i++);
            String descripcion = resultSet.getString(i++);
            LocalDateTime fechaCarrera = resultSet.getTimestamp(i++).toLocalDateTime();
            float precioCarrera = resultSet.getFloat(i++);
            int maxParticipantes = resultSet.getInt(i++);
            LocalDateTime altaCarrera = resultSet.getTimestamp(i++).toLocalDateTime();
            int numeroInscritos = resultSet.getInt(i++);
            /* Return carrera. */
            return new Carrera(carreraId, ciudad, descripcion, fechaCarrera, precioCarrera,
                    maxParticipantes,altaCarrera,numeroInscritos);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Carrera> findCarreras(Connection connection, LocalDate fechaMax) {
        /* Create "queryString". */
        String queryString = "SELECT carreraId, ciudad, descripcion, fechaCarrera, precioCarrera,"
                + "maxParticipantes, altaCarrera, numeroInscritos FROM Carrera WHERE"
                + " fechaCarrera < ? AND fechaCarrera > ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;

            preparedStatement.setTimestamp(i++, Timestamp.valueOf(fechaMax.atStartOfDay()));
            preparedStatement.setTimestamp(i++, Timestamp.valueOf(LocalDate.now().atStartOfDay()));

            /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();

            /* Read movies. */
            List<Carrera> carreras = new ArrayList<>();

            while (resultSet.next()) {

                i = 1;
                Long carreraId = resultSet.getLong(i++);
                String ciudadS = resultSet.getString(i++);
                String descripcion = resultSet.getString(i++);
                LocalDateTime fechaCarrera = resultSet.getTimestamp(i++).toLocalDateTime();
                float precioCarrera = resultSet.getFloat(i++);
                int maxParticipantes = resultSet.getInt(i++);
                LocalDateTime altaCarrera = resultSet.getTimestamp(i++).toLocalDateTime();
                int numeroInscritos = resultSet.getInt(i++);

                carreras.add(new Carrera(carreraId, ciudadS, descripcion, fechaCarrera, precioCarrera,
                        maxParticipantes, altaCarrera, numeroInscritos));

            }

            /* Return movies. */
            return carreras;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Carrera> findCarrerasC(Connection connection, LocalDate fechaMax, String ciudad) {
        /* Create "queryString". */
        String queryString = "SELECT carreraId, ciudad, descripcion, fechaCarrera, precioCarrera,"
                + "maxParticipantes, altaCarrera, numeroInscritos FROM Carrera WHERE"
                + " fechaCarrera < ?" + " AND ciudad = ? AND fechaCarrera > ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setTimestamp(i++, Timestamp.valueOf(fechaMax.atStartOfDay()));
            preparedStatement.setString(i++, ciudad);
            preparedStatement.setTimestamp(i++, Timestamp.valueOf(LocalDate.now().atStartOfDay()));

            /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();

            /* Read movies. */
            List<Carrera> carreras = new ArrayList<>();

            while (resultSet.next()) {

                i = 1;
                Long carreraId = resultSet.getLong(i++);
                String ciudadR = resultSet.getString(i++);
                String descripcion = resultSet.getString(i++);
                LocalDateTime fechaCarrera = resultSet.getTimestamp(i++).toLocalDateTime();
                float precioCarrera = resultSet.getFloat(i++);
                int maxParticipantes = resultSet.getInt(i++);
                LocalDateTime altaCarrera = resultSet.getTimestamp(i++).toLocalDateTime();
                int numeroInscritos = resultSet.getInt(i++);

                carreras.add(new Carrera(carreraId, ciudadR, descripcion, fechaCarrera, precioCarrera,
                        maxParticipantes, altaCarrera, numeroInscritos));

            }

            /* Return movies. */
            return carreras;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Connection connection, Carrera carrera) throws InstanceNotFoundException {
        /* Create "queryString". */
        String queryString = "UPDATE Carrera"
                + " SET ciudad = ?, descripcion = ?, fechaCarrera = ?, "
                + "precioCarrera = ?, maxParticipantes = ?, altaCarrera = ?, "
                + "numeroInscritos = ? WHERE carreraId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setString(i++, carrera.getCiudad());
            preparedStatement.setString(i++, carrera.getDescripcion());
            preparedStatement.setTimestamp(i++, Timestamp.valueOf(carrera.getFechaCarrera()));
            preparedStatement.setFloat(i++, carrera.getPrecioCarrera());
            preparedStatement.setInt(i++, carrera.getMaxParticipantes());
            preparedStatement.setTimestamp(i++, Timestamp.valueOf(carrera.getAltaCarrera()));
            preparedStatement.setInt(i++, carrera.getNumeroInscritos());
            preparedStatement.setLong(i++, carrera.getCarreraId());


            /* Execute query. */
            int updatedRows = preparedStatement.executeUpdate();

            if (updatedRows == 0) {
                throw new InstanceNotFoundException(carrera.getCarreraId(), Carrera.class.getName());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(Connection connection,Long carreraId) throws InstanceNotFoundException{

        /* Create "queryString". */
        String queryString = "DELETE FROM Carrera WHERE" + " carreraId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, carreraId);

            /* Execute query. */
            int removedRows = preparedStatement.executeUpdate();

            if (removedRows == 0) {
                throw new InstanceNotFoundException(carreraId, Carrera.class.getName());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
