package etatsdocument;

import bibliotheque.Abonne;
import bibliotheque.Document;
import bibliotheque.EtatDocument;

class Reserve implements EtatDocument {

    @Override
    public int getEtat() {
        return RESERVE;
    }

    @Override
    public void reserver(Document document) {
        throw new IllegalStateException("Impossible de reserver le " + document.getClass().getSimpleName() + " " + document.numero() + " car il est deja reserve par : " + document.getEmprunteur());
    }

    @Override
    public void rendreDispo(Document document) {
        throw new IllegalStateException("Impossible de retourner le " + document.getClass().getSimpleName() + " " + document.numero() + " car il est juste reserve par : " + document.getEmprunteur());
    }

    @Override
    public void emprunter(Document document, Abonne abonne) {
        if (abonne.getNumero() != document.getEmprunteur()) {
             throw new IllegalStateException("Impossible d'emprunter le " + document.getClass().getSimpleName() + " " + document.numero() + " car il est deja reserve par : " + document.getEmprunteur());
        }
        document.setEtat(new Emprunte());
    }

}
