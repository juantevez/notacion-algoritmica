#!/usr/bin/env python3
"""
Genera gráficas de rendimiento a partir de resultados JMH JSON
"""

import json
import matplotlib.pyplot as plt
import pandas as pd
from datetime import datetime
import os

def load_results(json_file):
    """Carga los resultados del benchmark desde JSON"""
    with open(json_file, 'r', encoding='utf-8') as f:
        return json.load(f)

def extract_data(data):
    """Extrae benchmark, score y unidad de los resultados"""
    benchmarks = []
    scores = []
    units = []
    errors = []

    for b in data['benchmarks']:
        # Nombre limpio del benchmark
        name = b['benchmark'].split('.')[-1]
        score = b['primaryMetric']['score']
        unit = b['primaryMetric']['scoreUnit']
        error = b['primaryMetric']['scoreError']

        benchmarks.append(name)
        scores.append(score)
        units.append(unit)
        errors.append(error)

    return benchmarks, scores, units, errors

def normalize_scores(scores, units):
    """Normaliza todos los scores a microsegundos para comparación"""
    normalized = []
    for score, unit in zip(scores, units):
        if 'ms/op' in unit:
            normalized.append(score * 1000)  # ms → μs
        elif 's/op' in unit:
            normalized.append(score * 1_000_000)  # s → μs
        elif 'ns/op' in unit:
            normalized.append(score / 1000)  # ns → μs
        else:
            normalized.append(score)  # ya está en μs
    return normalized

def create_bar_chart(benchmarks, scores, errors, output_file='benchmark-chart.png'):
    """Crea gráfica de barras con errores"""
    plt.figure(figsize=(14, 8))

    colors = plt.cm.viridis(range(0, 256, 256 // len(benchmarks)))

    bars = plt.bar(range(len(benchmarks)), scores,
                   yerr=errors, capsize=5, color=colors, alpha=0.8)

    plt.xticks(range(len(benchmarks)), benchmarks, rotation=45, ha='right')
    plt.ylabel('Tiempo (microsegundos por operación)')
    plt.title('📊 Comparativa de Rendimiento - Big O Algorithms\n(JMH Benchmark Results)',
              fontsize=14, fontweight='bold')
    plt.grid(axis='y', alpha=0.3, linestyle='--')
    plt.tight_layout()

    # Agregar valores sobre las barras
    for i, (bar, score) in enumerate(zip(bars, scores)):
        height = bar.get_height()
        plt.text(bar.get_x() + bar.get_width()/2., height,
                f'{score:.2f}', ha='center', va='bottom', fontsize=9)

    plt.savefig(output_file, dpi=300, bbox_inches='tight')
    print(f"✅ Gráfica guardada en: {output_file}")
    plt.close()

def create_comparison_table(benchmarks, scores, units):
    """Crea tabla comparativa en consola"""
    print("\n" + "="*80)
    print("📋 TABLA COMPARATIVA DE RENDIMIENTO")
    print("="*80)
    print(f"{'Benchmark':<40} {'Score':>15} {'Unidad':<15}")
    print("-"*80)

    for bench, score, unit in zip(benchmarks, scores, units):
        print(f"{bench:<40} {score:>15.6f} {unit:<15}")

    print("="*80 + "\n")

def create_csv_report(benchmarks, scores, units, errors, output_file='benchmark-report.csv'):
    """Exporta resultados a CSV"""
    df = pd.DataFrame({
        'Benchmark': benchmarks,
        'Score': scores,
        'Error': errors,
        'Unit': units
    })
    df.to_csv(output_file, index=False)
    print(f"✅ Reporte CSV guardado en: {output_file}")

def main():
    # Buscar archivo JSON
    json_files = ['jmh-results.json', 'target/jmh-results.json', 'results.json']
    json_file = None

    for f in json_files:
        if os.path.exists(f):
            json_file = f
            break

    if not json_file:
        print("❌ Error: No se encontró el archivo jmh-results.json")
        print("   Ejecuta: java -jar target/benchmarks.jar -rf json -rff jmh-results.json")
        return

    print(f"📂 Cargando resultados desde: {json_file}")

    # Cargar y procesar datos
    data = load_results(json_file)
    benchmarks, scores, units, errors = extract_data(data)

    print(f"✅ {len(benchmarks)} benchmarks encontrados\n")

    # Mostrar tabla en consola
    create_comparison_table(benchmarks, scores, units)

    # Normalizar scores para gráfica comparativa
    normalized_scores = normalize_scores(scores, units)

    # Crear gráfica
    create_bar_chart(benchmarks, normalized_scores, errors, 'benchmark-chart.png')

    # Exportar a CSV
    create_csv_report(benchmarks, scores, units, errors, 'benchmark-report.csv')

    # Resumen estadístico
    print("\n📈 RESUMEN ESTADÍSTICO")
    print("-"*80)
    print(f"  Benchmark más rápido: {benchmarks[scores.index(min(scores))]}")
    print(f"  Benchmark más lento:  {benchmarks[scores.index(max(scores))]}")
    print(f"  Ratio lento/rápido:   {max(scores)/min(scores):.2f}x")
    print("-"*80 + "\n")

if __name__ == '__main__':
    main()
