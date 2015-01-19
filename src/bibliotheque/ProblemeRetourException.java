package bibliotheque;

public class ProblemeRetourException extends Exception {

    private final static String message = "Il y'a un probleme avec le retour de ce document.";

    public ProblemeRetourException() {
        super(ProblemeRetourException.message);
    }
    
    public ProblemeRetourException(String message) {
        super(message);
    }

    public ProblemeRetourException(String message, Exception e) {
        super(message, e);
    }
}
