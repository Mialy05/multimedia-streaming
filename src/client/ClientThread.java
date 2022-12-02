package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientThread implements Runnable {
    Socket client;
    Thread running;
    Boolean stop;

    public ClientThread(Socket client) {
        this.client = client;
        this.stop = false;
    }

    @Override
    public void run() {
        Scanner scann = new Scanner(System.in);
        try {
            PrintWriter out = new PrintWriter(client.getOutputStream());
            String msg;
            while (true) {
                msg = scann.nextLine();

                if(running != null) {
                    this.stop = true;
                    try {
                        running.interrupt();
                        System.out.println("Lecture courante interrompu");
                    } catch (Exception e) {
                        System.out.println("Tsy afaka mamono zah");
                    }
                    System.out.println("running : " + running.isAlive() + " interrupted: " + running.isInterrupted());

                }
                System.out.println("req : " + msg);
                out.println(msg);
                out.flush();
                Thread listener = new Thread(new Listener(client, stop));
                listener.start();
                this.running = listener;
                System.out.println("thread vaovao !!!");
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // while (msg != null) {
        //     out.println(msg);
        //     out.flush();
        //     msg = scann.nextLine();
        // }
        
    }


}
