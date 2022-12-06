package client;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.stage.Stage;


public class Listener extends Application implements Runnable  {
    Socket sender;
    Pane component;
    String type;
    byte[] imgData;
    boolean ready;
    PlayAudio player;

    public Listener(Socket sender, Pane component) {
        this.sender = sender;
        this.component = component;
        player = new PlayAudio(sender);
    }

    @Override
    public void run() {
        DataInputStream input;

        try {
            System.out.println("mihaino oh");
            input = new DataInputStream(sender.getInputStream());
            String info = input.readUTF();
            
            System.out.println("nivoaka avec " + info);
            String[] infos = info.split(";;");

            type = infos[0];
            // Thread.sleep(2000);
        
        // SONGS
            if (type.compareToIgnoreCase("song") == 0) {
                File background = new File("assets-client/musique.png");
                FileInputStream backgroundIn = new FileInputStream(background);
                Image img = new Image(backgroundIn);
                ImageView imgView = new ImageView(img);
                imgView.setFitHeight(500);
                imgView.setFitWidth(600);
                imgView.setPreserveRatio(true);

    
                component.getChildren().clear();
                component.getChildren().add(imgView);
                Thread audioPlayer = new Thread(player);
                audioPlayer.start();
                
            }
            // IMAGES
            else if (type.compareToIgnoreCase("img") == 0) {
                Platform.runLater(new LoadingImage(sender, Integer.parseInt(infos[1]) ,component));
            }
        // VIDEO
            else if (type.compareToIgnoreCase("video") == 0) {
                File tmp = File.createTempFile("loading-video", ".mp4");
                Thread loader = new Thread(new LoadingVideo(input, tmp, sender));
                loader.start();
                Media video = new Media(tmp.toURI().toString());
                System.out.println("Kely sisa");
                Thread.sleep(2000);
                ShowVideo videoViewer = new ShowVideo(component, video);
                videoViewer.start();
            }

        } catch (IOException e) {

            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public void start(Stage arg0) throws Exception {
        
    }

    public Socket getSender() {
        return sender;
    }

    public void setSender(Socket sender) {
        this.sender = sender;
    }

    public Pane getComponent() {
        return component;
    }

    public void setComponent(Pane component) {
        this.component = component;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getImgData() {
        return imgData;
    }

    public void setImgData(byte[] imgData) {
        this.imgData = imgData;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    

}
