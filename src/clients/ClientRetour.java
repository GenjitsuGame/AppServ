package clients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientRetour {

    private static final String ADR_IP_BIBLIO = "localhost";
    private static final int PORT_RESERVATION = 2700;

    public static void main(String[] args) throws IOException {
        Socket laSocket = new Socket(ADR_IP_BIBLIO, PORT_RESERVATION);
        BufferedReader socketIn = new BufferedReader(new InputStreamReader(laSocket.getInputStream()));
        PrintWriter socketOut = new PrintWriter(laSocket.getOutputStream(), true);
        /*
         * bonjour
         */
        System.out.println("Bienvenue sur le systeme de retour de livre : ");
        /*
         * saisie des donnees
         */
        @SuppressWarnings("resource")
        Scanner clavier = new Scanner(System.in);
        System.out.println("le numero d'abonne, svp :");
        int numAbonne = clavier.nextInt();
        System.out.println("Le numero de livre emprunte :");
        int numDocument = clavier.nextInt();
        /*
         * envoi des donnees au service
         */
        socketOut.println(numAbonne);
        socketOut.println(numDocument);
        /*
         * reception de la reponse et affichage de cette reponse
         */
        System.out.println(socketIn.readLine());
        // fermeture de la connexion
        laSocket.close();
    }
}
