package server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
        String fileType = "";
        boolean error = false;

        while (true) {
            try {
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                msg = in.readLine();

                while (msg != null) {
                    System.out.println(">" + msg);
                    String folder = "./assets";
                    File file, dir;
                    int numero;
                    File[] files;
                    dir = new File(folder);
                    if (msg.startsWith("S")) {
                        fileType = "SONG";
                        folder += "/songs";
                        dir = new File(folder);
                    } 
                    else if (msg.startsWith("I")) {
                        folder += "/img";
                        fileType = "IMG";
                        dir = new File(folder);
                    }
                    else if (msg.startsWith("V")) {
                        folder += "/video";
                        fileType = "VIDEO";
                        dir = new File(folder);
                    }
                    else {
                        error = true;
                    }
                    
                    files = dir.listFiles();

                    numero = Integer.parseInt(msg.substring(1));
                    if (numero < files.length) {
                        file = files[numero];
                    } else {
                        error = true;
                        file = null;
                    }

                    DataOutputStream out = new DataOutputStream(client.getOutputStream());
                    if (!error) {
                        DataInputStream inputStream = new DataInputStream(new FileInputStream(file));
                        byte[] data = inputStream.readAllBytes();
                        System.out.println(data.length);
                        System.out.println("Sending " + file.getName() + " ... ");
                        out.writeUTF(fileType + ";;" + data.length + ";;" + file.getName());
                        out.write(data);
                    } else {
                        out.writeUTF("Erreur fichier n'existe pas dans le repertoire");
                    }

                    msg = in.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
