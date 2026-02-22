package com.bigO.exponential;

import com.bigO.dto.ExecutionResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExponentialTimeAlgorithmsTest {

    private ExponentialTimeAlgorithms algorithms;

    @BeforeEach
    void setUp() {
        algorithms = new ExponentialTimeAlgorithms();
    }

    @Test
    @DisplayName("O(2^n) - Fibonacci n=0")
    void fibonacci_Zero() {
        ExecutionResult<Long> result = algorithms.fibonacci(0);
        assertEquals(0L, result.value());
    }

    @Test
    @DisplayName("O(2^n) - Fibonacci n=1")
    void fibonacci_One() {
        ExecutionResult<Long> result = algorithms.fibonacci(1);
        assertEquals(1L, result.value());
    }

    @Test
    @DisplayName("O(2^n) - Fibonacci n=10")
    void fibonacci_Ten() {
        ExecutionResult<Long> result = algorithms.fibonacci(10);
        assertEquals(55L, result.value());
        assertTrue(result.steps() > 100); // Muchas llamadas recursivas
    }

    @Test
    @DisplayName("O(2^n) - Verificar crecimiento exponencial Fibonacci")
    void fibonacci_ExponentialGrowth() {
        ExecutionResult<Long> fib10 = algorithms.fibonacci(10);
        ExecutionResult<Long> fib15 = algorithms.fibonacci(15);

        // El número de pasos debería crecer exponencialmente
        double ratio = (double) fib15.steps() / fib10.steps();
        assertTrue(ratio > 10, "Ratio esperado >10, obtenido: " + ratio);
    }

    @Test
    @DisplayName("O(2^n) - Generar subconjuntos")
    void generateSubsets() {
        int[] array = {1, 2, 3};
        ExecutionResult<List<List<Integer>>> result = algorithms.generateSubsets(array);

        assertEquals(8, result.value().size()); // 2^3 = 8 subconjuntos
        assertEquals(15, result.steps());
    }

    @Test
    @DisplayName("O(2^n) - Subconjuntos array vacío")
    void generateSubsets_Empty() {
        int[] array = {};
        ExecutionResult<List<List<Integer>>> result = algorithms.generateSubsets(array);

        assertEquals(1, result.value().size()); // Solo el conjunto vacío
    }

    @Test
    @DisplayName("O(2^n) - Torres de Hanoi n=3")
    void towersOfHanoi_Three() {
        ExecutionResult<Long> result = algorithms.towersOfHanoi(3);
        assertEquals(7L, result.value()); // 2^3 - 1 = 7 movimientos
    }

    @Test
    @DisplayName("O(2^n) - Torres de Hanoi crecimiento")
    void towersOfHanoi_Growth() {
        ExecutionResult<Long> hanoi3 = algorithms.towersOfHanoi(3);
        ExecutionResult<Long> hanoi5 = algorithms.towersOfHanoi(5);

        assertEquals(31L, hanoi5.value()); // 2^5 - 1 = 31
        assertEquals(7L, hanoi3.value());  // 2^3 - 1 = 7
    }

    @Test
    @DisplayName("O(2^n) - Problema de la mochila")
    void knapsack() {
        int[] weights = {2, 3, 4, 5};
        int[] values = {3, 4, 5, 6};
        int capacity = 5;

        ExecutionResult<Integer> result = algorithms.knapsack(weights, values, capacity);

        assertEquals(7, result.value()); // Mejor combinación: pesos 2+3, valores 3+4
        assertTrue(result.steps() > 10);
    }

    @Test
    @DisplayName("O(2^n) - Advertencia de rendimiento para n grande")
    void fibonacci_LargeN_PerformanceWarning() {
        // Solo probamos hasta n=20 para no hacer el test muy lento
        long startTime = System.currentTimeMillis();
        ExecutionResult<Long> result = algorithms.fibonacci(20);
        long endTime = System.currentTimeMillis();

        assertEquals(6765L, result.value());
        assertTrue(endTime - startTime < 5000, "El test tomó demasiado tiempo");
    }
}
