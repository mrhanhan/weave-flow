package com.flowerpot.wflow.model;

/**
 * 定义管道的生命周期。
 * 1. 开始
 *    当确定执行图时，会调用所有管道的开始处理方法
 * 2. 处理数据
 *    执行数据转换时，由上一个管道来回调
 * 3. 暂停
 *    当执行暂停后，当前节点不会再继续往下发送数据
 * 4. 继续
 *    当继续执行后，会继续往下发送数据
 * 5. 结束
 *    执行结束，并告知所有子管道当前管道执行结束
 * @author Mrhan
 * @date 2022/1/21 16:23
 */
public interface PipelineCycle<T> {
    /**
     * 开始处理方法
     */
    void start();
    /**
     * 处理数据
     * @param data 输入数据
     */
    void input(T data);
    /**
     * 粘贴
     */
    void pause();

    /**
     * 恢复到运行状态
     */
    void resume();

    /**
     * 停止
     */
    void stop();
    /**
     * 结束状态
     */
    void finish();

}
