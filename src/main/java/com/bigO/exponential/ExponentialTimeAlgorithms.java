package com.bigO.exponential;

import com.bigO.dto.ExecutionResult;

import java.util.ArrayList;
import java.util.List;

public class ExponentialTimeAlgorithms {

    private long steps = 0;

    /**
     * O(2^n) - Fibonacci Recursivo (sin memoización)
     * Cada llamada genera 2 llamadas adicionales
     */
    public ExecutionResult<Long> fibonacci(int n) {
        steps = 0;
        if (n < 0) {
            return new ExecutionResult<>(null, steps);
        }
        long result = fibonacciRecursive(n);
        return new ExecutionResult<>(result, steps);
    }

    private long fibonacciRecursive(int n) {
        steps++;
        if (n <= 1) {
            return n;
        }
        return fibonacciRecursive(n - 1) + fibonacciRecursive(n - 2);
    }

    /**
     * O(2^n) - Generar todos los subconjuntos (Power Set)
     */
    public ExecutionResult<List<List<Integer>>> generateSubsets(int[] array) {
        steps = 0;
        List<List<Integer>> subsets = new ArrayList<>();
        if (array == null) {
            return new ExecutionResult<>(subsets, steps);
        }
        generateSubsetsRecursive(array, 0, new ArrayList<>(), subsets);
        return new ExecutionResult<>(subsets, steps);
    }

    private void generateSubsetsRecursive(int[] array, int index,
                                          List<Integer> current,
                                          List<List<Integer>> subsets) {
        steps++;
        if (index == array.length) {
            subsets.add(new ArrayList<>(current));
            return;
        }

        // Incluir el elemento actual
        current.add(array[index]);
        generateSubsetsRecursive(array, index + 1, current, subsets);

        // Excluir el elemento actual
        current.remove(current.size() - 1);
        generateSubsetsRecursive(array, index + 1, current, subsets);
    }

    /**
     * O(2^n) - Torres de Hanoi
     * Retorna el número de movimientos necesarios: 2^n - 1
     */
    public ExecutionResult<Long> towersOfHanoi(int n) {
        steps = 0;
        if (n <= 0) {
            return new ExecutionResult<>(0L, steps);
        }
        solveHanoi(n, 'A', 'C', 'B');
        return new ExecutionResult<>(steps, steps);
    }

    private void solveHanoi(int n, char from, char to, char aux) {
        steps++;
        if (n == 1) {
            return;
        }
        solveHanoi(n - 1, from, aux, to);
        solveHanoi(n - 1, aux, to, from);
    }

    /**
     * O(2^n) - Resolver problema de la mochila (fuerza bruta)
     */
    public ExecutionResult<Integer> knapsack(int[] weights, int[] values, int capacity) {
        steps = 0;
        if (weights == null || values == null || weights.length == 0) {
            return new ExecutionResult<>(0, steps);
        }
        int result = knapsackRecursive(weights, values, capacity, 0);
        return new ExecutionResult<>(result, steps);
    }

    private int knapsackRecursive(int[] weights, int[] values,
                                  int capacity, int index) {
        steps++;
        if (capacity <= 0 || index >= weights.length) {
            return 0;
        }

        // No incluir el elemento actual
        int withoutCurrent = knapsackRecursive(weights, values, capacity, index + 1);

        // Incluir el elemento actual (si cabe)
        int withCurrent = 0;
        if (weights[index] <= capacity) {
            withCurrent = values[index] +
                    knapsackRecursive(weights, values, capacity - weights[index], index + 1);
        }

        return Math.max(withoutCurrent, withCurrent);
    }
}
