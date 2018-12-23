package com.ssh.Task;

import com.ssh.constants.MyConstants;
import com.ssh.util.GlobelData;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

public class MonitorThread implements Runnable{
    private ExecutorService executorService;//线程池

    public MonitorThread(ExecutorService executorService){
        this.executorService=executorService;
    }
    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("线程池当前共有"+GlobelData.realThreadCount+"个线程----------------------");
            System.out.println("线程池上次共有"+GlobelData.lastThreadCount+"个线程----------------------");
            if(GlobelData.realThreadCount==GlobelData.lastThreadCount){
                GlobelData.compareTimes++;
                if(GlobelData.compareTimes==5){    //线程池当前线程数和上次获取的线程数相等时，多比较几次，减少偶然情况。
                    executorService.shutdown();      //此时所有任务都已加入到线程池，调用shutdown()方法，不再接受新的线程，且等到线程池中的所有线程结束后终止
                }
            }
            GlobelData.lastThreadCount=GlobelData.realThreadCount;

            ThreadPoolExecutor threadPoolExecutor=(ThreadPoolExecutor)executorService;
            int count=(int)(threadPoolExecutor.getTaskCount()-threadPoolExecutor.getCompletedTaskCount());
            GlobelData.realThreadCount=(int)(threadPoolExecutor.getTaskCount());
            GlobelData.executedTaskCount=(int)(threadPoolExecutor.getCompletedTaskCount());
            System.out.println("当前线程池中的线程数："+count);
            System.out.println("任务总数："+threadPoolExecutor.getTaskCount());
            System.out.println("已完成任务数："+threadPoolExecutor.getCompletedTaskCount());

            System.out.println("核心池大小："+threadPoolExecutor.getCorePoolSize());
            System.out.println("活跃的任务数："+threadPoolExecutor.getActiveCount());


            if(count<=1){      //线程池中除去监听器线程，其他线程全部结束时
                GlobelData.status= MyConstants.status3;
                GlobelData.endTime=System.currentTimeMillis();
                GlobelData.executedTaskCount++;
                break;
            }
        }
    }
}
