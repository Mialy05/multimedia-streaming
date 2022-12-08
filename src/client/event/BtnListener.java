package client.event;

import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;

import client.loadingMedia.ResponseListener;

import java.awt.event.*;
import java.io.IOException;
import java.io.PrintWriter;

public class BtnListener implements MouseListener {
    JFrame frame;
    Socket client;
    String req;

    public BtnListener(JFrame frame, Socket client, String req) {
        this.frame = frame;
        this.client = client;
        this.req = req;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        try {
            PrintWriter out = new PrintWriter(client.getOutputStream());
            out.println(req);
            out.flush();
            Thread listener = new Thread(new ResponseListener(client, frame, req));
            listener.start();
        } catch (IOException e1) {

            e1.printStackTrace();
        }
        
        System.out.println(req);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    
}
