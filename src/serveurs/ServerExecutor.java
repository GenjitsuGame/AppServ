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
public class ServerExecutor extends ServerService {

    private ServiceExecutor serviceExecutor;

    public ServerExecutor(int port, Class<? extends Service> serviceClass, Class<? extends ServiceExecutor> executorClass) throws IOException, InstantiationException, IllegalAccessException {
        super(port, serviceClass);
        this.serviceExecutor = executorClass.newInstance();
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
                serviceExecutor.invoke(dial);
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
