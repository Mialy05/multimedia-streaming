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
            try {
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                msg = in.readLine();
                System.out.println("Ito msg " + msg);
                if (running != null) {
                    running.interrupt();
                    System.out.println("interrupted " + running.isInterrupted());
                }
                
                Sender sender = new Sender(msg, client);
                Thread threadSender = new Thread(sender);
                running = threadSender;
                threadSender.start();

            } catch (IOException e) {
                // e.printStackTrace();
                System.out.println("Client disconnected");
                break;
            } 
        }

    }

}
