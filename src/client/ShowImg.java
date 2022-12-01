package client;

import java.io.ByteArrayInputStream;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ShowImg extends Application {
    StackPane root;
    byte[] data;

    public ShowImg(StackPane root, byte[] data) {
        this.root = root;
        this.data = data;
    }

    @Override
    public void start(Stage primary) throws Exception {
        ImageView view;

        Image img = new Image(new ByteArrayInputStream(data));
        view = new ImageView(img);

        root.getChildren().add(view);
    }
}
