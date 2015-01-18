package bibliotheque;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Abonne {

    private static final long BAN_LENGTH = 30;
    
    private int numero;
    private String nom;
    private Date interdit;

    public Abonne(int numero, String nom) {
        this.numero = numero;
        this.nom = nom;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNom() {
        return nom;
    }

    public void interdire() {
        this.interdit = new Date(new Date().getTime() + Executors.newSingleThreadScheduledExecutor().schedule(() -> {
            this.interdit = null;
        }, BAN_LENGTH, TimeUnit.DAYS).getDelay(TimeUnit.MILLISECONDS));
    }
    
    public long getInterdit() {
        return this.interdit.getTime();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.numero;
        hash = 41 * hash + Objects.hashCode(this.nom);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Abonne other = (Abonne) obj;
        if (this.numero != other.numero) {
            return false;
        }
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        return true;
    }
    
    
}
