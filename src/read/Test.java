package read;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Test {
    /**
     * 增加四个线程，将16秒打完的日志只需要四秒打完
     * @param args
     */

    public static void main(String[] args) {
        //阻塞队列
        final BlockingQueue<String> queue = new ArrayBlockingQueue<String>(1);
        for (int i = 0; i < 4; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true) {
                        try {
                            String log = queue.take();
                            parseLog(log);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }

        System.out.println( "begin:" + (System.currentTimeMillis()/1000));

        //（这行不可改动）
        for (int i = 0; i < 16; i++) {
            //（这行不可改动）
            final String log = "" + (i+1);
            {
                try {
                    queue.put(log);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                Test.parseLog(log);
            }
        }
    }

    //（方法内部不可改动）
    public static void parseLog(String log){
        System.out.println(log + ":" +(System.currentTimeMillis()/1000));
        try{
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
