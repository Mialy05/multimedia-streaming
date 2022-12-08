package client.loadingMedia;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.plaf.DimensionUIResource;

import client.event.StopVideo;
import client.event.VideoEvent;

import java.awt.Component;
import java.awt.Container;
import java.awt.Image;
import java.awt.BorderLayout;

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
        JLabel title = new JLabel();
        Container mainComponent = frame.getContentPane();

        try {
            System.out.println("mihaino oh");
            input = new DataInputStream(sender.getInputStream());
            String info = "";
            while (info.equals("")) {
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
            title.setText(infos[2].toUpperCase());
            title.setHorizontalAlignment(SwingConstants.CENTER);
            title.setBorder(BorderFactory.createTitledBorder("Mandeha izao"));

            resetFrame(frame);

            // SONGS
            if (type.compareToIgnoreCase("song") == 0) {

                File background = new File("assets-client/music.jpg");

                PlayAudio player = new PlayAudio(sender, frame);
                Thread audioPlayer = new Thread(player);
                audioPlayer.start();



                Image img = ImageIO.read(background);
                img = img.getScaledInstance(700, 500, Image.SCALE_SMOOTH);
                ImageIcon displayImg = new ImageIcon(img);
                JLabel label = new JLabel();
                label.setIcon(displayImg);

                mainComponent.add(label);
                mainComponent.add(title, BorderLayout.NORTH);

                frame.repaint();
            }
            // IMAGES
            else if (type.compareToIgnoreCase("img") == 0) {
                int imgSize = Integer.parseInt(infos[1]);
                System.out.println(imgSize);
                byte[] imgData = new byte[imgSize];
                input.readFully(imgData);
                Image img = ImageIO.read(new ByteArrayInputStream(imgData));
                img = img.getScaledInstance(700, 500, Image.SCALE_SMOOTH);

                ImageIcon displayImg = new ImageIcon(img);

                JLabel label = new JLabel();
                label.setIcon(displayImg);

                mainComponent.add(label);
                mainComponent.add(title, BorderLayout.NORTH);

                frame.repaint();

                System.out.println("Loading finished " + imgData.length);
            }
            // VIDEO
            else if (type.compareToIgnoreCase("video") == 0) {
                File tmp = File.createTempFile("loading-video", ".mkv");

                EmbeddedMediaPlayerComponent component = new EmbeddedMediaPlayerComponent();
                component.mediaPlayer().events().addMediaPlayerEventListener(new VideoEvent(frame, tmp));

                Thread loader = new Thread(new LoadingVideo(input, tmp, sender));
                loader.start();

                
                mainComponent.add(component);
                JPanel controlers = new JPanel();
                controlers.setName("Options");
                JButton btn = new JButton("Hajanona");
                System.out.println(tmp.length());
                JLabel message = new JLabel("");
                btn.addMouseListener(new StopVideo(Integer.parseInt(infos[1]), tmp, component, frame, message));
                btn.setPreferredSize(new DimensionUIResource(100, 50));
                controlers.add(btn);
                
                controlers.add(message);
                mainComponent.add(title, BorderLayout.NORTH);
                mainComponent.add(controlers, BorderLayout.SOUTH);

                frame.repaint();
                component.mediaPlayer().media().play(tmp.toPath().toString());
            }

        } catch (IOException e) {

            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void resetFrame(JFrame frame) {
    // Par défaut, Il n'y a qu'un élément dans le conteneur principal 
        Component[] components = frame.getContentPane().getComponents();
        for (int i = components.length-1; i >= 1; i--) {
            frame.getContentPane().remove(components[i]);
        }
        frame.repaint();
    }

}
