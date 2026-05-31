package Modelo;

import Estrucuturas.ListaEnlazada;

public class ConsultaRuta {
    
    private Estacion estacionOrigen;
    private Estacion estacionDestino;
    private ListaEnlazada<Estacion> rutaOptima;
    private double distanciaTotal;
    private int tiempoEstimado;
    private double costoTotal;
    private int cantidadTransbordos;


    public ConsultaRuta(Estacion estacionOrigen, Estacion estacionDestino) {
        this.estacionOrigen = estacionOrigen;
        this.estacionDestino = estacionDestino;
        this.rutaOptima = new ListaEnlazada<>();
        this.distanciaTotal = 0.0;
        this.tiempoEstimado = 0;
        this.costoTotal = 0.0;
        this.cantidadTransbordos = 0;
    }

    public Estacion getEstacionOrigen() {
        return estacionOrigen;
    }

    public Estacion getEstacionDestino() {
        return estacionDestino;
    }

    public ListaEnlazada<Estacion> getRutaOptima() {
        return rutaOptima;
    }

    public void setRutaOptima(ListaEnlazada<Estacion> rutaOptima) {
        this.rutaOptima = rutaOptima;
        calcularMetricas();
    }

    public double getDistanciaTotal() {
        return distanciaTotal;
    }

    public int getTiempoEstimado() {
        return tiempoEstimado;
    }

    public double getCostoTotal() {
        return costoTotal;
    }

    public int getCantidadTransbordos() {
        return cantidadTransbordos;
    }

    private void calcularMetricas() {
        this.distanciaTotal = 0.0;
        this.cantidadTransbordos = 0;
        
        String lineaAnterior = null;
        
        for (int i = 0; i < rutaOptima.getTamaño(); i++) { 
            Estacion estacion = rutaOptima.obtener(i);
            
            if (i > 0) {
                Estacion estacionAnterior = rutaOptima.obtener(i - 1);
                distanciaTotal += estacionAnterior.calcularDistanciaA(estacion);
            }
            
            if (lineaAnterior != null && !lineaAnterior.equals(estacion.getLinea())) {
                cantidadTransbordos++;
            }
            lineaAnterior = estacion.getLinea();
        }
        
        this.tiempoEstimado = (rutaOptima.getTamaño() * 3) + (cantidadTransbordos * 5); 
        
        this.costoTotal = calcularCostoRuta();
    }


    private double calcularCostoRuta() {
        double tarifaBase = 2500; 
        double costoTransbordo = 0; 
        
        return tarifaBase + (cantidadTransbordos * costoTransbordo);
    }

    public ListaEnlazada<String> obtenerLineasUtilizadas() {
        ListaEnlazada<String> lineas = new ListaEnlazada<>();
        
        for (int i = 0; i < rutaOptima.getTamaño(); i++) {  
            Estacion estacion = rutaOptima.obtener(i);
            String linea = estacion.getLinea();
            
            if (!lineas.contiene(linea)) {
                lineas.agregar(linea);
            }
        }
        
        return lineas;
    }

    public String generarInstruccionesRuta() {
        StringBuilder sb = new StringBuilder();
        sb.append(" INSTRUCCIONES DE RUTA \n\n");
        sb.append("Origen: ").append(estacionOrigen.getNombre()).append("\n");
        sb.append("Destino: ").append(estacionDestino.getNombre()).append("\n\n");
        
        sb.append("Recorrido:\n");
        String lineaActual = null;
        
        for (int i = 0; i < rutaOptima.getTamaño(); i++) { 
            Estacion estacion = rutaOptima.obtener(i);
            
            if (!estacion.getLinea().equals(lineaActual)) {
                if (lineaActual != null) {
                    sb.append("   Transbordo a Linea ").append(estacion.getLinea()).append("\n");
                } else {
                    sb.append("   Iniciar en Linea ").append(estacion.getLinea()).append("\n");
                }
                lineaActual = estacion.getLinea();
            }
            
            sb.append("     ").append(i + 1).append(". ").append(estacion.getNombre()).append("\n");
        }
        
        sb.append("\nResumen:\n");
        sb.append("  - Estaciones: ").append(rutaOptima.getTamaño()).append("\n");
        sb.append("  - Distancia: ").append(String.format("-", distanciaTotal)).append(" km\n");
        sb.append("  - Tiempo estimado: ").append(tiempoEstimado).append(" minutos\n");
        sb.append("  - Transbordos: ").append(cantidadTransbordos).append("\n");
        sb.append("  - Costo: $").append(String.format("-", costoTotal)).append("\n");
        
        return sb.toString();
    }

    @Override
    public String toString() {
        return String.format("Ruta: %s -> %s | Estaciones: %d | Tiempo: %d min | Costo: $%.0f",
                           estacionOrigen.getNombre(),
                           estacionDestino.getNombre(),
                           rutaOptima.getTamaño(),
                           tiempoEstimado,
                           costoTotal);
    }
}
