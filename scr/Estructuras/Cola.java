public class Cola<T> {
    
    private ListaEnlazada<T> elementos;
    
    public Cola() {
        this.elementos = new ListaEnlazada<>();
    }

    public void encolar(T dato) {
        elementos.agregar(dato);
    }

    public T desencolar() {
        if (estaVacia()) {
            throw new RuntimeException("La cola está vacía");
        }

        return elementos.eliminar(0);
    }

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
