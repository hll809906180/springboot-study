package cn.leadeon.sprmail.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description: 基本定时任务
 *  使用Scheduled Task的弊端就是不适用于分布式集群的操作，Scheduled Task是一种轻量级的任务定时调度器，相比于Quartz,
 *  减少了很多的配置信息，但是Scheduled Task不适用于服务器集群，引文在服务器集群下会出现任务被多次调度执行的情况，因为
 *  集群的节点之间是不会共享任务信息的，每个节点的定时任务都会定时执行
 * @author: he.l
 * @create: 2019-04-19 16:37
 **/
@Component
public class BaseScheduleTask {

    private int count=0;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(cron="*/6 * * * * ?")
    private void process(){
        System.out.println("this is scheduler task runing  "+(count++));
    }

    @Scheduled(fixedRate = 6000)
    public void reportCurrentTime() {
        System.out.println("现在时间：" + dateFormat.format(new Date()));
    }

    /**
     * 基于注解@Scheduled默认为单线程，开启多个任务时，任务的执行时机会受上一个任务执行时间的影响。
     * Cron表达式参数分别表示：
     * 秒（0~59） 例如0/5表示每5秒
     * 分（0~59）
     * 时（0~23）
     * 日（0~31）的某天，需计算
     * 月（0~11）
     * 周几（ 可填1-7 或 SUN/MON/TUE/WED/THU/FRI/SAT）
     * @Scheduled：除了支持灵活的参数表达式cron之外，还支持简单的延时操作，例如 fixedDelay ，fixedRate 填写相应的毫秒数即可。
     * */
}
