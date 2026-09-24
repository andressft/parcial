# Parcial 1 · Sistema de gestión de cargadores para vehículos eléctricos

**Universidad Santiago de Cali** · Facultad de Ingeniería · Algoritmia y Programación 2 · Programación Orientada a Objetos

- **Nombre:** Andrés Julián Jiménez
- **Código:** 1107851401
- **N:** 1 (d1 = 0, d2 = 1)
- **r:** N mod 4 = 1
- **Ruta asignada:** r = 1 → `promedioVoltajePorConector(CargadorVE[] flota, TipoConector conector)`, usando el conector cuyo `ordinal()` es N mod 5 (TIPO_2).

## Parte F — Atributos calculados de C6 (con N = 1, d1 = 0, d2 = 1)

| Atributo | Regla | Valor calculado |
|---|---|---|
| fabricante | `"USC-" + N` | USC-1 |
| anioInstalacion | `2015 + d2` | 2016 |
| voltajeNominal | 220 si N par, 400 si N impar | 400 |
| tipoConector | `TipoConector.values()[N % 5]` | TIPO_2 |
| tipoCargador | `TipoCargador.values()[N % 6]` | PEDESTAL |
| numeroConectores | `d1 % 3 + 1` | 1 |
| puestosParqueo | `d2 % 4 + 1` | 2 |
| potenciaMaxima | `20 + N` | 21.0 kW |
| ubicacion | `Ubicacion.values()[N % 8]` | UNIVERSIDAD |

## Compilación y ejecución

Desde la raíz del repositorio:

```bash
mvn clean compile
java -cp target/classes co.edu.usc.voltacali.App
```

Para correr las pruebas unitarias:

```bash
mvn test
```

## Estructura del proyecto

```
parcial/
├── pom.xml
├── .gitignore
├── README.md
├── src/
│   ├── main/java/co/edu/usc/voltacali/
│   │   ├── App.java
│   │   └── CargadorVE.java
│   └── test/java/co/edu/usc/voltacali/
│       └── AppTest.java
└── docs/
    ├── capturaparcial.png
    └── capturaparcial2.png
```
