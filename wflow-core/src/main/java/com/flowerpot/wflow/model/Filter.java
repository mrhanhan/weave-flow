package com.flowerpot.wflow.model;

/**
 * Filter
 * 过滤器，过滤管道中的数据。经过过滤器过滤过的数据最终才会继续流下去
 *
 * @author Mrhan
 * @date 2021/12/21 15:45
 */
@FunctionalInterface
public interface Filter<T> {

    /**
     * 过滤器
     * @param data      需要过滤的数据
     * @param pipeline  数据所在的管道
     * @param chain     chain
     */
    void filter(T data, Pipeline<T> pipeline, FilterChain<T> chain);
}
