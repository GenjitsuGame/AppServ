package bibliotheque;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Bibliotheque {

    private static final List<Document> documents = Collections.synchronizedList(new LinkedList());
    private static final List<Abonne> abonnes = Collections.synchronizedList(new LinkedList());

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
        Document d;
        synchronized (verrou) {
            d = getDocument(document);
        }
        d.reserver(getAbonne(abonne));
    }

    public static void emprunter(int abonne, int document) throws PasLibreException {
        Document d;
        synchronized (verrou) {
            d = getDocument(document);
        }
        d.emprunter(getAbonne(abonne));
    }

    public static void rendreDispo(int document, String etat) throws ProblemeRetourException {
        Document d;
        synchronized (verrou) {
            d = getDocument(document);
        }
        d.rendreDispo(Document.Etat.valueOf(etat.toUpperCase()));
    }

    public static void addAbonne(Abonne abonne) {
        abonnes.add(abonne);
    }

    public static void addDocument(Document document) {
        documents.add(document);
    }
}
