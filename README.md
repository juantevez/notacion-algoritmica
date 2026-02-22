# 📘 Big O Notation - Java 17 Project

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://openjdk.java.net/)
[![Maven](https://img.shields.io/badge/Maven-3.8+-blue.svg)](https://maven.apache.org/)
[![JUnit](https://img.shields.io/badge/JUnit-5.10.0-green.svg)](https://junit.org/junit5/)
[![JMH](https://img.shields.io/badge/JMH-1.37-yellow.svg)](https://openjdk.java.net/projects/code-tools/jmh/)

Proyecto educativo que demuestra las principales **complejidades algorítmicas (Big O)** mediante implementación práctica en **Java 17**, tests unitarios con **JUnit 5** y benchmarks de rendimiento con **JMH**.

---

## 🎯 Objetivo

Entender cómo escala el rendimiento de los algoritmos según el tamaño de entrada (`n`), comparando **teóricamente** y midiendo **prácticamente** el número de operaciones y el tiempo real de ejecución.

---

## 📊 Resumen de Complejidades

| Package | Complejidad | Nombre | Algoritmos Incluidos |
|:--------|:-----------:|:-------|:---------------------|
| `constant` | **O(1)** | Constante | Acceso por índice, Verificar par/impar |
| `logarithmic` | **O(log n)** | Logarítmica | Búsqueda Binaria |
| `linear` | **O(n)** | Lineal | Búsqueda Lineal, Suma de array |
| `linearithmic` | **O(n log n)** | Linearítmica | Merge Sort, Quick Sort |
| `quadratic` | **O(n²)** | Cuadrática | Bubble Sort, Selection Sort, Pares |
| `exponential` | **O(2^n)** | Exponencial | Fibonacci, Subsets, Hanoi, Knapsack |

---

## 📚 Detalle Teórico y Práctico

### 1️⃣ O(1) - Tiempo Constante

> **Teoría:** El tiempo de ejecución es independiente del tamaño de la entrada. Es la complejidad más eficiente posible.

*   **📈 Gráfica:** Línea horizontal plana.
*   **🧠 Concepto:** No importa si tienes 10 o 10 millones de elementos, la operación toma el mismo tiempo.
*   **💻 Ejemplo Práctico:**
    ```java
    public ExecutionResult<Integer> getElementByIndex(int[] array, int index) {
        return new ExecutionResult<>(array[index], 1); // 1 solo paso
    }
    ```
*   **✅ Cuándo usar:** Acceso a arrays, HashMaps (get/put), operaciones matemáticas básicas.
*   **⚠️ Consideraciones:** Difícil de lograr en operaciones que dependen de datos variables.

---

### 2️⃣ O(log n) - Tiempo Logarítmico

> **Teoría:** El tiempo crece logarítmicamente. Cada paso reduce el problema a la mitad.

*   **📈 Gráfica:** Curva que se aplana rápidamente.
*   **🧠 Concepto:** "Divide y vencerás". Muy eficiente para grandes volúmenes de datos.
*   **💻 Ejemplo Práctico:**
    ```java
    while (left <= right) {
        int mid = left + (right - left) / 2;
        // Reduce el espacio de búsqueda a la mitad
    }
    ```
*   **✅ Cuándo usar:** Búsquedas en arrays ordenados, árboles binarios de búsqueda (BST).
*   **⚠️ Consideraciones:** Requiere que los datos estén **ordenados** previamente.

---

### 3️⃣ O(n) - Tiempo Lineal

> **Teoría:** El tiempo crece proporcionalmente al tamaño de la entrada.

*   **📈 Gráfica:** Línea diagonal recta (45°).
*   **🧠 Concepto:** Si duplicas los datos, duplicas el tiempo de procesamiento.
*   **💻 Ejemplo Práctico:**
    ```java
    for (int i = 0; i < array.length; i++) {
        steps++; // 1 paso por cada elemento
        if (array[i] == target) return i;
    }
    ```
*   **✅ Cuándo usar:** Recorrer listas, búsquedas en datos no ordenados, operaciones de streaming.
*   **⚠️ Consideraciones:** Aceptable para datos pequeños/medianos, costoso para millones de registros.

---

### 4️⃣ O(n log n) - Tiempo Linearítmico

> **Teoría:** Combina operación lineal con logarítmica. Típico en algoritmos de ordenamiento eficientes.

*   **📈 Gráfica:** Ligeramente más inclinada que O(n).
*   **🧠 Concepto:** Divides el problema (log n) y procesas cada nivel (n).
*   **💻 Ejemplo Práctico:**
    ```java
    mergeSortRecursive(array, left, mid);   // log n divisiones
    mergeSortRecursive(array, mid+1, right);
    merge(array, left, mid, right);         // n operaciones por nivel
    ```
*   **✅ Cuándo usar:** Ordenamiento de grandes conjuntos de datos (estándar en `Arrays.sort()`).
*   **⚠️ Consideraciones:** Más complejo de implementar que O(n²), pero mucho más escalable.

---

### 5️⃣ O(n²) - Tiempo Cuadrático

> **Teoría:** El tiempo crece proporcionalmente al cuadrado de la entrada. Común en bucles anidados.

*   **📈 Gráfica:** Curva parabólica ascendente.
*   **🧠 Concepto:** Pequeños incrementos en `n` causan grandes incrementos en tiempo.
*   **💻 Ejemplo Práctico:**
    ```java
    for (int i = 0; i < n; i++) {           // n iteraciones
        for (int j = 0; j < n - i - 1; j++) { // n iteraciones por cada i
            steps++; // Total: n * n
        }
    }
    ```
*   **✅ Cuándo usar:** Solo para conjuntos de datos muy pequeños (<50 elementos) o educación.
*   **⚠️ Consideraciones:** **Evitar** en producción con datos grandes. 1000 elementos = 1,000,000 de operaciones.

---

### 6️⃣ O(2^n) - Tiempo Exponencial

> **Teoría:** El tiempo se duplica con cada elemento adicional. Es la complejidad más costosa.

*   **📈 Gráfica:** Curva vertical casi inmediata.
*   **🧠 Concepto:** Intratable para `n` grandes. Solo usable para `n` muy pequeños.
*   **💻 Ejemplo Práctico:**
    ```java
    return fibonacciRecursive(n - 1) + fibonacciRecursive(n - 2);
    // Árbol de llamadas de profundidad n
    ```
*   **✅ Cuándo usar:** Problemas NP-Completos, fuerza bruta cuando no hay otra opción, `n` < 30.
*   **⚠️ Consideraciones:** **Peligroso**. `n=50` puede tardar años en ejecutarse. Usar memoización o programación dinámica para optimizar.

---

## 🔬 Comparativa de Rendimiento

Número aproximado de operaciones según el tamaño de entrada (`n`):

| n | O(1) | O(log n) | O(n) | O(n log n) | O(n²) | O(2^n) |
|:-:|:----:|:--------:|:----:|:----------:|:-----:|:------:|
| 10 | 1 | 3 | 10 | 33 | 100 | 1,024 |
| 100 | 1 | 7 | 100 | 664 | 10,000 | 1.26×10³⁰ |
| 1000 | 1 | 10 | 1,000 | 9,966 | 1,000,000 | 🚀 Imposible |

> **Nota:** O(2^n) con n=1000 es físicamente imposible de calcular en el tiempo de vida del universo.

---

## 🛠️ Tecnologías Utilizadas

| Tecnología | Versión | Propósito |
|------------|---------|-----------|
| **Java** | 17 | Lenguaje base (Records, var, Switch expressions) |
| **JUnit 5** | 5.10.0 | Testing unitario |
| **JMH** | 1.37 | Benchmarking de rendimiento |
| **Maven** | 3.8+ | Gestión de dependencias y build |
| **Python** | 3.x | Generación de reportes (opcional) |

---

## 🚀 Cómo Ejecutar

### Prerrequisitos
*   JDK 17 o superior
*   Maven 3.8+
*   Python 3.x (opcional, para reportes)

### Comandos

```bash
# 1. Compilar el proyecto
mvn clean package

# 2. Ejecutar tests unitarios
mvn test

# 3. Ejecutar benchmarks (puede tomar varios minutos)
java -jar target/benchmarks.jar

# 4. Ejecutar benchmarks con parámetros específicos
java -jar target/benchmarks.jar -p inputSize=1000
java -jar target/benchmarks.jar ".*Linear.*"

# 5. Exportar resultados a JSON
java -jar target/benchmarks.jar -rf json -rff jmh-results.json

# 6. Generar reporte visual (Python)
py scripts\generate-report-html.py

# 7. Abrir reporte HTML
start benchmark-report.html
```

---

## 📁 Estructura del Proyecto

```
notacion/
├── src/
│   ├── main/java/com/bigO/
│   │   ├── dto/                      # Clases de soporte (ExecutionResult)
│   │   ├── constant/                 # O(1)
│   │   ├── logarithmic/              # O(log n)
│   │   ├── linear/                   # O(n)
│   │   ├── linearithmic/             # O(n log n)
│   │   ├── quadratic/                # O(n²)
│   │   ├── exponential/              # O(2^n)
│   │   └── benchmark/                # Benchmarks JMH
│   └── test/java/com/bigO/           # Tests unitarios para cada package
├── scripts/
│   ├── generate-report-html.py       # Generador de reportes (sin dependencias)
│   └── run-full-benchmark.bat        # Script de ejecución completa (Windows)
├── target/
│   └── benchmarks.jar                # JAR ejecutable de benchmarks
├── jmh-results.json                  # Resultados crudos de JMH
├── benchmark-report.html             # Reporte visual generado
├── benchmark-report.csv              # Datos en formato CSV
├── pom.xml                           # Configuración de Maven
└── README.md                         # Este archivo
```

---

## 📊 Benchmarking con JMH

El proyecto incluye benchmarks reales usando **JMH (Java Microbenchmark Harness)** para medir el tiempo de ejecución real de cada algoritmo.

### Parámetros de JMH

| Parámetro | Valor | Descripción |
|-----------|-------|-------------|
| `-wi` | 3 | Iteraciones de warmup |
| `-i` | 5 | Iteraciones de medición |
| `-f` | 2 | Número de forks |
| `-p` | inputSize=1000 | Tamaño de entrada |
| `-rf` | json | Formato de resultado |
| `-rff` | resultados.json | Archivo de resultado |

### Ejemplo de Salida

```
Benchmark                        Mode  Cnt     Score    Error  Units
ConstantBenchmark.getElement     avgt    5     0.001 ±  0.001  us/op
LogarithmicBenchmark.search      avgt    5     0.050 ±  0.010  us/op
LinearBenchmark.search           avgt    5     1.500 ±  0.200  us/op
LinearithmicBenchmark.sort       avgt    5    50.000 ±  5.000  us/op
QuadraticBenchmark.sort          avgt    5  1500.000 ± 100.000  us/op
ExponentialBenchmark.fib         avgt    3  5000.000 ± 500.000  ms/op
```

---

## 📈 Formato Inteligente de Unidades

El script de reportes convierte automáticamente las unidades para mejor legibilidad:

| Rango | Unidad | Ejemplo |
|-------|--------|---------|
| < 1,000 μs | **μs** (microsegundos) | 513.41 μs |
| 1,000 - 1,000,000 μs | **ms** (milisegundos) | 1.94 ms |
| 1,000,000 - 60,000,000 μs | **s** (segundos) | 2.69 s |
| 60,000,000 - 3,600,000,000 μs | **min** (minutos) | 27.25 min |
| > 3,600,000,000 μs | **hr** (horas) | 7.79 hr |

---

## 🎁 Scripts Incluidos

### `scripts/generate-report-html.py`
Genera un reporte HTML interactivo con gráficas usando Chart.js (desde CDN, sin instalar dependencias).

```bash
py scripts\generate-report-html.py
start benchmark-report.html
```

### `scripts/run-full-benchmark.bat` (Windows)
Ejecuta todo el proceso con un doble clic: compilar, benchmarks y generar reporte.

```bash
scripts\run-full-benchmark.bat
```

---

## 📸 Ejemplo de Reporte HTML

El reporte generado incluye:

*   📊 **Gráfica de barras interactiva** con Chart.js
*   📋 **Tabla detallada** con tiempos formateados inteligentemente
*   🎨 **Código de colores** por tipo de complejidad
*   📈 **Resumen estadístico** (más rápido, más lento, ratio)
*   📱 **Diseño responsive** para móviles y escritorio

---

## 📈 Lecciones Aprendidas

1.  **La elección del algoritmo importa:** La diferencia entre O(n log n) y O(n²) puede ser segundos vs horas.
2.  **Medir es crucial:** No asumas la complejidad, mide los pasos reales y el tiempo de ejecución.
3.  **Trade-offs:** A veces un algoritmo más complejo de leer (Merge Sort) vale la pena por el rendimiento.
4.  **Evitar exponenciales:** O(2^n) debe ser la última opción y solo para `n` muy pequeños.
5.  **El hardware importa:** Los benchmarks reales varían según CPU, RAM, JVM y optimizaciones.

---

## 🤝 Contribuciones

¡Las contribuciones son bienvenidas! Si encuentras un error o quieres agregar más algoritmos:

1.  Fork el proyecto
2.  Crea una rama (`git checkout -b feature/NuevoAlgoritmo`)
3.  Commit tus cambios (`git commit -m 'Add nuevo algoritmo'`)
4.  Push a la rama (`git push origin feature/NuevoAlgoritmo`)
5.  Abre un Pull Request

---

## 📄 Licencia

Este proyecto está bajo la licencia MIT. Ver el archivo [LICENSE](LICENSE) para más detalles.

---

## 📞 Contacto

*   **Autor:** Juan T.
*   **Año:** 2026
*   **Proyecto:** Big O Notation - Java 17

---

<div align="center">

**Hecho con ❤️ para aprender y enseñar complejidad algorítmica**

[⬆ Volver al inicio](#-big-o-notation---java-17-project)
