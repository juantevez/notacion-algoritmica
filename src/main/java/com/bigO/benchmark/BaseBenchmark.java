package com.bigO.benchmark;

import org.openjdk.jmh.annotations.*;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(2)
public abstract class BaseBenchmark {

    @Param({"100", "1000", "10000"})
    protected int inputSize;

    protected int[] testData;
    protected int[] sortedData;

    @Setup
    public void setUp() {
        testData = new int[inputSize];
        sortedData = new int[inputSize];

        for (int i = 0; i < inputSize; i++) {
            testData[i] = inputSize - i; // Desordenado
            sortedData[i] = i;           // Ordenado
        }
    }
}
