package client;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class ShowVideo {
    Pane component;
    Media video;

    public ShowVideo(Pane component, Media video) {
        this.component = component;
        this.video = video;
    }

    public void start() {
        MediaPlayer player = new MediaPlayer(video);

        MediaView view = new MediaView(player);
        component.getChildren().clear();
        component.getChildren().add(view);
        System.out.println(player.getStatus());
        player.setOnReady(new Runnable() {

            @Override
            public void run() {
                System.out.println("Video is ready to play");
                player.play();

                player.setOnEndOfMedia(new Runnable() {

                    @Override
                    public void run() {
                        component.getChildren().remove(view);
                        Label label = new Label("Safidio izay zavatra ho henoinao na hojerenao");
                        label.setAlignment(Pos.CENTER);
                        label.setPadding(new Insets(20));
                        label.setStyle("-fx-font-size: 20px; -fx-text-fill: blueviolet;");
                        component.getChildren().add(label);
                    }

                });
            }

        });

    }
}
