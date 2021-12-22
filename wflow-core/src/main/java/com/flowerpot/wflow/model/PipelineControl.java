package com.flowerpot.wflow.model;

/**
 * PipelineControl
 * 管道控制器
 * @author Mrhan
 * @date 2021/12/21 16:28
 */
public interface PipelineControl {

    /**
     * 获取状态
     * @return 获取管道状态
     */
    PipelineStatus getStatus();
    /**
     * 暂停当前管道的操作
     */
    void pause();

    /**
     * 恢复当前管道的操作
     */
    void resume();

    /**
     * 停止当前管道的操作
     */
    void stop();
}
