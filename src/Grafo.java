import java.util.*;

public class Grafo {
    private final Map<String, List<Arista>> listaAdyacencia;

    public Grafo() {
        listaAdyacencia = new HashMap<>();
    }

    public void agregarVertice(String nuevaVertice) {
        listaAdyacencia.putIfAbsent(nuevaVertice, new ArrayList<>());
    }

    public void agregarArista(String origen, String nuevoDestino, int pesoArista) {
        // Crear vértices automáticamente si no existen
        listaAdyacencia.putIfAbsent(origen, new ArrayList<>());
        listaAdyacencia.putIfAbsent(nuevoDestino, new ArrayList<>());

        listaAdyacencia.get(origen).add(new Arista(nuevoDestino, pesoArista));
        listaAdyacencia.get(nuevoDestino).add(new Arista(origen, pesoArista)); // Grafo no dirigido
    }

    public boolean existeVertice(String vertice) {
        return listaAdyacencia.containsKey(vertice);
    }

    public void mostrarGrafo() {
        System.out.println("\n🗺️  GRAFO DE UBICACIONES:");
        System.out.println("═".repeat(50));

        if (listaAdyacencia.isEmpty()) {
            System.out.println("El grafo está vacío.");
            return;
        }

        for (Map.Entry<String, List<Arista>> entry : listaAdyacencia.entrySet()) {
            System.out.print("📍 " + entry.getKey() + " → ");
            if (entry.getValue().isEmpty()) {
                System.out.println("(Sin conexiones)");
            } else {
                for (int i = 0; i < entry.getValue().size(); i++) {
                    Arista arista = entry.getValue().get(i);
                    System.out.print(arista.getDestino() + " (" + arista.getPeso() + " km)");
                    if (i < entry.getValue().size() - 1) {
                        System.out.print(", ");
                    }
                }
                System.out.println();
            }
        }
        System.out.println("═".repeat(50));
    }

    public void algoritmoDijkstra(String inicio,
                                  Map<String, Integer> distancias,
                                  Map<String, String> predecesores) {
        PriorityQueue<Vertice> colaVertices = new PriorityQueue<>(Comparator.comparingInt(Vertice::getDistancia));

        for (String vertice : listaAdyacencia.keySet()) {
            distancias.put(vertice, Integer.MAX_VALUE);
            predecesores.put(vertice, null);
        }

        distancias.put(inicio, 0);
        colaVertices.add(new Vertice(inicio, 0));

        while (!colaVertices.isEmpty()) {
            Vertice v = colaVertices.poll();
            String verticeActual = v.getNombre();

            for (Arista arista : listaAdyacencia.get(verticeActual)) {
                String vecino = arista.getDestino();
                int nuevaDistancia = distancias.get(verticeActual) + arista.getPeso();

                if (nuevaDistancia < distancias.get(vecino)) {
                    distancias.put(vecino, nuevaDistancia);
                    predecesores.put(vecino, verticeActual);
                    colaVertices.add(new Vertice(vecino, nuevaDistancia));
                }
            }
        }
    }

    public List<String> reconstruirCamino(String inicio, String destino, Map<String, String> predecesores) {
        List<String> camino = new ArrayList<>();

        for (String verticeActual = destino; verticeActual != null; verticeActual = predecesores.get(verticeActual)) {
            camino.add(verticeActual);
        }

        Collections.reverse(camino);
        if (!camino.isEmpty() && camino.get(0).equals(inicio)) {
            return camino;
        }

        return new ArrayList<>();
    }

    public String calcularRutaOptima(String inicio, String destino) {
        if (!listaAdyacencia.containsKey(inicio) || !listaAdyacencia.containsKey(destino)) {
            return "❌ Una o ambas ubicaciones no existen en el grafo.";
        }

        Map<String, Integer> distancias = new HashMap<>();
        Map<String, String> predecesores = new HashMap<>();

        algoritmoDijkstra(inicio, distancias, predecesores);

        List<String> camino = reconstruirCamino(inicio, destino, predecesores);

        if (camino.isEmpty()) {
            return "❌ No existe camino entre '" + inicio + "' y '" + destino + "'";
        } else {
            StringBuilder resultado = new StringBuilder();
            resultado.append("\n✅ RUTA ÓPTIMA ENCONTRADA:\n");
            resultado.append("═".repeat(40)).append("\n");
            resultado.append("📍 Origen: ").append(inicio).append("\n");
            resultado.append("📍 Destino: ").append(destino).append("\n");
            resultado.append("📏 Distancia total: ").append(distancias.get(destino)).append(" km\n");
            resultado.append("🛣️  Camino: ");

            for (int i = 0; i < camino.size(); i++) {
                resultado.append(camino.get(i));
                if (i < camino.size() - 1) {
                    resultado.append(" → ");
                }
            }

            // Calcular tiempo estimado (asumiendo 60 km/h)
            int tiempoEstimado = distancias.get(destino) / 60;
            if (tiempoEstimado == 0) tiempoEstimado = 1;
            resultado.append("\n⏱️  Tiempo estimado: ").append(tiempoEstimado).append(" horas\n");
            resultado.append("═".repeat(40));

            return resultado.toString();
        }
    }

    public int getNumeroVertices() {
        return listaAdyacencia.size();
    }

    public int getNumeroAristas() {
        int contador = 0;
        for (List<Arista> aristas : listaAdyacencia.values()) {
            contador += aristas.size();
        }
        return contador / 2; // Grafo no dirigido
    }

    public boolean estaConectado(String ubicacionCliente) {
        if (!listaAdyacencia.containsKey(ubicacionCliente) || !listaAdyacencia.containsKey("San José")) {
            return false;
        }

        // Usar BFS para verificar conectividad
        Set<String> visitados = new HashSet<>();
        Queue<String> cola = new LinkedList<>();

        cola.add("San José");
        visitados.add("San José");

        while (!cola.isEmpty()) {
            String actual = cola.poll();

            if (actual.equals(ubicacionCliente)) {
                return true;
            }

            for (Arista arista : listaAdyacencia.get(actual)) {
                String vecino = arista.getDestino();
                if (!visitados.contains(vecino)) {
                    visitados.add(vecino);
                    cola.add(vecino);
                }
            }
        }

        return false;
    }
}