package client.loadingMedia;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

public class PlayAudio implements Runnable {
    byte[] data;
    Socket server;
    boolean play;

    public PlayAudio() {
    }

    public PlayAudio(Socket server) {
        this.server = server;
        this.play = true;
    }

    @Override
    public void run() {
        int tranche = 50000;

        DataInputStream input;
        try {
            input = new DataInputStream(server.getInputStream());
            byte[] data = new byte[tranche];
            int read = input.read(data);

            AdvancedPlayer player;

            while (read == tranche) {
                try {
                    player = new AdvancedPlayer(new ByteArrayInputStream(data));
                    System.out.println("Playing ...");
                    player.play();
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }

                read = input.read(data);
            }
            if (read != -1) {
                System.out.println("farany");
                byte[] lastdata = new byte[read];
                for (int i = 0; i < read; i++) {
                    lastdata[i] = data[i];
                }
                player = new AdvancedPlayer(new ByteArrayInputStream(lastdata));
                player.play();
            }

        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (JavaLayerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void play() throws IOException, JavaLayerException {

    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public void setPlay(boolean play) {
        this.play = play;
    }

}
