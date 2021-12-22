package com.flowerpot.wflow.model.distributed;

import com.flowerpot.wflow.model.DefaultExecutionGraph;
import com.flowerpot.wflow.model.ExecutionGraph;
import com.flowerpot.wflow.model.Operate;
import com.flowerpot.wflow.model.OperateContext;
import com.flowerpot.wflow.model.Pipeline;
import lombok.RequiredArgsConstructor;

/**
 * DistributedExecutionGraph
 *
 * @author Mrhan
 * @date 2021/12/22 16:24
 */
@RequiredArgsConstructor
public class DistributedExecutionGraph extends DefaultExecutionGraph implements ExecutionGraph {

    private final DistributedPlatform distributedPlatform;

    @Override
    public void registerPipeline(String name, Pipeline<?> pipeline) {
        super.registerPipeline(name, pipeline);
        // 绑定消费
        distributedPlatform.bindToOperateContext(name, pipeline::input);
    }

    @Override
    public <IN, OUT> void diversion(String fromPipelineName, String toPipelineName, Operate<IN, OUT> operate) {
        // 第一步 获取管道
        Pipeline<IN> fromPipeline = getPipeline(fromPipelineName);
        // 第二部 获取 OperateContext
        fromPipeline.branch(operate, distributedPlatform.getFromOperateContext(toPipelineName));
    }

    @Override
    public <T> void input(String pipelineName, T data) {
        // 获取管道的名称
        OperateContext<T> context = distributedPlatform.getFromOperateContext(pipelineName);
        context.input(data);
    }

}
