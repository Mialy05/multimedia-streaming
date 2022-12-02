package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ClientThread extends Application implements Runnable {
    Socket client;
    Thread running;
    String req;
    Pane component;

    public ClientThread(Socket client, String req, Pane component) {
        this.client = client;
        this.req = req;
        this.component = component;
    }

    @Override
    public void run() {
        try {
            PrintWriter out = new PrintWriter(client.getOutputStream());
            // String msg;
            // while (true) {
                // msg = scann.nextLine();

                if(running != null) {
                    try {
                        running.interrupt();
                        System.out.println("Lecture courante interrompu");
                    } catch (Exception e) {
                        System.out.println("Tsy afaka mamono zah");
                    }
                    System.out.println("running : " + running.isAlive() + " interrupted: " + running.isInterrupted());

                }
                System.out.println("req : " + req);
                out.println(req);
                out.flush();
                Platform.runLater(new Listener(client, component));
                // Thread listener = new Thread(new Listener(client, component));
                // listener.start();
                // this.running = listener;
                System.out.println("thread vaovao !!!");
            // }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // while (msg != null) {
        //     out.println(msg);
        //     out.flush();
        //     msg = scann.nextLine();
        // }
        
    }

    @Override
    public void start(Stage arg0) throws Exception {
        // TODO Auto-generated method stub
        
    }


}
