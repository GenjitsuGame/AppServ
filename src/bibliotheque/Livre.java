package bibliotheque;

import java.util.Timer;

public class Livre implements Document {

    private int numero;
    private int abonne;
    private Timer delaiReservation;
    private final Object verrou;

    public Livre(int numero) {
        this.numero = numero;
        this.abonne = -1;
        this.delaiReservation = null;
        this.verrou = new Object();
    }

    private class RendreDispoTask {

    }

    @Override
    public int numero() {
        return numero;
    }

    @Override
    public void reserver(Abonne ab) throws PasLibreException {

    }

    @Override
    public void emprunter(Abonne ab) throws PasLibreException {

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
