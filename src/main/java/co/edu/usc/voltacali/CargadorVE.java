package co.edu.usc.voltacali;

import java.util.Vector;

public class CargadorVE {

    // Parte D.a: Enums anidados
    public enum TipoConector {
        TIPO_1, TIPO_2, CCS2, CHADEMO, GBT
    }

    public enum TipoCargador {
        MURAL, PEDESTAL, RAPIDO_DC, ULTRARRAPIDO, PORTATIL, BIDIRECCIONAL_V2G
    }

    public enum Ubicacion {
        CENTRO_COMERCIAL, UNIVERSIDAD, ESTACION_SERVICIO, PARQUEADERO_PUBLICO, RESIDENCIAL, HOTEL, TERMINAL, FLOTA_CORPORATIVA
    }

    // Parte D.e: Miembros estáticos
    public static int totalCargadores = 0;
    public static int contadorRegistros = 0;
    public static final double LIMITE_RED = 50.0;
    public static final double INCREMENTO_DEFECTO = 5.0;

    // Parte A: Atributos privados
    private String fabricante;
    private int anioInstalacion;
    private int voltajeNominal;
    private TipoConector tipoConector;
    private TipoCargador tipoCargador;
    private int numeroConectores;
    private int puestosParqueo;
    private double potenciaMaxima;
    private Ubicacion ubicacion;
    private double potenciaActual;

    // Parte D.c: Bitácora
    private Vector<RegistroSesion> bitacora;

    // Parte D.b: Clase interna no estática RegistroSesion
    public class RegistroSesion {
        private int idRegistro;
        private String evento;
        private boolean valido;
        private String fabricanteSnap;
        private int anioInstalacionSnap;
        private double potenciaActualSnap;

        public RegistroSesion(String evento, boolean valido) {
            contadorRegistros++;
            this.idRegistro = contadorRegistros;
            this.evento = evento;
            this.valido = valido;
            // Captura atributos del objeto externo directamente
            this.fabricanteSnap = fabricante;
            this.anioInstalacionSnap = anioInstalacion;
            this.potenciaActualSnap = potenciaActual;
        }

        public boolean isValido() {
            return valido;
        }

        public double getPotenciaActualSnap() {
            return potenciaActualSnap;
        }

        public String describir() {
            return String.format("Reg #%d | Válido: %b | Evento: %s | Fab: %s | Año: %d | Potencia: %.2f kW",
                    idRegistro, valido, evento, fabricanteSnap, anioInstalacionSnap, potenciaActualSnap);
        }
    }

    // Parte C: Constructores sobrecargados
    public CargadorVE(String fabricante, int anioInstalacion, int voltajeNominal, TipoConector tipoConector,
                      TipoCargador tipoCargador, int numeroConectores, int puestosParqueo,
                      double potenciaMaxima, Ubicacion ubicacion) {
        this.fabricante = fabricante;
        this.anioInstalacion = anioInstalacion;
        this.voltajeNominal = voltajeNominal;
        this.tipoConector = tipoConector;
        this.tipoCargador = tipoCargador;
        this.numeroConectores = numeroConectores;
        this.puestosParqueo = puestosParqueo;
        this.potenciaMaxima = potenciaMaxima;
        this.ubicacion = ubicacion;
        this.potenciaActual = 0.0;
        this.bitacora = new Vector<>();
        totalCargadores++;
    }

    public CargadorVE(String fabricante, int anioInstalacion, double potenciaMaxima) {
        this(fabricante, anioInstalacion, 220, TipoConector.TIPO_2, TipoCargador.PEDESTAL, 
             1, 1, potenciaMaxima, Ubicacion.PARQUEADERO_PUBLICO);
    }

    public CargadorVE(CargadorVE otro) {
        if (otro != null) {
            this.fabricante = otro.fabricante;
            this.anioInstalacion = otro.anioInstalacion;
            this.voltajeNominal = otro.voltajeNominal;
            this.tipoConector = otro.tipoConector;
            this.tipoCargador = otro.tipoCargador;
            this.numeroConectores = otro.numeroConectores;
            this.puestosParqueo = otro.puestosParqueo;
            this.potenciaMaxima = otro.potenciaMaxima;
            this.ubicacion = otro.ubicacion;
            this.potenciaActual = 0.0;
            this.bitacora = new Vector<>();
            totalCargadores++;
        }
    }

