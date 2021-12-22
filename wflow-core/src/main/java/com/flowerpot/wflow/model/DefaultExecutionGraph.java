package com.flowerpot.wflow.model;

import java.util.HashMap;
import java.util.Map;

/**
 * DefaultExecutionGraph
 *
 * @author Mrhan
 * @date 2021/12/21 17:49
 */
public class DefaultExecutionGraph implements ExecutionGraph{

    private final Map<String, Pipeline<?>> pipelineMap = new HashMap<>();

    @Override
    public void registerPipeline(String name, Pipeline<?> pipeline) {
        pipelineMap.put(name, pipeline);
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

    @Override
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
}
