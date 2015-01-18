/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bibliotheque;

import static bibliotheque.Document.DELAI_RESERVATION;
import static bibliotheque.Document.DELAI_RETOUR;
import documents.Livre;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author scalpa
 */
public abstract class DocumentBibliotheque implements Document {
    protected final int numero;
    protected Abonne emprunteur;
    protected Date dateDispo;
    protected final Object verrou;
    protected ScheduledFuture<Abonne> emprunt;
    
     public DocumentBibliotheque(int numero) {
        this.numero = numero;
        this.verrou = new Object();
    }

    @Override
    public int numero() {
        return numero;
    }

    @Override
    public void reserver(Abonne ab) throws PasLibreException {
        synchronized (this.verrou) {
            if (this.emprunt == null) {
                throw new PasLibreException("Le " + this.getClass().getSimpleName() + " : " + this.numero + " n'est pas disponible.\n"
                        + "Il sera disponible au plus tard le : " + this.dateDispo);
            }
            this.emprunteur = ab;
            this.emprunt = Executors.newSingleThreadScheduledExecutor().schedule(() -> ab, DELAI_RESERVATION, TimeUnit.HOURS);

        }
    }

    @Override
    public void emprunter(Abonne ab) throws PasLibreException {
        synchronized (this.verrou) {
            try {
                if (this.emprunt == null || this.emprunt.get().equals(ab)) {
                    this.emprunteur = ab;
                    this.emprunt = Executors.newSingleThreadScheduledExecutor().schedule(() -> {
                        ab.interdire();
                        return ab;
                    }, DELAI_RETOUR, TimeUnit.DAYS);
                } else {
                    throw new PasLibreException("Le " + this.getClass().getSimpleName() + " : " + this.numero + " n'est pas disponible.\n");
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Livre.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                Logger.getLogger(Livre.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
    }

    @Override
    public void rendreDispo(Etat etat) {
        synchronized (this.verrou) {
            if (etat == Etat.DEGRADE) {
                try {
                    this.emprunt.get().interdire();
                } catch (InterruptedException | ExecutionException ex) {
                    Logger.getLogger(Livre.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            this.emprunteur = null;
            this.dateDispo = null;
        }
    }

    public boolean isDispo() {
        synchronized (this.verrou) {
            return this.emprunteur == null;
        }
    }

}
