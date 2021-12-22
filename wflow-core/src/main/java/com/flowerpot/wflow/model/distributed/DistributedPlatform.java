package com.flowerpot.wflow.model.distributed;

import com.flowerpot.wflow.model.OperateContext;

/**
 * DistributedPlatform
 * 分布式平台, A 服务和 B 服务操作OperateContext 是无感操作。无需知道是否是分布式
 * @author Mrhan
 * @date 2021/12/22 16:03
 */
public interface DistributedPlatform {

    /**
     * 获取起始端的操作上下文
     * @param id        操作ID
     * @param <T>       数据类型
     * @return          返回发送数据端的 OperateContext
     */
    <T> OperateContext<T> getFromOperateContext(String id);

    /**
     * 绑定指定ID 数据消费的位置
     * @param id        操作ID
     * @param handler   处理From端发来的数据
     * @param <T>       数据类型
     */
    <T> void bindToOperateContext(String id, OperateContext<T> handler);

}
