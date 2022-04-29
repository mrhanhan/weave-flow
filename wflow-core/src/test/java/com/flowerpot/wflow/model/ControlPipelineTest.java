package com.flowerpot.wflow.model;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ControlPipelineTest
 * 控制管道测速
 * @author Mrhan
 * @date 2021/12/21 16:50
 */
public class ControlPipelineTest {

    static ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * 执行一个任务，每次间隔3 秒会暂停管道。3秒后管道会自动恢复
     * @param args
     */
    public static void main(String[] args) {
        // 构建管道
        AbstractPipeline<Integer> numberPipeline = new AbstractPipeline<>();

        numberPipeline.addSink(System.out::println);

        executorService.submit(() -> {
           while (!Thread.interrupted()) {
               numberPipeline.pause();
               System.out.println("开始暂停");
               try {
                   Thread.sleep(3000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               System.out.println("恢复操作");
               numberPipeline.resume();
           }
        });

        for (int i = 0; i < 1000; i++) {
            numberPipeline.input(i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
