#  SISTEMA DE GESTIÓN DE METRO

## ESTRUCTURAS DE DATOS - PROYECTO FINAL

**Autores:** Kevin Andres Caicedo.

           Santiago Alejandro Bambague.

           Miguel Angel Plaza.

           David Santiago Martinez.

           Elkin Gustavo Uni.

           Fabian Alenjandro Yasno.


##  DESCRIPCIÓN DEL PROYECTO

Sistema integral para la gestión de rutas de metro, administración de pasajeros y simulación de viajes. Implementa estructuras de datos desde cero sin usar las colecciones de Java.


##  ESTRUCTURAS DE DATOS IMPLEMENTADAS

 Lista Enlazada : Almacenamiento de pasajeros, estaciones, resultados | O(n) 

 Pila : Historial de consultas de rutas | O(1) 

 Cola : Atención de pasajeros en orden de llegada | O(1) 

 Árbol Binario : Búsqueda de estaciones por nombre | O(log n) 

 Tabla Hash : Indexación rápida de estaciones y líneas | O(1)

 Grafo : Red de conexiones (BFS/DFS/Dijkstra) | O(V+E) 

NOTA: No se utilizó NINGUNA clase de (Stack, Queue, ArrayList, HashMap, TreeMap, etc.)


##  ESTRUCTURA DEL PROYECTO

src/
├── Main/
│ ├── MenuConsola.java # Interfaz por línea de comandos
│ └── VentanaPrincipal.java # Interfaz gráfica (Java Swing)
├── Modelo/
│ ├── Estacion.java
│ ├── LineaMetro.java
│ ├── Pasajero.java
│ └── Viaje.java
├── Servicio/
│ ── ServicioMetro.java 
└── Estrucuturas/ 
├── Grafo.java
├── HashTable.java
├── ListaEnlazada.java
├── Pila.java
├── Cola.java
└── ArbolBinario.java


##  INSTRUCCIONES PARA EJECUTAR

1. Descargar o copiar todos los archivos en una misma carpeta.

2. Abrir la carpeta en un entorno de desarrollo Java como Visual Studio Code o NetBeans.

3. Verificar que el JDK esté instalado en el computador.

4. Seleccionar el archivo correspondiente al ejercicio que desea ejecutar.

5. Cada ejercicio tiene una clase principal la cual es la main para ejecutar el ejercicio.

### Ejecutar la Interfaz Gráfica (Recomendado)
