package Modelo;

import java.time.LocalDateTime;

public class Viaje {
    private String codigoViaje;
    private Estacion estacionOrigen;
    private Estacion estacionDestino;
    private LocalDateTime fechaHora;
    private double costo;
    private String tipoTarifa;

    public Viaje(String codigoViaje, Estacion estacionOrigen, 
                Estacion estacionDestino, double costo) {
        this.codigoViaje = codigoViaje;
        this.estacionOrigen = estacionOrigen;
        this.estacionDestino = estacionDestino;
        this.fechaHora = LocalDateTime.now();
        this.costo = costo;
        this.tipoTarifa = "Normal";
    }

    public String getCodigoViaje() {
        return codigoViaje;
    }

    public Estacion getEstacionOrigen() {
        return estacionOrigen;
    }

    public Estacion getEstacionDestino() {
        return estacionDestino;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public double getCosto() {
        return costo;
    }

    public String getTipoTarifa() {
        return tipoTarifa;
    }

    public void setTipoTarifa(String tipoTarifa) {
        this.tipoTarifa = tipoTarifa;
    }

    @Override
    public String toString() {
        return String.format("Viaje %s: %s -> %s ($%.2f)", 
                           codigoViaje, 
                           estacionOrigen.getNombre(),
                           estacionDestino.getNombre(),
                           costo);
    }
}
