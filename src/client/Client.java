package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Socket client = new Socket("127.0.0.1", 5000);
            PrintWriter out = new PrintWriter(client.getOutputStream());
            // Scanner scann = new Scanner(System.in);
            // String msg = scann.nextLine();

            Thread listener = new Thread(new ClientThread(client));
            listener.start();
            
            // while(msg != null) {
            //     out.println(msg);
            //     out.flush();
            //     msg = scann.nextLine();
            // }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
