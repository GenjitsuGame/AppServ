package bibliotheque;

import etatsdocument.Libre;

public interface EtatDocument {

    public static EtatDocument getEtatInitial() {
        return new Libre();
    }

    public final static int LIBRE = 1000;
    public final static int RESERVE = 1001;
    public final static int EMPRUNTE = 1002;
    
    int getEtat();
    
    void reserver(Document document);

    void rendreDispo(Document document);

    void emprunter(Document document, Abonne abonne);
}
