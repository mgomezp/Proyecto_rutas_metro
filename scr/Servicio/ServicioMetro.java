package Servicio;

import Estrucuturas.*;
import Modelo.*;
import java.util.Random;

public class ServicioMetro {

    private Grafo<Estacion> redMetro;                    
    private TablaHash<String, Estacion> estacionesMap;   
    private TablaHash<String, LineaMetro> lineasMap;     
    private ListaEnlazada<Pasajero> pasajerosRegistrados; 
    private Cola<Pasajero> colaAtencion;                 
    private Pila<String> historialConsultas;            
    private ArbolBinario<String> arbolEstaciones;       
    
    private double tarifaBase;
    private double tarifaReducida;
    private Random generadorAleatorio;

    public ServicioMetro() {
        this.redMetro = new Grafo<>();
        this.estacionesMap = new TablaHash<>();
        this.lineasMap = new TablaHash<>();
        this.pasajerosRegistrados = new ListaEnlazada<>();
        this.colaAtencion = new Cola<>();
        this.historialConsultas = new Pila<>();
        this.arbolEstaciones = new ArbolBinario<>();
        
        this.tarifaBase = 2500.0;
        this.tarifaReducida = 1500.0;
        this.generadorAleatorio = new Random();
    }

    public boolean agregarEstacion(Estacion estacion) {
        if (estacion == null) return false;
        
        String codigo = estacion.getCodigo();

        if (estacionesMap.contieneClave(codigo)) {
            return false;
        }

        estacionesMap.put(codigo, estacion);          
        redMetro.agregarVertice(estacion);            
        arbolEstaciones.insertar(estacion.getNombre().toUpperCase()); 
        
        return true;
    }

    public Estacion buscarEstacionPorCodigo(String codigo) {
        return estacionesMap.get(codigo);
    }

    public Estacion buscarEstacionPorNombre(String nombre) {
        if (!arbolEstaciones.buscar(nombre.toUpperCase())) {
            return null;
        }

        for (int i = 0; i < estacionesMap.getTamaño(); i++) {
        }
        return null;
    }

    public boolean conectarEstaciones(String codigo1, String codigo2) {
        Estacion e1 = estacionesMap.get(codigo1);
        Estacion e2 = estacionesMap.get(codigo2);
        
        if (e1 == null || e2 == null) {
            return false;
        }
        
        redMetro.agregarArista(e1, e2);
        return true;
    }

    public ListaEnlazada<Estacion> obtenerEstacionesAdyacentes(String codigoEstacion) {
        Estacion estacion = estacionesMap.get(codigoEstacion);
        if (estacion == null) {
            return new ListaEnlazada<>();
        }
        return redMetro.obtenerAdyacentes(estacion);
    }

    public boolean agregarLinea(LineaMetro linea) {
        if (linea == null || lineasMap.contieneClave(linea.getCodigo())) {
            return false;
        }
        
        lineasMap.put(linea.getCodigo(), linea);
        
        ListaEnlazada<Estacion> estaciones = linea.getEstaciones();
        for (int i = 0; i < estaciones.getTamaño() - 1; i++) {
            Estacion actual = estaciones.obtener(i);
            Estacion siguiente = estaciones.obtener(i + 1);

            agregarEstacion(actual);
            agregarEstacion(siguiente);

            redMetro.agregarArista(actual, siguiente);
        }
        
        return true;
    }

    public LineaMetro obtenerLineaPorCodigo(String codigoLinea) {
        return lineasMap.get(codigoLinea);
    }

    public ListaEnlazada<LineaMetro> obtenerTodasLasLineas() {
        ListaEnlazada<LineaMetro> resultado = new ListaEnlazada<>();
        return resultado;
    }

