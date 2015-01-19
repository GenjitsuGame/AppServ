package services;

import bibliotheque.Bibliotheque;
import bibliotheque.ProblemeRetourException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import serveurs.Service;

public class ServiceRetour extends Service {

    @Override
    public void run() {
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            in = new BufferedReader(new InputStreamReader(this.getSocket().getInputStream()));
            out = new PrintWriter(this.getSocket().getOutputStream());

            int numDoc = in.read();
            char[] buffer = new char[500];
            in.read(buffer);
            String etat = new String(buffer);

            Bibliotheque.rendreDispo(numDoc, etat.substring(0, etat.indexOf("\0")));

            out.println("Le document " + numDoc + " a bien ete rendu.");
        } catch (ProblemeRetourException e) {
            out.write(e.getMessage());
            out.flush();
            try {
                in.read();
            } catch (IOException ex) {
                Logger.getLogger(ServiceReservation.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IllegalStateException | IllegalArgumentException e) {
            out.write("Erreur lors du traitement de la demande : " + e.getMessage());
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
