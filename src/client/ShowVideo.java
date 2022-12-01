package client;

import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class ShowVideo {
    StackPane component;
    Media video;
    
    public ShowVideo(StackPane component, Media video) {
        this.component = component;
        this.video = video;
    }

    public void start() {
        MediaPlayer player = new MediaPlayer(video);
        
        player.setOnReady(new Runnable() {
            
            @Override
            public void run() {
                System.out.println("Video is ready to play");
                player.play();
            }
            
        });

        MediaView view = new MediaView(player);
        component.getChildren().add(view);
    }
}
