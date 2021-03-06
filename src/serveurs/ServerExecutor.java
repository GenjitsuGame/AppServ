package serveurs;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class ServerExecutor extends Server {

    private ExecutorService executorService;

    public ServerExecutor(int port, Class<? extends Service> serviceClass, ExecutorService executorService) throws IOException {
        super(port, serviceClass);
        this.executorService = executorService;
    }

    @Override
    public void run() {
        System.out.println("Serveur lancé");
        try {
            while (true) {
                Socket client_socket = listen_socket.accept();
                Service dial = serviceClass.newInstance();
                dial.setSocket(client_socket);
                this.init(dial);
                this.executorService.submit(dial);
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
