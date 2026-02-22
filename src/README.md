
# Algoritmos - Big O Notation

Proyecto educativo que demuestra las principales complejidades algorítmicas (Big O) mediante implementación práctica en **Java 17** y tests unitarios con **JUnit 5**.

## 🎯 Objetivo

Entender cómo escala el rendimiento de los algoritmos según el tamaño de entrada (`n`), comparando teóricamente y midiendo prácticamente el número de operaciones ejecutadas.

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
    // Acceso directo a memoria por índice
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
    // Búsqueda Binaria - Descarta la mitad del array en cada iteración
    while (left <= right) {
        int mid = left + (right - left) / 2;
        // ... reduce el espacio de búsqueda a la mitad
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
    // Búsqueda Lineal - Recorre elemento por elemento
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
    // Merge Sort - Divide el array y mergea los resultados
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
    // Bubble Sort - Dos bucles anidados
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
    // Fibonacci Recursivo - Cada llamada genera 2 nuevas llamadas
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
| **Maven** | 3.8+ | Gestión de dependencias y build |

---

## 🚀 Cómo Ejecutar

### Prerrequisitos
*   JDK 17 o superior
*   Maven 3.8+

### Comandos

```bash
# Clonar el repositorio
git clone <tu-repositorio>
cd big-o-algorithms

# Compilar el proyecto
mvn clean install

# Ejecutar todos los tests
mvn test

# Ejecutar tests por complejidad
mvn test -Dtest="LinearithmicTimeAlgorithmsTest"
mvn test -Dtest="ExponentialTimeAlgorithmsTest"

# Generar reporte de cobertura (requiere plugin Jacoco)
mvn test jacoco:report
```

---

## 📁 Estructura del Proyecto

```
src/
├── main/java/com/bigO/
│   ├── dto/                  # Clases de soporte (ExecutionResult)
│   ├── constant/             # O(1)
│   ├── logarithmic/          # O(log n)
│   ├── linear/               # O(n)
│   ├── linearithmic/         # O(n log n)
│   ├── quadratic/            # O(n²)
│   └── exponential/          # O(2^n)
└── test/java/com/bigO/       # Tests unitarios para cada package
```

---

## 📈 Lecciones Aprendidas

1. **La elección del algoritmo importa:** La diferencia entre O(n log n) y O(n²) puede ser segundos vs horas.
2. **Medir es crucial:** No asumas la complejidad, mide los pasos reales.
3. **Trade-offs:** A veces un algoritmo más complejo de leer (Merge Sort) vale la pena por el rendimiento.
4. **Evitar exponenciales:** O(2^n) debe ser la última opción y solo para `n` muy pequeños.

---

## 🤝 Contribuciones

¡Las contribuciones son bienvenidas! Si encuentras un error o quieres agregar más algoritmos:

1. Fork el proyecto
2. Crea una rama (`git checkout -b feature/NuevoAlgoritmo`)
3. Commit tus cambios (`git commit -m 'Add nuevo algoritmo'`)
4. Push a la rama (`git push origin feature/NuevoAlgoritmo`)
5. Abre un Pull Request

---

## 📄 Licencia

Este proyecto está bajo la licencia MIT. Ver el archivo [LICENSE](LICENSE) para más detalles.

---

### 💡 Recomendación Final

Te sugiero guardar este contenido en un archivo `README.md` en la raíz de tu proyecto. Si quieres llevarlo al siguiente nivel, podrías agregar:

1.  **Gráficas reales:** Ejecutar los algoritmos y generar un CSV con los resultados para plotear en Python/Excel.
2.  **Badge de CI/CD:** Agregar GitHub Actions para mostrar que los tests pasan.
3.  **Badge de Cobertura:** Mostrar el % de código cubierto por tests.

