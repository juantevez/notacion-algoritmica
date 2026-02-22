package com.bigO.linearithmic;

import com.bigO.dto.ExecutionResult;

public class LinearithmicTimeAlgorithms {

    private long steps = 0;

    /**
     * O(n log n) - Merge Sort
     * Divide el array en mitades (log n) y mergea cada nivel (n)
     */
    public ExecutionResult<int[]> mergeSort(int[] array) {
        steps = 0;
        if (array == null || array.length <= 1) {
            return new ExecutionResult<>(array, steps);
        }

        int[] result = mergeSortRecursive(array.clone(), 0, array.length - 1);
        return new ExecutionResult<>(result, steps);
    }

    private int[] mergeSortRecursive(int[] array, int left, int right) {
        if (left >= right) {
            return array;
        }

        steps++; // Contamos la división
        int mid = left + (right - left) / 2;

        mergeSortRecursive(array, left, mid);
        mergeSortRecursive(array, mid + 1, right);
        merge(array, left, mid, right);

        return array;
    }

    private void merge(int[] array, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];

        System.arraycopy(array, left, leftArray, 0, n1);
        System.arraycopy(array, mid + 1, rightArray, 0, n2);

        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {
            steps++; // Contamos cada comparación
            if (leftArray[i] <= rightArray[j]) {
                array[k++] = leftArray[i++];
            } else {
                array[k++] = rightArray[j++];
            }
        }

        while (i < n1) {
            steps++;
            array[k++] = leftArray[i++];
        }

        while (j < n2) {
            steps++;
            array[k++] = rightArray[j++];
        }
    }

    /**
     * O(n log n) - Quick Sort (caso promedio)
     */
    public ExecutionResult<int[]> quickSort(int[] array) {
        steps = 0;
        if (array == null || array.length <= 1) {
            return new ExecutionResult<>(array, steps);
        }

        int[] result = array.clone();
        quickSortRecursive(result, 0, result.length - 1);
        return new ExecutionResult<>(result, steps);
    }

    private void quickSortRecursive(int[] array, int low, int high) {
        if (low < high) {
            steps++;
            int pi = partition(array, low, high);
            quickSortRecursive(array, low, pi - 1);
            quickSortRecursive(array, pi + 1, high);
        }
    }

    private int partition(int[] array, int low, int high) {
        int pivot = array[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            steps++;
            if (array[j] < pivot) {
                i++;
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }

        int temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;

        return i + 1;
    }
}
