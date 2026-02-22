package com.bigO.dto;

public record ExecutionResult<T>(T value, long steps) {
    @Override
    public String toString() {
        return String.format("Valor: %s | Pasos: %d", value, steps);
    }
}