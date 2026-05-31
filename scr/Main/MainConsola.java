package Main;

import java.util.Scanner;
import java.util.InputMismatchException;
import Servicio.ServicioMetro;
import Modelo.*;
import Estrucuturas.ListaEnlazada;
import Estrucuturas.Pila;

public class MenuConsola {

    private static ServicioMetro servicio;
    private static Scanner scanner;

    public static void main(String[] args) {
        servicio = new ServicioMetro();
        scanner = new Scanner(System.in);
        
        ejecutarSistema();
    }

    private static void ejecutarSistema() {
        int opcion;
        
        servicio.cargarDatosEjemplo();
        System.out.println("\n Sistema inicializado con datos de prueba.");

        do {
            mostrarMenu();
            opcion = leerEntero();  
            
            switch (opcion) {
                case 1:
                    buscarRutaOptima();
                    break;
                case 2:
                    explorarRed("BFS");
                    break;
                case 3:
                    explorarRed("DFS");
                    break;
                case 4:
                    listarEstacionesConectadas();
                    break;
                case 5:
                    registrarNuevoPasajero();
                    break;
                case 6:
                    simularViaje();
                    break;
                case 7:
                    verHistorialConsultas();
                    break;
                case 8:
                    atenderPasajeros();
                    break;
                case 9:
                    mostrarEstadisticas();
                    break;
                case 0:
                    System.out.println("\n ¡Gracias por usar el Sistema de Metro! ");
                    break;
                default:
                    if (opcion != -1) {  // -1 es error de entrada
                        System.out.println(" Opción inválida.");
                    }
            }
            
            if (opcion != 0 && opcion != -1) {
                System.out.println("\nPresiona Enter para continuar...");
                scanner.nextLine();
            }
            
        } while (opcion != 0);
        
        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n        SISTEMA DE METRO          ");
        System.out.println(" 1.  Buscar Ruta Mas Corta (Dijkstra)");
        System.out.println(" 2.  Explorar Red (BFS - Anchura)");
        System.out.println(" 3.  Explorar Red (DFS - Profundidad)");
        System.out.println(" 4.  Ver Estaciones Conectadas");
        System.out.println(" 5.  Registrar Pasajero");
        System.out.println(" 6.  Simular Viaje y Cobro");
        System.out.println(" 7.  Ver Historial de Consultas (PILA)");
        System.out.println(" 8.  Atender Pasajeros (COLA)");
        System.out.println(" 9.  Estadisticas del Sistema");
        System.out.println(" 0. Salir");
        System.out.print("Seleccione una opcion: ");
    }

    private static void buscarRutaOptima() {
        System.out.println("\n BUSCAR RUTA OPTIMA (Dijkstra)");
        System.out.print("Ingrese codigo estacion ORIGEN (ej. E001): ");
        String origen = scanner.nextLine().trim().toUpperCase();
        
        System.out.print("Ingrese codigo estacion DESTINO (ej. E013): ");
        String destino = scanner.nextLine().trim().toUpperCase();

        ConsultaRuta ruta = servicio.encontrarRutaMasCorta(origen, destino);

        if (ruta != null) {
            System.out.println("\n" + ruta.generarInstruccionesRuta());
        }
    }

    private static void explorarRed(String tipo) {
        System.out.println("\n EXPLORAR RED (" + tipo + ")");
        System.out.print("Ingrese codigo estacion inicio: ");
        String codigo = scanner.nextLine().trim().toUpperCase();

        ListaEnlazada<Estacion> visitados;
        if (tipo.equals("BFS")) {
            visitados = servicio.busquedaAnchura(codigo);
            System.out.println(" estaciones alcanzables (Orden de cercania):");
        } else {
            visitados = servicio.busquedaProfundidad(codigo);
            System.out.println("Estaciones alcanzables (Orden de profundidad):");
        }

        if (visitados.estaVacia()) {
            System.out.println("No se encontraron estaciones.");
        } else {
            for (int i = 0; i < visitados.getTamaño(); i++) {
                System.out.println((i + 1) + ". " + visitados.obtener(i).getNombre());
            }
            System.out.println("Total visitadas: " + visitados.getTamaño());
        }
    }

