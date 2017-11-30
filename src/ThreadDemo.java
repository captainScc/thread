/**
 * Created by sharetimes on 2017/11/21.
 */
public class ThreadDemo {
    public static void main(String[] args) {
        Thread thread = new Thread(){
            @Override
            public void run() {
                while(true) {
                    try{
                        Thread.sleep(500);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName());
                }
            }
        };
        thread.start();

        //更具面向对象的思想，避免单继承的局限性，可以设置共享的资源
        //start()方法先找Thread子类的run（），找不到就执行父类Runnable的run（）
        //多线程的应用场景：文件下载
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try{
                        Thread.sleep(500);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName());
                }
            }
        });
        thread2.start();



    }
}
