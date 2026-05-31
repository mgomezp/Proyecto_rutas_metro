package Estrucuturas;

public class Grafo<T> {
    
    private TablaHash<T, ListaEnlazada<T>> adyacencia;
    private ListaEnlazada<T> vertices;
    
    public Grafo() {
        this.adyacencia = new TablaHash<>();
        this.vertices = new ListaEnlazada<>();
    }
    
    public void agregarVertice(T vertice) {
        if (!adyacencia.contieneClave(vertice)) {
            adyacencia.put(vertice, new ListaEnlazada<>());
            vertices.agregar(vertice);
        }
    }
    
    public void agregarArista(T origen, T destino) {
        agregarVertice(origen);
        agregarVertice(destino);
        
        ListaEnlazada<T> adyOrigen = adyacencia.get(origen);
        ListaEnlazada<T> adyDestino = adyacencia.get(destino);
        
        if (!adyOrigen.contiene(destino)) {
            adyOrigen.agregar(destino);
        }
        if (!adyDestino.contiene(origen)) {
            adyDestino.agregar(origen);
        }
    }
    
    public ListaEnlazada<T> obtenerAdyacentes(T vertice) {
        return adyacencia.get(vertice);
    }
    
    public ListaEnlazada<T> obtenerVertices() {
        return vertices;
    }
    
    public boolean contieneVertice(T vertice) {
        return adyacencia.contieneClave(vertice);
    }
    
    public int getCantidadVertices() {
        return vertices.getTamaño();
    }
    
    public int getCantidadAristas() {
        int total = 0;
        for (int i = 0; i < vertices.getTamaño(); i++) {
            T v = vertices.obtener(i);
            total += adyacencia.get(v).getTamaño();
        }
        return total / 2; // No dirigido
    }

    public ListaEnlazada<T> bfs(T inicio) {
        ListaEnlazada<T> visitados = new ListaEnlazada<>();
        Cola<T> cola = new Cola<>();
        
        cola.encolar(inicio);
        visitados.agregar(inicio);
        
        while (!cola.estaVacia()) {
            T actual = cola.desencolar();
            ListaEnlazada<T> adyacentes = adyacencia.get(actual);
            
            for (int i = 0; i < adyacentes.getTamaño(); i++) {
                T ady = adyacentes.obtener(i);
                if (!visitados.contiene(ady)) {
                    visitados.agregar(ady);
                    cola.encolar(ady);
                }
            }
        }
        return visitados;
    }

    public ListaEnlazada<T> dfs(T inicio) {
        ListaEnlazada<T> visitados = new ListaEnlazada<>();
        dfsRecursivo(inicio, visitados);
        return visitados;
    }
    
    private void dfsRecursivo(T vertice, ListaEnlazada<T> visitados) {
        visitados.agregar(vertice);
        ListaEnlazada<T> adyacentes = adyacencia.get(vertice);
        
        for (int i = 0; i < adyacentes.getTamaño(); i++) {
            T ady = adyacentes.obtener(i);
            if (!visitados.contiene(ady)) {
                dfsRecursivo(ady, visitados);
            }
        }
    }

    public ListaEnlazada<T> caminoMasCorto(T origen, T destino) {
        if (!contieneVertice(origen) || !contieneVertice(destino)) {
            return new ListaEnlazada<>();
        }
        
        TablaHash<T, Integer> distancias = new TablaHash<>();
        TablaHash<T, T> anteriores = new TablaHash<>();
        ListaEnlazada<T> noVisitados = new ListaEnlazada<>();

        for (int i = 0; i < vertices.getTamaño(); i++) {
            T v = vertices.obtener(i);
            distancias.put(v, Integer.MAX_VALUE);
            noVisitados.agregar(v);
        }
        distancias.put(origen, 0);
        
        while (!noVisitados.estaVacia()) {
            T actual = obtenerMinimo(noVisitados, distancias);
            if (actual == null || actual.equals(destino)) break;
            
            noVisitados.eliminarElemento(actual);
            ListaEnlazada<T> adyacentes = adyacencia.get(actual);
            
            for (int i = 0; i < adyacentes.getTamaño(); i++) {
                T ady = adyacentes.obtener(i);
                if (noVisitados.contiene(ady)) {
                    int nuevaDist = distancias.get(actual) + 1;
                    if (nuevaDist < distancias.get(ady)) {
                        distancias.put(ady, nuevaDist);
                        anteriores.put(ady, actual);
                    }
                }
            }
        }
        
        return reconstruirCamino(anteriores, origen, destino);
    }
    
    private T obtenerMinimo(ListaEnlazada<T> noVisitados, TablaHash<T, Integer> dist) {
        if (noVisitados.estaVacia()) return null;
        
        T minimo = noVisitados.obtener(0);
        int minDist = dist.get(minimo);
        
        for (int i = 1; i < noVisitados.getTamaño(); i++) {
            T v = noVisitados.obtener(i);
            int d = dist.get(v);
            if (d < minDist) {
                minimo = v;
                minDist = d;
            }
        }
        return minimo;
    }
    
    private ListaEnlazada<T> reconstruirCamino(TablaHash<T, T> anteriores, T origen, T destino) {
        ListaEnlazada<T> camino = new ListaEnlazada<>();
        T actual = destino;
        
        while (actual != null && !actual.equals(origen)) {
            camino.agregar(actual);
            actual = anteriores.get(actual);
        }
        
        if (actual != null) {
            camino.agregar(origen);
        }

        ListaEnlazada<T> invertido = new ListaEnlazada<>();
        for (int i = camino.getTamaño() - 1; i >= 0; i--) {
            invertido.agregar(camino.obtener(i));
        }
        return invertido;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < vertices.getTamaño(); i++) {
            T v = vertices.obtener(i);
            sb.append(v).append(" -> ").append(adyacencia.get(v)).append("\n");
        }
        return sb.toString();
    }
}
