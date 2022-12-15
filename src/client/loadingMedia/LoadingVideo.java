package client.loadingMedia;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

public class LoadingVideo implements Runnable {
    Socket sender;
    DataInputStream source;
    File destination;

    public LoadingVideo(DataInputStream source, File destination, Socket sender) throws IOException {
        this.destination = destination;
        this.sender = sender;
        this.source = new DataInputStream(sender.getInputStream());
    }

    @Override
    public void run() {
        try {
            DataOutputStream writer = new DataOutputStream(new FileOutputStream(destination));

            int tranche = 130000;

            byte[] dataSource = new byte[tranche];
            int read = source.read(dataSource);
            System.out.println("vovaky " + read);
            while (read == tranche) {
                writer.write(dataSource);
                read = source.read(dataSource);
                System.out.println("content " + destination.length());

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("farany");
            byte[] lastdata = new byte[read];
            for (int i = 0; i < read; i++) {
                lastdata[i] = dataSource[i];
            }
            writer.write(lastdata);

            System.out.println("loading finished " + destination.length());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
