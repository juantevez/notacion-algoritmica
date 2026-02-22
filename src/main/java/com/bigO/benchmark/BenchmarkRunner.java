package com.bigO.benchmark;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

public class BenchmarkRunner {

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(".*Benchmark.*")  // Regex para seleccionar benchmarks
                .warmupIterations(3)
                .measurementIterations(5)
                .forks(2)
                .warmupTime(TimeValue.seconds(1))
                .measurementTime(TimeValue.seconds(1))
                .build();

        new Runner(opt).run();
    }
}