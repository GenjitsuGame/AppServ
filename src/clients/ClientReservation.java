package clients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientReservation implements Runnable {

    private static final String ADR_IP_BIBLIO = "localhost";
    private static final int PORT_RESERVATION = 2500;

    @Override
    public void run() {
        try (@SuppressWarnings("resource") Scanner clavier = new Scanner(System.in);
                Socket laSocket = new Socket(ADR_IP_BIBLIO, PORT_RESERVATION);
                BufferedReader socketIn = new BufferedReader(new InputStreamReader(new Socket(ADR_IP_BIBLIO, PORT_RESERVATION).getInputStream()));
                PrintWriter socketOut = new PrintWriter(laSocket.getOutputStream(), true);) {

            /*
             * bonjour
             */
            System.out.println("Bienvenue sur votre systeme de reservation : ");
            System.out.println("Vous pouvez ici reserver un livre disponible ");
            System.out.println("et passer le chercher dans les 5 heures");
            /*
             * saisie des donnees
             */

            System.out.println("Votre numero d'abonne, svp :");
            int numAbonne = clavier.nextInt();
            System.out.println("Le numero de livre que vous souhaitez reserver :");
            int numDocument = clavier.nextInt();
            /*
             * envoi des donnees au service
             */
            socketOut.write(numAbonne);
            socketOut.write(numDocument);
            /*
             * reception de la reponse et affichage de cette reponse
             */
            System.out.println(socketIn.readLine());
            // fermeture de la connexion
            //laSocket.close();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
