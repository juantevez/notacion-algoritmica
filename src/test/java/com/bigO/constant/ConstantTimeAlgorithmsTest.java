package com.bigO.constant;

import com.bigO.dto.ExecutionResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConstantTimeAlgorithmsTest {

    private ConstantTimeAlgorithms algorithms;

    @BeforeEach
    void setUp() {
        algorithms = new ConstantTimeAlgorithms();
    }

    @Test
    @DisplayName("O(1) - Obtener elemento por índice válido")
    void getElementByIndex_ValidIndex() {
        int[] array = {10, 20, 30, 40, 50};
        ExecutionResult<Integer> result = algorithms.getElementByIndex(array, 2);

        assertEquals(30, result.value());
        assertEquals(1, result.steps());
    }

    @Test
    @DisplayName("O(1) - Índice fuera de límites")
    void getElementByIndex_InvalidIndex() {
        int[] array = {10, 20, 30};
        ExecutionResult<Integer> result = algorithms.getElementByIndex(array, 10);

        assertNull(result.value());
        assertEquals(1, result.steps());
    }

    @Test
    @DisplayName("O(1) - Mismo costo para array pequeño y grande")
    void getElementByIndex_ConstantTimeRegardlessOfSize() {
        int[] smallArray = new int[10];
        int[] largeArray = new int[1_000_000];

        ExecutionResult<Integer> smallResult = algorithms.getElementByIndex(smallArray, 5);
        ExecutionResult<Integer> largeResult = algorithms.getElementByIndex(largeArray, 500);

        assertEquals(smallResult.steps(), largeResult.steps());
        assertEquals(1, smallResult.steps());
    }

    @Test
    @DisplayName("O(1) - Verificar número par")
    void isEven() {
        assertTrue(algorithms.isEven(4).value());
        assertFalse(algorithms.isEven(7).value());
        assertEquals(1, algorithms.isEven(4).steps());
    }
}
