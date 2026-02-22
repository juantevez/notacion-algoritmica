package com.bigO.linearithmic;

import com.bigO.dto.ExecutionResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinearithmicTimeAlgorithmsTest {

    private LinearithmicTimeAlgorithms algorithms;

    @BeforeEach
    void setUp() {
        algorithms = new LinearithmicTimeAlgorithms();
    }

    @Test
    @DisplayName("O(n log n) - Merge Sort array desordenado")
    void mergeSort_UnsortedArray() {
        int[] array = {64, 34, 25, 12, 22, 11, 90};
        ExecutionResult<int[]> result = algorithms.mergeSort(array);

        int[] expected = {11, 12, 22, 25, 34, 64, 90};
        assertArrayEquals(expected, result.value());
        assertTrue(result.steps() > 0);
    }

    @Test
    @DisplayName("O(n log n) - Merge Sort array ya ordenado")
    void mergeSort_AlreadySorted() {
        int[] array = {1, 2, 3, 4, 5};
        ExecutionResult<int[]> result = algorithms.mergeSort(array);

        int[] expected = {1, 2, 3, 4, 5};
        assertArrayEquals(expected, result.value());
    }

    @Test
    @DisplayName("O(n log n) - Quick Sort")
    void quickSort() {
        int[] array = {50, 23, 9, 18, 61, 32};
        ExecutionResult<int[]> result = algorithms.quickSort(array);

        int[] expected = {9, 18, 23, 32, 50, 61};
        assertArrayEquals(expected, result.value());
    }

    @Test
    @DisplayName("O(n log n) - Verificar complejidad n log n")
    void mergeSort_NLogNComplexity() {
        int[] small = new int[100];
        int[] large = new int[1000];

        for (int i = 0; i < small.length; i++) small[i] = (int)(Math.random() * 1000);
        for (int i = 0; i < large.length; i++) large[i] = (int)(Math.random() * 1000);

        ExecutionResult<int[]> smallResult = algorithms.mergeSort(small);
        ExecutionResult<int[]> largeResult = algorithms.mergeSort(large);

        // n log n: 1000*log(1000) / 100*log(100) ≈ 15
        double ratio = (double) largeResult.steps() / smallResult.steps();
        assertTrue(ratio > 5 && ratio < 25,
                "Ratio esperado ~15, obtenido: " + ratio);
    }

    @Test
    @DisplayName("O(n log n) - Array vacío")
    void mergeSort_EmptyArray() {
        int[] array = {};
        ExecutionResult<int[]> result = algorithms.mergeSort(array);

        assertArrayEquals(new int[]{}, result.value());
        assertEquals(0, result.steps());
    }
}
