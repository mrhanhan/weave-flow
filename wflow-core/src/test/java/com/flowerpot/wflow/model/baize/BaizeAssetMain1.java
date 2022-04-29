package com.flowerpot.wflow.model.baize;

import com.flowerpot.wflow.model.AbstractPipeline;
import com.flowerpot.wflow.model.DefaultExecutionGraph;
import com.flowerpot.wflow.model.Operate;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * BaizeAssetMain
 *
 * @author Mrhan
 * @date 2021/12/21 17:17
 */
public class BaizeAssetMain1 {

    private static ExecutorService executorService = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        DefaultExecutionGraph defaultExecutionGraph = new DefaultExecutionGraph();
        defaultExecutionGraph.registerPipeline("entrance", new AbstractPipeline<String>());
        defaultExecutionGraph.registerPipeline("subdomain", new AbstractPipeline<String[]>());
        defaultExecutionGraph.registerPipeline("web", new AbstractPipeline<String[]>());
        defaultExecutionGraph.registerPipeline("httpx", new AbstractPipeline<String>());
        defaultExecutionGraph.registerPipeline("vul", new AbstractPipeline<String>());
        defaultExecutionGraph.registerPipeline("waf", new AbstractPipeline<String>());
        defaultExecutionGraph.registerPipeline("component", new AbstractPipeline<String>());
        defaultExecutionGraph.registerPipeline("zw", new AbstractPipeline<String>());
        // 白泽资产测绘入口
        defaultExecutionGraph.diversion("entrance", "subdomain", (data, context) -> {
            System.out.println("进行IPC 备案查询: " + data);
            context.input(data);
        });
        defaultExecutionGraph.diversion("subdomain", "httpx", (data, context) -> {
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
        });
        defaultExecutionGraph.diversion("httpx", "web", (Operate<String, String[]>) (data, context) -> {
            System.out.println("HTTPX 检测子域名是否存活:" + data);
            executorService.submit(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                context.input(new String[]{"127.0.0.1", data});
            });
        });

        defaultExecutionGraph.diversion("web", "waf", (Operate<String[], String>) (data, context) -> {
            System.out.println("检测WAF:" + data[1]);
            executorService.submit(() -> {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                context.input(data[1]);
            });
        });
        defaultExecutionGraph.diversion("web", "vul", (Operate<String[], String>) (data, context) -> {
            System.out.println("检测漏洞:" + data[1]);
            executorService.submit(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                context.input(data[1]);
            });
        });
        defaultExecutionGraph.diversion("web", "zw", (Operate<String[], String>) (data, context) -> {
            System.out.println("检测指纹:" + data[1]);
            executorService.submit(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                context.input(data[1]);
            });
        });

        defaultExecutionGraph.diversion("web", "component", (Operate<String[], String>) (data, context) -> {
            System.out.println("检测高关注组件:" + data[1]);
            executorService.submit(() -> {
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                context.input(data[1]);
            });
        });

        defaultExecutionGraph.addSink("web", data -> {
            System.out.println("Web 数据存入数据库");
        });
        defaultExecutionGraph.addSink("zw", data -> {
            System.out.println("Web 指纹存入数据库");
        });
        defaultExecutionGraph.addSink("component", data -> {
            System.out.println("Web 高关注组件存入数据库");
        });
        defaultExecutionGraph.addSink("vul", data -> {
            System.out.println("Web 漏洞存入数据库");
        });
        defaultExecutionGraph.start();
        Scanner scanner = new Scanner(System.in);
        while (!Thread.interrupted()) {
            System.out.println("请输入需要检测的目标:");
            defaultExecutionGraph.input("entrance", scanner.nextLine());
        }

    }
}
