package com.flowerpot.wflow.model;

/**
 * Sink
 * Sink 是对数据做最总的落地操作。例如，管道中的内容最终都会流入到一个最终的位置存起来。mysql、redis 等
 * @author Mrhan
 * @date 2021/12/21 15:43
 */
public interface Sink<T> {

    /**
     * 下沉数据
     * @param data 数据
     */
    void sink(T data);
}
