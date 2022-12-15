package server;


import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server
 */
public class Server {

    public static void main(String[] args) {
        String listToSend = getMediaList();
        try {
            ServerSocket server = new ServerSocket(5000);
            Socket client;
            DataOutputStream out;
            ServerThread reqThread;
            Thread listener;

            while(true) {
                client = server.accept();
                System.out.println(client.getInetAddress().getHostName() + " connected");
                out = new DataOutputStream(client.getOutputStream());
                out.writeUTF(listToSend);
                
                System.out.println("lasa");
                reqThread = new ServerThread(client);
                listener = new Thread(reqThread);
                listener.start();
            } 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getMediaList() {
        String list = "";
        File assets = new File("assets");
        File[] folders = assets.listFiles();
        for (File folder : folders) {
            list += folder.getName() + ":";
            File[] files = folder.listFiles();
            for (File file : files) {
                list += file.getName() + "//";
            }
            list += ";;";
        }
        return list;
    } 
}