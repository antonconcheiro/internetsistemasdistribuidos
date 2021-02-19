package es.udc.ws.runfic.model.carrera;

import es.udc.ws.util.configuration.ConfigurationParametersManager;

public class SqlCarreraDaoFactory {


    private final static String CLASS_NAME_PARAMETER = "SqlCarreraDaoFactory.className";
    private static SqlCarreraDao dao = null;

    private SqlCarreraDaoFactory() {
    }

    @SuppressWarnings("rawtypes")
    private static SqlCarreraDao getInstance() {
        try {
            String daoClassName = ConfigurationParametersManager
                    .getParameter(CLASS_NAME_PARAMETER);
            Class daoClass = Class.forName(daoClassName);
            return (SqlCarreraDao) daoClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public synchronized static SqlCarreraDao getDao() {

        if (dao == null) {
            dao = getInstance();
        }
        return dao;

    }
}
