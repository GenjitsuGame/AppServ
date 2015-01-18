/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import bibliotheque.Bibliotheque;
import bibliotheque.PasLibreException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import serveurs.Service;

public class ServiceEmprunt extends Service {

    @Override
    public void run() {
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            in = new BufferedReader(new InputStreamReader(this.getSocket().getInputStream()));
            out = new PrintWriter(this.getSocket().getOutputStream());

            int numAbo = in.read();
            int numDoc = in.read();

            Bibliotheque.emprunter(numAbo, numDoc);

            out.println("Le document " + numDoc + " a bien ete emprunte par l'abonne : " + numAbo);
        } catch (PasLibreException e) {
            out.write(e.getMessage());
            out.flush();
            try {
                in.read();
            } catch (IOException ex) {
                Logger.getLogger(ServiceReservation.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    Logger.getLogger(ServiceReservation.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
