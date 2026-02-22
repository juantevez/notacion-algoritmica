package com.bigO.quadratic;

import com.bigO.dto.ExecutionResult;

public class QuadraticTimeAlgorithms {

    private long steps = 0;

    /**
     * O(n²) - Bubble Sort
     * Dos bucles anidados que recorren el array
     */
    public ExecutionResult<int[]> bubbleSort(int[] array) {
        steps = 0;
        if (array == null || array.length <= 1) {
            return new ExecutionResult<>(array, steps);
        }

        int[] result = array.clone();
        int n = result.length;

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                steps++;
                if (result[j] > result[j + 1]) {
                    int temp = result[j];
                    result[j] = result[j + 1];
                    result[j + 1] = temp;
                }
            }
        }

        return new ExecutionResult<>(result, steps);
    }

    /**
     * O(n²) - Selection Sort
     */
    public ExecutionResult<int[]> selectionSort(int[] array) {
        steps = 0;
        if (array == null || array.length <= 1) {
            return new ExecutionResult<>(array, steps);
        }

        int[] result = array.clone();
        int n = result.length;

        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                steps++;
                if (result[j] < result[minIdx]) {
                    minIdx = j;
                }
            }
            if (minIdx != i) {
                int temp = result[i];
                result[i] = result[minIdx];
                result[minIdx] = temp;
            }
        }

        return new ExecutionResult<>(result, steps);
    }

    /**
     * O(n²) - Encontrar todos los pares de un array
     */
    public ExecutionResult<int[][]> findAllPairs(int[] array) {
        steps = 0;
        if (array == null || array.length == 0) {
            return new ExecutionResult<>(new int[0][2], steps);
        }

        int n = array.length;
        int[][] pairs = new int[n * n][2];
        int index = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                steps++;
                pairs[index][0] = array[i];
                pairs[index][1] = array[j];
                index++;
            }
        }

        return new ExecutionResult<>(pairs, steps);
    }

    /**
     * O(n²) - Detectar duplicados (fuerza bruta)
     */
    public ExecutionResult<Boolean> hasDuplicates(int[] array) {
        steps = 0;
        if (array == null || array.length <= 1) {
            return new ExecutionResult<>(false, steps);
        }

        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                steps++;
                if (array[i] == array[j]) {
                    return new ExecutionResult<>(true, steps);
                }
            }
        }

        return new ExecutionResult<>(false, steps);
    }
}
