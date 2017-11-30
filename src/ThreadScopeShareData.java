import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by sharetimes on 2017/11/21.
 * 线程范围内的共享变量
 */
public class ThreadScopeShareData {
    private static int data = 0;
    //保证线程范围内变量共享
    private static Map<Thread,Integer> map = new HashMap<Thread,Integer>();

    public static void main(String[] args) {
        for(int i = 1;i <= 2;i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    data = new Random().nextInt();
                    map.put(Thread.currentThread(),data);
                    System.out.println(Thread.currentThread().getName() + "has put data :" + data);
                    new A().get();
                    new B().get();
                }
            }).start();
        }
    }

    static class A{
        public void get(){
            System.out.println("A from " + Thread.currentThread().getName() + "get data :" + map.get(Thread.currentThread()));
        }
    }

     static class B{
        public void get(){
            System.out.println("B from " + Thread.currentThread().getName() + "get data :" +  map.get(Thread.currentThread()));
        }
    }
}
