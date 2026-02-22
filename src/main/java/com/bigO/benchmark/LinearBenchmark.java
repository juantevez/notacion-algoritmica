package com.bigO.benchmark;

import com.bigO.linear.LinearTimeAlgorithms;
import org.openjdk.jmh.annotations.*;

@State(Scope.Thread)
public class LinearBenchmark extends BaseBenchmark {

    private LinearTimeAlgorithms algorithms;

    @Setup
    public void setUpAlgorithms() {
        algorithms = new LinearTimeAlgorithms();
    }

    @Benchmark
    public int linearSearch() {
        return algorithms.linearSearch(testData, -1).value();
    }

    @Benchmark
    public long sumArray() {
        return algorithms.sumArray(testData).value();
    }
}