    public ConsultaRuta encontrarRutaMasCorta(String codigoOrigen, String codigoDestino) {
        Estacion origen = estacionesMap.get(codigoOrigen);
        Estacion destino = estacionesMap.get(codigoDestino);
        
        if (origen == null || destino == null) {
            System.out.println("ERROR: Estación no encontrada");
            return null;
        }

        ListaEnlazada<Estacion> camino = redMetro.caminoMasCorto(origen, destino);
        
        if (camino.estaVacia() || camino.getTamaño() == 0) {
            System.out.println("No existe ruta entre las estaciones seleccionadas");
            return null;
        }

        ConsultaRuta consulta = new ConsultaRuta(origen, destino);
        consulta.setRutaOptima(camino);

        String registro = origen.getNombre() + " -> " + destino.getNombre();
        historialConsultas.apilar(registro);
        
        return consulta;
    }

    public ListaEnlazada<Estacion> busquedaAnchura(String codigoEstacion) {
        Estacion estacion = estacionesMap.get(codigoEstacion);
        if (estacion == null) {
            return new ListaEnlazada<>();
        }
        return redMetro.bfs(estacion);
    }

    public ListaEnlazada<Estacion> busquedaProfundidad(String codigoEstacion) {
        Estacion estacion = estacionesMap.get(codigoEstacion);
        if (estacion == null) {
            return new ListaEnlazada<>();
        }
        return redMetro.dfs(estacion);
    }

    public boolean existeRuta(String codigo1, String codigo2) {
        Estacion e1 = estacionesMap.get(codigo1);
        Estacion e2 = estacionesMap.get(codigo2);
        
        if (e1 == null || e2 == null) return false;

        ListaEnlazada<Estacion> alcanzables = redMetro.bfs(e1);
        return alcanzables.contiene(e2);
    }

    public boolean registrarPasajero(Pasajero pasajero) {
        if (pasajero == null || pasajerosRegistrados.contiene(pasajero)) {
            return false;
        }
        pasajerosRegistrados.agregar(pasajero);
        return true;
    }

    public Pasajero buscarPasajero(String documento) {
        for (int i = 0; i < pasajerosRegistrados.getTamaño(); i++) {
            Pasajero p = pasajerosRegistrados.obtener(i);
            if (p.getDocumento().equals(documento)) {
                return p;
            }
        }
        return null;
    }

    public boolean agregarAColaAtencion(String documento) {
        Pasajero pasajero = buscarPasajero(documento);
        if (pasajero != null) {
            colaAtencion.encolar(pasajero); 
            return true;
        }
        return false;
    }

    public Pasajero atenderSiguientePasajero() {
        if (colaAtencion.estaVacia()) {
            return null;
        }
        return colaAtencion.desencolar();  
    }

    public int obtenerTamanioCola() {
        return colaAtencion.getTamaño();
    }

    public boolean recargarTarjeta(String documento, double monto) {
        Pasajero pasajero = buscarPasajero(documento);
        if (pasajero != null && monto > 0) {
            return pasajero.recargarTarjeta(monto);
        }
        return false;
    }

    public boolean simularViaje(String documentoPasajero, String codigoOrigen, String codigoDestino) {

        Pasajero pasajero = buscarPasajero(documentoPasajero);
        if (pasajero == null) {
            System.out.println("❌ Pasajero no registrado");
            return false;
        }

        ConsultaRuta ruta = encontrarRutaMasCorta(codigoOrigen, codigoDestino);
        if (ruta == null) {
            return false;
        }

        double costo = pasajero.tieneTarifaReducida() ? tarifaReducida : tarifaBase;

        if (!pasajero.tieneSaldoSuficiente(costo)) {
            System.out.println("❌ Saldo insuficiente. Recargue su tarjeta.");
            return false;
        }

        pasajero.descontarSaldo(costo);

        String codigoViaje = generarCodigoViaje();
        Estacion origen = estacionesMap.get(codigoOrigen);
        Estacion destino = estacionesMap.get(codigoDestino);
        
        Viaje viaje = new Viaje(codigoViaje, origen, destino, costo);
        if (pasajero.tieneTarifaReducida()) {
            viaje.setTipoTarifa("Reducida");
        }
        
        pasajero.registrarViaje(viaje);

        System.out.println("✅ Viaje exitoso: " + origen.getNombre() + " -> " + destino.getNombre());
        System.out.println("   Costo: $" + costo + " | Saldo restante: $" + pasajero.getSaldoTarjeta());
        return true;
    }

