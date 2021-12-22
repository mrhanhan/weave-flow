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
    /**
     * 管道的控制
     */
    private PipelineControl control;

    public AbstractPipeline() {
        filterChain.setFinalFilter(this::finalFilter);
        dataContainer = new DefaultDataContainer<>();
        control = new DefaultPipelineControl<T>(this);
    }

    @Override
    public void input(T data) {
        // 判断管道是否是暂停了
        if (this.control.getStatus() == PipelineStatus.PAUSED) {
            this.dataContainer.put(data);
        } else if (this.control.getStatus() == PipelineStatus.RUNNING) {
            // 过滤器过滤数据
            this.filterChain.filter(data, this);
        }
    }

    @Override
    @SuppressWarnings("all")
    public <D, C extends OperateContext<D>> void branch(Operate<T, D> operate, C context) {
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

    @Override
    public PipelineControl getControl() {
        return control;
    }

    public void setControl(PipelineControl control) {
        this.control = control;
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
        private OperateContext<OUT> context;
    }
}
