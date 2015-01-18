/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrencytests;

import bibliotheque.Abonne;
import bibliotheque.Bibliotheque;
import bibliotheque.PasLibreException;
import documents.Livre;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author scalpa
 */
public class BibliothequeTest {

    private int num;

    private final int NB_ABONNE = 20;
    private final int NB_DOCUMENT = 10;

    public static void main(String[] args) {

        BibliothequeTest bt = new BibliothequeTest();
        bt.testSomeMethod();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            Logger.getLogger(ApplicationTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public BibliothequeTest() {

        resetNum();
        for (int i = 0; i < NB_ABONNE; ++i) {
            int num = getNumero();
            Bibliotheque.addAbonne(new Abonne(num, String.valueOf(num)));
        }

        resetNum();
        for (int i = 0; i < NB_DOCUMENT; ++i) {
            Bibliotheque.addDocument(new Livre(getNumero()));
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

        List<Runnable> runnables = new ArrayList<>();
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(NB_CONCURRENT_THREADS);

        for (int i = 0; i < NB_ABONNE; ++i) {

            Random r = new Random();

            final int numAbonne = i;
            final int numDocument = r.nextInt(this.NB_DOCUMENT);
            final int sleep = r.nextInt(100);

            runnables.add(() -> {
                try {
                    try {
                        Thread.sleep(sleep);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(BibliothequeTest.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Bibliotheque.reserver(numAbonne, numDocument);
                } catch (PasLibreException e) {
                    System.err.println(e.getMessage());
                }
            });
        }

        long delay = System.currentTimeMillis() + 5000;

        for (Runnable r : runnables) {
            ses.schedule(r, delay - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        ses.shutdown();
        while (true) {
            try {
                System.out.println("Waiting for the service to terminate...");
                if (ses.awaitTermination(10, TimeUnit.SECONDS)) {
                    break;
                }
            } catch (InterruptedException e) {
            }
        }
        System.out.println("Done cleaning");
        ses.shutdownNow();
    }

}
