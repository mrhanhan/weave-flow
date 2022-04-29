package com.flowerpot.wflow.model;

/**
 * PipelineStatus
 * 管道状态
 * @author Mrhan
 * @date 2021/12/21 16:31
 */
public enum PipelineStatus {
    /**
     * 运行中的状态
     */
    RUNNING,
    /**
     * 初始化中
     */
    INITIALIZING,
    /**
     * 回复中
     */
    RESUMING,
    /**
     * 暂停中
     */
    PAUSING,
    /**
     * 暂停的状态
     */
    PAUSED,
    /**
     * 停止中
     */
    STOPPING,
    /**
     * 停止的状态
     */
    STOP,
    /**
     * 结束
     */
    FINISH
}
