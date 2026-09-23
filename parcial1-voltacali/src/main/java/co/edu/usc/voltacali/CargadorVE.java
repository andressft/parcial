package co.edu.usc.voltacali;




public class CargadorVE {

    public enum TipoConector {TIPO_1, TIPO_2, CCS2, CHADEMO, GBT}
    public enum TipoCargador {MURAL, PEDESTAL, RAPIDO_DC, ULTRARRAPIDO, PORTATIL, BIDIRECCIONAL_V2G}
    public enum tipoUbicacion {CENTRO_COMERCIAL, UNIVERSIDAD, ESTACION_SERVICIO, PARQUEADERO_PUBLICO, RESIDENCIAL, HOTEL, TERMINAL, FLOTA_CORPORATIVA}

    public static int totalCargadores = 0;
    public static int totalPuestosParqueo = 0;
    public static double LIMITE_RED = 50.0;
    public static double INCREMENTO_DEFECTO = 5.0;


    private String fabricante;
    private int anioInstalacion;
    private int voltajeNominal;
    private TipoConector tipoConector;
    private TipoCargador tipoCargador;
    private int numeroConectores;
    private int puestosParqueo;
    private double potenciaMaxima;
    private tipoUbicacion ubicacion;
    private double potenciaActual;

    private Vector<RegistroSesion> bitacora;

    public class RegistroSesion {
        private int idRegsistro;
        private String evento;
        private boolean valido;
        private String fabricanteSnap;
        private int anioInstalacionSnap;
        private double potenciaActualSnap;
        

        public RegistroSesion(String fechaHora, double energiaConsumida, double tiempoCarga) {
            this.fechaHora = fechaHora;
            this.energiaConsumida = energiaConsumida;
            this.tiempoCarga = tiempoCarga;
        }

        public String getFechaHora() {
            return fechaHora;
        }

        public double getEnergiaConsumida() {
            return energiaConsumida;
        }

        public double getTiempoCarga() {
            return tiempoCarga;
        }
    }


    

    
    
    
    
    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public int getAnioInstalacion() {
        return anioInstalacion;
    }

    public void setAnioInstalacion(int anioInstalacion) {
        this.anioInstalacion = anioInstalacion;
    }

    public int getVoltajeNominal() {
        return voltajeNominal;
    }

    public void setVoltajeNominal(int voltajeNominal) {
        this.voltajeNominal = voltajeNominal;
    }

    public TipoConector getTipoConector() {
        return tipoConector;
    }

    public void setTipoConector(TipoConector tipoConector) {
        this.tipoConector = tipoConector;
    }

    public TipoCargador getTipoCargador() {
        return tipoCargador;
    }

    public void setTipoCargador(TipoCargador tipoCargador) {
        this.tipoCargador = tipoCargador;
    }

    public int getNumeroConectores() {
        return numeroConectores;
    }

    public void setNumeroConectores(int numeroConectores) {
        this.numeroConectores = numeroConectores;
    }

    public int getPuestosParqueo() {
        return puestosParqueo;
    }

    public void setPuestosParqueo(int puestosParqueo) {
        this.puestosParqueo = puestosParqueo;
    }

    public double getPotenciaMaxima() {
        return potenciaMaxima;
    }

    public void setPotenciaMaxima(double potenciaMaxima) {
        this.potenciaMaxima = potenciaMaxima;
    }

    public tipoUbicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(tipoUbicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public double getPotenciaActual() {
        return potenciaActual;
    }

    public void setPotenciaActual(double potenciaActual) {
        this.potenciaActual = potenciaActual;
    }


    ///parteB
    


    public double TiempoestimadoCarga(double energiaKWh) {
        if (this.potenciaActual == 0) {
            System.out.println("La potencia actual es 0KWh");
            return -1.0;
        }
        return energiaKWh / this.potenciaActual;
    }
    
    public double TiempoestimadoCarga(double energiaKWh,double potenciaProgramada) {
        if (this.potenciaProgramada <= 0) {
            System.out.println("Error:La potencia programada es invalida");
            return -1.0;
        }
        return energiaKWh / this.potenciaActual;
    }

    public double EnergiaConsumida(double tiempoCarga) {
        if (tiempoCarga < 0) {
            throw new IllegalArgumentException("El tiempo de carga no puede ser negativo.");
        }
        return potenciaActual * tiempoCarga;
    }

    public double CostoCarga(double energiaConsumida, double costoKWh) {
        if (energiaConsumida < 0 || costoKWh < 0) {
            throw new IllegalArgumentException("La energía consumida y el costo por kWh no pueden ser negativos.");
        }
        return energiaConsumida * costoKWh;
    }


}