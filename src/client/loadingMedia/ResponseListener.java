package client.loadingMedia;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.plaf.DimensionUIResource;

import java.awt.Image;

import javazoom.jl.player.advanced.AdvancedPlayer;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.media.MediaEventListener;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

public class ResponseListener implements Runnable {
    Socket sender;
    JFrame frame;
    String type;

    public ResponseListener(Socket sender, JFrame frame, String type) {
        this.sender = sender;
        this.frame = frame;
        this.type = type;
    }

    @Override
    public void run() {
        DataInputStream input;

        try {
            System.out.println("mihaino oh");
            input = new DataInputStream(sender.getInputStream());
            String info = input.readUTF();

            System.out.println("header " + info);
            String[] infos = info.split(";;");

            type = infos[0];
            // Thread.sleep(2000);

            // SONGS
            if (type.compareToIgnoreCase("song") == 0) {
                File background = new File("assets-client/musique.png");
                // FileInputStream backgroundIn = new FileInputStream(background);

                PlayAudio player = new PlayAudio(sender);
                Thread audioPlayer = new Thread(player);
                audioPlayer.start();

                Image img = ImageIO.read(background);
                img = img.getScaledInstance(600, 500, Image.SCALE_SMOOTH);
                ImageIcon displayImg = new ImageIcon(img);
                JLabel label = new JLabel();
                label.setIcon(displayImg);

                frame.add(label);
                frame.setVisible(true);

            }
            // IMAGES
            else if (type.compareToIgnoreCase("img") == 0) {
                // JFrame showFrame = new JFrame(infos[2]);
                // showFrame.setBounds(500, 200, 700, 600);

                int imgSize = Integer.parseInt(infos[1]);
                byte[] imgData = new byte[imgSize];
                input.readFully(imgData);
                Image img = ImageIO.read(new ByteArrayInputStream(imgData));
                img = img.getScaledInstance(600, 500, Image.SCALE_SMOOTH);

                ImageIcon displayImg = new ImageIcon(img);

                JLabel label = new JLabel();
                label.setIcon(displayImg);

                frame.add(label);
                System.out.println("Loading finished " + imgData.length);
            }
            // VIDEO
            else if (type.compareToIgnoreCase("video") == 0) {
                File tmp = File.createTempFile("loading-video", ".mp4");

                EmbeddedMediaPlayerComponent component = new EmbeddedMediaPlayerComponent();
                component.mediaPlayer().events().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
                    @Override
                    public void finished(MediaPlayer mediaPlayer) {
                        System.out.println("VITAAAAAAAAAAAAAAA");
                        component.release();
                        while(tmp.exists()) {
                            tmp.delete();
                        }
                    }
                });

                Thread loader = new Thread(new LoadingVideo(input, tmp, sender));
                loader.start();
                frame.add(component);
                // System.out.println("Kely sisa");
                // Thread.sleep(1000);
                // ShowVideo videoViewer = new ShowVideo(component, tmp);
                // videoViewer.start();
                component.mediaPlayer().media().play(tmp.toPath().toString());

            }

        } catch (IOException e) {

            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
