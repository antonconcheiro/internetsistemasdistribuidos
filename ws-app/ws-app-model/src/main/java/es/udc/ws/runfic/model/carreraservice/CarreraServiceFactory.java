package es.udc.ws.runfic.model.carreraservice;

import es.udc.ws.runfic.model.carrera.Carrera;
import es.udc.ws.util.configuration.ConfigurationParametersManager;

public class CarreraServiceFactory {


    private final static String CLASS_NAME_PARAMETER = "CarreraServiceFactory.className";
    private static CarreraService service = null;

    private CarreraServiceFactory() {
    }

    @SuppressWarnings("rawtypes")
    private static CarreraService getInstance() {
        try {
            String serviceClassName = ConfigurationParametersManager
                    .getParameter(CLASS_NAME_PARAMETER);
            Class serviceClass = Class.forName(serviceClassName);
            return (CarreraService) serviceClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public synchronized static CarreraService getService() {
        if (service == null) {
            service = getInstance();
        }
        return service;

    }
}
