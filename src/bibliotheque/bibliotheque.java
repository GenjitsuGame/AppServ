package bibliotheque;

import java.util.LinkedList;
import java.util.List;

public class Bibliotheque {

    private static final List<Document> documents = new LinkedList();
    private static final List<Abonne> abonnes = new LinkedList();
    private static final Object verrou = new Object();

    private static Abonne getAbonne(int num) {
        for (Abonne abonne : abonnes) {
            if (abonne.getNumero() == num) {
                return abonne;
            }
        }
        return null;
    }

    private static Document getDocument(int num) {
        for (Document document : documents) {
            if (document.numero() == num) {
                return document;
            }
        }
        return null;
    }

    public static void reserver(int abonne, int document) throws PasLibreException {
        synchronized (verrou) {
            getDocument(document).reserver(getAbonne(abonne));
        }
    }

    public static void emprunter(int abonne, int document) throws PasLibreException {
        synchronized (verrou) {
            getDocument(document).emprunter(getAbonne(abonne));
        }
    }

    public static void rendreDispo(int document) {
        synchronized (verrou) {
            getDocument(document).rendreDispo();
        }
    }
}
