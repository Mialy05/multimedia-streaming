package client;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

public class Audio implements Runnable {
    byte[] data;

    public Audio(byte[] data) {
        this.data = data;
    }

    @Override
    public void run() {
        System.out.println("playing");
        DataInputStream input = new DataInputStream(new ByteArrayInputStream(data));
        try {
            AdvancedPlayer player = new AdvancedPlayer(input);
            player.play();
            
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }

        
    }

}
