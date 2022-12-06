package client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javafx.application.Application;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoadingImage extends Application implements Runnable {
    Socket server;
    int imgSize;
    Pane component;

    public LoadingImage(Socket server, int imgSize, Pane component) {
        this.server = server;
        this.imgSize = imgSize;
        this.component = component;
    }

    @Override
    public void run() {
        Vector<Byte> data = new Vector<Byte>();
        DataInputStream input;
        int tranche = 100000;
        System.out.println(imgSize);
        Vector<byte[]> dataRead = new Vector<byte[]>();
        try {
            input = new DataInputStream(server.getInputStream());
    
            byte[] imgData = new byte[this.imgSize];
            input.readFully(imgData);
  
            System.out.println("Loading finished " + imgData.length);

            component.getChildren().clear();
            ShowImg imgViewer = new ShowImg(component, imgData);
            imgViewer.start(null);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void start(Stage arg0) throws Exception {
        // TODO Auto-generated method stub

    }

}
