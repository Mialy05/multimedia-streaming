package client;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

public class ClientThread implements Runnable {
    Socket client;

    public ClientThread(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        DataInputStream musicStream;
        AdvancedPlayer musicPlayer;
        int tranche = 100000;
        int nbr = 0;
        byte[] d = new byte[tranche];

        while (true) {
            try {
                DataInputStream in = new DataInputStream(client.getInputStream());
                byte msg = in.readByte();
                nbr = 0;

                while (in.available() != 0) {
                    if (nbr >= tranche) {
                

                        musicStream = new DataInputStream(new ByteArrayInputStream(d));
                        musicPlayer = new AdvancedPlayer(musicStream);
                        musicPlayer.play();

                        d = new byte[tranche];
                        nbr = 0;

                    }
                    if (nbr < tranche) {
                        d[nbr] = msg;
                        nbr++;
                    }

                    msg = in.readByte();
                }

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JavaLayerException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
