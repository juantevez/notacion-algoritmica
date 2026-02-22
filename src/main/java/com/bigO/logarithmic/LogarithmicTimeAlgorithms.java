package com.bigO.logarithmic;

import com.bigO.dto.ExecutionResult;

public class LogarithmicTimeAlgorithms {

    public ExecutionResult<Integer> binarySearch(int[] sortedArray, int target) {
        long steps = 0;
        int left = 0;
        int right = sortedArray.length - 1;

        while (left <= right) {
            steps++;
            int mid = left + (right - left) / 2;

            if (sortedArray[mid] == target) {
                return new ExecutionResult<>(mid, steps);
            }

            if (sortedArray[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return new ExecutionResult<>(-1, steps);
    }
}