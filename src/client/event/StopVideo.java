package client.event;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.*;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import client.loadingMedia.ResponseListener;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

/*
 * Manajanona ilay video rehefa vo chargé hatram farany izy
 */
public class StopVideo implements MouseListener{
    int totalSize;
    File tmp;
    EmbeddedMediaPlayerComponent player;
    JFrame frame;
    JLabel message;
    
    public StopVideo(int totalSize, File tmp, EmbeddedMediaPlayerComponent player, JFrame frame, JLabel message) {
        this.totalSize = totalSize;
        this.tmp = tmp;
        this.player = player;
        this.frame = frame;
        this.message = message;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int currentSize = (int)tmp.length();
        if(currentSize == totalSize) {
            this.player.release();
            ResponseListener.resetFrame(frame);
        }
        else {
            double pourcentage = (currentSize*100)/totalSize;

            // message.add(new JLabel("Mbola tsy afaka tapahana raha tsy feno 100% (" + pourcentage + ")"));
            message.setText("Mbola tsy afaka tapahana raha tsy feno 100% (" + pourcentage + ")");
            message.repaint();
            // this.frame.getContentPane().add(message,BorderLayout.LINE_END);
            System.out.println(pourcentage);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        
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
