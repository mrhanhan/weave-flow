package com.flowerpot.wflow.model;

/**
 * OperateContext
 * 有些时候有些数据的操作，并不是实时的响应。这时候需要操作上下文来做一个异步数据的处理方式
 * @author Mrhan
 * @date 2021/12/21 16:06
 */
public interface OperateContext<T> {
    /**
     * 处理数据
     * @param data 输入数据
     */
    void input(T data);
}
