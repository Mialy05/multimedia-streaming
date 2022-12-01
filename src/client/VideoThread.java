package client;

import java.io.File;

import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class VideoThread implements Runnable {
    File file;
    StackPane root;
    
    public VideoThread(File file, StackPane root) {
        this.file = file;
        this.root = root;
    }

    @Override
    public void run() {
        System.out.println(">>" + file.length());
        Media media = new Media(file.toURI().toString());
        MediaPlayer player = new MediaPlayer(media);
        player.setAutoPlay(true);
        MediaView view = new MediaView(player);
        root.getChildren().add(view);
    }
    
}
