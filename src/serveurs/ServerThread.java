package serveurs;

import java.io.IOException;
import java.net.Socket;

public class ServerThread extends Server {

    protected Thread thread;

    public ServerThread(int port, Class<? extends Service> serviceClass) throws IOException {
        super(port, serviceClass);
        this.thread = new Thread(this);
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
                ServiceThread dial = new ServiceThread(serviceClass.newInstance());
                dial.getService().setSocket(client_socket);
                this.init(dial.getService());
                dial.start();
            }
        } catch (IOException e) {
            System.err.println("Serveur arrete :" + e);
        } catch (InstantiationException | IllegalAccessException e) {/*
             * déjà testé dans le constructeur
             */

        } /*
         * déjà testé dans le constructeur
         */ finally {
            this.close();
        }
    }

    public void start() {
        this.thread.start();
    }

}
