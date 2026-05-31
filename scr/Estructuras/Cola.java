public class Cola<T> {
    
    private ListaEnlazada<T> elementos;
    
    public Cola() {
        this.elementos = new ListaEnlazada<>();
    }
    
    /**
     * Agrega un elemento al final de la cola.
     */
    public void encolar(T dato) {
        elementos.agregar(dato);
    }
    
    /**
     * Remueve y retorna el elemento del frente.
     */
    public T desencolar() {
        if (estaVacia()) {
            throw new RuntimeException("La cola está vacía");
        }
        // El primero en entrar está en el índice 0
        return elementos.eliminar(0);
    }
    
    /**
     * Retorna el elemento del frente sin removerlo.
     */
    public T frente() {
        if (estaVacia()) {
            throw new RuntimeException("La cola está vacía");
        }
        return elementos.obtener(0);
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
        return "Cola: " + elementos.toString();
    }
}
