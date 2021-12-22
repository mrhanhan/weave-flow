package com.flowerpot.wflow.model;

import java.util.Vector;

/**
 * DefaultDataContainer
 * 默认数据容器
 * @author Mrhan
 * @date 2021/12/21 16:44
 */
public class DefaultDataContainer<T> implements DataContainer<T>{

    private final Vector<T> vector;

    public DefaultDataContainer() {
        this.vector = new Vector<>();
    }

    @Override
    public int getSize() {
        return vector.size();
    }

    @Override
    public void put(T data) {
        vector.addElement(data);
    }

    @Override
    public T pop() {
        if (vector.isEmpty()) {
            return null;
        }
        T data = vector.elementAt(0);
        vector.removeElementAt(0);
        return data;
    }

    @Override
    public void clear() {
        vector.clear();
    }
}