    private String generarCodigoViaje() {
        return "VIA-" + System.currentTimeMillis() + "-" + generadorAleatorio.nextInt(999);
    }

    public Pila<String> obtenerHistorialConsultas() {
        return historialConsultas;
    }

    public ListaEnlazada<String> obtenerUltimasConsultas(int n) {
        ListaEnlazada<String> recientes = new ListaEnlazada<>();
        Pila<String> copia = new Pila<>();

        while (!historialConsultas.estaVacia() && recientes.getTamaño() < n) {
            String consulta = historialConsultas.desapilar();
            recientes.agregar(consulta);
            copia.apilar(consulta);
        }

        while (!copia.estaVacia()) {
            historialConsultas.apilar(copia.desapilar());
        }
        
        return recientes;
    }

    public String generarEstadisticas() {
        StringBuilder sb = new StringBuilder();
        sb.append("╔════════════════════════════════╗\n");
        sb.append("║  ESTADÍSTICAS DEL SISTEMA METRO ║\n");
        sb.append("╚════════════════════════════════╝\n\n");
        
        sb.append("📊 INFRAESTRUCTURA:\n");
        sb.append("   • Estaciones registradas: ").append(estacionesMap.getTamaño()).append("\n");
        sb.append("   • Líneas activas: ").append(lineasMap.getTamaño()).append("\n");
        sb.append("   • Conexiones en red: ").append(redMetro.getCantidadAristas()).append("\n");
        sb.append("   • Altura del árbol de búsqueda: ").append(arbolEstaciones.getAltura()).append("\n\n");
        
        sb.append("👥 USUARIOS:\n");
        sb.append("   • Pasajeros registrados: ").append(pasajerosRegistrados.getTamaño()).append("\n");
        sb.append("   • En cola de atención: ").append(colaAtencion.getTamaño()).append("\n");
        sb.append("   • Consultas realizadas: ").append(historialConsultas.getTamaño()).append("\n\n");
        
        sb.append("💰 TARIFAS:\n");
        sb.append("   • Tarifa base: $").append((int)tarifaBase).append("\n");
        sb.append("   • Tarifa reducida: $").append((int)tarifaReducida).append("\n");
        
        return sb.toString();
    }

    public ListaEnlazada<Pasajero> listarPasajeros() {
        return pasajerosRegistrados;
    }

    public ListaEnlazada<Estacion> buscarEstacionesPorTexto(String texto) {
        ListaEnlazada<Estacion> resultados = new ListaEnlazada<>();
        String busqueda = texto.toUpperCase();

        for (int i = 0; i < estacionesMap.getTamaño(); i++) {
        }
        
        return resultados;
    }

