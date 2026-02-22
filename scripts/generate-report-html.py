#!/usr/bin/env python3
"""
Genera reporte HTML de benchmarks SIN dependencias externas
Formato inteligente de unidades (μs, ms, s, min)
"""

import json
import os
from datetime import datetime

def load_results(json_file):
    with open(json_file, 'r', encoding='utf-8') as f:
        return json.load(f)

def extract_data(data):
    """Extrae datos manejando ambos formatos de JSON de JMH"""
    benchmarks, scores, units, errors = [], [], [], []

    if isinstance(data, list):
        items = data
    elif isinstance(data, dict) and 'benchmarks' in data:
        items = data['benchmarks']
    else:
        raise ValueError(f"Formato de JSON no reconocido: {type(data)}")

    for b in items:
        try:
            benchmarks.append(b['benchmark'].split('.')[-1])
            scores.append(b['primaryMetric']['score'])
            units.append(b['primaryMetric']['scoreUnit'])
            errors.append(b['primaryMetric']['scoreError'])
        except KeyError as e:
            print(f"⚠️  Advertencia: Falta clave {e} en un benchmark")
            continue

    return benchmarks, scores, units, errors

def normalize_to_microseconds(scores, units):
    """Normaliza todo a microsegundos para comparación"""
    normalized = []
    for score, unit in zip(scores, units):
        if 'ms/op' in unit:
            normalized.append(score * 1000)
        elif 's/op' in unit:
            normalized.append(score * 1_000_000)
        elif 'ns/op' in unit:
            normalized.append(score / 1000)
        else:
            normalized.append(score)
    return normalized

def format_time(microseconds):
    """
    Convierte microsegundos a la unidad más legible
    Returns: (valor_formateado, unidad)
    """
    if microseconds < 1000:
        return f"{microseconds:.2f}", "μs"
    elif microseconds < 1_000_000:
        return f"{microseconds / 1000:.2f}", "ms"
    elif microseconds < 60_000_000:
        return f"{microseconds / 1_000_000:.2f}", "s"
    elif microseconds < 3_600_000_000:
        return f"{microseconds / 60_000_000:.2f}", "min"
    else:
        return f"{microseconds / 3_600_000_000:.2f}", "hr"

