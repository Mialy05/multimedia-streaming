package client;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

public class ClientListener implements Runnable {
    Socket client;

    public ClientListener(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        DataInputStream musicStream;
        AdvancedPlayer musicPlayer;
        int tranche = 100000;
        DataInputStream input;

        try {
            input = new DataInputStream(client.getInputStream());
            String header = input.readUTF();
            String[] infos = header.split(";;");
            tranche = Integer.parseInt(infos[1])/5;

            System.out.println(tranche);
            byte[] dataToRead = new byte[tranche];

            while(input.read() != -1) {
                input.read(dataToRead);
                musicStream = new DataInputStream(new ByteArrayInputStream(dataToRead));
                musicPlayer = new AdvancedPlayer(musicStream);
                musicPlayer.play();
            }
        
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
    }
    

}
