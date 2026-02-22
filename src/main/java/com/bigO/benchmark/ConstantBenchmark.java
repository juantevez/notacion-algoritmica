package com.bigO.benchmark;

import com.bigO.constant.ConstantTimeAlgorithms;
import org.openjdk.jmh.annotations.*;

@State(Scope.Thread)
public class ConstantBenchmark extends BaseBenchmark {

    private ConstantTimeAlgorithms algorithms;

    @Setup
    public void setUpAlgorithms() {
        algorithms = new ConstantTimeAlgorithms();
    }

    @Benchmark
    public int getElementByIndex() {
        return algorithms.getElementByIndex(testData, inputSize / 2).value();
    }

    @Benchmark
    public boolean isEven() {
        return algorithms.isEven(inputSize).value();
    }
}
