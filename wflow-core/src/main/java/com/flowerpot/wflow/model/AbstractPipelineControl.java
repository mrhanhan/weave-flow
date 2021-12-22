package com.flowerpot.wflow.model;

/**
 * AbstractPipelineControl
 *
 * @author Mrhan
 * @date 2021/12/21 16:39
 */
public abstract class AbstractPipelineControl<T> implements PipelineControl {
    /**
     * 数据容器
     */
    private final DataContainer<T> dataContainer;
    private final Pipeline<T> pipeline;

    /**
     * 构造控制器
     * @param dataContainer     数据容器
     * @param pipeline          数据管道
     */
    public AbstractPipelineControl(DataContainer<T> dataContainer, Pipeline<T> pipeline) {
        this.dataContainer = dataContainer;
        this.pipeline = pipeline;
    }
    /**
     * 构造控制器
     * @param pipeline          数据管道
     */
    public AbstractPipelineControl(AbstractPipeline<T> pipeline) {
        this.dataContainer = pipeline.getDataContainer();
        this.pipeline = pipeline;
    }

    @Override
    public void pause() {
        // 暂停
        setStatus(PipelineStatus.PAUSED);
    }

    @Override
    public void resume() {
        // 恢复
        setStatus(PipelineStatus.RUNNING);
        T data;
        while ((data = dataContainer.pop()) != null) {
            pipeline.input(data);
        }
    }

    @Override
    public void stop() {
        setStatus(PipelineStatus.STOP);
        // 清除容器数据
        dataContainer.clear();
    }

    /**
     * 设置管道状态
     * @param status 管道状态
     */
    public abstract void setStatus(PipelineStatus status);
}
