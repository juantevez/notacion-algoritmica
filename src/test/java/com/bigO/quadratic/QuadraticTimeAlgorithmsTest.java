package com.bigO.quadratic;

import com.bigO.dto.ExecutionResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuadraticTimeAlgorithmsTest {

    private QuadraticTimeAlgorithms algorithms;

    @BeforeEach
    void setUp() {
        algorithms = new QuadraticTimeAlgorithms();
    }

    @Test
    @DisplayName("O(n²) - Bubble Sort")
    void bubbleSort() {
        int[] array = {64, 34, 25, 12, 22, 11, 90};
        ExecutionResult<int[]> result = algorithms.bubbleSort(array);

        int[] expected = {11, 12, 22, 25, 34, 64, 90};
        assertArrayEquals(expected, result.value());
    }

    @Test
    @DisplayName("O(n²) - Selection Sort")
    void selectionSort() {
        int[] array = {5, 2, 8, 1, 9};
        ExecutionResult<int[]> result = algorithms.selectionSort(array);

        int[] expected = {1, 2, 5, 8, 9};
        assertArrayEquals(expected, result.value());
    }

    @Test
    @DisplayName("O(n²) - Encontrar todos los pares")
    void findAllPairs() {
        int[] array = {1, 2, 3};
        ExecutionResult<int[][]> result = algorithms.findAllPairs(array);

        assertEquals(9, result.value().length); // 3 * 3 = 9 pares
        assertEquals(9, result.steps());
    }

    @Test
    @DisplayName("O(n²) - Detectar duplicados existentes")
    void hasDuplicates_Found() {
        int[] array = {1, 2, 3, 2, 5};
        ExecutionResult<Boolean> result = algorithms.hasDuplicates(array);

        assertTrue(result.value());
    }

    @Test
    @DisplayName("O(n²) - Detectar duplicados no existentes")
    void hasDuplicates_NotFound() {
        int[] array = {1, 2, 3, 4, 5};
        ExecutionResult<Boolean> result = algorithms.hasDuplicates(array);

        assertFalse(result.value());
        assertEquals(10, result.steps()); // n*(n-1)/2 = 5*4/2 = 10
    }

    @Test
    @DisplayName("O(n²) - Verificar complejidad cuadrática")
    void bubbleSort_QuadraticComplexity() {
        int[] small = new int[50];
        int[] large = new int[100];

        for (int i = 0; i < small.length; i++) small[i] = small.length - i;
        for (int i = 0; i < large.length; i++) large[i] = large.length - i;

        ExecutionResult<int[]> smallResult = algorithms.bubbleSort(small);
        ExecutionResult<int[]> largeResult = algorithms.bubbleSort(large);

        // n²: 100² / 50² = 4
        double ratio = (double) largeResult.steps() / smallResult.steps();
        assertTrue(ratio >= 3.5 && ratio <= 4.5,
                "Ratio esperado ~4, obtenido: " + ratio);
    }

    @Test
    @DisplayName("O(n²) - Array vacío")
    void bubbleSort_EmptyArray() {
        int[] array = {};
        ExecutionResult<int[]> result = algorithms.bubbleSort(array);

        assertArrayEquals(new int[]{}, result.value());
        assertEquals(0, result.steps());
    }
}
