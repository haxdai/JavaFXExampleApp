package mx.edu.itt.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

/**
 * Clase utilitaria con funciones comunes.
 * @author hasdai
 *
 */
public class POSUtil {
	
	private POSUtil () {}
	
	/** The date pattern that is used for conversion. Change as you wish. */
    private static final String DATE_PATTERN = "dd.MM.yyyy";
    
    /** The date formatter. */
    private static final DateTimeFormatter DATE_FORMATTER = 
            DateTimeFormatter.ofPattern(DATE_PATTERN);
    
    /**
     * Returns the given date as a well formatted String. The above defined 
     * {@link POSUtil#DATE_PATTERN} is used.
     * 
     * @param date the date to be returned as a string
     * @return formatted string
     */
    public static String format(LocalDate date) {
        if (date == null) {
            return null;
        }
        return DATE_FORMATTER.format(date);
    }

    /**
     * Converts a String in the format of the defined {@link POSUtil#DATE_PATTERN} 
     * to a {@link LocalDate} object.
     * 
     * Returns null if the String could not be converted.
     * 
     * @param dateString the date as String
     * @return the date object or null if it could not be converted
     */
    public static LocalDate parse(String dateString) {
        try {
            return DATE_FORMATTER.parse(dateString, LocalDate::from);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Checks the String whether it is a valid date.
     * 
     * @param dateString
     * @return true if the String is a valid date
     */
    public static boolean isValidDate(String dateString) {
        // Try to parse the String.
        return POSUtil.parse(dateString) != null;
    }
    
    /**
     * Muestra un mensaje de alerta.
     * @param message mensaje.
     */
    public static void showAlert(String message) {
    	Alert alert = new Alert(AlertType.WARNING, message);
    	alert.showAndWait();
    }
    
    /**
     * Muestra un diálogo de confirmación.
     * @param message Mensaje.
     * @return
     */
    public static Alert showConfirmAlert(String message) {
    	Alert alert = new Alert(AlertType.CONFIRMATION, 
    			message, ButtonType.YES, ButtonType.NO);
    	
    	alert.showAndWait();
    	return alert;
    }
}