    public void cargarDatosEjemplo() {
        
        LineaMetro ruta1 = new LineaMetro("R1", "Línea Verde", "Verde", 3);
        LineaMetro ruta2 = new LineaMetro("R2", "Línea Azul", "Azul", 3);
        LineaMetro ruta3 = new LineaMetro("R3", "Línea Roja", "Roja", 3);

        Estacion estacion1 = new Estacion("E001", "Puesto de salud, Sauces", "R1", 0, 0, true, true);
        Estacion estacion2 = new Estacion("E002", "Calle 13", "R1", 1, 0, false, true);
        Estacion estacion3 = new Estacion("E003", "Romboi chirimia", "R1", 2, 0, true, false);
        Estacion estacion4 = new Estacion("E004", "Campanario", "R1", 3, 0, true, true);
        Estacion estacion5 = new Estacion("E005", "Bella vista", "R1", 4, 0, false, false);
        Estacion estacion6 = new Estacion("E006", "Las Garzas", "R1", 4, 0, false, false);
        
        for (Estacion e : new Estacion[]{estacion1, estacion2, estacion3, estacion4, estacion5, estacion6}) {
            ruta1.getEstaciones().agregar(e);
            agregarEstacion(e);
        }

        Estacion estacion7 = new Estacion("E007", "barrio bolivar", "R2", 0, 1, true, true);
        Estacion estacion8 = new Estacion("E008", "Centro carrera 4", "R2", 1, 1, false, true);
        Estacion estacion2_R2 = new Estacion("E002", "Calle 13", "R2", 2, 1, true, false);
        Estacion estacion10 = new Estacion("E010", "Hospital Susana lopez", "R2", 3, 1, true, true);
        Estacion estacion11 = new Estacion("E011", "Thomas Cipriano", "R2", 3, 1, true, true);

        for (Estacion e : new Estacion[]{estacion6, estacion7, estacion8, estacion2_R2, estacion10}) {
            ruta2.getEstaciones().agregar(e);
            agregarEstacion(e);
        }

        Estacion estacion12 = new Estacion("E012", "Centro carrera 8", "R3", 0, 2, true, true);
        Estacion estacion13 = new Estacion("E013", "Parque carantanta", "R3", 1, 2, false, false);
        Estacion estacion4_R3 = new Estacion("E004", "Campanario", "R3", 3, 2, true, true);
        Estacion estacion5_R3 = new Estacion("E005", "Bella vista", "R3", 4, 2, true, true);
        Estacion estacion15 = new Estacion("E015", "La Paz", "R3", 0,2, true, true);

        for (Estacion e : new Estacion[]{estacion11, estacion12, estacion4_R3, estacion13, estacion5_R3, estacion15}) {
            ruta3.getEstaciones().agregar(e);
            agregarEstacion(e);
        }

        agregarLinea(ruta1);
        agregarLinea(ruta2);
        agregarLinea(ruta3);

        conectarEstaciones("E002", "E002"); 
        conectarEstaciones("E004", "E004"); 

        Pasajero pasajero1 = new Pasajero("1111", "Juan", "Pérez");
        pasajero1.recargarTarjeta(10000);
        
        Pasajero pasajero2 = new Pasajero("2222", "María", "Gómez");
        pasajero2.setEsPersonaDiscapacitada(true);
        pasajero2.recargarTarjeta(5000);
        
        Pasajero pasajero3 = new Pasajero("3333", "Carlos", "López");
        pasajero3.setEsAdultoMayor(true);
        pasajero3.recargarTarjeta(3000);

        registrarPasajero(pasajero1);
        registrarPasajero(pasajero2);
        registrarPasajero(pasajero3);

        agregarAColaAtencion("2222");
        agregarAColaAtencion("3333");

        System.out.println("¡Datos cargados exitosamente!");
        System.out.println("   • 3 líneas de metro");
        System.out.println("   • 13 estaciones registradas");
        System.out.println("   • 3 pasajeros creados");
        System.out.println("   • 2 pasajeros en cola de atención");
        System.out.println("   • Red de metro conectada con transbordos");
    }

    public Grafo<Estacion> getRedMetro() { return redMetro; }
    public TablaHash<String, Estacion> getEstacionesMap() { return estacionesMap; }
    public TablaHash<String, LineaMetro> getLineasMap() { return lineasMap; }
    public ListaEnlazada<Pasajero> getPasajerosRegistrados() { return pasajerosRegistrados; }
    public Cola<Pasajero> getColaAtencion() { return colaAtencion; }
    public Pila<String> getHistorialConsultas() { return historialConsultas; }
    public ArbolBinario<String> getArbolEstaciones() { return arbolEstaciones; }
}
