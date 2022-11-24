import java.util.Vector;

/**
 * App
 */
public class App {
    public static void main(String[] args) {
        Vector<Integer> nombres = new Vector<Integer>();
        Vector<Integer> nombres2 = new Vector<Integer>();

        for (int i = 10; i < 25; i++) {
            nombres2.add(i);
        }

        for (int i = 0; i < 10; i++) {
            nombres.add(i);
        }

        // for(Integer integer : nombres) {
        //     // System.out.println(integer);
        //     nombres = nombres2;
        // }

        int n = 0;
        // System.out.println(nombres.get(n));
    
        while(n<nombres.size())  {
            System.out.println(nombres.get(n));
            nombres = nombres2;
            n++;
        }
    }
    
}