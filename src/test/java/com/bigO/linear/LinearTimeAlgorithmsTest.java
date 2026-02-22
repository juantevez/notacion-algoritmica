package com.bigO.linear;

import com.bigO.dto.ExecutionResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinearTimeAlgorithmsTest {

    private LinearTimeAlgorithms algorithms;

    @BeforeEach
    void setUp() {
        algorithms = new LinearTimeAlgorithms();
    }

    @Test
    @DisplayName("O(n) - Búsqueda lineal elemento al inicio")
    void linearSearch_ElementAtStart() {
        int[] array = {5, 10, 15, 20};
        ExecutionResult<Integer> result = algorithms.linearSearch(array, 5);

        assertEquals(0, result.value());
        assertEquals(1, result.steps());
    }

    @Test
    @DisplayName("O(n) - Búsqueda lineal elemento al final")
    void linearSearch_ElementAtEnd() {
        int[] array = {5, 10, 15, 20};
        ExecutionResult<Integer> result = algorithms.linearSearch(array, 20);

        assertEquals(3, result.value());
        assertEquals(4, result.steps());
    }

    @Test
    @DisplayName("O(n) - Búsqueda lineal elemento no encontrado")
    void linearSearch_ElementNotFound() {
        int[] array = {5, 10, 15};
        ExecutionResult<Integer> result = algorithms.linearSearch(array, 100);

        assertEquals(-1, result.value());
        assertEquals(array.length, result.steps());
    }

    @Test
    @DisplayName("O(n) - Suma de array")
    void sumArray() {
        int[] array = {1, 2, 3, 4, 5};
        ExecutionResult<Long> result = algorithms.sumArray(array);

        assertEquals(15L, result.value());
        assertEquals(array.length, result.steps());
    }

    @Test
    @DisplayName("O(n) - Verificar complejidad lineal")
    void linearSearch_LinearComplexity() {
        int[] smallArray = new int[100];
        int[] largeArray = new int[1000];

        ExecutionResult<Integer> smallResult = algorithms.linearSearch(smallArray, -1);
        ExecutionResult<Integer> largeResult = algorithms.linearSearch(largeArray, -1);

        assertEquals(smallArray.length, smallResult.steps());
        assertEquals(largeArray.length, largeResult.steps());
        assertEquals(10, largeResult.steps() / smallResult.steps());
    }
}
