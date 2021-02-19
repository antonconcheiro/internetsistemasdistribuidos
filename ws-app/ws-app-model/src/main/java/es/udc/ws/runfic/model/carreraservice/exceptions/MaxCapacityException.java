package es.udc.ws.runfic.model.carreraservice.exceptions;

public class MaxCapacityException extends Exception{

    private int maxCapacity;

    public MaxCapacityException(int maxCapacity) {
        super("La carrera ha llegado a su número máximo de corredores = \"" + maxCapacity+ "\")");
        this.maxCapacity=maxCapacity;
    }

    public int getMaxCapacity() { return maxCapacity; }

    public void setMaxCapacity(int maxCapacity) { this.maxCapacity = maxCapacity; }
}
