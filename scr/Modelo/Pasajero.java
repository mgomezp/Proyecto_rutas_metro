package Modelo;

import Estrucuturas.ListaEnlazada;

public class Pasajero {
    
    private String documento;
    private String nombre;
    private String apellido;
    private String correoElectronico;
    private String telefono;
    private boolean esPersonaDiscapacitada;
    private boolean esAdultoMayor;
    private double saldoTarjeta;
    private ListaEnlazada<Viaje> historialViajes;

    public Pasajero(String documento, String nombre, String apellido,
                   String correoElectronico, String telefono,
                   boolean esPersonaDiscapacitada, boolean esAdultoMayor) {
        this.documento = documento;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correoElectronico = correoElectronico;
        this.telefono = telefono;
        this.esPersonaDiscapacitada = esPersonaDiscapacitada;
        this.esAdultoMayor = esAdultoMayor;
        this.saldoTarjeta = 0.0;
        this.historialViajes = new ListaEnlazada<>();
    }

    public Pasajero(String documento, String nombre, String apellido) {
        this(documento, nombre, apellido, "", "", false, false);
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public boolean isEsPersonaDiscapacitada() {
        return esPersonaDiscapacitada;
    }

    public void setEsPersonaDiscapacitada(boolean esPersonaDiscapacitada) {
        this.esPersonaDiscapacitada = esPersonaDiscapacitada;
    }

    public boolean isEsAdultoMayor() {
        return esAdultoMayor;
    }

    public void setEsAdultoMayor(boolean esAdultoMayor) {
        this.esAdultoMayor = esAdultoMayor;
    }

    public double getSaldoTarjeta() {
        return saldoTarjeta;
    }

    public ListaEnlazada<Viaje> getHistorialViajes() {
        return historialViajes;
    }

    public boolean recargarTarjeta(double monto) {
        if (monto > 0) {
            saldoTarjeta += monto;
            return true;
        }
        return false;
    }

    public boolean descontarSaldo(double monto) {
        if (monto >= 0 && saldoTarjeta >= monto) {
            saldoTarjeta -= monto;
            return true;
        }
        return false;
    }

    public void registrarViaje(Viaje viaje) {
        if (viaje != null) {
            historialViajes.agregar(viaje);
        }
    }

    public int getTotalViajes() {
        return historialViajes.getTamaño();  
    }

    public double calcularGastoTotal() {
        double gastoTotal = 0.0;
        for (int i = 0; i < historialViajes.getTamaño(); i++) { 
            Viaje viaje = historialViajes.obtener(i);
            gastoTotal += viaje.getCosto();
        }
        return gastoTotal;
    }

    public boolean tieneTarifaReducida() {
        return esAdultoMayor || esPersonaDiscapacitada;
    }

    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    public boolean tieneSaldoSuficiente(double costoViaje) {
        return saldoTarjeta >= costoViaje;
    }

    @Override
    public String toString() {
        return String.format("%s %s (Doc: %s) - Saldo: $%.2f", 
                           nombre, apellido, documento, saldoTarjeta);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Pasajero pasajero = (Pasajero) obj;
        return documento.equals(pasajero.documento);
    }

    @Override
    public int hashCode() {
        return documento.hashCode();
    }
}
