package cn.leadeon.sprjsp.comm;

import java.util.List;
import java.util.concurrent.*;

/**
 * @description: 导出工具类
 * @author: he.l
 * @create: 2019-07-23 17:17
 **/
public class ExportUtil {

    public static void main(String[] args) {
        System.out.println(Runtime.getRuntime().availableProcessors());
    }

    // cpu 核数
    private static final int cpu = Runtime.getRuntime().availableProcessors();
    // 一个文件存放数据量
    private static final int rows = 500000;
    /**
     *  根据导出数据量计算 线程池大小
     * @param total
     * @return
     */
    public static Integer getPoolSize(int total){
        Integer poolSize =  total%rows==0?total/rows:total/rows+1;
        if(poolSize>cpu*2){
            poolSize = cpu*2;
        }
        return poolSize;
    }

    public static  ExecutorService fixedThreadExport(int total,List<Thread> list){
        //创建固定大小的线程池
        ExecutorService pool = Executors.newFixedThreadPool(cpu);
        for(int i=0;i<list.size();i++){
            pool.execute(list.get(i));
        }
        return pool;
    }

    public static void threadPool(int total,List<Thread> list){
        //创建等待队列
        BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<Runnable>(20);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                cpu,
                getPoolSize(total),
                2,
                TimeUnit.MILLISECONDS,
                blockingQueue);
    }
}
