package client;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import javazoom.jl.player.advanced.AdvancedPlayer;

public class ClientThread implements Runnable {
    Socket client;

    public ClientThread(Socket client) {
        this.client = client; 
    }

    @Override
    public void run() {
        DataInputStream musicStream;
        AdvancedPlayer musicPlayer;
        int tranche = 50000;
        int nbr = 0;
        Byte[] d = new Byte[tranche];
        Vector<Byte> dataTmp = new Vector<Byte>();

        DataInputStream input;
        try {
            input = new DataInputStream(client.getInputStream());
            String info = input.readUTF();
            String[] infos = info.split(";;");

            String type = infos[0];
            JFrame frame = new JFrame();
            JLabel label = new JLabel();
            frame.add(label);
            frame.setSize(500, 500);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            // while (true) {
            try {
                Byte msg = input.readByte();
                if (type.compareToIgnoreCase("song") == 0) {
                    nbr = 0;
                    while (msg != null) {
                        // input.readFully(d, 0, tranche);
                        // play(d);
                        // musicStream = new DataInputStream(new ByteArrayInputStream(d));
                        // musicPlayer = new AdvancedPlayer(musicStream);
                        // musicPlayer.play();
                        if (nbr >= tranche) {
                            label.setText("Playing " + infos[1]);
                            System.out.println("Playing " + infos[1]);

                            for (Byte b : d) {
                                if(b != null) {
                                    dataTmp.add(b);
                                }
                            }

                            byte[] byteToRead = new byte[dataTmp.size()];
                            for (int i=0; i<dataTmp.size(); i++) {
                                byteToRead[i] = dataTmp.get(i);
                            }
                            dataTmp.removeAllElements();
                            System.out.println("mamaky " + byteToRead.length);
                            musicStream = new DataInputStream(new ByteArrayInputStream(byteToRead));
                            musicPlayer = new AdvancedPlayer(musicStream);
                            musicPlayer.play();

                            d = new Byte[tranche];
                            nbr = 0;
                        }
                        if (nbr < tranche) {
                            d[nbr] = msg;
                            nbr++;
                        }

                        msg = input.readByte();
                    }
                    // System.out.println("tonga daholo ny avy amin'ny serveur ");
                } else {
                    Vector<Byte> data = new Vector<Byte>();

                    while (input.available() != 0) {
                        data.add(msg);
                        label.setText("Loading ...");

                        msg = input.readByte();
                    }

                    System.out.println("mivoaka " + data.size());

                    byte[] imgData = new byte[data.size()];
                    for (int i = 0; i < data.size(); i++) {
                        imgData[i] = data.get(i);
                    }

                    ImageIcon img = new ImageIcon(imgData);
                    label.setIcon(img);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JavaLayerException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            // }
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }

    public static void play(byte[] b) throws JavaLayerException {
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(b));
        Player p = new Player(in);
        p.play();
    }

}
