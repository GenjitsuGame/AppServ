package serveurs;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class ServiceThread  {

    private Service service;
    
    protected final Thread thread;

    public ServiceThread(Service service) {
        this.service = service;
        this.thread = new Thread(service);
    }

    /**
     * start uniquement par Serveur Creation date: (17/12/2003 13:54:07)
     */
    public void start() {
        this.thread.start();
    }
    
    
    public void close() {
        try {
            this.thread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(ServiceThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Service getService() {
        return this.service;
    }

}
