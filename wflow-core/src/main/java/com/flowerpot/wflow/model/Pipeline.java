package com.flowerpot.wflow.model;

/**
 * Pipeline
 *
 * 管道类，数据流通的基本通道。管道中可以对数据做各种操作，然后可以衍生出各种管道。
 * 管道有如下功能:
 *  1. 数据流入的功能
 *  2. 管道可以进行分支。就是说，数据通过了特定的处理形程了新的管道类型
 *  3. 可以对管道的数据进行过滤。例如，去重等等
 *  4. 可以对管道的数据进行可控操作，例如：暂停、继续、关闭
 *
 * 例如: 输入 数字的管道，添加了一个对数字做平方运算的操作，然后得到了一个新得数字管道。
 *
 * @author Mrhan
 * @date 2021/12/21 15:34
 */
public interface Pipeline<T> {

    /**
     * 获取管道名称
     * @return 返回管道名称
     */
    String getName();
    /**
     * 数据输入的功能
     * @param data 输入的数据
     */
    void input(T data);

    /**
     * 在当前管道创建新的分支
     * @param operate   管道数据
     * @param context   操作上下文
     * @param <D>  输出数据
     * @param <C>  操作上下文
     */
    <D, C extends OperateContext<D>> void branch(Operate<T, D> operate, C context);

    /**
     * 在当前管道创建新的分支
     * @param operate   管道数据
     * @param pipeline   新的分支管道
     * @param <D>  输出数据
     * @param <P>  分支管道
     * @return  返回信的管道分支
     */
    default <D, P extends Pipeline<D>> P branch(Operate<T, D> operate, P pipeline) {
        this.branch(operate, (OperateContext<D>)pipeline::input);
        return pipeline;
    }

    /**
     * 在当前管道上添加数据下沉
     * @param sink 数据下沉
     */
    void addSink(Sink<T> sink);

    /**
     * 添加过滤器
     * @param filter 过滤器
     */
    void addFilter(Filter<T> filter);

    /**
     * 获取管道控制器
     * @return 返回管道控制器
     */
    PipelineControl getControl();
}