    // Parte A: Getters y Setters
    public String getFabricante() { return fabricante; }
    public void setFabricante(String fabricante) { this.fabricante = fabricante; }

    public int getAnioInstalacion() { return anioInstalacion; }
    public void setAnioInstalacion(int anioInstalacion) { this.anioInstalacion = anioInstalacion; }

    public int getVoltajeNominal() { return voltajeNominal; }
    public void setVoltajeNominal(int voltajeNominal) { this.voltajeNominal = voltajeNominal; }

    public TipoConector getTipoConector() { return tipoConector; }
    public void setTipoConector(TipoConector tipoConector) { this.tipoConector = tipoConector; }

    public TipoCargador getTipoCargador() { return tipoCargador; }
    public void setTipoCargador(TipoCargador tipoCargador) { this.tipoCargador = tipoCargador; }

    public int getNumeroConectores() { return numeroConectores; }
    public void setNumeroConectores(int numeroConectores) { this.numeroConectores = numeroConectores; }

    public int getPuestosParqueo() { return puestosParqueo; }
    public void setPuestosParqueo(int puestosParqueo) { this.puestosParqueo = puestosParqueo; }

    public double getPotenciaMaxima() { return potenciaMaxima; }
    public void setPotenciaMaxima(double potenciaMaxima) { this.potenciaMaxima = potenciaMaxima; }

    public Ubicacion getUbicacion() { return ubicacion; }
    public void setUbicacion(Ubicacion ubicacion) { this.ubicacion = ubicacion; }

    public double getPotenciaActual() { return potenciaActual; }

    public void setPotenciaActual(double potenciaActual) {
        if (potenciaActual < 0 || potenciaActual > potenciaMaxima) {
            System.out.println("Error: Potencia actual fuera de rango permitido (0 - " + potenciaMaxima + " kW).");
            bitacora.add(new RegistroSesion("Intento de cambio a potencia inválida: " + potenciaActual, false));
        } else {
            this.potenciaActual = potenciaActual;
            bitacora.add(new RegistroSesion("Cambio de potencia actual a " + potenciaActual, true));
        }
    }

    public Vector<RegistroSesion> getBitacora() { return bitacora; }

    // Parte B & C: Métodos de Modificación de Potencia
    public void aumentarPotencia() {
        aumentarPotencia(INCREMENTO_DEFECTO);
    }

    public void aumentarPotencia(double incremento) {
        double nuevaPotencia = potenciaActual + incremento;
        if (nuevaPotencia < 0 || nuevaPotencia > potenciaMaxima) {
            System.out.println("Error: Aumento no permitido. Excede potencia máxima o es negativo.");
            bitacora.add(new RegistroSesion("Intento inválido de aumentar potencia en " + incremento, false));
        } else {
            this.potenciaActual = nuevaPotencia;
            bitacora.add(new RegistroSesion("Aumento de potencia en " + incremento, true));
        }
    }

    public boolean aumentarPotencia(double incremento, int veces) {
        boolean todoExitoso = true;
        for (int i = 0; i < veces; i++) {
            double nuevaPotencia = potenciaActual + incremento;
            if (nuevaPotencia < 0 || nuevaPotencia > potenciaMaxima) {
                System.out.println("Error en paso " + (i + 1) + ": Incremento detenido.");
                bitacora.add(new RegistroSesion("Paso " + (i + 1) + " de aumento por " + incremento + " fue rechazado.", false));
                todoExitoso = false;
                break;
            } else {
                this.potenciaActual = nuevaPotencia;
                bitacora.add(new RegistroSesion("Paso " + (i + 1) + " de aumento: potencia actual " + potenciaActual, true));
            }
        }
        return todoExitoso;
    }

