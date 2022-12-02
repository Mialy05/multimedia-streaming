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
        int i=1;

        while (true) {
            try {
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                msg = in.readLine();
                if(running != null) {
                    System.out.println("Nouvelle requete. Lecture courante interrompu");
                    running.interrupt();
                    System.out.println("running " + running.isAlive() + " interrupted " + running.isInterrupted());
                }
                System.out.println(msg);
                if(i==1) {
                    Thread sender = new Thread(new Sender(msg, client));
                    running = sender;
                    sender.start();
                }
                i++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
