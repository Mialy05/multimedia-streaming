package server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread implements Runnable {
    Socket client;

    public ServerThread(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        BufferedReader in;
        String msg;

        

        // while (true) {
            try {
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                // msg = in.readLine();

                // while (msg != null) {
                //     System.out.println(">" + msg);
                    // PrintWriter out;
                    // try {
                    //     out = new PrintWriter(client.getOutputStream());
                    //     File file = new File("./assets/songs/essai.html");
                    //     FileInputStream inputStream = new FileInputStream(file);
                    //     byte[] data = inputStream.readAllBytes();
                        
                    //     out.println(new String(new String(data)));
                    //     out.flush();
                    // } catch (IOException e1) {
                    //     e1.printStackTrace();
                    // }
                    File file = new File("./assets/songs/AURORA - Runaway.mp3");
                    DataInputStream inputStream = new DataInputStream(new FileInputStream(file));
                    byte[] data = inputStream.readAllBytes();
                    System.out.println(data.length);
                    DataOutputStream out = new DataOutputStream(client.getOutputStream());
                    System.out.println("Sending " + file.getName() + " ... ");
                    out.write(data);
                    // msg = in.readLine();
                // }
            } catch (IOException e) {
                e.printStackTrace();
            }
        // }

    }

}
