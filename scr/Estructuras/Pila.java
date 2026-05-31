package Estructuras;

public class Pila<T> {
    
    private ListasEnlazada<T> elementos;
    
    public Pila() {
        this.elementos = new ListasEnlazada<>();
    }
    
    public void apilar(T dato) {
        elementos.agregar(dato);
    }
 
    public T desapilar() {
        if (estaVacia()) {
            throw new RuntimeException("La pila esta vacia");
        }
        return elementos.eliminar(elementos.getTamaño() - 1);
    }
    
    public T cima() {
        if (estaVacia()) {
            throw new RuntimeException("La pila esta vacia");
        }
        return elementos.obtener(elementos.getTamaño() - 1);
    }
    
    public boolean estaVacia() {
        return elementos.estaVacia();
    }
    
    public int getTamaño() {
        return elementos.getTamaño();
    }
    
    public void limpiar() {
        elementos.limpiar();
    }
    
    @Override
    public String toString() {
        return "Pila: " + elementos.toString();
    }
}

