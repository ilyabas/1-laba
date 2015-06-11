/*
 * To change thinObject license header, chooutObjecte License Headers in Project Properties.
 * To change thinObject template file, chooutObjecte Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Queue;

/**
 *
 * @author Harry
 */
public class ServerThread extends Thread{

    private Socket socket;
    private BufferedReader reader;
    private PrintStream writer;
    ArrayList<Queue> queues;
    int curUserQueue;
    public ServerThread(Socket socket, ArrayList<Queue> queues) throws IOException {
        this.socket = socket;
        writer = new PrintStream(socket.getOutputStream());
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.queues = queues;
    }
    
    @Override
    public void run() {
        
        int queueWithFreePlace = checkQueues();
        if ( queueWithFreePlace == -1)
        {
            System.err.println("No free place !");
            writer.println("disconnect");
            //disconnect();
        }
        else
        {
            System.err.println(" new customer! at: " + socket.getInetAddress());
            queues.get(queueWithFreePlace).addToQueue();
            curUserQueue = queueWithFreePlace;
            writer.println("next");
            String request = "";
            do
            {
                try {
                    request = reader.readLine();
                } catch (IOException ex) {
                    Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
                }
                switch(request)
                {
                    case "2":
                        moveToAnotherQueue();
                    break;
                    case "1":
                        lookAtQueues();
                    break;
                }
            }
            while (! request.equals("0"));
            queues.get(queueWithFreePlace).removeFromQueue();
            System.err.println(" lost connect " + socket.getInetAddress());
            disconnect();
        }
    }
    private void moveToAnotherQueue()
    {
        String result = "";
        try {
            result = reader.readLine();
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        int newQueueNumber = Integer.parseInt(result) - 1;
        if (queues.get(newQueueNumber).isFull() )
            writer.println("No place in queue!");
        else
        {
            queues.get(curUserQueue).removeFromQueue();
            queues.get(newQueueNumber).addToQueue();
            curUserQueue = newQueueNumber;
            writer.println("Got it!");
        }
    }
    
    private void lookAtQueues()
    {
        writer.println(queues.size());
        for (Queue queue: queues)
        {
            writer.println(queue.toString());
        }
    }
    private int checkQueues()
    {
        int i = 0;
        for (Queue queue: queues)
        {
            if ( ! queue.isFull())
                return i;
            i++;
        }
        return -1;
    }
    public void disconnect()
    {
        try {
            if (writer != null) {
             writer.close();
            }
            if (reader != null) {
             reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.interrupt();
        }
    }
    
}
