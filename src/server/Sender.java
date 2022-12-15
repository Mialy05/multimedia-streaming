package server;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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

        try {
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            if (!error) { 
                FileInputStream inputStream = new FileInputStream(file);
                byte[] data = inputStream.readAllBytes();
              
                System.out.println("Sending " + file.getName() + " " +data.length+ "  ... ");
                out.writeUTF(fileType + ";;" + data.length + ";;" + file.getName());
                out.write(data);
                out.flush();

                inputStream.close();
            } else {
                out.writeUTF("Erreur fichier n'existe pas dans le repertoire");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
