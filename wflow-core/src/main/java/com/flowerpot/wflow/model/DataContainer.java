package com.flowerpot.wflow.model;

/**
 * DataContainer
 * 数据容器, 当管道进行暂停后，后续数据将存在容器中。
 * @author Mrhan
 * @date 2021/12/21 16:34
 */
public interface DataContainer<T> {

    /**
     * 获取容器数据量的大小
     * @return 返回数据量
     */
    int getSize();

    /**
     * 放入数据
     * @param data 返回数据
     */
    void put(T data);

    /**
     * 取出数据
     * @return 返回取出来的数据。当数据不存在时，返回 null
     */
    T pop();

    /**
     * 清空容器
     */
    void clear();
}