    private static void listarEstacionesConectadas() {
        System.out.println("\n ESTACIONES CONECTADAS");
        System.out.print("Ingrese codigo estacion: ");
        String codigo = scanner.nextLine().trim().toUpperCase();

        ListaEnlazada<Estacion> adyacentes = servicio.obtenerEstacionesAdyacentes(codigo);
        
        if (adyacentes.estaVacia()) {
            System.out.println("Estacion no encontrada o sin conexiones.");
        } else {
            System.out.println("Estaciones conectadas directamente a [" + codigo + "]:");
            for (int i = 0; i < adyacentes.getTamaño(); i++) {
                System.out.println(" -> " + adyacentes.obtener(i).getNombre() + " (" + adyacentes.obtener(i).getLinea() + ")");
            }
        }
    }

    private static void registrarNuevoPasajero() {
        System.out.println("\n REGISTRAR NUEVO PASAJERO");
        System.out.print("Documento: ");
        String doc = scanner.nextLine().trim();
        
        System.out.print("Nombre: ");
        String nom = scanner.nextLine().trim();
        
        System.out.print("Apellido: ");
        String ape = scanner.nextLine().trim();

        Pasajero p = new Pasajero(doc, nom, ape);
        System.out.print("Saldo inicial: ");
        double saldo = leerDouble();
        p.recargarTarjeta(saldo);

        if (servicio.registrarPasajero(p)) {
            System.out.println(" ¡Pasajero registrado exitosamente.!");
            System.out.print("Desea agregarlo a la cola de atencion? (s/n): ");
            String resp = scanner.nextLine().trim();
            if (resp.equalsIgnoreCase("s")) {
                servicio.agregarAColaAtencion(doc);
                System.out.println(" Agregado a cola.");
            }
        } else {
            System.out.println(" Error: el pasajero ya existe.");
        }
    }

    private static void simularViaje() {
        System.out.println("\n SIMULAR VIAJE");
        System.out.print("Documento Pasajero: ");
        String doc = scanner.nextLine().trim();
        
        System.out.print("Estacion Origen: ");
        String origen = scanner.nextLine().trim().toUpperCase();
        
        System.out.print("Estacion Destino: ");
        String destino = scanner.nextLine().trim().toUpperCase();

        boolean exito = servicio.simularViaje(doc, origen, destino);
        
        if (!exito) {
            System.out.println(" El viaje no pudo completarse.");
        }
    }

    private static void verHistorialConsultas() {
        System.out.println("\n HISTORIAL DE CONSULTAS ");
        
        Pila<String> historial = servicio.obtenerHistorialConsultas();
        
        if (historial.estaVacia()) {
            System.out.println("No hay consultas registradas.");
        } else {
            int tam = historial.getTamaño();
            System.out.println("Total consultas en historial: " + tam);
            System.out.println("Ultima consulta realizada (CIMA):");
            try {
                System.out.println(" | " + historial.cima());
            } catch (Exception e) {
            }
        }
    }

    private static void atenderPasajeros() {
        System.out.println("\n ATENCION DE PASAJEROS ");
        int espera = servicio.obtenerTamanioCola();
        System.out.println("Pasajeros en espera: " + espera);

        if (espera > 0) {
            System.out.print("Atender al siguiente pasajero? (s/n): ");
            String resp = scanner.nextLine().trim();
            if (resp.equalsIgnoreCase("s")) {
                Pasajero atendido = servicio.atenderSiguientePasajero();
                if (atendido != null) {
                    System.out.println(" Atendiendo a: " + atendido.getNombreCompleto());
                }
            }
        } else {
            System.out.println("No hay nadie en espera.");
        }
    }

    private static void mostrarEstadisticas() {
        System.out.println("\n" + servicio.generarEstadisticas());
        System.out.println(" Arboles usados para busqueda de nombres de estaciones.");
        System.out.println(" HashTables usados para indexacion de estaciones y lineas.");
    }


    private static int leerEntero() {
        try {
            int valor = scanner.nextInt();
            scanner.nextLine();  
            return valor;
        } catch (InputMismatchException e) {
            scanner.nextLine();  
            System.out.println(" Entrada inválida. Por favor ingrese un número.");
            return -1;
        }
    }

    private static double leerDouble() {
        try {
            double valor = scanner.nextDouble();
            scanner.nextLine();  
            return valor;
        } catch (InputMismatchException e) {
            scanner.nextLine();  
            System.out.println(" Entrada inválida. Por favor ingrese un número.");
            return 0.0;
        }
    }
}
