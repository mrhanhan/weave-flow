package com.flowerpot.wflow.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * DefaultFilterChain
 *
 * @author Mrhan
 * @date 2021/12/21 15:49
 */
public class DefaultFilterChain<T> implements FilterChain<T>{

    /**
     * 过滤器List
     */
    private final List<Filter<T>> filterList = new ArrayList<>();
    /**
     * 最终过滤器
     */
    private Filter<T> finalFilter;

    /**
     * 当前过滤器执行的位置
     */
    private int current = 0;

    /**
     * 过滤器
     * @param filter 添加过滤器
     */
    public void addFilter(Filter<T> filter) {
        filterList.add(filter);
    }

    public void setFinalFilter(Filter<T> finalFilter) {
        this.finalFilter = finalFilter;
    }

    @Override
    public void filter(T data, Pipeline<T> pipeline) {
        if (current >= filterList.size()) {
            // 执行最终过滤器
            if (Objects.nonNull(finalFilter)) {
                finalFilter.filter(data, pipeline, this);
            }
        } else {
            filterList.get(current ++).filter(data, pipeline, this);
        }
    }
}