def create_html_report(benchmarks, scores, units, errors, normalized, output_file='benchmark-report.html'):
    min_idx, max_idx = scores.index(min(scores)), scores.index(max(scores))
    ratio = max(scores) / min(scores) if min(scores) > 0 else 0

    # Formatear valores para la tabla
    formatted_scores = []
    formatted_units = []
    for score in normalized:
        val, unit = format_time(score)
        formatted_scores.append(val)
        formatted_units.append(unit)

    def get_color(name):
        if 'Constant' in name: return '#4CAF50'
        if 'Logarithmic' in name: return '#8BC34A'
        if 'Linear' in name: return '#2196F3'
        if 'Linearithmic' in name: return '#FF9800'
        if 'Quadratic' in name: return '#FF5722'
        if 'Exponential' in name: return '#F44336'
        return '#9E9E9E'

    def get_row_class(i, min_idx, max_idx):
        if i == min_idx: return 'fast'
        if i == max_idx: return 'slow'
        return ''

    colors = [get_color(b) for b in benchmarks]

    import json as json_module
    benchmarks_json = json_module.dumps(benchmarks)
    normalized_json = json_module.dumps(normalized)
    colors_json = json_module.dumps(colors)

    # Generar filas de la tabla (CORREGIDO)
    table_rows = ''.join([
        f'<tr>'
        f'<td>{b}</td>'
        f'<td class="{get_row_class(i, min_idx, max_idx)}">{formatted_scores[i]}</td>'
        f'<td>{format_time(errors[i])[0]}</td>'
        f'<td>{u}</td>'
        f'</tr>'
        for i, (b, u) in enumerate(zip(benchmarks, units))
    ])

    html = f"""<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Big O Benchmark Report</title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style>
        * {{ box-sizing: border-box; margin: 0; padding: 0; }}
        body {{ font-family: 'Segoe UI', Arial, sans-serif; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); padding: 40px 20px; }}
        .container {{ max-width: 1400px; margin: 0 auto; background: white; border-radius: 15px; padding: 40px; box-shadow: 0 20px 60px rgba(0,0,0,0.3); }}
        h1 {{ color: #333; text-align: center; margin-bottom: 10px; font-size: 2.5em; }}
        .subtitle {{ text-align: center; color: #666; margin-bottom: 30px; }}
        .summary {{ display: grid; grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); gap: 20px; margin: 30px 0; }}
        .card {{ background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 25px; border-radius: 10px; text-align: center; }}
        .card h3 {{ font-size: 2.5em; margin-bottom: 10px; }}
        .card p {{ opacity: 0.9; }}
        .chart-container {{ position: relative; height: 500px; margin: 40px 0; }}
        table {{ width: 100%; border-collapse: collapse; margin-top: 30px; font-size: 0.95em; }}
        th, td {{ padding: 15px; text-align: left; border-bottom: 1px solid #ddd; }}
        th {{ background: #667eea; color: white; position: sticky; top: 0; }}
        tr:hover {{ background: #f5f5f5; }}
        .fast {{ color: #4CAF50; font-weight: bold; }}
        .slow {{ color: #F44336; font-weight: bold; }}
        .footer {{ text-align: center; margin-top: 40px; color: #999; font-size: 0.9em; }}
        .legend {{ display: flex; flex-wrap: wrap; gap: 15px; justify-content: center; margin: 20px 0; }}
        .legend-item {{ display: flex; align-items: center; gap: 8px; }}
        .legend-color {{ width: 20px; height: 20px; border-radius: 4px; }}
        @media (max-width: 768px) {{ .container {{ padding: 20px; }} h1 {{ font-size: 1.8em; }} table {{ font-size: 0.8em; }} }}
    </style>
</head>
<body>
    <div class="container">
        <h1>📊 Big O Algorithm Benchmark</h1>
        <p class="subtitle">Generado: {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}</p>

        <div class="summary">
            <div class="card">
                <h3>{len(benchmarks)}</h3>
                <p>Total Benchmarks</p>
            </div>
            <div class="card">
                <h3 class="fast">{benchmarks[min_idx]}</h3>
                <p>Más Rápido</p>
            </div>
            <div class="card">
                <h3 class="slow">{benchmarks[max_idx]}</h3>
                <p>Más Lento</p>
            </div>
            <div class="card">
                <h3>{ratio:,.0f}x</h3>
                <p>Ratio Lento/Rápido</p>
            </div>
        </div>

        <div class="legend">
            <div class="legend-item"><div class="legend-color" style="background: #4CAF50;"></div> O(1) Constante</div>
            <div class="legend-item"><div class="legend-color" style="background: #8BC34A;"></div> O(log n) Logarítmica</div>
            <div class="legend-item"><div class="legend-color" style="background: #2196F3;"></div> O(n) Lineal</div>
            <div class="legend-item"><div class="legend-color" style="background: #FF9800;"></div> O(n log n) Linearítmica</div>
            <div class="legend-item"><div class="legend-color" style="background: #FF5722;"></div> O(n²) Cuadrática</div>
            <div class="legend-item"><div class="legend-color" style="background: #F44336;"></div> O(2^n) Exponencial</div>
        </div>

        <div class="chart-container">
            <canvas id="benchmarkChart"></canvas>
        </div>

        <table>
            <thead>
                <tr><th>Benchmark</th><th>Tiempo</th><th>Error</th><th>Unidad Original</th></tr>
            </thead>
            <tbody>
                {table_rows}
            </tbody>
        </table>

        <div class="footer">
            <p>Java 17 • JMH 1.37 • Big O Notation Project</p>
        </div>
    </div>

    <script>
        const ctx = document.getElementById('benchmarkChart').getContext('2d');
        new Chart(ctx, {{
            type: 'bar',
            data: {{
                labels: {benchmarks_json},
                datasets: [{{
                    label: 'Tiempo (μs/op)',
                    data: {normalized_json},
                    backgroundColor: {colors_json},
                    borderColor: {colors_json},
                    borderWidth: 2,
                    borderRadius: 5
                }}]
            }},
            options: {{
                responsive: true,
                maintainAspectRatio: false,
                plugins: {{
                    title: {{ display: true, text: 'Comparativa de Rendimiento - Big O Algorithms', font: {{ size: 18 }} }},
                    legend: {{ display: false }},
                    tooltip: {{
                        callbacks: {{
                            label: function(context) {{
                                const value = context.parsed.y;
                                let formatted, unit;
                                if (value < 1000) {{ formatted = value.toFixed(2); unit = 'μs'; }}
                                else if (value < 1000000) {{ formatted = (value/1000).toFixed(2); unit = 'ms'; }}
                                else if (value < 60000000) {{ formatted = (value/1000000).toFixed(2); unit = 's'; }}
                                else {{ formatted = (value/60000000).toFixed(2); unit = 'min'; }}
                                return formatted + ' ' + unit;
                            }}
                        }}
                    }}
                }},
                scales: {{
                    y: {{
                        beginAtZero: true,
                        title: {{ display: true, text: 'Tiempo (microsegundos)' }},
                        grid: {{ color: '#f0f0f0' }},
                        ticks: {{
                            callback: function(value) {{
                                if (value >= 3600000000) return (value/3600000000).toFixed(1) + 'h';
                                if (value >= 60000000) return (value/60000000).toFixed(1) + 'min';
                                if (value >= 1000000) return (value/1000000).toFixed(1) + 's';
                                if (value >= 1000) return (value/1000).toFixed(1) + 'ms';
                                return value + 'μs';
                            }}
                        }}
                    }},
                    x: {{ grid: {{ display: false }}, ticks: {{ maxRotation: 45, minRotation: 45 }} }}
                }}
            }}
        }});
    </script>
</body>
</html>"""

    with open(output_file, 'w', encoding='utf-8') as f:
        f.write(html)
    print(f"✅ Reporte HTML guardado en: {output_file}")

