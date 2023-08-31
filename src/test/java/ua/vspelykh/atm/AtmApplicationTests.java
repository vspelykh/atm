package ua.vspelykh.atm;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

class AtmApplicationTests {

    @Test
    void contextLoads() {

       Thread t = new Thread(){
           @Override
           public void run() {
               for (int i = 1; i < 3; i++) {
                   
               System.out.print(i + "..");
               }
           }
       };
       t.run();
    }

}
