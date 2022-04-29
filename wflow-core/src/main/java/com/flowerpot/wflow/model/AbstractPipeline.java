package com.flowerpot.wflow.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * AbstractPipeline
 *
 * @author Mrhan
 * @date 2021/12/21 15:53
 */
public class AbstractPipeline<T> implements Pipeline<T>{

    @Setter
    @Getter
    private String name;

    @Getter
    @Setter
    private PipelineStatus status;

    /**
     * 控制器
     */
    private final DefaultFilterChain<T> filterChain = new DefaultFilterChain<>();
    /**
     * 数据落地
     */
    private final List<Sink<T>> sinkList = new ArrayList<>();
    /**
     * 操作绑定管道
     */
    private final List<OperateBindPipeline<T, Object>> operateBindPipelineList = new ArrayList<>();
    /**
     * 数据容器
     */
    private DataContainer<T> dataContainer;


    public AbstractPipeline() {
        filterChain.setFinalFilter(this::finalFilter);
        dataContainer = new DefaultDataContainer<>();
    }

    @Override
    public void start() {
        status = PipelineStatus.RUNNING;
    }

    @Override
    public void input(T data) {
        // 判断管道是否是暂停了
        if (this.getStatus() == PipelineStatus.PAUSED || this.getStatus() == PipelineStatus.PAUSING) {
            this.dataContainer.put(data);
        } else if (this.getStatus() == PipelineStatus.RUNNING) {
            // 过滤器过滤数据
            this.filterChain.filter(data, this);
        }
    }

    @Override
    public void pause() {
        status = PipelineStatus.PAUSED;
    }

    @Override
    public void resume() {
        status = PipelineStatus.RESUMING;
        // 读取数据
        while (this.dataContainer.getSize() > 0) {
            // 处理数据
            input(this.dataContainer.pop());
        }
        status = PipelineStatus.RUNNING;
    }

    @Override
    public void stop() {
        this.status = PipelineStatus.STOP;
        this.dataContainer.clear();
    }

    @Override
    public void finish() {
        this.status = PipelineStatus.FINISH;
        // TODO 上级节点如果处理完数据和会调用子节点
    }

    @Override
    @SuppressWarnings("all")
    public <D, C extends PipelineCycle<D>> void branch(Operate<T, D> operate, C context) {
        OperateBindPipeline<T, D> operateBindPipeline = new OperateBindPipeline<>();
        operateBindPipeline.operate = operate;
        operateBindPipeline.context = context;
        operateBindPipelineList.add((OperateBindPipeline<T, Object>)operateBindPipeline);
    }


    @Override
    public void addSink(Sink<T> sink) {
        sinkList.add(sink);
    }

    @Override
    public void addFilter(Filter<T> filter) {
        filterChain.addFilter(filter);
    }

    /**
     * 最终过滤器
     * @param data      数据
     * @param pipeline  管道
     * @param chain     过滤器链
     */
    private void finalFilter(T data, Pipeline<T> pipeline, FilterChain<T> chain) {
        // 执行数据落地
        for (Sink<T> sink : sinkList) {
            sink.sink(data);
        }
        // 执行数据转换
        for (OperateBindPipeline<T, Object> bindPipeline : operateBindPipelineList) {
            bindPipeline.operate.operate(data, bindPipeline.context);
        }
    }

    public DataContainer<T> getDataContainer() {
        return dataContainer;
    }

    public void setDataContainer(DataContainer<T> dataContainer) {
        this.dataContainer = dataContainer;
    }


    private static class OperateBindPipeline<IN, OUT> {
        private Operate<IN, OUT> operate;
        private PipelineCycle<OUT> context;
    }
}
