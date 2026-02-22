package com.bigO.benchmark;

import com.bigO.logarithmic.LogarithmicTimeAlgorithms;
import org.openjdk.jmh.annotations.*;

@State(Scope.Thread)
public class LogarithmicBenchmark extends BaseBenchmark {

    private LogarithmicTimeAlgorithms algorithms;

    @Setup
    public void setUpAlgorithms() {
        algorithms = new LogarithmicTimeAlgorithms();
    }

    @Benchmark
    public int binarySearch() {
        return algorithms.binarySearch(sortedData, inputSize - 1).value();
    }
}