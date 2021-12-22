package com.flowerpot.wflow.model;

/**
 * ExecutionGraph
 * 执行图，执行图中，管理所有管道信息。
 *
 * @author Mrhan
 * @date 2021/12/21 17:42
 */
public interface ExecutionGraph {

    /**
     * 注册管道
     *
     * @param name     管道名称
     * @param pipeline 管道
     */
    void registerPipeline(String name, Pipeline<?> pipeline);

    /**
     * 导流，把指定管道的数据导向另一个管道
     *
     * @param fromPipeline 原管道
     * @param toPipeline   目标管道
     * @param operate      操作
     * @param <IN>         源管道数据类型
     * @param <OUT>        目标管道数据类型
     */
    <IN, OUT> void diversion(String fromPipeline, String toPipeline, Operate<IN, OUT> operate);

    /**
     * 给指定管道添加Sink
     *
     * @param pipelineName 管道名称
     * @param sink         sink
     * @param <T>          返回名称
     */
    <T> void addSink(String pipelineName, Sink<T> sink);

    /**
     * 给指定管道添加过滤器
     *
     * @param pipelineName 管道名称
     * @param filter       过滤器
     * @param <T>          管道数据类型
     */
    <T> void addFilter(String pipelineName, Filter<T> filter);

    /**
     * 输入数据
     * @param pipelineName  管道名称
     * @param data  输入的数据
     * @param <T>   输入数据的泛型
     */
    <T> void input(String pipelineName, T data);
}
