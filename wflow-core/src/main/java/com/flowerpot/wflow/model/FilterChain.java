package com.flowerpot.wflow.model;

/**
 * FilterChain
 * 过滤器链
 * @author Mrhan
 * @date 2021/12/21 15:47
 */
public interface FilterChain<T> {

    /**
     * 过滤数据
     * @param data      数据
     * @param pipeline  过滤器所在的管道
     */
    void filter(T data, Pipeline<T> pipeline);
}
