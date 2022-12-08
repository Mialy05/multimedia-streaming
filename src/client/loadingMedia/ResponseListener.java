package client.loadingMedia;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Component;
import java.awt.Image;
import java.awt.Canvas;

import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

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
            String info = "";
            while(info.equals("")) {
                try {
                    System.out.println("Waiting ...");
                    info = input.readUTF();
                    break;
                } catch (Exception e) {
                    input.readByte();
                }
            }
            // info = input.readUTF();

            System.out.println("header " + info);
            String[] infos = info.split(";;");

            type = infos[0];
            Component[] components = frame.getContentPane().getComponents();
            if(components.length > 1) {
                frame.getContentPane().remove(components[1]);
            }
                        
            frame.repaint();

            // SONGS
            if (type.compareToIgnoreCase("song") == 0) {
                File background = new File("assets-client/musique.png");
                // FileInputStream backgroundIn = new FileInputStream(background);

                PlayAudio player = new PlayAudio(sender, frame);
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
                int imgSize = Integer.parseInt(infos[1]);
                System.out.println(imgSize);
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
                        Component[] components = frame.getContentPane().getComponents();
                        frame.getContentPane().remove(components[1]);
                        
                        frame.repaint();
                        component.release();
                        while (tmp.exists()) {
                            tmp.delete();
                        }
                    }
                });

                Thread loader = new Thread(new LoadingVideo(input, tmp, sender));
                loader.start();
                
                Canvas canva = new Canvas();
                canva.setVisible(true);
                

                frame.add(component);
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
