package es.udc.ws.runfic.client.service.exceptions;

public class MaxCapacityException extends Exception{

    private int maxCapacity;

    public MaxCapacityException(int maxCapacity) {
        super("La carrera ya ha llegado a su capacidad máxima de \"" + maxCapacity
                + "\" corredores \")");
        this.maxCapacity=maxCapacity;
    }

    public int getMaxCapacity() { return maxCapacity; }

    public void setMaxCapacity(int maxCapacity) { this.maxCapacity = maxCapacity; }
}
