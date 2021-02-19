-- ----------------------------------------------------------------------------
-- Model
-- -----------------------------------------------------------------------------

DROP TABLE Inscripcion;
DROP TABLE Carrera;

-- --------------------------------- Movie ------------------------------------
CREATE TABLE Carrera (
    carreraId BIGINT NOT NULL AUTO_INCREMENT,
    ciudad VARCHAR(255) COLLATE latin1_bin NOT NULL,
    descripcion VARCHAR(1024) COLLATE latin1_bin NOT NULL,
    fechaCarrera DATETIME NOT NULL,
    precioCarrera FLOAT NOT NULL,
    maxParticipantes SMALLINT NOT NULL,
    altaCarrera DATETIME NOT NULL,
    numeroInscritos SMALLINT NOT NULL,
    CONSTRAINT CarreraPK PRIMARY KEY(carreraId) )ENGINE = InnoDB;

    -- CONSTRAINT validRuntime CHECK ( runtime >= 0 AND runtime <= 1000 ),
    -- CONSTRAINT validPrice CHECK ( price >= 0 AND price <= 1000) ) ENGINE = InnoDB;

-- --------------------------------- Sale ------------------------------------

CREATE TABLE Inscripcion (
    inscripcionId BIGINT NOT NULL AUTO_INCREMENT,
    emailUsuario VARCHAR(40) COLLATE latin1_bin NOT NULL,
    carreraId BIGINT NOT NULL,
    tarjeta VARCHAR(16) NOT NULL,
    dorsal SMALLINT NOT NULL,
    fechaInscripcion DATETIME NOT NULL,
    entregado BIT,
    CONSTRAINT InscripcionPK PRIMARY KEY(inscripcionId),
    CONSTRAINT InscripcionCarreraIdFK FOREIGN KEY(carreraId)
        REFERENCES Carrera(carreraId) ON DELETE CASCADE ) ENGINE = InnoDB;
