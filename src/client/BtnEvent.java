package client;

import java.net.Socket;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.stage.Stage;

public class BtnEvent extends Application implements EventHandler<MouseEvent> {
    Pane component;
    Media video;
    Socket client;
    String req;
    
    public BtnEvent(Pane component, Media video, Socket client, String req) {
        this.component = component;
        this.video = video;
        this.client = client;
        this.req = req;
    }

    @Override
    public void handle(MouseEvent e) {
        Button btn = (Button) e.getSource();
        if(btn.getText().compareToIgnoreCase("hihaino") == 0) {
            btn.setText("Aoka izay");
        }
        else {
            btn.setText("hihaino");
        }
        // ShowVideo shower = new ShowVideo(component, video);
        // shower.start();
        Platform.runLater(new ClientThread(client, req, component));
        // Thread clienThread = new Thread(new ClientThread(client, req, component));
        // clienThread.start();
    }

    @Override
    public void start(Stage arg0) throws Exception {
        // TODO Auto-generated method stub
        
    }
    
}
