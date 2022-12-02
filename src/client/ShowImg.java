package client;

import java.io.ByteArrayInputStream;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ShowImg extends Application {
    Pane root;
    byte[] data;

    public ShowImg(Pane root, byte[] data) {
        this.root = root;
        this.data = data;
    }

    public void start() throws Exception {
        ImageView view;

        Image img = new Image(new ByteArrayInputStream(data));
        view = new ImageView(img);

        root.getChildren().add(view);
    }

    @Override
    public void start(Stage arg0) throws Exception {
        ImageView view;

        Image img = new Image(new ByteArrayInputStream(data));
        view = new ImageView(img);

        root.getChildren().add(view);
    }
}
