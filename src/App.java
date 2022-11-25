import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 * App
 */
public class App {
    public static void main(String[] args) throws FileNotFoundException, JavaLayerException {
        FileInputStream in = new FileInputStream(new File("assets/songs/Astrid S - Trust Issues (Lyrics)(MP3_160K).mp3"));
        Player player = new Player(in);
        player.play();
    }
    
}