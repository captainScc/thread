import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ThreadCacheTest {
    /**
     * 通过读写锁写一个缓存系统
     */
    //缓存仓库
    private Map<String, Object> map = new HashMap<>();
    //定义读写锁

    private ReadWriteLock rwl = new ReentrantReadWriteLock();

    public static void main(String[] args) {
        int b = 1;
    }
    public Object get(String key){
        rwl.readLock().lock();
        Object value = null;
        try{
            value = map.get(key);
            if(value == null) {
                rwl.readLock().unlock();
                rwl.writeLock().lock();
                try{
                    //可能几个线程同时写
                    if(value == null) {
                        //查询数据库
                        value = "aaaa";
                    }
                } finally {
                    rwl.writeLock().unlock();
                }
                rwl.readLock().lock();
            }
        } finally {
            rwl.readLock().unlock();
        }
        return value;
    }
}
