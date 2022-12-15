package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerThread implements Runnable {
    Socket client;
    Thread running;

    public ServerThread(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        BufferedReader in;
        String msg;
        while (!client.isClosed()) {
            System.out.println(client.isClosed());
            try {
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                msg = in.readLine();
                System.out.println("Ito msg " + msg);
        // hanapaka an'ilay envoi ana data raha ohatra ka nandefa requete ilay client 
                // if (running != null) {
                //     running.interrupt();
                //     System.out.println("interrupted " + running.isInterrupted());
                // }
                if(msg.equals("STOP")) {
                    client.close();
                }
                else {
                    Sender sender = new Sender(msg, client);
                    Thread threadSender = new Thread(sender);
                    running = threadSender;
                    threadSender.start();
                }
                

            } catch (IOException e) {
                System.out.println("Erreur de connexion");
                try {
                    client.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } 
        }
        System.out.println(client.getInetAddress().getHostName() + " disconnected");
    }

}
