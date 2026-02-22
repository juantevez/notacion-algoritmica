package com.bigO.benchmark;

import com.bigO.quadratic.QuadraticTimeAlgorithms;
import org.openjdk.jmh.annotations.*;

@State(Scope.Thread)
public class QuadraticBenchmark extends BaseBenchmark {

    private QuadraticTimeAlgorithms algorithms;

    @Setup
    public void setUpAlgorithms() {
        algorithms = new QuadraticTimeAlgorithms();
    }

    @Benchmark
    public int[] bubbleSort() {
        return algorithms.bubbleSort(testData.clone()).value();
    }

    @Benchmark
    public int[] selectionSort() {
        return algorithms.selectionSort(testData.clone()).value();
    }

    @Benchmark
    public boolean hasDuplicates() {
        return algorithms.hasDuplicates(testData).value();
    }
}