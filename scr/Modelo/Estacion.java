package Modelo;
public class Estacion {
    
    private String codigo;
    private String nombre;
    private String linea;
    private double coordenadaX;
    private double coordenadaY;
    private boolean tieneAscensor;
    private boolean tieneEscalerasMoviles;

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

    public Estacion(String codigo, String nombre, String linea) {
        this(codigo, nombre, linea, 0.0, 0.0, false, false);
    }

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

    public double calcularDistanciaA(Estacion otraEstacion) {
        double deltaX = this.coordenadaX - otraEstacion.coordenadaX;
        double deltaY = this.coordenadaY - otraEstacion.coordenadaY;
        return Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
    }

    public boolean esAccesible() {
        return tieneAscensor || tieneEscalerasMoviles;
    }

 
    @Override
    public String toString() {
        return String.format("[%s] %s - Línea %s", 
                           codigo, nombre, linea);
    }

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
