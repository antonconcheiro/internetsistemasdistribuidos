package es.udc.ws.runfic.model.inscripcion;

import es.udc.ws.runfic.model.carrera.Carrera;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

public class Jdbc3CcSqlInscripcionDao extends AbstractSqlInscripcionDao {

    @Override
    public Inscripcion create(Connection connection, Inscripcion inscripcion) throws InstanceNotFoundException {
        /* Create "queryString". */
        String queryString = "INSERT INTO Inscripcion"
                + " (emailUsuario, carreraId, tarjeta, dorsal," +
                "fechaInscripcion, entregado) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                queryString, Statement.RETURN_GENERATED_KEYS))  {

            /* Fill "preparedStatement". */

            int i = 1;
            preparedStatement.setString(i++, inscripcion.getEmailUsuario());
            preparedStatement.setLong(i++, inscripcion.getCarrera());
            preparedStatement.setString(i++, inscripcion.getTarjeta());
            preparedStatement.setInt(i++, inscripcion.getDorsal());
            preparedStatement.setTimestamp(i++, Timestamp.valueOf(inscripcion.getFechaInscripcion()));
            preparedStatement.setBoolean(i++, inscripcion.getEntregado());

            /* Execute query. */
            preparedStatement.executeUpdate();

            /* Get generated identifier. */
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (!resultSet.next()) {
                throw new SQLException(
                        "JDBC driver did not return generated key.");
            }

            Long inscripcionId = resultSet.getLong(1);

            return new Inscripcion(inscripcionId,inscripcion.getEmailUsuario(),inscripcion.getCarrera(),
                    inscripcion.getTarjeta(),inscripcion.getDorsal(),inscripcion.getFechaInscripcion(),
                    inscripcion.getEntregado());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
