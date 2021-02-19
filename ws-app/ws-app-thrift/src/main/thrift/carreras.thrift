namespace java es.udc.ws.runfic.thrift

struct ThriftCarreraDto {
    1: i64 carreraId;
    2: string ciudad;
    3: string descripcion;
    4: string fechaCarrera;
    5: double precio
    6: i32 plazasDisp
}

struct ThriftInscripcionDto {
    1: i64 inscripcionId;
    2: i64 carreraId;
    3: string emailUsuario;
    4: string tarjeta;
    5: i32 dorsal;
    6: bool entregado;
}

exception ThriftInputValidationException {
    1: string message
}

exception ThriftInstanceNotFoundException {
    1: string instanceId
    2: string instanceType
}

exception ThriftAlreadyRegisteredException {
    1: string emailUsuario;
}

exception ThriftClientDorsalEntregadoException {
    1: i64 inscripcionId;
}

exception ThriftClientTarjetaNotEqualException {
    1: i64 inscripcionId;
    3: string tarjeta;
}

exception ThriftMaxCapacityException {
    1: i32 maxCapacity;
}

exception ThriftOutOfTimeException {

}

service ThriftCarreraService {

   i64 addCarrera(1: ThriftCarreraDto carrera) throws (1: ThriftInputValidationException e)

   ThriftCarreraDto findCarrera(1: i64 carreraId)

   list<ThriftCarreraDto> findCarreras(1: string fechaMax, 2: string ciudad)

   i64 addInscripcion(1: ThriftInscripcionDto inscripcion) throws (1: ThriftInputValidationException e,
                                                    2: ThriftInstanceNotFoundException ee,
                                                    3: ThriftMaxCapacityException eee,
                                                    4: ThriftOutOfTimeException eeee,
                                                    5: ThriftAlreadyRegisteredException eeeee)

   i32 recogerDorsal(1: i64 inscripcionId,2: string tarjeta) throws (1: ThriftInputValidationException e,
                                                                   2: ThriftInstanceNotFoundException ee,
                                                                   3: ThriftClientDorsalEntregadoException eee,
                                                                   4: ThriftClientTarjetaNotEqualException eeee)

   ThriftInscripcionDto findInscripcion(1: i64 inscripcionId) throws (1: ThriftInstanceNotFoundException e)

   list<ThriftInscripcionDto> findInscripciones(1: string email) throws (1: ThriftInputValidationException e)
   
}