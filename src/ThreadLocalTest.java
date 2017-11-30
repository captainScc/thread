
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static javafx.scene.input.KeyCode.K;

/**
 * Created by sharetimes on 2017/11/21.
 * 通过ThreadLocal实现线程数据共享
 */
public class ThreadLocalTest {
    private static ThreadLocal<Integer> x = new ThreadLocal<>();
    private static ThreadLocal<MyThreadScopeData> t = new ThreadLocal<>();
    public static void main(String[] args) {
        for(int i = 1;i <= 2;i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int data = new Random().nextInt();
                    System.out.println(Thread.currentThread().getName() + "has put data :" + data);
                    x.set(data);

                    MyThreadScopeData myThreadScopeData = new MyThreadScopeData();
                    myThreadScopeData.setName("name:" + data);
                    myThreadScopeData.setAge(data);
                    t.set(myThreadScopeData);
                    new A().get();
                    new B().get();
                }
            }).start();
        }
    }

    static class A{
        public void get(){
            MyThreadScopeData myThreadScopeData = t.get();
            System.out.println("A from " + myThreadScopeData.getName());
            System.out.println("A from " + myThreadScopeData.getAge());
//            System.out.println("A from " + Thread.currentThread().getName() + "get data :" + x.get());
        }
    }

    static class B{
        public void get(){
            MyThreadScopeData myThreadScopeData = t.get();
            System.out.println("B from " + myThreadScopeData.getName());
            System.out.println("B from " + myThreadScopeData.getAge());
//            System.out.println("B from " + Thread.currentThread().getName() + "get data :" +  x.get());
        }
    }
}

class MyThreadScopeData{
//    private MyThreadScopeData(){};

    public static MyThreadScopeData getInstance(){
        return null;
    }

    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}