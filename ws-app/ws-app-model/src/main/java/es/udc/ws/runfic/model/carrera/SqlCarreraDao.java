package es.udc.ws.runfic.model.carrera;

import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface SqlCarreraDao {

    Carrera create(Connection connection, Carrera carrera);

    Carrera find(Connection connection,Long carreraId) throws InstanceNotFoundException;

    List<Carrera> findCarreras(Connection connection, LocalDate fechaMax);

    List<Carrera> findCarrerasC(Connection connection, LocalDate fechaMax, String ciudad);

    void update(Connection connection, Carrera carrera) throws InstanceNotFoundException;

    void remove(Connection connection,Long carreraId) throws InstanceNotFoundException;
}
