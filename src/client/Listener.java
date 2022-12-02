package client;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;

import javafx.application.Application;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.stage.Stage;
import javazoom.jl.decoder.JavaLayerException;


public class Listener extends Application implements Runnable  {
    Socket sender;
    Pane component;
    String type;
    byte[] imgData;

    public Listener(Socket sender, Pane component) {
        this.sender = sender;
        this.component = component;
    }

    @Override
    public void run() {
        DataInputStream input;

        try {
            System.out.println("mihaino oh");
            input = new DataInputStream(sender.getInputStream());
            String info = input.readUTF();
            System.out.println(info);
            String[] infos = info.split(";;");

            type = infos[0];
            Thread.sleep(2000);
        
        // SONGS
            if (type.compareToIgnoreCase("song") == 0) {
                int tranche = 50000;
                int nbr = 0;
                Byte[] d = new Byte[tranche];
                Vector<Byte> dataTmp = new Vector<Byte>();
                Byte msg = input.readByte();
                nbr = 0;
                while (msg != null) {
                    if (nbr >= tranche) {
                        // System.out.println("Playing ...");

                        for (Byte b : d) {
                            if (b != null) {
                                dataTmp.add(b);
                            }
                        }
                        byte[] byteToRead = new byte[dataTmp.size()];
                        System.out.println(byteToRead.length);
                        for (int i = 0; i < dataTmp.size(); i++) {
                            byteToRead[i] = dataTmp.get(i);
                        }
                        dataTmp.removeAllElements();
                        PlayAudio playAudio = new PlayAudio(byteToRead);
                        playAudio.play();
                    // JAVAFX    
                        // component.getChildren().add(new Label("Playing " + infos[2]));

                        d = new Byte[tranche];
                        nbr = 0;
                    }
                    if (nbr < tranche) {
                        d[nbr] = msg;
                        nbr++;
                    }
                    msg = input.readByte();
                    
                }
            }
            // IMAGES
            else if (type.compareToIgnoreCase("img") == 0) {
                Vector<Byte> data = new Vector<Byte>();
                byte msg = input.readByte();
                while (input.available() != 0) {
                    data.add(msg);

                    msg = input.readByte();
                }

                System.out.println("mivoaka " + data.size());

                imgData = new byte[data.size()];
                for (int i = 0; i < data.size(); i++) {
                    imgData[i] = data.get(i);
                }

                ShowImg imgViewer = new ShowImg(component, imgData);
                imgViewer.start(null);
            }
        // VIDEO
            else if (type.compareToIgnoreCase("video") == 0) {
                File tmp = File.createTempFile("loading-video", ".mp4");
                Thread loader = new Thread(new LoadingVideo(input, tmp, sender));
                loader.start();
                Media video = new Media(tmp.toURI().toString());
                ShowVideo videoViewer = new ShowVideo(component, video);
                videoViewer.start();
            }
        } catch (IOException e) {

            e.printStackTrace();
        } catch (JavaLayerException e) {

            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public void start(Stage arg0) throws Exception {
        
    }

}
