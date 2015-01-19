package application;

import concurrencytests.BibliothequeTest;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import serveurs.ServerExecutor;
import services.ServiceEmprunt;
import services.ServiceReservation;
import services.ServiceRetour;

public class ApplicationTest {

    public static void main(String[] args) {

        new BibliothequeTest();
        
        ExecutorService services = Executors.newFixedThreadPool(20);
        ExecutorService serveurs = Executors.newFixedThreadPool(3);
        
        try {
            serveurs.submit(new ServerExecutor(2500, ServiceReservation.class, services));
            serveurs.submit(new ServerExecutor(2600, ServiceEmprunt.class, services));
            serveurs.submit(new ServerExecutor(2700, ServiceRetour.class, services));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
       
    }

}
