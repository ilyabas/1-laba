/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Harry
 */
public class BigCahunaBurger_client{
    
    private static Socket socket;
    private static BufferedReader reader;
    private static PrintStream writer;

    private static void setConnection()
    {
         try {
            socket = new Socket(InetAddress.getLocalHost(), 8071);
            writer = new PrintStream(socket.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(BigCahunaBurger_client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void closeConnection()
    {
        try {
            if (writer != null)
                writer.close();
            if (reader != null)
                reader.close();
            if (socket != null)
                socket.close();
            System.exit(0);
        } catch (IOException ex) {
            Logger.getLogger(BigCahunaBurger_client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private static void runMainMenu()
    {
        String choice = "";
        try {
            choice = reader.readLine();
        } catch (IOException ex) {
            Logger.getLogger(BigCahunaBurger_client.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!choice.equals("next"))
            closeConnection();
        else
        {
            Scanner scan = new Scanner(System.in);
            do
            {
                writeMenu();
                choice = scan.nextLine();
                switch(choice)
                {
                    case "2":
                        moveToAnotherQueue();
                    break;
                    case "1":
                        lookAtQueues();
                    break;
                    case "0":
                        sendCancel();
                    break;
                }
            }while ( ! choice.equals("0"));
        }
    }
   public static void main(String[] args) {
        setConnection();
        runMainMenu();
        closeConnection();
    }
    
    private static void writeMenu()
    {
        System.out.println("Welcome to Big Cahuna Burger!\n1. Look at queues\n2. Change queue\n0. Go away\n");        
    }
    
    private static void lookAtQueues()
    {
        writer.println("1");
        try {
            
            String sizeStr = reader.readLine();
            int size = Integer.parseInt(sizeStr);
            for (int i = 0; i < size; i++)
            {
                sizeStr = reader.readLine();
                System.out.println(sizeStr);
            }
        } catch (IOException ex) {
            Logger.getLogger(BigCahunaBurger_client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void moveToAnotherQueue()
    {
        writer.println("2");
        System.out.println("To what queue do you want?");
        Scanner scan = new Scanner(System.in);
        String position = scan.nextLine();
        writer.println(position);
        try {
            position = reader.readLine();
        } catch (IOException ex) {
            Logger.getLogger(BigCahunaBurger_client.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(position);
    }
    
    private static void sendCancel()
    {
        writer.println("0");
    }
}
