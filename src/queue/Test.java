package queue;

import java.util.concurrent.Semaphore;
import java.util.concurrent.SynchronousQueue;

public class Test {
    /**
     * 生产者生产数据，消费者消费数据，请写10个线程消费产生的数据，使消费者依次消费，保证消费数据是有序的
     */
    public static void main(String[] args) {
        final Semaphore semaphore = new Semaphore(1);
        final SynchronousQueue<String> queue = new SynchronousQueue<>();
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        semaphore.acquire();
                        String input = queue.take();
                        String output = TestDo.doSome(input);
                        System.out.println(Thread.currentThread().getName() + ":" + output);
                        semaphore.release();
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        System.out.println("begin:" + (System.currentTimeMillis()/1000));
        //（这行不能动）
        for (int i = 0; i < 10; i++) {
            //（这行不能动）
            String input = i + "";
            try {
                queue.put(input);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}

//（不能改动此TestDo类）
class TestDo{
    public static String doSome(String input) {
        try{
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String output = input + ":" +(System.currentTimeMillis()/1000);
        return output;
    }
}