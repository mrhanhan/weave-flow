package com.flowerpot.wflow.model;

/**
 * DefaultPipelineControl
 *
 * @author Mrhan
 * @date 2021/12/21 16:48
 */
public class DefaultPipelineControl<T> extends AbstractPipelineControl<T> {

    private PipelineStatus status = PipelineStatus.RUNNING;

    public DefaultPipelineControl(DataContainer<T> dataContainer, Pipeline<T> pipeline) {
        super(dataContainer, pipeline);
    }

    public DefaultPipelineControl(AbstractPipeline<T> pipeline) {
        super(pipeline);
    }

    @Override
    public void setStatus(PipelineStatus status) {
        this.status = status;
    }

    @Override
    public PipelineStatus getStatus() {
        return this.status;
    }
}
