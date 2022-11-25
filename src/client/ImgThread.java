package client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ImgThread implements Runnable {
    Socket client;

    public ImgThread(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        JFrame frame = new JFrame();
        JLabel label = new JLabel();
        frame.add(label);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        while (true) {

            DataInputStream in;
            try {
                in = new DataInputStream(client.getInputStream());
                byte msg = in.readByte();
                Vector<Byte> data = new Vector<Byte>();

                while (in.available() != 0) {
                    data.add(msg);
                    label.setText("Loading ...");
                    
                    msg = in.readByte();
                }

                byte[] imgData = new byte[data.size()];
                for (int i=0; i<data.size(); i++) {
                    imgData[i] = data.get(i);
                }

                ImageIcon img = new ImageIcon(imgData);
                label.setIcon(img);
                // frame.add(label);
                

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }

}
