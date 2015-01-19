package documents;

import bibliotheque.Abonne;
import bibliotheque.DocumentBibliotheque;
import bibliotheque.PasLibreException;

public class DVD extends DocumentBibliotheque {

    private int ageMinimum;

    public DVD(int numero, int ageMinimum) {
        super(numero);
        this.ageMinimum = ageMinimum;
    }

    @Override
    public void emprunter(Abonne ab) throws PasLibreException {
        synchronized (this.verrou) {
            if (ab.getAge() < this.ageMinimum) {
                throw new IllegalArgumentException("L'abonne est trop jeune pour ce document.");
            }
            super.emprunter(ab);
        }

    }

    @Override
    public void reserver(Abonne ab) throws PasLibreException {
        synchronized (this.verrou) {
            if (ab.getAge() < this.ageMinimum) {
                throw new IllegalArgumentException("L'abonne est trop jeune pour ce document.");
            }
            super.reserver(ab);
        }
    }

}
