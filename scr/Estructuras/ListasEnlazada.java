package Estructuras;
public class ListasEnlazada<T> {

    private static class Nodo<T> {
        T dato;
        Nodo<T> siguiente;
        
        Nodo(T dato) {
            this.dato = dato;
            this.siguiente = null;
        }
    }
    
    private Nodo<T> cabeza;
    private int tamaño;
    
    public ListasEnlazada() {
        this.cabeza = null;
        this.tamaño = 0;
    }
    
    public void agregar(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        
        if (cabeza == null) {
            cabeza = nuevoNodo;
        } else {
            Nodo<T> actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevoNodo;
        }
        tamaño++;
    }
    
    public T obtener(int indice) {
        if (indice < 0 || indice >= tamaño) {
            throw new IndexOutOfBoundsException(
                "Índice: " + indice + ", Tamaño: " + tamaño
            );
        }
        
        Nodo<T> actual = cabeza;
        for (int i = 0; i < indice; i++) {
            actual = actual.siguiente;
        }
        return actual.dato;
    }
    
    public T eliminar(int indice) {
        if (indice < 0 || indice >= tamaño) {
            throw new IndexOutOfBoundsException(
                "Índice: " + indice + ", Tamaño: " + tamaño
            );
        }
        
        T datoEliminado;
        
        if (indice == 0) {
            datoEliminado = cabeza.dato;
            cabeza = cabeza.siguiente;
        } else {
            Nodo<T> anterior = cabeza;
            for (int i = 0; i < indice - 1; i++) {
                anterior = anterior.siguiente;
            }
            Nodo<T> nodoEliminar = anterior.siguiente;
            datoEliminado = nodoEliminar.dato;
            anterior.siguiente = nodoEliminar.siguiente;
        }
        
        tamaño--;
        return datoEliminado;
    }
    
    public boolean eliminarElemento(T dato) {
        int indice = indiceDe(dato);
        if (indice != -1) {
            eliminar(indice);
            return true;
        }
        return false;
    }
    
    public int getTamaño() {
        return tamaño;
    }
    
    public boolean estaVacia() {
        return tamaño == 0;
    }
    
    public void limpiar() {
        cabeza = null;
        tamaño = 0;
    }
    
    public boolean contiene(T dato) {
        return indiceDe(dato) != -1;
    }
    
    public int indiceDe(T dato) {
        Nodo<T> actual = cabeza;
        int indice = 0;
        
        while (actual != null) {
            if (actual.dato == null && dato == null) {
                return indice;
            }
            if (actual.dato != null && actual.dato.equals(dato)) {
                return indice;
            }
            actual = actual.siguiente;
            indice++;
        }
        return -1;
    }
    
    @Override
    public String toString() {
        if (estaVacia()) {
            return "[]";
        }
        
        StringBuilder sb = new StringBuilder("[");
        Nodo<T> actual = cabeza;
        
        while (actual != null) {
            sb.append(actual.dato);
            if (actual.siguiente != null) {
                sb.append(", ");
            }
            actual = actual.siguiente;
        }
        sb.append("]");
        return sb.toString();
    }
}
