package client;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JButton;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.stage.Stage;

public class Client extends Application {
    static Socket client;
    public static void main(String[] args) {
        try {
            client = new Socket("127.0.0.1", 5000);
            
            // Thread clienThread = new Thread(new ClientThread(client));
            // clienThread.start();
            launch(args);
           
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    @Override
    public void start(Stage primary) throws Exception {
        primary.setTitle("Multimedia Streaming");
        // GridPane root = new GridPane();
        HBox root = new HBox();
        root.setPadding(new Insets(10));
        VBox left = new VBox();
        left.setPrefSize(400, 600);
        left.setStyle("-fx-background-color: #FFFFFF;");
        
        Label leftLabel = new Label("LISTE MEDIA");
        left.getChildren().add(leftLabel);
        
        VBox mediaList = new VBox();
        Label sectionTitre = new Label("HIRA");
        sectionTitre.setStyle("-fx-color: #008000;");
        mediaList.getChildren().add(new Label("HIRA"));
        
        GridPane component = new GridPane();
        component.setHgap(30);
        component.setVgap(20);

        // DISPLAY MEDIA    
        Pane right = new Pane();
        right.setPrefSize(800, 600);
        // Label rightLabel = new Label("right SIDE");
        // right.getChildren().add(rightLabel);
        File film = new File("assets/video/PASSION - Miraculous Wu.mp4");
        Media video = new Media(film.toURI().toString());
        // MediaPlayer player = new MediaPlayer(video);
        // player.setAutoPlay(true);
        // MediaView view = new MediaView(player);
        // right.getChildren().add(view);
        

        for(int i=0; i<5; i++) {
            Label titre = new Label("hira.mp3");
            component.add(titre, 0, i);
            GridPane.setHalignment(titre, HPos.RIGHT);
            Button btn = new Button("Hihaino");
            BtnEvent listener = new BtnEvent(right, video, client, "V3");
            btn.setOnMouseClicked(listener);
            GridPane.setHalignment(btn, HPos.LEFT);
            component.add(btn, 1, i);
        }
        
        Button btn1 = new Button("Hira");
        component.add(btn1, 0, 5);
        btn1.setOnMouseClicked(new BtnEvent(right, video, client, "S1"));
        
        Button btn2 = new Button("Sary");
        component.add(btn2, 0, 6);
        btn2.setOnMouseClicked(new BtnEvent(right, video, client, "I1"));
        // component.getChildren().addAll(titre, btn);
        
        mediaList.getChildren().addAll(component);
        left.getChildren().add(mediaList);

    

        root.getChildren().addAll(left, right);
        Scene scene = new Scene(root, 1200, 600);
        primary.setScene(scene);
        primary.show();
        // primary.setOnCloseRequest(new CloseWindow(client));
    }
}
