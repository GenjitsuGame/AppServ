/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrencytests;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import serveurs.ServerExecutor;
import services.ServiceEmprunt;
import services.ServiceReservation;
import services.ServiceRetour;

/**
 *
 * @author scalpa
 */
public class ApplicationTest {

    public static void main(String[] args) {

        new BibliothequeTest();
        
        ExecutorService services = Executors.newFixedThreadPool(20);
        ExecutorService serveurs = Executors.newFixedThreadPool(3);
        
        try {
            serveurs.submit(new ServerExecutor(2500, ServiceReservation.class, services));
            serveurs.submit(new ServerExecutor(2600, ServiceEmprunt.class, services));
            serveurs.submit(new ServerExecutor(2700, ServiceRetour.class, services));
        } catch (IOException iOException) {
        }
        
        
       
    }

}
