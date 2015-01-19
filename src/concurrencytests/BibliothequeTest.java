package concurrencytests;

import bibliotheque.Abonne;
import bibliotheque.Bibliotheque;
import bibliotheque.PasLibreException;
import bibliotheque.ProblemeRetourException;
import documents.DVD;
import documents.Livre;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BibliothequeTest {

    private int num;
    
    private int cpt = 0;

    private final int NB_ABONNE = 20;
    private final int NB_DOCUMENT = 10;

    private final int OPERATION_RENDRE = 0;
    private final int OPERATION_RESERVER = 1;
    private final int OPERATION_EMPRUNTER = 2;

    
    public static void main(String[] args) {

        BibliothequeTest bt = new BibliothequeTest();
        bt.testSomeMethod();

    }

    public BibliothequeTest() {

        resetNum();
        Random r = new Random();
        for (int i = 0; i < NB_ABONNE; ++i) {
            int num = getNumero();
            Bibliotheque.addAbonne(new Abonne(num, String.valueOf(num), r.nextInt(20) + 10));
        }

        resetNum();
        for (int i = 0; i < NB_DOCUMENT; ++i) {
            Bibliotheque.addDocument(r.nextBoolean() == false ? new Livre(getNumero()) : r.nextBoolean() == false ? new DVD(getNumero(), 12) : new DVD(getNumero(), 16));
        }

    }

    private int getNumero() {
        return num++;
    }

    private void resetNum() {
        this.num = 0;
    }

    public void testSomeMethod() {
        int NB_CONCURRENT_THREADS = 10;
        int NB_RUNNABLE = 250;

        List<Runnable> runnables = new ArrayList<>();
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(NB_CONCURRENT_THREADS);

        for (int i = 0; i < NB_RUNNABLE; ++i) {

            Random r = new Random();

            final int numAbonne = r.nextInt(NB_ABONNE);
            final int numDocument = r.nextInt(this.NB_DOCUMENT);
            final int sleep = r.nextInt(500);
            final int operation = r.nextInt(3);
            final boolean etat = r.nextBoolean();

            runnables.add(() -> {
                try {
                    Thread.sleep(sleep);
                } catch (InterruptedException ex) {
                    Logger.getLogger(BibliothequeTest.class.getName()).log(Level.SEVERE, null, ex);
                }
                ++cpt;
                try {
                    if (operation == OPERATION_RESERVER) {
                        Bibliotheque.reserver(numAbonne, numDocument);
                        System.out.println(numAbonne + " reserve " + numDocument);
                    } else if (operation == OPERATION_EMPRUNTER) {
                        Bibliotheque.emprunter(numAbonne, numDocument);
                        System.out.println(numAbonne + " emprunte " + numDocument);
                    } else if (operation == OPERATION_RENDRE) {
                        Bibliotheque.rendreDispo(numDocument, (etat == false) ? "DEGRADE" : "OK");
                        System.out.println("L'emprunteur rend " + numDocument);
                    }

                } catch ( ProblemeRetourException | PasLibreException |IllegalStateException | IllegalArgumentException e) {
                    System.err.println(e.getMessage());
                }
            });
        }

        for (Runnable r : runnables) {
            ses.execute(r);
        }
        
        ses.shutdown();
        try {
            ses.awaitTermination(1, TimeUnit.MINUTES);
            System.out.println(cpt);
        } catch (InterruptedException ex) {
            Logger.getLogger(BibliothequeTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