    public void reducirPotencia(double reduccion) {
        double nuevaPotencia = potenciaActual - reduccion;
        if (nuevaPotencia < 0 || nuevaPotencia > potenciaMaxima) {
            System.out.println("Error: Reducción no permitida. Resultado negativo o fuera de rango.");
            bitacora.add(new RegistroSesion("Intento inválido de reducir potencia en " + reduccion, false));
        } else {
            this.potenciaActual = nuevaPotencia;
            bitacora.add(new RegistroSesion("Reducción de potencia en " + reduccion, true));
        }
    }

    public void cortarCarga() {
        this.potenciaActual = 0.0;
        bitacora.add(new RegistroSesion("Corte de carga ejecutado", true));
    }

    // Parte B & C: Métodos de Cálculo de Tiempo
    public double tiempoEstimadoCarga(double energiaKWh) {
        if (this.potenciaActual == 0) {
            System.out.println("Error: Potencia actual es 0 kW.");
            return -1.0;
        }
        return energiaKWh / this.potenciaActual;
    }

    public double tiempoEstimadoCarga(double energiaKWh, double potenciaProgramada) {
        if (potenciaProgramada <= 0) {
            System.out.println("Error: Potencia programada inválida.");
            return -1.0;
        }
        return energiaKWh / potenciaProgramada;
    }

    public double tiempoEstimadoCarga(double energiaKWh, int pausas, double minutosPorPausa) {
        double tiempoBase = tiempoEstimadoCarga(energiaKWh);
        if (tiempoBase == -1.0) return -1.0;
        double tiempoPausasHoras = (pausas * minutosPorPausa) / 60.0;
        return tiempoBase + tiempoPausasHoras;
    }

    // Parte B & C: Métodos de Visualización
    public void mostrar() {
        mostrar(false);
    }

    public void mostrar(boolean detallado) {
        System.out.println("=== CargadorVE ===");
        System.out.println("Fabricante: " + fabricante);
        System.out.println("Año Instalación: " + anioInstalacion);
        System.out.println("Voltaje Nominal: " + voltajeNominal + " V");
        System.out.println("Tipo Conector: " + tipoConector);
        System.out.println("Tipo Cargador: " + tipoCargador);
        System.out.println("Número Conectores: " + numeroConectores);
        System.out.println("Puestos Parqueo: " + puestosParqueo);
        System.out.println("Potencia Máxima: " + potenciaMaxima + " kW");
        System.out.println("Ubicación: " + ubicacion);
        System.out.println("Potencia Actual: " + potenciaActual + " kW");
        if (detallado) {
            System.out.println("--- Bitácora (" + bitacora.size() + " registros) ---");
            for (RegistroSesion r : bitacora) {
                System.out.println("  " + r.describir());
            }
        }
    }

    // Parte D.e: Métodos Estáticos
    public static int getTotalCargadores() {
        return totalCargadores;
    }

    public static int[] contarPorTipo(CargadorVE[] flota) {
        int[] conteos = new int[TipoCargador.values().length];
        if (flota == null) return conteos;
        for (CargadorVE c : flota) {
            if (c != null && c.getTipoCargador() != null) {
                conteos[c.getTipoCargador().ordinal()]++;
            }
        }
        return conteos;
    }

    public static double promedioPotencia(CargadorVE[] flota) {
        if (flota == null) return 0.0;
        double suma = 0.0;
        int cuenta = 0;
        for (CargadorVE c : flota) {
            if (c != null) {
                suma += c.getPotenciaActual();
                cuenta++;
            }
        }
        return cuenta == 0 ? 0.0 : suma / cuenta;
    }

    public static CargadorVE mayorPotencia(CargadorVE[] flota) {
        if (flota == null) return null;
        CargadorVE mayor = null;
        for (CargadorVE c : flota) {
            if (c != null) {
                if (mayor == null || c.getPotenciaActual() > mayor.getPotenciaActual()) {
                    mayor = c;
                }
            }
        }
        return mayor;
    }

    public static int excesosDePotenciaContratada(CargadorVE[] flota) {
        if (flota == null) return 0;
        int conteo = 0;
        for (CargadorVE c : flota) {
            if (c != null && c.getBitacora() != null) {
                for (RegistroSesion r : c.getBitacora()) {
                    if (r.isValido() && r.getPotenciaActualSnap() > LIMITE_RED) {
                        conteo++;
                    }
                }
            }
        }
        return conteo;
    }

