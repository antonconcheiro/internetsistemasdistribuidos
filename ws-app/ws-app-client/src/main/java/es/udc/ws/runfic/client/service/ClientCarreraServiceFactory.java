package es.udc.ws.runfic.client.service;

import es.udc.ws.util.configuration.ConfigurationParametersManager;

import java.lang.reflect.InvocationTargetException;

public class ClientCarreraServiceFactory {

    private final static String CLASS_NAME_PARAMETER
            = "ClientCarreraServiceFactory.className";
    private static Class<ClientCarreraService> serviceClass = null;

    private ClientCarreraServiceFactory() {
    }

    @SuppressWarnings("unchecked")
    private synchronized static Class<ClientCarreraService> getServiceClass() {
        if (serviceClass == null) {
            try {
                String serviceClassName = ConfigurationParametersManager
                        .getParameter(CLASS_NAME_PARAMETER);
                serviceClass = (Class<ClientCarreraService>) Class.forName(serviceClassName);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return serviceClass;
    }

    public static ClientCarreraService getService() {
        try {
            return (ClientCarreraService) getServiceClass().getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

}
