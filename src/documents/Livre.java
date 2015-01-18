package documents;

import bibliotheque.Abonne;
import bibliotheque.Document;
import bibliotheque.PasLibreException;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Livre implements Document {

    private int numero;
    private int abonne;
    private boolean estDispo;
    private Date dateDispo;
    private final Object verrou;

    public Livre(int numero) {
        this.numero = numero;
        this.abonne = -1;
        this.estDispo = true;
        this.verrou = new Object();
    }

    @Override
    public int numero() {
        return numero;
    }

    @Override
    public void reserver(Abonne ab) throws PasLibreException {
        synchronized (this.verrou) {
            if (!this.estDispo) {
                if (this.dateDispo == null) {
                    throw new IllegalStateException("Le livre : " + this.numero + " n'est pas disponible alors qu'aucun emprunt ou reservation n'est en cours.");
                }
                throw new PasLibreException("Le livre : " + this.numero + " n'est pas disponible.\n"
                        + "Il sera disponible au plus tard le : " + this.dateDispo);
            }
            this.estDispo = false;
            this.dateDispo = new Date(new Date().getTime() + Executors.newSingleThreadScheduledExecutor().schedule(() -> {
                this.estDispo = true;
            }, numero, TimeUnit.DAYS).getDelay(TimeUnit.MILLISECONDS));
            System.out.println("le livre " + this.numero + " a bien ete reserve par : " + ab.getNom());
        }
    }

    @Override
    public void emprunter(Abonne ab) throws PasLibreException {
        synchronized (this.verrou) {

        }
    }

    @Override
    public void rendreDispo() {
        this.abonne = -1;
    }

    public boolean isDispo() {
        return this.abonne < 0;
    }

    @Override
    public Abonne getEmprunteur() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
