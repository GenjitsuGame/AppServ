/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrencytests;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author scalpa
 */
public class ApplicationTest {

    public static void main(String[] args) {

        BibliothequeTest bt = new BibliothequeTest();
        bt.testSomeMethod();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            Logger.getLogger(ApplicationTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
