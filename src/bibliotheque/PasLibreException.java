package bibliotheque;

public class PasLibreException extends Exception {

    private final static String message = "Ce document n'est pas libre :\n";

    public PasLibreException() {
        super(PasLibreException.message);
    }
    
    public PasLibreException(String message) {
        super(message);
    }

    public PasLibreException(String message, Exception e) {
        super(message, e);
    }
}
