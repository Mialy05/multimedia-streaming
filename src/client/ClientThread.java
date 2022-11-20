package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientThread implements Runnable {
    Socket client;
    
    public ClientThread(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        while (true) {
            try {
                BufferedReader in  = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String msg = in.readLine();

                while(msg != null) {
                    System.out.println(">" + msg);
                    msg = in.readLine();
                }
    
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }    
        }
    }
    
}