def create_csv_report(benchmarks, scores, units, errors, normalized, output_file='benchmark-report.csv'):
    with open(output_file, 'w', encoding='utf-8') as f:
        f.write("Benchmark,Score,Formatted,Unit,Error,OriginalUnit\n")
        for b, s, n, e, u in zip(benchmarks, scores, normalized, errors, units):
            formatted, time_unit = format_time(n)
            f.write(f"{b},{s},{formatted},{time_unit},{e},{u}\n")
    print(f"✅ Reporte CSV guardado en: {output_file}")

def create_ascii_chart(benchmarks, normalized):
    """Crea gráfica ASCII con formato inteligente de unidades"""
    print("\n" + "═"*100)
    print("📊 RENDIMIENTO DE ALGORITMOS (Formato Inteligente)")
    print("═"*100)

    max_score = max(normalized) if normalized else 1
    max_bar_width = 60

    # Agrupar por tipo de benchmark (promediar si hay múltiples ejecuciones)
    benchmark_groups = {}
    for bench, score in zip(benchmarks, normalized):
        if bench not in benchmark_groups:
            benchmark_groups[bench] = []
        benchmark_groups[bench].append(score)

    # Calcular promedios
    benchmark_averages = {k: sum(v)/len(v) for k, v in benchmark_groups.items()}

    # Ordenar por tiempo (más rápido primero)
    sorted_benchmarks = sorted(benchmark_averages.items(), key=lambda x: x[1])

    for bench, avg_score in sorted_benchmarks:
        formatted, unit = format_time(avg_score)
        bar_length = int((avg_score / max_score) * max_bar_width) if max_score > 0 else 0
        bar = "█" * bar_length

        # Color coding según complejidad
        if 'Constant' in bench or 'Index' in bench or 'Even' in bench:
            prefix = "🟢"
        elif 'Logarithmic' in bench or 'Binary' in bench:
            prefix = "🔵"
        elif 'Linear' in bench:
            prefix = "🔷"
        elif 'Linearithmic' in bench or 'Sort' in bench and 'Bubble' not in bench and 'Selection' not in bench:
            prefix = "🟠"
        elif 'Quadratic' in bench or 'Bubble' in bench or 'Selection' in bench:
            prefix = "🔴"
        elif 'Exponential' in bench or 'Fibonacci' in bench or 'Hanoi' in bench:
            prefix = "🟣"
        else:
            prefix = "⚪"

        print(f"{prefix} {bench:<30} |{bar:<60}| {formatted:>12} {unit}")

    print("═"*100)
    print("\n📋 Leyenda: 🟢 O(1)  🔵 O(log n)  🔷 O(n)  🟠 O(n log n)  🔴 O(n²)  🟣 O(2^n)")
    print("═"*100 + "\n")

def main():
    json_files = ['jmh-results.json', 'target/jmh-results.json', 'results.json']
    json_file = next((f for f in json_files if os.path.exists(f)), None)

    if not json_file:
        print("❌ Error: No se encontró jmh-results.json")
        print("   Ejecuta: java -jar target/benchmarks.jar -rf json -rff jmh-results.json")
        return

    print(f"📂 Cargando resultados desde: {json_file}")
    data = load_results(json_file)
    print(f"📋 Tipo de datos JSON: {type(data).__name__}")

    benchmarks, scores, units, errors = extract_data(data)
    print(f"✅ {len(benchmarks)} benchmarks encontrados\n")

    normalized = normalize_to_microseconds(scores, units)

    # Gráfica ASCII en consola con formato mejorado
    create_ascii_chart(benchmarks, normalized)

    # Reporte HTML
    create_html_report(benchmarks, scores, units, errors, normalized)

    # Reporte CSV
    create_csv_report(benchmarks, scores, units, errors, normalized)

    # Resumen
    min_idx, max_idx = scores.index(min(scores)), scores.index(max(scores))
    min_formatted, min_unit = format_time(normalized[min_idx])
    max_formatted, max_unit = format_time(normalized[max_idx])

    print("\n📈 RESUMEN")
    print("-"*100)
    print(f"  Benchmark más rápido: {benchmarks[min_idx]} ({min_formatted} {min_unit})")
    print(f"  Benchmark más lento:  {benchmarks[max_idx]} ({max_formatted} {max_unit})")
    print(f"  Ratio lento/rápido:   {max(scores)/min(scores):,.0f}x")
    print("-"*100)
    print("\n🖼️  Para ver el reporte HTML:")
    print("   start benchmark-report.html\n")

if __name__ == '__main__':
    main()

