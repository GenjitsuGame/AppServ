/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clients;

/**
 *
 * @author scalpa
 */
public abstract class Client implements Runnable {
    
    protected final Thread thread;

    public Client() {
        this.thread = new Thread(this);
    }
    
    public void start() {
        this.thread.start();
    }
    
    
}
