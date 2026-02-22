package com.bigO.linear;

import com.bigO.dto.ExecutionResult;

public class LinearTimeAlgorithms {

    public ExecutionResult<Integer> linearSearch(int[] array, int target) {
        long steps = 0;
        for (int i = 0; i < array.length; i++) {
            steps++;
            if (array[i] == target) {
                return new ExecutionResult<>(i, steps);
            }
        }
        return new ExecutionResult<>(-1, steps);
    }

    public ExecutionResult<Long> sumArray(int[] array) {
        long steps = 0;
        long sum = 0;
        for (int num : array) {
            steps++;
            sum += num;
        }
        return new ExecutionResult<>(sum, steps);
    }
}