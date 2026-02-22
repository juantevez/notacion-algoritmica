package com.bigO.benchmark;

import com.bigO.exponential.ExponentialTimeAlgorithms;
import org.openjdk.jmh.annotations.*;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 2, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 3, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Fork(1)
public class ExponentialBenchmark extends BaseBenchmark {

    private ExponentialTimeAlgorithms algorithms;

    @Param({"10", "15", "20"})
    private int fibInputSize;

    @Setup
    public void setUpAlgorithms() {
        algorithms = new ExponentialTimeAlgorithms();
    }

    @Benchmark
    public long fibonacci() {
        return algorithms.fibonacci(fibInputSize).value();
    }

    @Benchmark
    public int towersOfHanoi() {
        return algorithms.towersOfHanoi(fibInputSize).value().intValue();
    }
}
