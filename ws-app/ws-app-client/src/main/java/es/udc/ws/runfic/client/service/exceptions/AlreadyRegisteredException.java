package es.udc.ws.runfic.client.service.exceptions;

public class AlreadyRegisteredException extends Exception{

    private String mail;

    public AlreadyRegisteredException(String mail) {
        super(mail);
        this.mail=mail;
    }

    public String getMail() { return mail; }

    public void setMail(String mail) { this.mail = mail; }
}
