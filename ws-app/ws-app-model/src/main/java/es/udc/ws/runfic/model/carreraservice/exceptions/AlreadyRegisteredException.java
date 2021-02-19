package es.udc.ws.runfic.model.carreraservice.exceptions;

public class AlreadyRegisteredException extends Exception{




    private String email;
    private Long carreraId;

    public AlreadyRegisteredException(String email,Long carreraId) {
        super("El usuario " + email+ " ya ha sido registrado en la carrera "+carreraId+" )");
        this.email = email;
        this.carreraId = carreraId;
    }

    public String getEmail() { return email; }
    public Long getCarrera() { return carreraId; }

    public void setEmail(String email) { this.email = email; }
    public void setCarreraId(Long carreraId) { this.carreraId = carreraId; }
}
