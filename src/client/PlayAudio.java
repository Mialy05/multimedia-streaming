package client;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

public class PlayAudio {
    byte[] data;

    public PlayAudio(byte[] data) {
        this.data = data;
    }


    public void play() {
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
