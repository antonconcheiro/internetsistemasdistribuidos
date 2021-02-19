package es.udc.ws.runfic.model.inscripcion;

import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.sql.Connection;
import java.util.List;

public interface SqlInscripcionDao {

    Inscripcion create (Connection connection, Inscripcion inscripcion) throws InstanceNotFoundException;

    List<Inscripcion> findInscripciones(Connection connection, String emailUsuario) throws InstanceNotFoundException;

    Inscripcion find(Connection connection, Long inscripcionId) throws InstanceNotFoundException;

    void remove(Connection connection, Long inscripcionId) throws InstanceNotFoundException;

    void update(Connection connection, Inscripcion inscripcion) throws InstanceNotFoundException;
}
