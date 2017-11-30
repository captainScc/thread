import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueCommunication {
    /**
     * 用两个只有一个空间的阻塞队列来实现同步通知的功能
     * @param args
     */
    public static void main(String[] args) {

        final TraditionalThreadCommunication.Business business = new TraditionalThreadCommunication.Business();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1;i<=10;i++){
                    business.sub(i);
                }
            }
        }).start();

        for (int i = 1;i<=10;i++){
            business.main(i);
        }
    }

    //用到共同数据或共同算法的若干方法，应该归在同一类上
    static class Business{
        BlockingQueue<Integer> queue1 = new ArrayBlockingQueue<Integer>(1);
        BlockingQueue<Integer> queue2 = new ArrayBlockingQueue<Integer>(1);
        //不能使用static给queue2赋值，内部类初始化时才给成员初始化
        {
            try {
                queue2.put(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //同步，保证循环不被打断
        //锁是放在资源上，不要放到线程上，保证调用到资源就会同步
        public  void sub(int i) {
            try {
                queue1.put(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int j = 1; j <= 2; j++) {
                System.out.println("sub" + j + "-----"+i);
            }
            try {
                queue2.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        public void main(int i){
            try {
                queue2.put(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for(int j =1;j<=4;j++){
                System.out.println("main " + j + "-----"+i);
            }
            try {
                queue1.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}