package example.day08;

import java.awt.*;

public class 작업스레드2 extends Thread {
    public void run(){
        for(int i = 1; i<=5; i++){
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            toolkit.beep();
            try {
                Thread.sleep(500);
            } catch (Exception e){}
        }
    }
}
