import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by sharetimes on 2017/11/21.
 */
public class TraditionalTimerTest {
    //jdk5前定时器
    public static void main(String[] args) {

        //内部类
        class MyTimerTask extends TimerTask{
            @Override
            public void run() {
                System.out.println("bombing!");
                new Timer().schedule(new MyTimerTask(), 2000);
            }
        }

        new Timer().schedule(new MyTimerTask(),2000);

        while(true) {
            System.out.println(LocalTime.now().getSecond()%2+1);
            try{
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}