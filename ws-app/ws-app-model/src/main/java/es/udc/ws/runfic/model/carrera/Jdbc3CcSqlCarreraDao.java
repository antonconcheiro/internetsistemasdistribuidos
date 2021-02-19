package es.udc.ws.runfic.model.carrera;

import java.sql.*;

public class Jdbc3CcSqlCarreraDao extends AbstractSqlCarreraDao {

    @Override
    public Carrera create (Connection connection, Carrera carrera) {

        /* Create "queryString". */
        String queryString = "INSERT INTO Carrera"
                + " (ciudad, descripcion, fechaCarrera, precioCarrera, maxParticipantes, altaCarrera, numeroInscritos)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                queryString, Statement.RETURN_GENERATED_KEYS)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setString(i++, carrera.getCiudad());
            preparedStatement.setString(i++, carrera.getDescripcion());
            preparedStatement.setTimestamp(i++, Timestamp.valueOf(carrera.getFechaCarrera()));
            preparedStatement.setFloat(i++, carrera.getPrecioCarrera());
            preparedStatement.setInt(i++, carrera.getMaxParticipantes());
            preparedStatement.setTimestamp(i++, Timestamp.valueOf(carrera.getAltaCarrera()));
            preparedStatement.setInt(i++, carrera.getNumeroInscritos());

            /* Execute query. */
            preparedStatement.executeUpdate();

            /* Get generated identifier. */
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (!resultSet.next()) {
                throw new SQLException(
                        "JDBC driver did not return generated key.");
            }
            Long carreraId = resultSet.getLong(1);

            /* Return carrera. */
            return new Carrera(carreraId, carrera.getCiudad(), carrera.getDescripcion(),
                    carrera.getFechaCarrera(), carrera.getPrecioCarrera(),
                    carrera.getMaxParticipantes(), carrera.getAltaCarrera());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
