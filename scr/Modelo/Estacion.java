package Modelo;

/**
 * Clase que representa una estación del sistema de metro.
 * Contiene información sobre el nombre, ubicación y conexiones de la estación.
 * 
 * @author Kevin Caicedo
 * @version 1.0
 */
public class Estacion {
    
    // Atributos privados - Encapsulamiento
    private String codigo;
    private String nombre;
    private String linea;
    private double coordenadaX;
    private double coordenadaY;
    private boolean tieneAscensor;
    private boolean tieneEscalerasMoviles;

    /**
     * Constructor completo de la clase Estacion.
     * 
     * @param codigo Código único identificador de la estación
     * @param nombre Nombre comercial de la estación
     * @param linea Línea del metro a la que pertenece
     * @param coordenadaX Coordenada X de ubicación
     * @param coordenadaY Coordenada Y de ubicación
     * @param tieneAscensor Indica si la estación cuenta con ascensor
     * @param tieneEscalerasMoviles Indica si tiene escaleras móviles
     */
    public Estacion(String codigo, String nombre, String linea, 
                   double coordenadaX, double coordenadaY,
                   boolean tieneAscensor, boolean tieneEscalerasMoviles) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.linea = linea;
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
        this.tieneAscensor = tieneAscensor;
        this.tieneEscalerasMoviles = tieneEscalerasMoviles;
    }

    /**
     * Constructor simplificado con valores por defecto.
     * 
     * @param codigo Código de la estación
     * @param nombre Nombre de la estación
     * @param linea Línea del metro
     */
    public Estacion(String codigo, String nombre, String linea) {
        this(codigo, nombre, linea, 0.0, 0.0, false, false);
    }

    // Métodos Getter - Acceso controlado a atributos
    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getLinea() {
        return linea;
    }

    public double getCoordenadaX() {
        return coordenadaX;
    }

    public double getCoordenadaY() {
        return coordenadaY;
    }

    public boolean isTieneAscensor() {
        return tieneAscensor;
    }

    public boolean isTieneEscalerasMoviles() {
        return tieneEscalerasMoviles;
    }

    // Métodos Setter - Modificación controlada
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public void setCoordenadaX(double coordenadaX) {
        this.coordenadaX = coordenadaX;
    }

    public void setCoordenadaY(double coordenadaY) {
        this.coordenadaY = coordenadaY;
    }

    public void setTieneAscensor(boolean tieneAscensor) {
        this.tieneAscensor = tieneAscensor;
    }

    public void setTieneEscalerasMoviles(boolean tieneEscalerasMoviles) {
        this.tieneEscalerasMoviles = tieneEscalerasMoviles;
    }

    /**
     * Calcula la distancia euclidiana entre esta estación y otra.
     * 
     * @param otraEstacion La otra estación para calcular distancia
     * @return Distancia euclidiana entre las dos estaciones
     */
    public double calcularDistanciaA(Estacion otraEstacion) {
        double deltaX = this.coordenadaX - otraEstacion.coordenadaX;
        double deltaY = this.coordenadaY - otraEstacion.coordenadaY;
        return Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
    }

    /**
     * Verifica si la estación es accesible para personas con movilidad reducida.
     * 
     * @return true si tiene ascensor o escaleras móviles, false en caso contrario
     */
    public boolean esAccesible() {
        return tieneAscensor || tieneEscalerasMoviles;
    }

    /**
     * Representación en texto de la estación.
     * 
     * @return String con la información de la estación
     */
    @Override
    public String toString() {
        return String.format("[%s] %s - Línea %s", 
                           codigo, nombre, linea);
    }

    /**
     * Compara dos estaciones basándose en su código.
     * 
     * @param obj El objeto a comparar
     * @return true si ambas estaciones tienen el mismo código
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Estacion estacion = (Estacion) obj;
        return codigo.equals(estacion.codigo);
    }

    @Override
    public int hashCode() {
        return codigo.hashCode();
    }
}
