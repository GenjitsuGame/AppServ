package bibliotheque;

import com.sun.corba.se.spi.ior.ObjectKey;

public interface Document {

    public final long DELAI_RESERVATION = 5;
    public final long DELAI_RETOUR = 14;

    public enum Etat {

        DEGRADE,
        OK
    }

    int numero();

    void reserver(Abonne ab) throws PasLibreException;

    void emprunter(Abonne ab) throws PasLibreException;

    void rendreDispo(Etat etat);

}
