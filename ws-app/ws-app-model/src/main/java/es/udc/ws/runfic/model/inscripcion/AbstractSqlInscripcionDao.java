package es.udc.ws.runfic.model.inscripcion;

import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSqlInscripcionDao implements SqlInscripcionDao {

    @Override
    public Inscripcion find(Connection connection, Long inscripcionId)
            throws InstanceNotFoundException{

        /* Create "queryString". */
        String queryString = "SELECT emailUsuario, carreraId, tarjeta, dorsal,"
                + "fechaInscripcion, entregado FROM Inscripcion WHERE inscripcionId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, inscripcionId);

            /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new InstanceNotFoundException(inscripcionId,
                        Inscripcion.class.getName());
            }

            /*Get results.*/
            i = 1;
            String emailUsuario = resultSet.getString(i++);
            Long carrera = resultSet.getLong(i++);
            String tarjeta = resultSet.getString(i++);
            int dorsal = resultSet.getInt(i++);
            LocalDateTime fechaInscripcion = resultSet.getTimestamp(i++).toLocalDateTime();
            boolean entregado = resultSet.getBoolean(i++);

            /* Return inscripcion.*/
            return new Inscripcion(inscripcionId, emailUsuario, carrera, tarjeta,
                    dorsal, fechaInscripcion, entregado);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(Connection connection, Long inscripcionId)
            throws InstanceNotFoundException {

        /* Create "queryString". */
        String queryString = "DELETE FROM Inscripcion WHERE inscripcionId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, inscripcionId);

            /* Execute query. */
            int removedRows = preparedStatement.executeUpdate();

            if (removedRows == 0) {
                throw new InstanceNotFoundException(inscripcionId,
                        Inscripcion.class.getName());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Inscripcion> findInscripciones(Connection connection, String emailUsuario)
            throws InstanceNotFoundException {

        String queryString = "SELECT inscripcionId, carreraId, tarjeta, dorsal,"
                + "fechaInscripcion, entregado FROM Inscripcion WHERE emailUsuario = ?";
        List<Inscripcion> results = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            int i = 1;
            preparedStatement.setString(i++, emailUsuario);

            /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                i = 1;

                Long inscripcionId = resultSet.getLong(i++);
                Long carrera = resultSet.getLong(i++);
                String tarjeta = resultSet.getString(i++);
                int dorsal = resultSet.getInt(i++);
                LocalDateTime fechaInscripcion = resultSet.getTimestamp(i++).toLocalDateTime();
                boolean entregado = resultSet.getBoolean(i++); /* Get results. */

                /* Add inscripcion to list */
                results.add(new Inscripcion(inscripcionId, emailUsuario, carrera, tarjeta,
                        dorsal, fechaInscripcion, entregado));
            }

            return results;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Connection connection, Inscripcion inscripcion)
            throws InstanceNotFoundException {

        /* Create "queryString". */
        String queryString = "UPDATE Inscripcion SET emailUsuario = ?, carreraId = ?, tarjeta= ?, dorsal = ?,"
                + " fechaInscripcion = ?, entregado = ? WHERE inscripcionId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;

            preparedStatement.setString(i++, inscripcion.getEmailUsuario());
            preparedStatement.setLong(i++, inscripcion.getCarrera());
            preparedStatement.setString(i++, inscripcion.getTarjeta());
            preparedStatement.setInt(i++, inscripcion.getDorsal());
            preparedStatement.setTimestamp(i++,Timestamp.valueOf(inscripcion.getFechaInscripcion()));
            preparedStatement.setBoolean(i++, inscripcion.getEntregado());
            preparedStatement.setLong(i++, inscripcion.getInscripcionId());

            /* Execute query.*/
            int updatedRows = preparedStatement.executeUpdate();

            if (updatedRows == 0) {
                throw new InstanceNotFoundException(Inscripcion.class,
                        Inscripcion.class.getName());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
