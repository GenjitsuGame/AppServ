/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import bibliotheque.Bibliotheque;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import serveurs.Service;

public class ServiceRetour extends Service {

    @Override
    public void run() {
        PrintWriter out = null;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(this.getSocket().getInputStream()));) {
            out = new PrintWriter(this.getSocket().getOutputStream(), true);
            Bibliotheque.rendreDispo(in.read(), in.readLine());
        } catch (IOException e) {

        }
    }

}
