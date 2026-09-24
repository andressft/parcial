package co.edu.usc.voltacali;

import co.edu.usc.voltacali.CargadorVE.*;

public class App {
    public static void main(String[] args) {
      
        int d1 = 0; 
        int d2 = 1; 
        int N = 10 * d1 + d2; // N = 1
        int r = N % 4;        // r = 1

        
        CargadorVE c1 = new CargadorVE("ABB", 2023, 400, TipoConector.CCS2, TipoCargador.RAPIDO_DC, 2, 2, 60.0, Ubicacion.UNIVERSIDAD);
        CargadorVE c2 = new CargadorVE("Siemens", 2022, 220, TipoConector.TIPO_2, TipoCargador.MURAL, 1, 1, 22.0, Ubicacion.CENTRO_COMERCIAL);
        CargadorVE c3 = new CargadorVE("Delta", 2024, 800, TipoConector.CCS2, TipoCargador.ULTRARRAPIDO, 2, 2, 150.0, Ubicacion.ESTACION_SERVICIO);
        CargadorVE c4 = new CargadorVE("Wallbox", 2021, 220, TipoConector.TIPO_2, TipoCargador.MURAL, 1, 1, 11.0, Ubicacion.RESIDENCIAL);
        CargadorVE c5 = new CargadorVE("Enel X", 2025, 22.0);

        CargadorVE[] flota = new CargadorVE[]{c1, c2, c3, c4, c5};

     
        c1.setPotenciaActual(40);
        System.out.println(String.format("[P01] Potencia C1: %.2f kW", c1.getPotenciaActual()));

        c1.aumentarPotencia(15);
        System.out.println(String.format("[P02] Potencia C1: %.2f kW", c1.getPotenciaActual()));

        System.out.println(String.format("[P03] Tiempo estimado (66 kWh): %.2f hrs", c1.tiempoEstimadoCarga(66)));

        c1.aumentarPotencia(10); // Rechazado
        System.out.println(String.format("[P04] Potencia C1: %.2f kW", c1.getPotenciaActual()));

        c1.reducirPotencia(30);
        System.out.println(String.format("[P05] Potencia C1: %.2f kW", c1.getPotenciaActual()));

        System.out.println(String.format("[P06] Tiempo estimado con pausas: %.2f hrs", c1.tiempoEstimadoCarga(50, 2, 15)));

        System.out.println(String.format("[P07] Tiempo estimado potencia programada: %.2f hrs", c1.tiempoEstimadoCarga(50, 40.0)));

        c1.aumentarPotencia();
        System.out.println(String.format("[P08] Potencia C1: %.2f kW", c1.getPotenciaActual()));

        c1.aumentarPotencia(5, 3);
        System.out.println(String.format("[P09] Potencia C1: %.2f kW", c1.getPotenciaActual()));

        c1.reducirPotencia(50); // Rechazado
        System.out.println(String.format("[P10] Potencia C1: %.2f kW", c1.getPotenciaActual()));

        c1.cortarCarga();
        System.out.println(String.format("[P11] Potencia C1: %.2f kW", c1.getPotenciaActual()));

        System.out.println(String.format("[P12] Tiempo estimado potencia cero: %.2f", c1.tiempoEstimadoCarga(10)));

        // ==========================================
        // Paso 3: Operaciones sobre el resto de la flota
        // ==========================================
        c2.setPotenciaActual(22);
        c3.setPotenciaActual(120);
        c4.aumentarPotencia(7.4);
        c5.aumentarPotencia(30); // Rechazado
        System.out.println(String.format("[P13] C2: %.2f kW | C3: %.2f kW | C4: %.2f kW | C5: %.2f kW",
                c2.getPotenciaActual(), c3.getPotenciaActual(), c4.getPotenciaActual(), c5.getPotenciaActual()));

        
        System.out.println("[P14] Contar por tipo:");
        int[] conteoTipos = CargadorVE.contarPorTipo(flota);
        for (int i = 0; i < TipoCargador.values().length; i++) {
            System.out.println("  " + TipoCargador.values()[i] + ": " + conteoTipos[i]);
        }

        System.out.println(String.format("[P15] Promedio potencia: %.2f kW", CargadorVE.promedioPotencia(flota)));

        CargadorVE mayor = CargadorVE.mayorPotencia(flota);
        System.out.println(String.format("[P16] Mayor potencia: %s (%.2f kW)", mayor.getFabricante(), mayor.getPotenciaActual()));

        System.out.println("[P17] Excesos de potencia contratada: " + CargadorVE.excesosDePotenciaContratada(flota));

        CargadorVE[] fConector = CargadorVE.filtrar(flota, TipoConector.TIPO_2);
        CargadorVE[] fTipo = CargadorVE.filtrar(flota, TipoCargador.MURAL);
        CargadorVE[] fUbi = CargadorVE.filtrar(flota, Ubicacion.UNIVERSIDAD);
        System.out.println(String.format("[P18] Filtros -> TIPO_2: %d | MURAL: %d | UNIVERSIDAD: %d",
                fConector.length, fTipo.length, fUbi.length));

        System.out.println("[P19] Valores C5 (Constructor Reducido):");
        c5.mostrar(false);

        CargadorVE copiaC3 = new CargadorVE(c3);
        System.out.println(String.format("[P20] Copia C3 -> Fab: %s | PotActual: %.2f | Bitácora Size: %d | Total Cargadores: %d",
                copiaC3.getFabricante(), copiaC3.getPotenciaActual(), copiaC3.getBitacora().size(), CargadorVE.getTotalCargadores()));

        System.out.println("[P21] C1 Detallado:");
        c1.mostrar(true);

        System.out.println("[P22] Contador global de registros: " + CargadorVE.contadorRegistros);

        System.out.println("[P23] Pruebas de robustez:");
        double promNull = CargadorVE.promedioPotencia(new CargadorVE[]{c1, null, c3});
        int[] conteoNull = CargadorVE.contarPorTipo(null);
        System.out.println(String.format("  Promedio con null: %.2f kW | Conteo con null: %d elementos", promNull, conteoNull.length));

        
        System.out.println(String.format("[R] Ruta Individual -> N = %d | r = %d", N, r));
        switch (r) {
            case 0:
                int conectoresBuscados = (N % 3) + 1;
                CargadorVE[] resConn = CargadorVE.cargadoresPorConectores(flota, conectoresBuscados);
                System.out.println("Cargadores con " + conectoresBuscados + " conectores: " + resConn.length);
                break;
            case 1:
                TipoConector conBuscado = TipoConector.values()[N % 5]; // TIPO_2
                double promVolt = CargadorVE.promedioVoltajePorConector(flota, conBuscado);
                System.out.println(String.format("Promedio voltaje para %s: %.2f V", conBuscado, promVolt));
                break;
            case 2:
                CargadorVE.tipoMasFrecuente(flota);
                break;
            case 3:
                CargadorVE.sesionesSobreLimiteRed(flota);
                break;
        }

       
        String fabC6 = "USC-" + N;
        int anioC6 = 2015 + d2;
        int voltC6 = (N % 2 == 0) ? 220 : 400;
        TipoConector tcC6 = TipoConector.values()[N % 5];
        TipoCargador tcarC6 = TipoCargador.values()[N % 6];
        int numConnC6 = (d1 % 3) + 1;
        int puestosC6 = (d2 % 4) + 1;
        double potMaxC6 = 20.0 + N;
        Ubicacion ubiC6 = Ubicacion.values()[N % 8];

        CargadorVE c6 = new CargadorVE(fabC6, anioC6, voltC6, tcC6, tcarC6, numConnC6, puestosC6, potMaxC6, ubiC6);

        System.out.println(String.format("[X01] C6 Datos Calculados (N=%d, d1=%d, d2=%d):", N, d1, d2));
        c6.mostrar(false);

        c6.setPotenciaActual(c6.getPotenciaMaxima() / 2.0);
        boolean todoPaso = c6.aumentarPotencia(d2 + 5, d1 + 1);
        System.out.println(String.format("[X02] Potencia C6 Final: %.2f kW | ¿Todos los pasos válidos?: %b", c6.getPotenciaActual(), todoPaso));

        System.out.println(String.format("[X03] Tiempo estimado C6: %.2f hrs", c6.tiempoEstimadoCarga(N + 10)));

        CargadorVE[] flotaExtendida = new CargadorVE[flota.length + 1];
        for (int i = 0; i < flota.length; i++) {
            flotaExtendida[i] = flota[i];
        }
        flotaExtendida[flota.length] = c6;
        System.out.println("[X04] Flota extendida creada con éxito. Tamaño: " + flotaExtendida.length);

        System.out.println("[X05] Estadísticas de Flota Extendida:");
        System.out.println(String.format("  Promedio potencia: %.2f kW", CargadorVE.promedioPotencia(flotaExtendida)));
        System.out.println("  Mayor potencia: " + CargadorVE.mayorPotencia(flotaExtendida).getFabricante());
        System.out.println("  Excesos sobre límite: " + CargadorVE.excesosDePotenciaContratada(flotaExtendida));

        System.out.println("[X06] Estado Global Final e Inspección C6:");
        System.out.println(" Total Cargadores: " + CargadorVE.getTotalCargadores());
        System.out.println(" Total Registros: " + CargadorVE.contadorRegistros);
        c6.mostrar(true);
    }
}