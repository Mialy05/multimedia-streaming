package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

public class ImgThread implements Runnable {
    Socket client;

    public ImgThread(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        File file = new File("./assets/songs/img1.png");
        byte[] data;
        try {
            DataInputStream inputStream = new DataInputStream(new FileInputStream(file));
            data = inputStream.readAllBytes();
            System.out.println(data.length);
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            System.out.println("Sending " + file.getName() + " ... ");
            out.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
