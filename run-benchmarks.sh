#!/bin/bash

echo "🚀 Ejecutando Benchmarks Big O..."
echo "================================="

# Compilar proyecto
mvn clean package -DskipTests

# Ejecutar benchmarks
echo ""
echo "📊 Ejecutando JMH Benchmarks..."
java -jar target/benchmarks.jar -wi 3 -i 5 -f 2 -rf json -rff jmh-results.json

# Mostrar resumen
echo ""
echo "✅ Benchmarks completados!"
echo "📁 Resultados guardados en: jmh-results.json"
echo ""
echo "📈 Para ver resultados en formato legible:"
echo "   cat jmh-results.json | jq '.benchmarks[] | {benchmark: .benchmark, score: .primaryMetric.score, unit: .primaryMetric.scoreUnit}'"

