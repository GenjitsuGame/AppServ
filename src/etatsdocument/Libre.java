package etatsdocument;

import bibliotheque.Abonne;
import bibliotheque.Document;
import bibliotheque.EtatDocument;

public class Libre implements EtatDocument {

    @Override
    public int getEtat() {
        return LIBRE;
    }

    @Override
    public void reserver(Document document) {
        document.setEtat(new Reserve());
    }

    @Override
    public void rendreDispo(Document document) {
        throw new IllegalStateException("Impossible de retourner le " + document.getClass().getSimpleName() + " " + document.numero() + " alors qu'il est libre.");
    }

    @Override
    public void emprunter(Document document, Abonne abonne) {
        document.setEtat(new Emprunte());
    }

}
