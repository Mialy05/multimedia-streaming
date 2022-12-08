package client;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.plaf.DimensionUIResource;

import client.event.BtnListener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class AppClient {
    Socket client;

    public AppClient() {
        try {
            client = new Socket("127.0.0.1", 5000);
            JFrame frame = new JFrame("Multimedia Streaming");
            JPanel mainComponent = createGui(client, frame);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setBounds(200, 200, 1200, 600);
            frame.setVisible(true);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new AppClient();
    }

    public static JPanel createGui(Socket client, JFrame frame) throws IOException {
        JPanel gui = new JPanel();
        gui.setLayout(new BorderLayout(20, 10));
        frame.setContentPane(gui);

        JPanel leftSide = new JPanel();
        gui.add(leftSide, BorderLayout.WEST);
        leftSide.setPreferredSize(new DimensionUIResource(500, 600));
        leftSide.setBackground(Color.CYAN);
        BoxLayout leftLayout = new BoxLayout(leftSide, BoxLayout.PAGE_AXIS);
        
        leftSide.setLayout(leftLayout);

    // LOAD MEDIA LIST FROM SERVER
        DataInputStream input = new DataInputStream(client.getInputStream());
        String medias = input.readUTF();

        if (medias != null) {
            String[] categories = medias.split(";;");

            for (String category : categories) {
                JPanel categoryBox = new JPanel();
                leftSide.add(categoryBox);
                categoryBox.setLayout(new BoxLayout(categoryBox, BoxLayout.PAGE_AXIS));
                
                String type = category.split(":")[0];
                String lists = category.split(":")[1];
                String[] elements = lists.split("//");
                
                JLabel categoryTitle = new JLabel(type);
                categoryBox.add(categoryTitle);
                
                int numero = 0;
                for (String e : elements) {
                    JPanel element = new JPanel();
                    categoryBox.add(element);
                    leftSide.add(element);    
                    JLabel elementTitle = new JLabel(e);
                    JButton btn = new JButton();
                    element.add(elementTitle);
                    element.add(btn);
                    element.setPreferredSize(new DimensionUIResource(500, 20));

                    String model = "";
                    if (type.compareToIgnoreCase("img") == 0) {
                        model = "I";
                        btn.setText("Hijery");
                    } else if (type.compareToIgnoreCase("songs") == 0) {
                        model = "S";
                        btn.setText("Hihaino");
                    } else if (type.compareToIgnoreCase("video") == 0) {
                        model = "V";
                        btn.setText("Hijery");
                    }

                    BtnListener listener = new BtnListener(frame, client, model + numero);
                    btn.addMouseListener(listener);

                    numero++;
                }
            }
        }
        return gui;
    }
}
