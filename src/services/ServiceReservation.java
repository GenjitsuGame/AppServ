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

/**
 *
 * @author scalpa
 */
public class ServiceReservation extends Service {

    @Override
    public void run() {
        PrintWriter out = null;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(this.getSocket().getInputStream()));) {
            out = new PrintWriter(this.getSocket().getOutputStream(), true);
            Bibliotheque.reserver(in.read(), in.read());
        } catch (IOException ex) {
            Logger.getLogger(ServiceReservation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PasLibreException ex) {
            out.println(ex.getMessage());
        } finally {
            out.close();
        }
    }

}
