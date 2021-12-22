package com.flowerpot.wflow.model.baize;

import com.flowerpot.wflow.model.AbstractPipeline;
import com.flowerpot.wflow.model.Pipeline;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * BaizeAssetMain
 *
 * @author Mrhan
 * @date 2021/12/21 17:17
 */
public class BaizeAssetMain {

    private static ExecutorService executorService = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        // 白泽资产测绘入口
        Pipeline<String> entrance = new AbstractPipeline<>();
        Pipeline<String[]> web = new AbstractPipeline<>();
        // IPC 备案查询
        entrance.branch((data, context) -> {
            System.out.println("进行IPC 备案查询: " + data);
            context.input(data);
        }, new AbstractPipeline<>()).branch((data, context) -> {
            System.out.println("进行子域名查询:" + data);
            executorService.submit(() -> {
                for (int i = 0; i < (Math.random() * 100 % 10); i++) {
                    context.input(i + "." + data);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }, new AbstractPipeline<String>()).branch((data, context) -> {
            System.out.println("HTTPX 检测子域名是否存活:" + data);
            executorService.submit(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                context.input(new String[]{"127.0.0.1", data});
            });
        }, web);

        web.addSink(data -> {
            System.out.println("Web 数据存入数据库");
        });
        web.branch((data, context) -> {
            System.out.println("检测WAF:" + data[1]);
            executorService.submit(() -> {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                context.input(data[1]);
            });
        }, new AbstractPipeline<>()).addSink(data -> System.out.println("WAF存入数据库:" + data));

        web.branch((data, context) -> {
            System.out.println("检测漏洞:" + data[1]);
            executorService.submit(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                context.input(data[1]);
            });
        }, new AbstractPipeline<>()).addSink(data -> System.out.println("漏洞存入数据库:" + data));

        web.branch((data, context) -> {
            System.out.println("检测高关注组件:" + data[1]);
            executorService.submit(() -> {
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                context.input(data[1]);
            });
        }, new AbstractPipeline<>()).addSink(data -> System.out.println("高关注组件存入数据库:" + data));

        web.branch((data, context) -> {
            System.out.println("检测指纹:" + data[1]);
            executorService.submit(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                context.input(data[1]);
            });
        }, new AbstractPipeline<>()).addSink(data -> System.out.println("指纹存入数据库:" + data));

        Scanner scanner = new Scanner(System.in);
        while (!Thread.interrupted()) {
            System.out.println("请输入需要检测的目标:");
            entrance.input(scanner.nextLine());
        }
    }
}
