package com.bigO.logarithmic;

import com.bigO.dto.ExecutionResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogarithmicTimeAlgorithmsTest {

    private LogarithmicTimeAlgorithms algorithms;

    @BeforeEach
    void setUp() {
        algorithms = new LogarithmicTimeAlgorithms();
    }

    @Test
    @DisplayName("O(log n) - Búsqueda binaria elemento encontrado")
    void binarySearch_ElementFound() {
        int[] array = {1, 3, 5, 7, 9, 11, 13};
        ExecutionResult<Integer> result = algorithms.binarySearch(array, 7);

        assertEquals(3, result.value());
        assertTrue(result.steps() < array.length);
    }

    @Test
    @DisplayName("O(log n) - Búsqueda binaria elemento no encontrado")
    void binarySearch_ElementNotFound() {
        int[] array = {1, 3, 5, 7, 9};
        ExecutionResult<Integer> result = algorithms.binarySearch(array, 10);

        assertEquals(-1, result.value());
    }

    @Test
    @DisplayName("O(log n) - Verificar complejidad logarítmica")
    void binarySearch_LogarithmicComplexity() {
        int n = 1024;
        int[] array = new int[n];
        for (int i = 0; i < n; i++) array[i] = i * 2;

        ExecutionResult<Integer> result = algorithms.binarySearch(array, 2046);

        // log2(1024) = 10, permitimos margen
        assertTrue(result.steps() <= Math.ceil(Math.log(n) / Math.log(2)) + 1);
    }

    @Test
    @DisplayName("O(log n) - Array vacío")
    void binarySearch_EmptyArray() {
        int[] array = {};
        ExecutionResult<Integer> result = algorithms.binarySearch(array, 5);

        assertEquals(-1, result.value());
        assertEquals(0, result.steps());
    }
}
