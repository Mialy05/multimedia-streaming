package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server
 */
public class Server {

    public static void main(String[] args) {
        try {
            while(true) {
                ServerSocket server = new ServerSocket(5000);
                Socket client = server.accept();
                System.out.println(client.getInetAddress().getHostName() + " connected");
                Thread listener = new Thread(new ServerThread(client));
                listener.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}