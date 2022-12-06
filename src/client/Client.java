package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JButton;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Client extends Application {
    static Socket client;
    String mediaList;

    public static void main(String[] args) {
        try {
            client = new Socket("127.0.0.1", 5000);
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
        left.setPrefSize(500, 600);
        left.setStyle("-fx-background-color: #FFFFFF;");

        // DISPLAY MEDIA
        Pane right = new Pane();
        right.setPrefSize(700, 600);
        Label label = new Label("Safidio izay zavatra ho henoinao na hojerenao");
        label.setAlignment(Pos.CENTER);
        label.setPadding(new Insets(20));
        label.setStyle("-fx-font-size: 20px; -fx-text-fill: blueviolet;");
        right.getChildren().add(label);

        // LOAD MEDIA LIST FROM SERVER
        DataInputStream input = new DataInputStream(client.getInputStream());
        String medias = input.readUTF();

        if (medias != null) {
            String[] categories = medias.split(";;");

            for (String category : categories) {
                VBox mediaList = new VBox();
                Label sectionTitre = new Label();
                sectionTitre.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: blueviolet;");
                mediaList.getChildren().add(sectionTitre);

                GridPane component = new GridPane();
                component.setHgap(30);
                component.setVgap(20);
                String type = category.split(":")[0];
                String lists = category.split(":")[1];
                String[] elements = lists.split("//");
                int i = 0;
                int numero = 0;
                for (String element : elements) {
                    Label titre = new Label(element);
                    titre.setPrefWidth(350);
                    component.add(titre, 0, i);
                    GridPane.setHalignment(titre, HPos.LEFT);
                    Button btn = new Button();
                    btn.setPrefSize(90, 25);
                    String model = "";
                    if (type.compareToIgnoreCase("img") == 0) {
                        model = "I";
                        sectionTitre.setText("SARY");
                        btn.setText("Hijery");
                    } else if (type.compareToIgnoreCase("songs") == 0) {
                        model = "S";
                        sectionTitre.setText("HIRA");
                        btn.setText("Hihaino");
                    } else if (type.compareToIgnoreCase("video") == 0) {
                        model = "V";
                        sectionTitre.setText("SARY MIHETSIKA");
                        btn.setText("Hijery");
                    }
                    BtnEvent listener = new BtnEvent(right, client, model + numero);
                    btn.setOnMouseClicked(listener);
                    GridPane.setHalignment(btn, HPos.RIGHT);
                    component.add(btn, 1, i);
                    i++;
                    numero++;
                }
                mediaList.getChildren().addAll(component);
                left.getChildren().add(mediaList);
            }

            root.getChildren().addAll(left, right);

            Scene scene = new Scene(root, 1200, 600);
            primary.setScene(scene);
            primary.show();

            primary.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent arg0) {
                    primary.close();
                    System.exit(0);
                }

            });
        } else {
            System.out.println("Miandry kely");
        }
    }
}
