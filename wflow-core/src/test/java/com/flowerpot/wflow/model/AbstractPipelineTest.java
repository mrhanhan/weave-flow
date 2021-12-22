package com.flowerpot.wflow.model;

import java.security.SecureRandom;

public class AbstractPipelineTest {

    public static void main(String[] args) {
        AbstractPipeline<Integer> numberPipeline = new AbstractPipeline<>();
        // 数据落地
        numberPipeline.addSink(data -> System.out.println("一级管道: " + data));
        // 数据过滤
        numberPipeline.addFilter(((data, pipeline, chain) -> {
            chain.filter(data, pipeline);
        }));
        // 转换为字符管道
        numberPipeline.branch((data, context) -> {
            context.input((char)data.intValue());
        }, new AbstractPipeline<Character>()).addSink(data -> System.out.println("二级管道: " + data));
        // 转换为字符管道
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < 100; i++) {
            numberPipeline.input(random.nextInt(255));
        }
    }

}