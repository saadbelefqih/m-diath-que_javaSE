/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mediatheque.share;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USER
 */
public class MainThread extends Thread{

    @Override
    public void run() {
         int port=1234;
        ServerSocket sersoc ; 
        try {
            sersoc = new ServerSocket (port);
        
        System.out.println ("serveur active sur port " + port) ; 
        while (true) { 
            Socket soc = sersoc.accept(); 
            KindlsThread kindlsThread = new KindlsThread(soc);
            kindlsThread.start();
        }
        } catch (IOException ex) {
            Logger.getLogger(MainThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MainThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
}
