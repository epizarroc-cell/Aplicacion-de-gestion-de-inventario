import java.util.LinkedList;
import java.util.Queue;

public class ColaClientes {
    private Queue<Cliente> colaPrioridad1;
    private Queue<Cliente> colaPrioridad2;
    private Queue<Cliente> colaPrioridad3;

    public ColaClientes() {
        colaPrioridad1 = new LinkedList<>();
        colaPrioridad2 = new LinkedList<>();
        colaPrioridad3 = new LinkedList<>();
    }

    // Agregar cliente a la cola según su prioridad
    public void encolar(Cliente cliente) {
        switch (cliente.getPrioridad()) {
            case 1:
                colaPrioridad1.offer(cliente);
                break;
            case 2:
                colaPrioridad2.offer(cliente);
                break;
            case 3:
                colaPrioridad3.offer(cliente);
                break;
            default:
                System.out.println("Prioridad no válida");
        }
        System.out.println("✅ Cliente " + cliente.getNombre() + " agregado a la cola (" + cliente.getTipoCliente() + ")");
    }

    // Verificar conectividad antes de atender
    public Cliente atenderSiguiente(Grafo grafo) {
        Cliente cliente = null;

        // Buscar cliente según prioridad
        if (!colaPrioridad3.isEmpty()) {
            cliente = colaPrioridad3.peek();
        } else if (!colaPrioridad2.isEmpty()) {
            cliente = colaPrioridad2.peek();
        } else if (!colaPrioridad1.isEmpty()) {
            cliente = colaPrioridad1.peek();
        }

        if (cliente == null) {
            System.out.println("❌ No hay clientes en la cola");
            return null;
        }

        // Verificar si la ubicación del cliente está conectada
        if (!grafo.estaConectado(cliente.getUbicacion())) {
            System.out.println("❌ No se puede atender al cliente " + cliente.getNombre() +
                    ". Su ubicación '" + cliente.getUbicacion() +
                    "' no está conectada a la red de entrega.");

            // Remover cliente de la cola (no se puede atender)
            if (!colaPrioridad3.isEmpty()) {
                colaPrioridad3.poll();
            } else if (!colaPrioridad2.isEmpty()) {
                colaPrioridad2.poll();
            } else if (!colaPrioridad1.isEmpty()) {
                colaPrioridad1.poll();
            }

            return null;
        }

        // Atender cliente normalmente
        if (!colaPrioridad3.isEmpty()) {
            return colaPrioridad3.poll();
        } else if (!colaPrioridad2.isEmpty()) {
            return colaPrioridad2.poll();
        } else if (!colaPrioridad1.isEmpty()) {
            return colaPrioridad1.poll();
        }

        return null;
    }

    // Verificar si la cola está vacía
    public boolean estaVacia() {
        return colaPrioridad1.isEmpty() && colaPrioridad2.isEmpty() && colaPrioridad3.isEmpty();
    }

    // Mostrar estado de la cola
    public void mostrarEstadoCola() {
        System.out.println("\n=== ESTADO DE LA COLA ===");
        System.out.println("🔴 Clientes Premium (Prioridad 3): " + colaPrioridad3.size());
        System.out.println("🟡 Clientes Afiliados (Prioridad 2): " + colaPrioridad2.size());
        System.out.println("🟢 Clientes Básicos (Prioridad 1): " + colaPrioridad1.size());
        System.out.println("📊 Total de clientes en espera: " + (colaPrioridad1.size() + colaPrioridad2.size() + colaPrioridad3.size()));

        // Mostrar próximos clientes a atender
        if (!colaPrioridad3.isEmpty()) {
            Cliente proximo = colaPrioridad3.peek();
            System.out.println("⏭️  Próximo cliente Premium: " + proximo.getNombre() + " (📍 " + proximo.getUbicacion() + ")");
        } else if (!colaPrioridad2.isEmpty()) {
            Cliente proximo = colaPrioridad2.peek();
            System.out.println("⏭️  Próximo cliente Afiliado: " + proximo.getNombre() + " (📍 " + proximo.getUbicacion() + ")");
        } else if (!colaPrioridad1.isEmpty()) {
            Cliente proximo = colaPrioridad1.peek();
            System.out.println("⏭️  Próximo cliente Básico: " + proximo.getNombre() + " (📍 " + proximo.getUbicacion() + ")");
        }
    }

    // Obtener tamaño total de la colas
    public int obtenerTamanoTotal() {
        return colaPrioridad1.size() + colaPrioridad2.size() + colaPrioridad3.size();
    }
}