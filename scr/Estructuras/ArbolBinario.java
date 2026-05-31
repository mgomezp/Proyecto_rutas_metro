public class ArbolBinario<T extends Comparable<T>> {
    
    private static class Nodo<T> {
        T dato;
        Nodo<T> izquierdo;
        Nodo<T> derecho;
        
        Nodo(T dato) {
            this.dato = dato;
            this.izquierdo = null;
            this.derecho = null;
        }
    }
    
    private Nodo<T> raiz;
    private int tamaño;
    
    public ArbolBinario() {
        this.raiz = null;
        this.tamaño = 0;
    }
    
    public void insertar(T dato) {
        raiz = insertarRecursivo(raiz, dato);
        tamaño++;
    }
    
    private Nodo<T> insertarRecursivo(Nodo<T> nodo, T dato) {
        if (nodo == null) {
            return new Nodo<>(dato);
        }
        if (dato.compareTo(nodo.dato) < 0) {
            nodo.izquierdo = insertarRecursivo(nodo.izquierdo, dato);
        } else if (dato.compareTo(nodo.dato) > 0) {
            nodo.derecho = insertarRecursivo(nodo.derecho, dato);
        }
        return nodo;
    }
    
    public boolean buscar(T dato) {
        return buscarRecursivo(raiz, dato);
    }
    
    private boolean buscarRecursivo(Nodo<T> nodo, T dato) {
        if (nodo == null) return false;
        if (dato.equals(nodo.dato)) return true;
        if (dato.compareTo(nodo.dato) < 0) {
            return buscarRecursivo(nodo.izquierdo, dato);
        } else {
            return buscarRecursivo(nodo.derecho, dato);
        }
    }
    
    public void eliminar(T dato) {
        raiz = eliminarRecursivo(raiz, dato);
        tamaño--;
    }
    
    private Nodo<T> eliminarRecursivo(Nodo<T> nodo, T dato) {
        if (nodo == null) return null;
        
        if (dato.compareTo(nodo.dato) < 0) {
            nodo.izquierdo = eliminarRecursivo(nodo.izquierdo, dato);
        } else if (dato.compareTo(nodo.dato) > 0) {
            nodo.derecho = eliminarRecursivo(nodo.derecho, dato);
        } else {
            
            if (nodo.izquierdo == null) return nodo.derecho;
            if (nodo.derecho == null) return nodo.izquierdo;
            
            
            nodo.dato = encontrarMinimo(nodo.derecho);
            nodo.derecho = eliminarRecursivo(nodo.derecho, nodo.dato);
        }
        return nodo;
    }
    
    private T encontrarMinimo(Nodo<T> nodo) {
        while (nodo.izquierdo != null) {
            nodo = nodo.izquierdo;
        }
        return nodo.dato;
    }
    
    // Recorridos
    public void inOrden() {
        inOrdenRecursivo(raiz);
        System.out.println();
    }
    
    private void inOrdenRecursivo(Nodo<T> nodo) {
        if (nodo != null) {
            inOrdenRecursivo(nodo.izquierdo);
            System.out.print(nodo.dato + " ");
            inOrdenRecursivo(nodo.derecho);
        }
    }
    
    public void preOrden() {
        preOrdenRecursivo(raiz);
        System.out.println();
    }
    
    private void preOrdenRecursivo(Nodo<T> nodo) {
        if (nodo != null) {
            System.out.print(nodo.dato + " ");
            preOrdenRecursivo(nodo.izquierdo);
            preOrdenRecursivo(nodo.derecho);
        }
    }
    
    public void postOrden() {
        postOrdenRecursivo(raiz);
        System.out.println();
    }
    
    private void postOrdenRecursivo(Nodo<T> nodo) {
        if (nodo != null) {
            postOrdenRecursivo(nodo.izquierdo);
            postOrdenRecursivo(nodo.derecho);
            System.out.print(nodo.dato + " ");
        }
    }
    
    public boolean estaVacia() {
        return raiz == null;
    }
    
    public int getTamaño() {
        return tamaño;
    }
    
    public int getAltura() {
        return alturaRecursivo(raiz);
    }
    
    private int alturaRecursivo(Nodo<T> nodo) {
        if (nodo == null) return 0;
        return 1 + Math.max(alturaRecursivo(nodo.izquierdo), alturaRecursivo(nodo.derecho));
    }
    
    public void limpiar() {
        raiz = null;
        tamaño = 0;
    }
}
