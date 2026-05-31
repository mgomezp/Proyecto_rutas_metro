package Estructuras;

public class TablaHash<K, V> {
    
    private static final int CAPACIDAD_INICIAL = 16;
    private static final double FACTOR_CARGA = 0.75;
    
    private ListasEnlazada<ParClaveValor<K, V>>[] tabla;
    private int tamaño;
    
    private static class ParClaveValor<K, V> {
        K clave;
        V valor;
        
        ParClaveValor(K clave, V valor) {
            this.clave = clave;
            this.valor = valor;
        }
    }
    
    public TablaHash() {
        this.tabla = new ListasEnlazada[CAPACIDAD_INICIAL];
        this.tamaño = 0;
    }
    
    private int hash(K clave) {
        return Math.abs(clave.hashCode() % tabla.length);
    }
    
    public void put(K clave, V valor) {
        int indice = hash(clave);
        
        if (tabla[indice] == null) {
            tabla[indice] = new ListasEnlazada<>();
        }
        
        ListasEnlazada<ParClaveValor<K, V>> lista = tabla[indice];
        
        // busca si ya existe la clave para actualizar
        for (int i = 0; i < lista.getTamaño(); i++) {
            ParClaveValor<K, V> par = lista.obtener(i);
            if (par.clave.equals(clave)) {
                par.valor = valor;
                return;
            }
        }
        
        // se inserta un nuevo par
        lista.agregar(new ParClaveValor<>(clave, valor));
        tamaño++;
        
        if ((double) tamaño / tabla.length > FACTOR_CARGA) {
            redimensionar();
        }
    }
    
    private void redimensionar() {
        ListasEnlazada<ParClaveValor<K, V>>[] tablaVieja = tabla;
        tabla = new ListasEnlazada[tabla.length * 2];
        tamaño = 0;
        
        for (int i = 0; i < tablaVieja.length; i++) {
            if (tablaVieja[i] != null) {
                for (int j = 0; j < tablaVieja[i].getTamaño(); j++) {
                    ParClaveValor<K, V> par = tablaVieja[i].obtener(j);
                    put(par.clave, par.valor);
                }
            }
        }
    }
    
    public V get(K clave) {
        int indice = hash(clave);
        ListasEnlazada<ParClaveValor<K, V>> lista = tabla[indice];
        
        if (lista == null) return null;
        
        for (int i = 0; i < lista.getTamaño(); i++) {
            ParClaveValor<K, V> par = lista.obtener(i);
            if (par.clave.equals(clave)) {
                return par.valor;
            }
        }
        return null;
    }
    
    public boolean contieneClave(K clave) {
        return get(clave) != null;
    }
    
    public boolean eliminar(K clave) {
        int indice = hash(clave);
        ListasEnlazada<ParClaveValor<K, V>> lista = tabla[indice];
        
        if (lista == null) return false;
        
        for (int i = 0; i < lista.getTamaño(); i++) {
            ParClaveValor<K, V> par = lista.obtener(i);
            if (par.clave.equals(clave)) {
                lista.eliminar(i);
                tamaño--;
                return true;
            }
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
        tabla = new ListasEnlazada[CAPACIDAD_INICIAL];
        tamaño = 0;
    }
}
