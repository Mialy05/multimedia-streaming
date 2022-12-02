package server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Sender implements Runnable {
    String req;
    Socket client;
    
    public Sender(String req, Socket client) {
        this.req = req;
        this.client = client;
    }

    @Override
    public void run() {
        String fileType = "";
        boolean error = false;
        System.out.println("Zany misy requete oh");
        System.out.println(">" + req);
        String folder = "./assets";
        File file, dir;
        int numero;
        File[] files;
        dir = new File(folder);
        if (req.startsWith("S")) {
            fileType = "SONG";
            folder += "/songs";
            dir = new File(folder);
        } else if (req.startsWith("I")) {
            folder += "/img";
            fileType = "IMG";
            dir = new File(folder);
        } else if (req.startsWith("V")) {
            folder += "/video";
            fileType = "VIDEO";
            dir = new File(folder);
        } else {
            error = true;
        }

        files = dir.listFiles();

        numero = Integer.parseInt(req.substring(1));
        if (numero < files.length) {
            file = files[numero];
        } else {
            error = true;
            file = null;
        }

        DataOutputStream out;
        try {
            out = new DataOutputStream(client.getOutputStream());
            if (!error) {
                // DataInputStream inputStream = new DataInputStream(new FileInputStream(file));
                FileInputStream inputStream = new FileInputStream(file);
                byte[] data = inputStream.readAllBytes();
                System.out.println(data.length);
                System.out.println("Sending " + file.getName() + " ... ");
                out.writeUTF(fileType + ";;" + data.length + ";;" + file.getName());
                System.out.println("Mandefa data");
                
                out.write(data);
                // out.flush();
            } else {
                out.writeUTF("Erreur fichier n'existe pas dans le repertoire");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