    // Parte C: Filtrado estático sobrecargado
    public static CargadorVE[] filtrar(CargadorVE[] flota, TipoConector conector) {
        if (flota == null) return new CargadorVE[0];
        int coincidencias = 0;
        for (CargadorVE c : flota) {
            if (c != null && c.getTipoConector() == conector) coincidencias++;
        }
        CargadorVE[] resultado = new CargadorVE[coincidencias];
        int idx = 0;
        for (CargadorVE c : flota) {
            if (c != null && c.getTipoConector() == conector) {
                resultado[idx++] = c;
            }
        }
        return resultado;
    }

    public static CargadorVE[] filtrar(CargadorVE[] flota, TipoCargador tipo) {
        if (flota == null) return new CargadorVE[0];
        int coincidencias = 0;
        for (CargadorVE c : flota) {
            if (c != null && c.getTipoCargador() == tipo) coincidencias++;
        }
        CargadorVE[] resultado = new CargadorVE[coincidencias];
        int idx = 0;
        for (CargadorVE c : flota) {
            if (c != null && c.getTipoCargador() == tipo) {
                resultado[idx++] = c;
            }
        }
        return resultado;
    }

    public static CargadorVE[] filtrar(CargadorVE[] flota, Ubicacion ubicacion) {
        if (flota == null) return new CargadorVE[0];
        int coincidencias = 0;
        for (CargadorVE c : flota) {
            if (c != null && c.getUbicacion() == ubicacion) coincidencias++;
        }
        CargadorVE[] resultado = new CargadorVE[coincidencias];
        int idx = 0;
        for (CargadorVE c : flota) {
            if (c != null && c.getUbicacion() == ubicacion) {
                resultado[idx++] = c;
            }
        }
        return resultado;
    }

    // Parte E: Métodos Auxiliares para Rutas Individuales (r = 0, 1, 2, 3)
    public static CargadorVE[] cargadoresPorConectores(CargadorVE[] flota, int conectores) {
        if (flota == null) return new CargadorVE[0];
        int cont = 0;
        for (CargadorVE c : flota) {
            if (c != null && c.getNumeroConectores() == conectores) cont++;
        }
        CargadorVE[] res = new CargadorVE[cont];
        int idx = 0;
        for (CargadorVE c : flota) {
            if (c != null && c.getNumeroConectores() == conectores) {
                res[idx++] = c;
            }
        }
        return res;
    }

    public static double promedioVoltajePorConector(CargadorVE[] flota, TipoConector conector) {
        if (flota == null) return 0.0;
        double suma = 0;
        int cont = 0;
        for (CargadorVE c : flota) {
            if (c != null && c.getTipoConector() == conector) {
                suma += c.getVoltajeNominal();
                cont++;
            }
        }
        return cont == 0 ? 0.0 : suma / cont;
    }

    public static void tipoMasFrecuente(CargadorVE[] flota) {
        int[] conteos = contarPorTipo(flota);
        int max = -1;
        for (int c : conteos) {
            if (c > max) max = c;
        }
        System.out.println("Frecuencia máxima de cargador: " + max);
        System.out.print("Tipos más frecuentes: ");
        for (int i = 0; i < conteos.length; i++) {
            if (conteos[i] == max && max > 0) {
                System.out.print(TipoCargador.values()[i] + " ");
            }
        }
        System.out.println();
    }

    public static void sesionesSobreLimiteRed(CargadorVE[] flota) {
        if (flota == null) {
            System.out.println("No hay datos de flota.");
            return;
        }
        boolean hay = false;
        for (CargadorVE c : flota) {
            if (c != null && c.getBitacora() != null) {
                for (RegistroSesion r : c.getBitacora()) {
                    if (r.isValido() && r.getPotenciaActualSnap() > LIMITE_RED) {
                        System.out.println(r.describir());
                        hay = true;
                    }
                }
            }
        }
        if (!hay) {
            System.out.println("No hay sesiones válidas que superen el límite de red (" + LIMITE_RED + " kW).");
        }
    }
}