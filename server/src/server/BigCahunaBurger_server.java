/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Queue;

/**
 *
 * @author Harry
 */
public class BigCahunaBurger_server {

    private static int defaultPort = 8071;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int port = defaultPort;
        if (args.length > 0)
            port = Integer.parseInt(args[0]);
        
        ArrayList<Queue> queues = new ArrayList<>();
        for (int i = 1; i < 4; i++)
        {
            queues.add(new Queue(i, 5, 0));
        }
        
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            System.out.println("Big Cahuna Burger ready at ");
            System.out.println(InetAddress.getLocalHost());
        } catch (IOException ex) {
            Logger.getLogger(BigCahunaBurger_server.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            while(true)
            {
                try {
                    Socket socket = server.accept();
                    System.out.println(socket.getInetAddress().getHostName() + " connected");
                    ServerThread thread = new ServerThread(socket, queues);
                    thread.start();
                    //socket.close();
                } catch (IOException ex) {
                    Logger.getLogger(BigCahunaBurger_server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
    }
    
}
