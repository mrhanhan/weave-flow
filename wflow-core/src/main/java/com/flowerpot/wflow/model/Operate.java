package com.flowerpot.wflow.model;

/**
 * Operate
 *
 * 操作接口，他的最终目的就是对管道中的数据做转换操作
 *
 * @author Mrhan
 * @date 2021/12/21 15:38
 */
@FunctionalInterface
public interface Operate<IN, OUT> {
    /**
     * 操作数据，吧输入的数据做特殊处理转换为输出数据
     * @param data 返回数据
     * @param context 操作上下文。有些时候
     */
    void operate(IN data, OperateContext<OUT> context);
}
