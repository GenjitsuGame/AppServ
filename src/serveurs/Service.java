package serveurs;

import java.net.*;

//  la classe Service, à sous-classer pour implémenter le service dans run()
public abstract class Service implements Runnable {

    private Socket socket;

    /**
     * getSocket sera utilisée uniquement dans votre service, donc protected
     * Creation date: (18/12/2003 16:13:51)
     *
     * @return java.net.Socket
     */
    protected Socket getSocket() {
        return socket;
    }

	// implémenter le run dans la sous-classe
    @Override
    abstract public void run();

    /**
     * setSocket permet l'init par Serveur, donc (package) Creation date:
     * (17/12/2003 13:54:07)
     *
     * @param newSocket java.net.Socket
     */
    void setSocket(java.net.Socket newSocket) {
        socket = newSocket;
    }

    @Override
    protected void finalize() throws Throwable {
        socket.close();
    }

}
