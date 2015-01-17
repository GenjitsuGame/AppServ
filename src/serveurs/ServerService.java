/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveurs;

import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author scalpa
 */
public class ServerService extends Server {

    protected Class<? extends Service> serviceClass; // la classe du service

    public ServerService(int port, Class<? extends Service> serviceClass) throws IOException, InstantiationException, IllegalAccessException {
        super(port);
        this.serviceClass = serviceClass;
        this.serviceClass.newInstance();
    }

    // Le corps de la thread d'ecoute :
    // Le serveur ecoute et accepte les connections.
    // pour chaque connection, il cree une instance de this.serviceClass
    // qui va la traiter
    @Override
    public void run() {
        System.out.println("Serveur lancé");
        try {
            while (true) {
                Socket client_socket = listen_socket.accept();
                Service dial = serviceClass.newInstance();
                dial.setSocket(client_socket);
                this.init(dial);
                dial.start();
            }
        } catch (IOException e) {
            System.err.println("Serveur arrete :" + e);
        } catch (InstantiationException e) {/*
             * déjà testé dans le constructeur
             */

        } catch (IllegalAccessException e) {/*
             * déjà testé dans le constructeur
             */

        } finally {
            this.close();
        }
    }

}
