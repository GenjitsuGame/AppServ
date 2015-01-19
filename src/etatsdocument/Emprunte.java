package etatsdocument;

import bibliotheque.Abonne;
import bibliotheque.Document;
import bibliotheque.EtatDocument;

class Emprunte implements EtatDocument {

    @Override
    public int getEtat() {
        return EMPRUNTE;
    }

    @Override
    public void reserver(Document document) {
        throw new IllegalStateException("Impossible de reserver le " + document.getClass().getSimpleName() + " " + document.numero() + " alors qu'il est emprunte : (" + document.numero() + ")");
    }

    @Override
    public void rendreDispo(Document document) {
        document.setEtat(new Libre());
    }

    @Override
    public void emprunter(Document document, Abonne abonne) {
        throw new IllegalStateException("Impossible d'emprunter le " + document.getClass().getSimpleName() + " " + document.numero() + " alors qu'il est déjà emprunter par : " + document.getEmprunteur());
    }

}
