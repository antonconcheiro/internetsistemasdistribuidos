package es.udc.ws.runfic.model.carreraservice;

import es.udc.ws.util.exceptions.InputValidationException;

import java.math.BigInteger;

public final class PropertyValidator {

    private PropertyValidator() {}

    public static void validateLong(String propertyName,
                                    long value, int lowerValidLimit, int upperValidLimit)
            throws InputValidationException {

        if ( (value < lowerValidLimit) || (value > upperValidLimit) ) {
            throw new InputValidationException("Invalid " + propertyName +
                    " value (it must be greater than " + lowerValidLimit +
                    " and lower than " + upperValidLimit + "): " + value);
        }

    }

    public static void validateNotNegativeLong(String propertyName,
                                               long longValue) throws InputValidationException {

        if (longValue < 0) {
            throw new InputValidationException("Invalid " + propertyName +
                    " value (it must be greater than 0): " +	longValue);
        }

    }

    public static void validateDouble(String propertyName,
                                      double doubleValue, double lowerValidLimit, double upperValidLimit)
            throws InputValidationException {

        if ((doubleValue < lowerValidLimit) ||
                (doubleValue > upperValidLimit)) {
            throw new InputValidationException("Invalid " + propertyName +
                    " value (it must be gtrater than " + lowerValidLimit +
                    " and lower than " + upperValidLimit + "): " +
                    doubleValue);
        }

    }

    public static void validateMandatoryString(String propertyName,
                                               String stringValue) throws InputValidationException {

        if ( (stringValue == null) || (stringValue.trim().length() == 0) ) {
            throw new InputValidationException("Invalid " + propertyName +
                    " value (it cannot be null neither empty): " +
                    stringValue);
        }

    }

    public static void validateCreditCard(String propertyValue)
            throws InputValidationException {

        boolean validCreditCard = true;
        if ( (propertyValue != null) && (propertyValue.length() == 16) ) {
            try {
                new BigInteger(propertyValue);
            } catch (NumberFormatException e) {
                validCreditCard = false;
            }
        } else {
            validCreditCard = false;
        }
        if (!validCreditCard) {
            throw new InputValidationException("Invalid credit card number" +
                    " (it should be a sequence of 16 numeric digits): " +
                    propertyValue);
        }

    }

    public static void validateEmail(String propertyValue)
            throws InputValidationException {

        boolean validEmail = true;
        if ( propertyValue != null ) {
            if(!propertyValue.contains("@")){validEmail=false;}
            else{
                String parts[] = propertyValue.split("@");
                if(parts[0]==null || parts[1]==null){validEmail=false;}
                else{
                    if(!parts[1].contains(".")){validEmail=false;}
                    else{
                        String parts1[] = parts[1].split("\\.");
                        if(parts1[0]==null || parts1[1]==null){validEmail=false;}
                    }
                }
            }
        } else {
            validEmail = false;
        }
        if (!validEmail) {
            throw new InputValidationException("Invalid email (it should be xxxxxx@xxxxxx.xxx): " +
                    propertyValue);
        }

    }

    public static void validateCodigoDorsal(Long propertyValue)
            throws InputValidationException {

        boolean validCodDorsal = true;
        if ( propertyValue != null ) {
            if (propertyValue < 0)
            validCodDorsal=false;
        } else {
            validCodDorsal = false;
        }
        if (!validCodDorsal) {
            throw new InputValidationException("Invalid codigo dorsal (it should be aaaaa00000): " +
                    propertyValue);
        }

    }

}
