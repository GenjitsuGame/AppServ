/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author scalpa
 */
public class ServiceExecutor {
    
    private final ExecutorService executor;

    public ServiceExecutor() {
        this.executor = Executors.newFixedThreadPool(10);
    }
    
    public void invoke(Runnable r) {
        this.executor.submit(r);
    }
    
    
}
