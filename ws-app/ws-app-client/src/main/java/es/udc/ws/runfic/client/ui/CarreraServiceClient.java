package es.udc.ws.runfic.client.ui;

import es.udc.ws.runfic.client.service.ClientCarreraService;
import es.udc.ws.runfic.client.service.ClientCarreraServiceFactory;
import es.udc.ws.runfic.client.service.dto.ClientCarreraDto;
import es.udc.ws.runfic.client.service.dto.ClientInscripcionDto;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import es.udc.ws.runfic.client.service.rest.RestClientCarreraService;
public class CarreraServiceClient {



    public static void main(String[] args){


        if(args.length == 0) {
            printUsageAndExit();
        }

        ClientCarreraService clientCarreraService = ClientCarreraServiceFactory.getService();

        if("-a".equalsIgnoreCase(args[0])) {
            validateArgs(args, 6, new int[] {4, 5});
            //[add] -a <ciudad> <descripcion> <fecha_carrera> <precio> <plazasDisp>
            try {

                DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

                Long carreraId = clientCarreraService.addCarrera(new ClientCarreraDto(null,
                        args[1], args[2], LocalDateTime.parse(args[3],formatter),Float.valueOf(args[4]),
                        Integer.valueOf(args[5])));

                System.out.println("Carrera " + carreraId + " created sucessfully");

            } catch (NumberFormatException | InputValidationException ex) {
                ex.printStackTrace(System.err);
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }

        }else if("-f".equalsIgnoreCase(args[0])) {
            if(args.length==2){
                validateArgs(args, 2, new int[] {1});
                // [find] -f <carreraId>

                try {
                    ClientCarreraDto foundCarrera = clientCarreraService.findCarrera(Long.valueOf(args[1]));

                    System.out.println("Found carrera " + foundCarrera);

                } catch (NumberFormatException | InstanceNotFoundException ex) {
                    ex.printStackTrace(System.err);
                } catch (Exception ex) {
                    ex.printStackTrace(System.err);
                }

            }else{
                validateArgs(args, 3, new int[] {});
                // [find] -f <fechaMax> <ciudad>

                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
                    var date = LocalDate.parse(args[1],formatter);

                    List<ClientCarreraDto> carreras = clientCarreraService.findCarreras(date,args[2]);

                    System.out.println("Found " + carreras.size() + " carrera(s)");
                    for (int i = 0; i < carreras.size(); i++) {
                        ClientCarreraDto carreraDto = carreras.get(i);
                        System.out.println("Id: " + carreraDto.getCarreraId() +
                                ", Ciudad: " + carreraDto.getCiudad() +
                                ", Descripcion: " + carreraDto.getDescripcion() +
                                ", Fecha Carrera: " + carreraDto.getFechaCarrera() +
                                ", Precio: " + carreraDto.getPrecioCarrera() +
                                ", Plazas Disponibles: " + carreraDto.getPlazasDisp());
                    }
                } catch (Exception ex) {
                    ex.printStackTrace(System.err);
                }
            }

        }else if("-i".equalsIgnoreCase(args[0])) {
            validateArgs(args, 4, new int[] {2});
            // [inscribirse] -i <email> <carreraId> <tarjeta>

            try {

                var ins = new ClientInscripcionDto(null,Long.valueOf(args[2]),args[1],args[3],0);

                Long inscID = clientCarreraService.addInscripcion(ins);

                System.out.println("Inscripcion " + inscID + " created sucessfully");

            } catch (NumberFormatException ex) {
                ex.printStackTrace(System.err);
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }

        }else if("-rd".equalsIgnoreCase(args[0])) {
            validateArgs(args, 3, new int[] {1});
            // [recoger dorsal] CarreraServiceClient -rd <inscripcion id> <tarjeta>

            try {
                int dorsal = clientCarreraService.recogerDorsal(Long.valueOf(args[1]),args[2]);

                System.out.println("Dorsal " + dorsal + " recogido");

            } catch (NumberFormatException | InputValidationException |
                    InstanceNotFoundException ex) {
                ex.printStackTrace(System.err);
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }

        }else if("-fi".equalsIgnoreCase(args[0])) {
            try{
                Double.parseDouble(args[1]);
                validateArgs(args, 2, new int[] {1});
                // [buscar inscripcion] -fi <inscripcionId>
                try {
                    ClientInscripcionDto foundInscripcion = clientCarreraService.findInscripcion(Long.valueOf(args[1]));

                    System.out.println("Found inscripcion " + args[1]);
                    System.out.println("Id: " + foundInscripcion.getInscripcionId() +
                            ", Carrera: " + foundInscripcion.getCarreraId() +
                            ", Email: " + foundInscripcion.getEmailUsuario() +
                            ", Tarjeta: " + foundInscripcion.getTarjeta());

                } catch (NumberFormatException | InstanceNotFoundException ex) {
                    ex.printStackTrace(System.err);
                } catch (Exception ex) {
                    ex.printStackTrace(System.err);
                }

            } catch (NumberFormatException e){
                printUsageAndExit();
            }

        }else if("-fe".equalsIgnoreCase(args[0])) {
            validateArgs(args, 2, new int[] {});
            // [buscar inscripcion] -fe <email>

            try {
                List<ClientInscripcionDto> foundInscripciones = clientCarreraService.findInscripciones(args[1]);

                System.out.println("Found " + foundInscripciones.size() + " inscripcion(es)");
                for (int i = 0; i < foundInscripciones.size(); i++) {
                    ClientInscripcionDto inscripcionDto = foundInscripciones.get(i);
                    System.out.println("Inscripcion Id: " + inscripcionDto.getInscripcionId() +
                            ", Carrera: " + inscripcionDto.getCarreraId() +
                            ", Email: " + inscripcionDto.getEmailUsuario() +
                            ", Tarjeta: " + inscripcionDto.getTarjeta());
                }

            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
        }
    }

    public static void validateArgs(String[] args, int expectedArgs, int[] numericArguments){

        if(expectedArgs != args.length) {
            printUsageAndExit();
        }
        for(int i = 0 ; i< numericArguments.length ; i++) {
            int position = numericArguments[i];
            try {
                Double.parseDouble(args[position]);
            } catch(NumberFormatException n) {
                printUsageAndExit();
            }
        }
    }

    public static void printUsageAndExit() {
        printUsage();
        System.exit(-1);
    }

    public static void printUsage() {
        System.err.println("Usage:\n" +
                "    [add]                CarreraServiceClient -a <ciudad> <descripcion> <fecha_carrera> <precio> <plazas_disponibles>\n" +
                "    [find]               CarreraServiceClient -f <carreraId>\n" +
                "    [find]               CarreraServiceClient -f <fecha_Max> <ciudad>\n" +
                "    [inscribirse]        CarreraServiceClient -i <email> <carreraId> <tarjeta>\n" +
                "    [recoger dorsal]     CarreraServiceClient -rd <inscripcion id> <tarjeta>\n" +
                "    [buscar inscripcion] CarreraServiceClient -fi <inscripcionId>\n" +
                "    [buscar inscripcion] CarreraServiceClient -fe <email>");
    }
}
