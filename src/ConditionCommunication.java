import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionCommunication {
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
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        private boolean isSubExcute = true;

        //同步，保证循环不被打断
        //锁是放在资源上，不要放到线程上，保证调用到资源就会同步
        public void sub(int i) {
            lock.lock();
            try {
                //while比if好在可以重新校验条件，也可以防止伪唤醒
                while (!isSubExcute) {
                    try {
                        condition.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                for (int j = 1; j <= 2; j++) {
                    System.out.println("sub" + j + "-----" + i);
                }

                isSubExcute = false;
                condition.signal();
            } finally {
                lock.unlock();
            }
        }

        public void main(int i){
            lock.lock();
            try {
                while (isSubExcute) {
                    try {
                        condition.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                for (int j = 1; j <= 4; j++) {
                    System.out.println("main " + j + "-----" + i);
                }

                isSubExcute = true;
                condition.signal();
            }finally {
                lock.unlock();
            }
        }
    }


}
