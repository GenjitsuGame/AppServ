package bibliotheque;

import static bibliotheque.Document.DELAI_RESERVATION;
import static bibliotheque.Document.DELAI_RETOUR;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public abstract class DocumentBibliotheque implements Document {

    protected final int numero;
    protected final Object verrou;
    protected ScheduledFuture emprunt;
    protected Abonne emprunteur;
    protected EtatDocument etatDocument;

    public DocumentBibliotheque(int numero) {
        this.numero = numero;
        this.verrou = new Object();
        this.etatDocument = EtatDocument.getEtatInitial();
    }

    @Override
    public int numero() {
        return numero;
    }

    @Override
    public void reserver(Abonne ab) throws PasLibreException {
        synchronized (this.verrou) {
            if (ab.estInterdit()) {
                throw new IllegalArgumentException("L'abonne " + ab.getNumero() + " est interdit d'emprunt.");
            }

            if (this.emprunt != null || this.emprunteur != null) {
                throw new PasLibreException("Le " + this.getClass().getSimpleName() + " : " + this.numero + " n'est pas disponible.");
            }
            this.emprunteur = ab;
            this.etatDocument.reserver(this);
            this.emprunt = Executors.newSingleThreadScheduledExecutor().schedule(() -> {
                this.etatDocument.rendreDispo(this);
                this.emprunteur = null;
                this.emprunt = null;
            }, DELAI_RESERVATION, TimeUnit.HOURS);
        }
    }

    @Override
    public void emprunter(Abonne ab) throws PasLibreException {
        synchronized (this.verrou) {
            if (ab.estInterdit()) {
                throw new IllegalArgumentException("L'abonne " + ab.getNumero() + " est interdit d'emprunt.");
            }

            try {
                this.etatDocument.emprunter(this, ab);
                this.emprunteur = ab;
                this.emprunt = Executors.newSingleThreadScheduledExecutor().schedule(() -> {
                }, DELAI_RETOUR, TimeUnit.DAYS);
            } catch (IllegalStateException e) {
                throw new PasLibreException("Le " + this.getClass().getSimpleName() + " : " + this.numero + " n'est pas disponible.");
            }
        }
    }

    @Override
    public void rendreDispo(Etat etat) throws ProblemeRetourException {
        synchronized (this.verrou) {

            this.etatDocument.rendreDispo(this);

            if ((etat == Etat.DEGRADE) || this.emprunt.isDone()) {
                this.emprunteur.interdire();
                int numEmprunteur = this.emprunteur.getNumero();
                this.emprunteur = null;
                if ((etat == Etat.DEGRADE)) {
                    this.emprunt.cancel(true);
                    this.emprunt = null;
                    throw new ProblemeRetourException(numEmprunteur + " est interdit d'emprunt pendant 1 mois pour avoir rendu le "
                            + this.getClass().getSimpleName() + " : " + numero + " dans un sale etat.");
                } else if (this.emprunt.isDone()) {
                    this.emprunt = null;
                    throw new ProblemeRetourException(numEmprunteur + " est interdit d'emprunt pendant 1 mois pour avoir rendu le "
                            + this.getClass().getSimpleName() + " : " + numero + " en retard.");
                }
            } else {
                this.emprunteur = null;
                this.emprunt.cancel(true);
                this.emprunt = null;
            }
        }
    }

    @Override
    public void setEtat(EtatDocument etatDocument
    ) {
        this.etatDocument = etatDocument;
    }

    @Override
    public int getEmprunteur() {
        return emprunteur.getNumero();
    }

}
