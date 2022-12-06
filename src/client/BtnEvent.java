package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class BtnEvent extends Application implements EventHandler<MouseEvent> {
    Pane component;
    Socket client;
    String req;

    public BtnEvent(Pane component, Socket client, String req) {
        this.component = component;
        this.client = client;
        this.req = req;
    }

    @Override
    public void handle(MouseEvent e) {
        Button btn = (Button) e.getSource();
        if (btn.getText().compareToIgnoreCase("hihaino") == 0 || btn.getText().compareToIgnoreCase("hijery") == 0) {
            btn.setText("Aoka izay");

        } else {
            if (req.startsWith("S")) {
                btn.setText("Hihaino");
            } else {
                btn.setText("Hijery");
            }
        }
        // component.getChildren().remove(0);
        Node node = component.getChildren().get(0);
        if(node.getClass().equals(Label.class)) {
            System.out.println("label");
            Label label = (Label)node;
            label.setText("Ho avy ... ");
        }
        else {
            System.out.println("mamafa");
            component.getChildren().remove(node);
        }
        try {
            PrintWriter out = new PrintWriter(client.getOutputStream());
            out.println(req);
            out.flush();
            Platform.runLater(new Listener(client, component));
        } catch (IOException e1) {

            e1.printStackTrace();
        }
    }

    @Override
    public void start(Stage arg0) throws Exception {
        // TODO Auto-generated method stub

    }

}
