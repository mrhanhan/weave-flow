package com.flowerpot.wflow.model;

import lombok.Getter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * DefaultExecutionGraph
 *
 * @author Mrhan
 * @date 2021/12/21 17:49
 */
public class DefaultExecutionGraph implements ExecutionGraph{

    private final Map<String, Pipeline<?>> pipelineMap = new HashMap<>();
    private final Set<String> entrancePipelineSet = new HashSet<>();
    @Getter
    private PipelineStatus status;
    @Override
    public void registerPipeline(String name, Pipeline<?> pipeline) {
        pipelineMap.put(name, pipeline);
    }

    @Override
    public void bindEntrancePipeline(String name) {
        entrancePipelineSet.add(name);
    }

    @Override
    @SuppressWarnings("all")
    public <IN, OUT> void diversion(String fromPipelineName, String toPipelineName, Operate<IN, OUT> operate) {
        Pipeline<IN> fromPipeline = getPipeline(fromPipelineName);
        Pipeline<OUT> toPipeline = getPipeline(toPipelineName);
        fromPipeline.branch(operate, toPipeline);
    }

    @Override
    @SuppressWarnings("all")
    public <T> void addSink(String pipelineName, Sink<T> sink) {
        Pipeline<T> pipeline = getPipeline(pipelineName);
        pipeline.addSink(sink);
    }

    @Override
    @SuppressWarnings("all")
    public <T> void addFilter(String pipelineName, Filter<T> filter) {
        Pipeline<T> pipeline = getPipeline(pipelineName);
        pipeline.addFilter(filter);
    }

    public <T> void input(String pipelineName, T data) {
        Pipeline<T> pipeline = getPipeline(pipelineName);
        pipeline.input(data);
    }

    /**
     * 获取指定名称的管道
     * @param pipelineName 管道名称
     * @param <T> 类型
     * @return 返回管道
     */
    @SuppressWarnings("all")
    protected <T> Pipeline<T> getPipeline(String pipelineName) {
        return (Pipeline<T>)pipelineMap.get(pipelineName);
    }

    @Override
    public void start() {
        this.status = PipelineStatus.INITIALIZING;
        for (Pipeline<?> value : pipelineMap.values()) {
            value.start();
        }
        this.status = PipelineStatus.RUNNING;
    }

    @Override
    public void input(Object data) {
        for (String entrancePipeline : entrancePipelineSet) {
            input(entrancePipeline, data);
        }
    }

    @Override
    public void pause() {
        this.status = PipelineStatus.PAUSING;
        for (Pipeline<?> value : pipelineMap.values()) {
            value.pause();
        }
        this.status = PipelineStatus.PAUSED;
    }

    @Override
    public void resume() {
        this.status = PipelineStatus.RESUMING;
        for (Pipeline<?> value : pipelineMap.values()) {
            value.resume();
        }
        this.status = PipelineStatus.RUNNING;
    }

    @Override
    public void stop() {
        this.status = PipelineStatus.STOPPING;
        for (Pipeline<?> value : pipelineMap.values()) {
            value.stop();
        }
        this.status = PipelineStatus.STOP;
    }

    @Override
    public void finish() {
        for (String entrancePipeline : entrancePipelineSet) {
            getPipeline(entrancePipeline).finish();
        }
        this.status = PipelineStatus.FINISH;
    }
}
