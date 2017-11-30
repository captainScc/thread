/**
 * Created by sharetimes on 2017/11/21.
 */
public class TraditionalThreadCommunication {

    public static void main(String[] args) {

        final  Business business = new Business();

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
        private boolean isSubExcute = true;

        //同步，保证循环不被打断
        //锁是放在资源上，不要放到线程上，保证调用到资源就会同步
        public synchronized void sub(int i) {
            //while比if好在可以重新校验条件，也可以防止伪唤醒
            while(!isSubExcute) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int j = 1; j <= 2; j++) {
                System.out.println("sub" + j + "-----"+i);
            }

            isSubExcute = false;
            this.notify();
        }

        public synchronized void main(int i){
            while(isSubExcute) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for(int j =1;j<=4;j++){
                System.out.println("main " + j + "-----"+i);
            }

            isSubExcute = true;
            this.notify();
        }
    }

}

