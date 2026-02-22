package com.bigO.constant;

import com.bigO.dto.ExecutionResult;

public class ConstantTimeAlgorithms {

    public ExecutionResult<Integer> getElementByIndex(int[] array, int index) {
        long steps = 1;
        if (index < 0 || index >= array.length) {
            return new ExecutionResult<>(null, steps);
        }
        return new ExecutionResult<>(array[index], steps);
    }

    public ExecutionResult<Boolean> isEven(int number) {
        return new ExecutionResult<>(number % 2 == 0, 1);
    }
}