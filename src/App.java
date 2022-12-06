import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 * App
 */
public class App {
    public static void main(String[] args) throws FileNotFoundException, JavaLayerException, InterruptedException, ExecutionException {
        // ExecutorService tp = Executors.newCachedThreadPool();
        // int i = 0;
        // FutureTask task = (FutureTask)tp.submit(() -> {
        //     if(i%2 == 0) {
        //         System.out.println("i est paire");
        //     }
        // }); 

        // while(i<10) {
        //     task.get();
        //     i++;
        // }

        int i=0;
        double[] a = new double[2];
        ExecutorService service = Executors.newCachedThreadPool();
        Future task = service.submit(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            a[0] = 5;
            a[1] = 10;
        });

        

        while(!task.isDone()) {
            System.out.println("Miandry kely");
        }
        System.out.println(a[0] + " " + a[1]);

    }

    public static double factorial(int i) {
        while(i<10) {
            i*=2;
        } 
        return i;
    }

}