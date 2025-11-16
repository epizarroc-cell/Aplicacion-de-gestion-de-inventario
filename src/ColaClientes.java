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

    // Atender siguiente cliente (el de mayor prioridad)
    public Cliente atenderSiguiente() {
        if (!colaPrioridad3.isEmpty()) {
            return colaPrioridad3.poll();
        } else if (!colaPrioridad2.isEmpty()) {
            return colaPrioridad2.poll();
        } else if (!colaPrioridad1.isEmpty()) {
            return colaPrioridad1.poll();
        } else {
            System.out.println("❌ No hay clientes en la cola");
            return null;
        }
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
            System.out.println("⏭️  Próximo cliente Premium: " + colaPrioridad3.peek().getNombre());
        } else if (!colaPrioridad2.isEmpty()) {
            System.out.println("⏭️  Próximo cliente Afiliado: " + colaPrioridad2.peek().getNombre());
        } else if (!colaPrioridad1.isEmpty()) {
            System.out.println("⏭️  Próximo cliente Básico: " + colaPrioridad1.peek().getNombre());
        }
    }

    // Obtener tamaño total de la colas
    public int obtenerTamanoTotal() {
        return colaPrioridad1.size() + colaPrioridad2.size() + colaPrioridad3.size();
    }
}