package Modelo;

import Estrucuturas.ListaEnlazada;

public class LineaMetro {
    
    private String codigo;
    private String nombre;
    private String color;
    private ListaEnlazada<Estacion> estaciones;
    private int tiempoPromedioViaje; // en minutos

    public LineaMetro(String codigo, String nombre, String color, int tiempoPromedioViaje) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.color = color;
        this.tiempoPromedioViaje = tiempoPromedioViaje;
        this.estaciones = new ListaEnlazada<>();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public ListaEnlazada<Estacion> getEstaciones() {
        return estaciones;
    }

    public int getTiempoPromedioViaje() {
        return tiempoPromedioViaje;
    }

    public void setTiempoPromedioViaje(int tiempoPromedioViaje) {
        this.tiempoPromedioViaje = tiempoPromedioViaje;
    }

    public void agregarEstacion(Estacion estacion) {
        if (estacion != null && !estaciones.contiene(estacion)) {
            estaciones.agregar(estacion);
        }
    }

    public boolean eliminarEstacion(Estacion estacion) {
        return estaciones.eliminarElemento(estacion); 
    }

    public Estacion buscarEstacionPorCodigo(String codigoEstacion) {
        for (int i = 0; i < estaciones.getTamaño(); i++) { 
            Estacion estacion = estaciones.obtener(i);
            if (estacion.getCodigo().equals(codigoEstacion)) {
                return estacion;
            }
        }
        return null;
    }

    public Estacion buscarEstacionPorNombre(String nombreEstacion) {
        for (int i = 0; i < estaciones.getTamaño(); i++) {  
            Estacion estacion = estaciones.obtener(i);
            if (estacion.getNombre().equalsIgnoreCase(nombreEstacion)) {
                return estacion;
            }
        }
        return null;
    }

    public int getCantidadEstaciones() {
        return estaciones.getTamaño(); 
    }

    public boolean contieneEstacion(Estacion estacion) {
        return estaciones.contiene(estacion);
    }

    public int calcularTiempoTotalRecorrido() {
        int cantidadEstaciones = estaciones.getTamaño();
        return cantidadEstaciones * tiempoPromedioViaje;
    }

    public Estacion getPrimeraEstacion() {
        if (estaciones.estaVacia()) {
            return null;
        }
        return estaciones.obtener(0);
    }

    public Estacion getUltimaEstacion() {
        if (estaciones.estaVacia()) {
            return null;
        }
        return estaciones.obtener(estaciones.getTamaño() - 1);
    }

    @Override
    public String toString() {
        return String.format("Línea %s - %s (%s) - %d estaciones", 
                           codigo, nombre, color, estaciones.getTamaño());
    }
} 
