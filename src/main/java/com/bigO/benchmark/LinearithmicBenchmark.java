package com.bigO.benchmark;

import com.bigO.linearithmic.LinearithmicTimeAlgorithms;
import org.openjdk.jmh.annotations.*;

@State(Scope.Thread)
public class LinearithmicBenchmark extends BaseBenchmark {

    private LinearithmicTimeAlgorithms algorithms;

    @Setup
    public void setUpAlgorithms() {
        algorithms = new LinearithmicTimeAlgorithms();
    }

    @Benchmark
    public int[] mergeSort() {
        return algorithms.mergeSort(testData.clone()).value();
    }

    @Benchmark
    public int[] quickSort() {
        return algorithms.quickSort(testData.clone()).value();
    }
}