package clients;


public abstract class Client implements Runnable {
    
    protected final Thread thread;

    public Client() {
        this.thread = new Thread(this);
    }
    
    public void start() {
        this.thread.start();
    }
    
    
}
